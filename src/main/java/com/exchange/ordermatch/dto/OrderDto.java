package com.exchange.ordermatch.dto;

public class OrderDto {

	private long price;
	private long volume;
	private int numOrders;
	
	public OrderDto() {}
	
	public OrderDto(long price, long volume, int numOrders) {
		this.price = price;
		this.volume = volume;
		this.numOrders = numOrders;
	}
	
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public int getNumOrders() {
		return numOrders;
	}

	public void setNumOrders(int numOrders) {
		this.numOrders = numOrders;
	}	
}
