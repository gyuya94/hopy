package com.sphinx.hopy.order.dao;

import java.util.Date;
import java.util.List;

import com.sphinx.hopy.order.domain.Order;

public interface OrderDao {
	public void addOrder(Order order);

	public void updateOrder(Order order);

	public Order getOrderByOrderId(String orderId);

	// 회원일때
	public List<Order> getOrderByCustomerId(Date startDate, Date endDate,
			String customerId, int start, int amount);

	// 관리자
	public List<Order> getOrderByDate(Date startDate, Date endDate, int start,
			int amount);
	
	public int countOrderByDate(Date startDate, Date endDate);
	
	public List<Order> getOrderByProductId(String productId, int start, int amount);
	
	public int countOrderByProductId(String productId);
	

	public int countOrderByCustomerId(String customerId);
}
