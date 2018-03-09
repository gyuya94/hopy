package com.sphinx.hopy.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sphinx.hopy.order.dao.OrderDao;
import com.sphinx.hopy.order.dao.OrderItemDao;
import com.sphinx.hopy.order.domain.Order;
import com.sphinx.hopy.order.domain.OrderItem;
import com.sphinx.hopy.order.service.OrderService;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.exception.NonExistProductException;
import com.sphinx.hopy.product.service.ProductService;
import com.sphinx.hopy.util.idGenerator.IdGenerator;
import com.sphinx.hopy.util.idGenerator.service.IdGeneratorService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private IdGeneratorService idGeneratorService;

	private IdGenerator orderItemIdGenerator;
	private IdGenerator orderIdGenerator;

	@Override
	public void addOrder(Order order) {
		if (order.getOrderId() == null) {
			String usableId = idGeneratorService.getUsableId(orderIdGenerator);
			order.setOrderId(usableId);
		}
		// 일단 order만 넣음
		orderDao.addOrder(order);
		// 그 주문에 해당하는 orderItem도 넣어줘야해..
		for (OrderItem orderItem : order.getOrderItemList()) {
			orderItemDao.addOrderItem(orderItem, order.getOrderId());
		}
	}

	@Override
	public void updateOrder(Order order) throws NullPointerException {
		// controller 단에서 에러 잡기
		orderDao.updateOrder(order);

	}

	@Override
	public void deleteOrder(Order order) {
		order.setDel(true);
		orderDao.updateOrder(order);

	}

	@Override
	public Order getOrderByOrderId(String orderId) {
		Order order = orderDao.getOrderByOrderId(orderId);
		return order;
	}

	@Override
	public List<Order> getOrderByCustomerId(Date startDate, Date endDate,
			String customerId, int start, int amount) {

		List<Order> orderList = orderDao.getOrderByCustomerId(startDate,
				endDate, customerId, start, amount);
		for (Order order : orderList) {
			String orderId = order.getOrderId();
			List<OrderItem> orderItemList = orderItemDao
					.getOrderItemListByOrderId(orderId);
			for (OrderItem orderItem : orderItemList) {
				String productId = orderItem.getProduct().getProductId();
				try {
					Product product = productService
							.getProductByProductId(productId);
					orderItem.setProduct(product);
				} catch (NonExistProductException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			order.setOrderItemList(orderItemList);
		}

		return orderList;
	}

	@Override
	public List<Order> getOrderByDate(Date startDate, Date endDate, int start,
			int amount) {
		List<Order> orderList = orderDao.getOrderByDate(startDate, endDate,
				start, amount);

		for (Order order : orderList) {
			organizeOrder(order);
		}

		return orderList;
	}

	public List<Order> getOrderByProductId(String productId, int start,
			int amount) {
		List<Order> orderList = orderDao.getOrderByProductId(productId, start,
				amount);

		for (Order order : orderList) {
			organizeOrder(order);
		}

		return orderList;
	}

	public int countOrderByProductId(String productId) {
		return orderDao.countOrderByProductId(productId);
	}

	public int countOrderByDate(Date startDate, Date endDate) {
		return orderDao.countOrderByDate(startDate, endDate);
	}

	@Override
	public void addOrderItem(OrderItem orderItem, String orderId) {
		orderItemDao.addOrderItem(orderItem, orderId);

	}

	@Override
	public void updateOrderItem(OrderItem orderItem)
			throws NullPointerException {
		// controller 단에서 에러 잡기
		orderItemDao.updateOrderItem(orderItem);

	}

	@Override
	public void deleteOrderItem(OrderItem orderItem) {
		orderItem.setDel(true);
		orderItemDao.updateOrderItem(orderItem);

	}

	@Override
	public OrderItem getOrderItemByOrderItemId(String orderItemId) {
		OrderItem orderItem = orderItemDao
				.getOrderItemByOrderItemId(orderItemId);
		return orderItem;
	}

	@Override
	public List<OrderItem> getOrderItemListByOrderId(String orderId) {
		List<OrderItem> orderItemList = orderItemDao
				.getOrderItemListByOrderId(orderId);
		return orderItemList;
	}

	@Override
	public String getNewOrderItemId() {
		String usableId = idGeneratorService.getUsableId(orderItemIdGenerator);
		return usableId;
	}

	public void init() {
		orderItemIdGenerator = new IdGenerator();
		orderItemIdGenerator.setPrefix("OI_");
		orderItemIdGenerator.setIdLength(10);
		orderItemIdGenerator.setTableName("orderItemIdTable");

		orderIdGenerator = new IdGenerator();
		orderIdGenerator.setPrefix("O_");
		orderIdGenerator.setIdLength(10);
		orderIdGenerator.setTableName("orderIdTable");
	}

	@Override
	public void organizeOrder(Order order) {
		// order만 들어가있넹..
		// orderItem도 넣어줘야해..
		// 그럼 product도 넣어줘야해..

		List<OrderItem> orderItemList = orderItemDao
				.getOrderItemListByOrderId(order.getOrderId());
		// orderItemList안의 orderItem의 product도 찾아넣어줘야합니다..
		// 너모 귀찮아오
		for (OrderItem orderItem : orderItemList) {
			try {
				Product product = productService.getProductByProductId(
						orderItem.getProduct().getProductId());
				orderItem.setProduct(product);
			} catch (NonExistProductException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ㅎㅎorderItem들 채운애로 바꾸는걸 깜빢했네
		order.setOrderItemList(orderItemList);
	}

	@Override
	public int countOrderByCustomerId(String customerId) {
		int count = orderDao.countOrderByCustomerId(customerId);
		return count;
	}
}
