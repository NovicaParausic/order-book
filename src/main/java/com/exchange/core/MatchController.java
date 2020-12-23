package com.exchange.core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exchange.core2.core.ExchangeApi;
import exchange.core2.core.common.CoreSymbolSpecification;
import exchange.core2.core.common.Order;
import exchange.core2.core.common.OrderAction;
import exchange.core2.core.common.OrderType;
import exchange.core2.core.common.SymbolType;
import exchange.core2.core.common.api.ApiAddUser;
import exchange.core2.core.common.api.ApiAdjustUserBalance;
import exchange.core2.core.common.api.ApiPlaceOrder;
import exchange.core2.core.common.api.binary.BatchAddSymbolsCommand;
import exchange.core2.core.common.api.reports.SingleUserReportQuery;
import exchange.core2.core.common.api.reports.SingleUserReportResult;
import exchange.core2.core.common.api.reports.TotalCurrencyBalanceReportQuery;
import exchange.core2.core.common.api.reports.TotalCurrencyBalanceReportResult;

/*
 * This is test controller which utilizes exchange core library for order matching
 * https://github.com/mzheravin/exchange-core
 */
@RestController
@RequestMapping(value = "/match")
public class MatchController {

	@Autowired
	ExchangeApi api;

	final int currencyCodeXbt = 11;
	final int currencyCodeLtc = 15;

	// symbol constants
	final int symbolXbtLtc = 241;

	@PostConstruct
	public void createNewSymbol() {

		// create symbol specification and publish it
		CoreSymbolSpecification symbolSpecXbtLtc = CoreSymbolSpecification.builder().symbolId(symbolXbtLtc) // symbol id
				.type(SymbolType.CURRENCY_EXCHANGE_PAIR).baseCurrency(currencyCodeXbt) // base = satoshi (1E-8)
				.quoteCurrency(currencyCodeLtc) // quote = litoshi (1E-8)
				.baseScaleK(1_000_000L) // 1 lot = 1M satoshi (0.01 BTC)
				.quoteScaleK(10_000L) // 1 price step = 10K litoshi
				.takerFee(1900L) // taker fee 1900 litoshi per 1 lot
				.makerFee(700L) // maker fee 700 litoshi per 1 lot
				.build();

		api.submitBinaryDataAsync(new BatchAddSymbolsCommand(symbolSpecXbtLtc));

		createTwoUser();
	}

	// @PostMapping(value = "/twoUsers")
	public void createTwoUser() {

		// create user uid=301
		api.submitCommandAsync(ApiAddUser.builder().uid(301L).build());

		// first user deposits 20 LTC
		api.submitCommandAsync(ApiAdjustUserBalance.builder().uid(301L).currency(currencyCodeLtc).amount(2_000_000_000L)
				.transactionId(1L).build());

		// create user uid=302
		api.submitCommandAsync(ApiAddUser.builder().uid(302L).build());

		// second user deposits 0.10 BTC
		api.submitCommandAsync(ApiAdjustUserBalance.builder().uid(302L).currency(currencyCodeXbt).amount(10_000_000L)
				.transactionId(2L).build());
	}

	@PostMapping(value = "/user")
	public void createNewUser() {// @RequestBody Long uid) {

		// create user uid=301
		api.submitCommandAsync(ApiAddUser.builder().uid(301L).build());
		// second user deposits 0.10 BTC
		api.submitCommandAsync(ApiAdjustUserBalance.builder().uid(301L).currency(currencyCodeLtc).amount(10_000_000L)
				.transactionId(401L).build());

		// create user uid=302
		api.submitCommandAsync(ApiAddUser.builder().uid(302L).build());
		// second user deposits 0.10 BTC
		api.submitCommandAsync(ApiAdjustUserBalance.builder().uid(302L).currency(currencyCodeXbt).amount(10_000_000L)
				.transactionId(402L).build());

	}


