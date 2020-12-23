package com.exchange.ordermatch.service;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exchange.ordermatch.dao.OrderEntityDao;
import com.exchange.ordermatch.dto.OrderDto;
import com.exchange.ordermatch.model.CurrencyPool;
import com.exchange.ordermatch.service.LoggingConfiguration.LoggingLevel;

import exchange.core2.collections.art.LongAdaptiveRadixTreeMap;
import exchange.core2.collections.objpool.ObjectsPool;
import exchange.core2.core.common.CoreSymbolSpecification;
import exchange.core2.core.common.IOrder;
import exchange.core2.core.common.MatcherTradeEvent;
import exchange.core2.core.common.OrderAction;
import exchange.core2.core.common.OrderType;
import exchange.core2.core.common.SymbolType;
import exchange.core2.core.common.cmd.CommandResultCode;
import exchange.core2.core.common.cmd.OrderCommand;
import exchange.core2.core.common.cmd.OrderCommandType;
import exchange.core2.core.orderbook.OrderBookEventsHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Service
public class OrderServiceH2 extends OrderService {

	@Autowired
	private OrderEntityDao dao;

	@Override
	public void newOrder(OrderCommand cmd, long currencyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void newOrderPlaceGtc(OrderCommand cmd, CurrencyPool currencyPool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void newOrderMatchIoc(OrderCommand cmd, CurrencyPool currencyPool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void newOrderMatchFokBudget(OrderCommand cmd, CurrencyPool currencyPool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommandResultCode cancelOrder(OrderCommand orderCommand, long currencyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandResultCode reduceOrder(OrderCommand orderCommand, long currencyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandResultCode moveOrder(OrderCommand cmd, long currencyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOrdersNum(OrderAction action, long currencyId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	List<OrderDto> getEntries(long currencyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalOrdersVolume(OrderAction action, long currencyId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IOrder getOrderById(long orderId, long currencyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
