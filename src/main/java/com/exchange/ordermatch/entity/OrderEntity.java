package com.exchange.ordermatch.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import exchange.core2.core.common.OrderAction;
import exchange.core2.core.common.OrderType;
import exchange.core2.core.common.cmd.OrderCommandType;


@Entity
@Table(name = "ORDER_ENTITY")
//@AllArgsConstructor
public class OrderEntity {

	@Id
	//@Getter
	private long orderId;

	//@Getter
	private int symbol;

	//@Getter
	private long price;

	//@Getter
	private long size;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	//@Getter
	private long uid;

	@Enumerated(EnumType.STRING)
	private OrderCommandType command;
	// required for PLACE_ORDER only;
	// for CANCEL/MOVE contains original order action (filled by orderbook)
	//@Getter
	@Enumerated(EnumType.STRING)
	private OrderAction action;

	@Enumerated(EnumType.STRING)
	private OrderType orderType;

	public OrderEntity() {}
	
	public OrderEntity(long orderId, int symbol, long price, long size, Date createDate, long uid,
			OrderCommandType command, OrderAction action, OrderType orderType) {
		this.orderId = orderId;
		this.symbol = symbol;
		this.price = price;
		this.size = size;
		this.createDate = createDate;
		this.uid = uid;
		this.command = command;
		this.action = action;
		this.orderType = orderType;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getSymbol() {
		return symbol;
	}

	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public OrderCommandType getCommand() {
		return command;
	}

	public void setCommand(OrderCommandType command) {
		this.command = command;
	}

	public OrderAction getAction() {
		return action;
	}

	public void setAction(OrderAction action) {
		this.action = action;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
	
}