	@PostMapping(value = "/orders")
	public void placeOrders() {

		procesGtc(301L, 501L, 2_030L, 10L, OrderAction.BID, OrderType.GTC);

		procesGtc(302L, 502L, 2_025L, 10L, OrderAction.BID, OrderType.GTC);
	}

	@PostMapping(value = "/twoOrders")
	public void placeTwoOrders() {

		// first user places Good-till-Cancel Bid order
		// he assumes BTCLTC exchange rate 154 LTC for 1 BTC
		// bid price for 1 lot (0.01BTC) is 1.54 LTC => 1_5400_0000 litoshi => 10K *
		// 15_400 (in price steps)
		api.submitCommandAsync(ApiPlaceOrder.builder()
								.uid(301L)
								.orderId(5001L)
								.price(15_400L)
								.reservePrice(15_600L) // can move bid order up to the 1.56 LTC, without replacing it
								.size(12L) // order size is 12 lots
								.action(OrderAction.BID).orderType(OrderType.GTC) // Good-till-Cancel
								.symbol(symbolXbtLtc).build());

		// second user places Immediate-or-Cancel Ask (Sell) order
		// he assumes worst rate to sell 152.5 LTC for 1 BTC
		api.submitCommandAsync(ApiPlaceOrder.builder().uid(302L).orderId(5002L).price(15_250L).size(10L) // order size
																											// is 10
																											// lots
				.action(OrderAction.ASK).orderType(OrderType.IOC) // Immediate-or-Cancel
				.symbol(symbolXbtLtc).build());
	}

	@PostMapping(value = "/order")
	public void placeOrder(@RequestBody Order order) {

		if (order.getAction().equals(OrderAction.ASK)) {
			procesGtc(order.getUid(), order.getOrderId(), order.getPrice(), order.getSize(), order.getAction(),
					OrderType.IOC);
		} else {
			procesIoc(order.getUid(), order.getOrderId(), order.getPrice(), order.getSize(), order.getAction(),
					OrderType.IOC);
		}

	}

	private void procesGtc(Long uid, Long orderId, Long price, Long size, OrderAction oAction, OrderType oType) {

		api.submitCommandAsync(
				ApiPlaceOrder.builder()
								.uid(uid)
								.orderId(orderId)
								.price(price)
								.reservePrice(price + 20L)
								.size(size) // order size is 10 lots
								.action(OrderAction.BID).orderType(OrderType.GTC) // Immediate-or-Cancel
								.symbol(symbolXbtLtc).build());
	}

	private void procesIoc(Long uid, Long orderId, Long price, Long size, OrderAction oAction, OrderType oType) {

		api.submitCommandAsync(
				ApiPlaceOrder.builder()
								.uid(uid)
								.orderId(orderId)
								.price(price)
								.reservePrice(15_600L)
								.size(size) // order size is 10 lots 
								.action(OrderAction.ASK)
								.orderType(OrderType.IOC) // Immediate-or-Cancel
								.symbol(symbolXbtLtc).build());
	}

	@PostMapping(value = "/book")
	public void requestOrderBook() {

		api.requestOrderBookAsync(symbolXbtLtc, 10);
	}

	@PostMapping(value = "/userBalance")
	public void checkUserBalance() throws ExecutionException, InterruptedException {

		Future<SingleUserReportResult> report = api.processReport(new SingleUserReportQuery(301), 0);
		System.out.println("User balance: " + report.get().toString());// getFees().get(currencyCodeLtc));
	}

	@PostMapping(value = "/systemBalance")
	public void checkSystemBalance() throws ExecutionException, InterruptedException {

		// check fees collected
		Future<TotalCurrencyBalanceReportResult> totalsReport = api.processReport(new TotalCurrencyBalanceReportQuery(),
				0);
		System.out.println("LTC fees collected: " + totalsReport.get().getFees().get(currencyCodeLtc));
	}
}
