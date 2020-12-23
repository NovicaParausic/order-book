package com.exchange.ordermatch.model;

import exchange.core2.core.common.IOrder;
import exchange.core2.core.common.OrderAction;
import exchange.core2.core.common.OrderType;
import exchange.core2.core.common.cmd.CommandResultCode;
import exchange.core2.core.common.cmd.OrderCommand;
import exchange.core2.core.common.cmd.OrderCommandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCommandBla implements IOrder {

	public OrderCommandType command;

	@Getter
	public long orderId;

	public int symbol;

	@Getter
	public long price;

	@Getter
	public long size;

	@Getter
	// new orders INPUT - reserved price for fast moves of GTC bid orders in
	// exchange mode
	public long reserveBidPrice;

	// required for PLACE_ORDER only;
	// for CANCEL/MOVE contains original order action (filled by orderbook)
	@Getter
	public OrderAction action;

	public OrderType orderType;

	@Getter
	public long uid;

	@Getter
	public long timestamp;

	// result code of command execution - can also be used for saving intermediate state
    public CommandResultCode resultCode;
	
    public static OrderCommand newOrder(OrderType orderType, long orderId, long uid, long price, long reserveBidPrice, long size, OrderAction action) {
        OrderCommand cmd = new OrderCommand();
        cmd.command = OrderCommandType.PLACE_ORDER;
        cmd.orderId = orderId;
        cmd.uid = uid;
        cmd.price = price;
        cmd.reserveBidPrice = reserveBidPrice;
        cmd.size = size;
        cmd.action = action;
        cmd.orderType = orderType;
        cmd.resultCode = CommandResultCode.VALID_FOR_MATCHING_ENGINE;
        return cmd;
    }
    
	public static OrderCommand cancel(long orderId, long uid) {
		OrderCommand cmd = new OrderCommand();
		cmd.command = OrderCommandType.CANCEL_ORDER;
		cmd.orderId = orderId;
		cmd.uid = uid;
		cmd.resultCode = CommandResultCode.VALID_FOR_MATCHING_ENGINE;
		return cmd;
	}
    
    @Override
	public int stateHash() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getFilled() {
		// TODO Auto-generated method stub
		return 0;
	}

}
