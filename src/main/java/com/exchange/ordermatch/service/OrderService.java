package com.exchange.ordermatch.service;

import java.util.List;

import com.exchange.ordermatch.dto.OrderDto;
import com.exchange.ordermatch.model.CurrencyPool;

import exchange.core2.core.common.IOrder;
import exchange.core2.core.common.OrderAction;
import exchange.core2.core.common.cmd.CommandResultCode;
import exchange.core2.core.common.cmd.OrderCommand;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Novica
 *
 *This abstract class is based on interface
 *import exchange.core2.core.orderbook.IOrderBook;
 *Some additional methods are added and some 
 *visibility modifiers are changed
 */

//@Slf4j
public abstract class OrderService {

	/**
	 * 
	 * @param cmd
	 */
	public abstract void newOrder(final OrderCommand cmd, long currencyId); //{
		
//		switch (cmd.orderType) {
//		case GTC:
//			newOrderPlaceGtc(cmd, currencyId);
//			break;
//		case IOC:
//			newOrderMatchIoc(cmd, currencyId);
//			break;
//		case FOK_BUDGET:
//			newOrderMatchFokBudget(cmd, currencyId);
//			break;
//		default:
//			log.warn("Unsupported order type: {}", cmd);
//		}
//	}

	/**
	 *  GTC(Good Till Canceled) describes a type of order which remains active
	 * until either the order is filled or canceled	 
	 * 
	 * @param cmd
	 */
	abstract protected void newOrderPlaceGtc(OrderCommand cmd, CurrencyPool currencyPool);

	 /**
	  * IOC, acronym for Immediate Or Cancel order, or accept order, 
	  * is an order type that must be execute immediately. In case the 
	  * entire order is not available at the moment for purchase 
	  * a partial fulfillment is possible but any portion of an IOC order 
	  * that cannot be filled immediately is canceled.
	  * 
	  * @param cmd
	  */
     
	abstract protected void newOrderMatchIoc(OrderCommand cmd, CurrencyPool currencyPool);
	
	 /**
	  * Fill Or Kill (FOK) is a conditional type of time in force 
	  * order that instruct brokerage a transaction immediately and
	  * completely or not at all.
	  * 
	  * @param cmd
	  */
     
	abstract protected void newOrderMatchFokBudget(OrderCommand cmd, CurrencyPool currencyPool);
	
	/**
	 * 
	 * @param orderCommand
	 * @return
	 */
	abstract public CommandResultCode cancelOrder(OrderCommand orderCommand, long currencyId);

	/**
	 * 
	 * @param orderCommand
	 * @return
	 */
	abstract public CommandResultCode reduceOrder(OrderCommand orderCommand, long currencyId);

	/**
	 * 
	 * @param cmd
	 * @return
	 */
	abstract public CommandResultCode moveOrder(OrderCommand cmd, long currencyId);

	/**
	 * 
	 * @param action
	 * @return
	 */
	abstract public int getOrdersNum(OrderAction action, long currencyId);

	/**
	 * 
	 */
	abstract List<OrderDto> getEntries(long currencyId);
	
	/**
	 * 
	 * @param action
	 * @return
	 */
	abstract public long getTotalOrdersVolume(OrderAction action, long currencyId);

	/**
	 * 
	 * @param orderId
	 * @return
	 */
	abstract public IOrder getOrderById(final long orderId, long currencyId);
}
