package com.sphinx.hopy.order.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
	private Map<String, OrderItem> orderItemMap = new HashMap<>();

	public Map<String, OrderItem> getOrderItemMap() {
		return orderItemMap;
	}

	public void setOrderItemMap(Map<String, OrderItem> orderItemMap) {
		this.orderItemMap = orderItemMap;
	}

	public void addOrderItem(OrderItem orderItem) {
		System.out.println("orderItem ID ? " + orderItem.getOrderItemId());
		orderItemMap.put(orderItem.getOrderItemId(), orderItem);
	};

	@Override
	public String toString() {
		return "Cart [orderItemMap=" + orderItemMap + "]";
	}

	public void updateOrderItem(OrderItem orderItem) {
		String orderItemId = orderItem.getOrderItemId();
		orderItemMap.put(orderItemId, orderItem);
	};

	public void removeOrderItem(OrderItem orderItem) {
		String orderItemId = orderItem.getOrderItemId();
		orderItemMap.remove(orderItemId);
	};

	public double getTotalPrice() {
		Set<String> keySet = orderItemMap.keySet();
		double totalPrice = 0;
		double totalPriceAdd = 0;
		for (String key : keySet) {
			double price = orderItemMap.get(key).getProduct().getPrice();
			int quantity = orderItemMap.get(key).getQuantity();
			totalPrice = price * quantity;
			totalPriceAdd += totalPrice;
		}
		System.out.println("getTotalPrice : "+ totalPriceAdd);
		return totalPriceAdd;
	};
}
