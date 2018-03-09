package com.sphinx.hopy.order.service;

import java.util.Date;
import java.util.List;

import com.sphinx.hopy.order.domain.Order;
import com.sphinx.hopy.order.domain.OrderItem;

public interface OrderService {
	public void addOrder(Order order);

	public void updateOrder(Order order);

	public void deleteOrder(Order order);

	public Order getOrderByOrderId(String orderId);

	public List<Order> getOrderByCustomerId( // 날짜 넣기
			Date startDate, Date endDate, String customerId, int start,
			int amount);

	public List<Order> getOrderByDate(Date startDate, Date endDate, int start,
			int amount);
	
	public List<Order> getOrderByProductId(String productId, int start, int amount);

	public int countOrderByProductId(String productId);
	
	// orderItemService
	public void addOrderItem(OrderItem orderItem, String orderId);

	public void updateOrderItem(OrderItem orderItem);

	public void deleteOrderItem(OrderItem orderItem);

	public OrderItem getOrderItemByOrderItemId(String orderItemId);

	public List<OrderItem> getOrderItemListByOrderId(String orderId);
	
	public String getNewOrderItemId();
	
	public int countOrderByDate(Date startDate, Date endDate);
	
	/** order객체 안의 요소들을 채워줄 메소드*/
	public void organizeOrder(Order order);

	public int countOrderByCustomerId(String customerId);
}
