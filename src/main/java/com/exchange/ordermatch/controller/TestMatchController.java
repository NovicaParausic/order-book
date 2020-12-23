package com.exchange.ordermatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.ordermatch.dto.OrderDto;
import com.exchange.ordermatch.service.OrderServiceOP;

import exchange.core2.core.common.OrderAction;
import exchange.core2.core.common.OrderType;
import exchange.core2.core.common.cmd.OrderCommand;

@RestController
@RequestMapping(value = "/service")
public class TestMatchController {

	@Autowired
	private OrderServiceOP orderService;
		
	
	
	private long idmin = 200L;
	private long idmax = 300L;
	private long uidmin = 400L;
	private long uidmax = 500L;
	
	// symbol constants
	final int symbolXbtLtc = 241;
	
	@PostMapping(value = "/bid")
	public String newBidOrder(@RequestBody OrderDto dto) {
		
		long price = dto.getPrice();
		OrderCommand oc = OrderCommand.builder()
				.uid(randomRange(uidmin, uidmax))
		        .orderId(randomRange(idmin, idmax))
		        .price(price)
		        .reserveBidPrice(price + 200L) // can move bid order up to the 1.56 LTC, without replacing it
		        .size(dto.getVolume()) // order size is 12 lots
		        .action(OrderAction.BID)
		        .orderType(OrderType.GTC) // Good-till-Cancel
		        .symbol(symbolXbtLtc)
		        .build();
		
		orderService.newOrder(oc, (long)symbolXbtLtc);
		
		return "vlabla";
	}
	
	@PostMapping(value = "/ask")
	public String newAskOrder(@RequestBody OrderDto dto) {
		
		long price = dto.getPrice();
		OrderCommand oc = OrderCommand.builder()
				.uid(randomRange(uidmin, uidmax))
		        .orderId(randomRange(idmin, idmax))
		        .price(price)
		        .reserveBidPrice(price + 200L) // can move bid order up to the 1.56 LTC, without replacing it
		        .size(dto.getVolume()) // order size is 12 lots
		        .action(OrderAction.ASK)
		        .orderType(OrderType.GTC) // Good-till-Cancel
		        .symbol(symbolXbtLtc)
		        .build();
		
		orderService.newOrder(oc, (long)symbolXbtLtc);
		
		return "vlabla";
	}
	
	@GetMapping
	public String newOrder() {
		
		OrderCommand oc = OrderCommand.builder()
				.uid(301L)
		        .orderId(5001L)
		        .price(15_400L)
		        .reserveBidPrice(15_600L) // can move bid order up to the 1.56 LTC, without replacing it
		        .size(12L) // order size is 12 lots
		        .action(OrderAction.BID)
		        .orderType(OrderType.GTC) // Good-till-Cancel
		        .symbol(symbolXbtLtc)
		        .build();
		
		orderService.newOrder(oc, (long)symbolXbtLtc);
		
		return "vlabla";
	}
	
	@GetMapping(value = "entries")
	public List<OrderDto> getEntries() {
		
		return orderService.getEntries((long)symbolXbtLtc);
	}
	
	private long randomRange(long min, long max) {
		return min + (long) (Math.random() * (max - min));
	}
}
