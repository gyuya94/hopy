package com.sphinx.hopy.order.service;

import java.util.List;

import com.sphinx.hopy.order.domain.OrderItem;

public interface OrderItemService {
public void addOrderItem(OrderItem orderItem, String orderId);
	
	public void updateOrderItem(OrderItem orderItem);
	
	public void deleteOrderItem(OrderItem orderItem);
	
	public OrderItem getOrderItemByOrderItemId(String orderItemId);
	
	public List<OrderItem> getOrderItemListByOrderId(String orderId);
}
