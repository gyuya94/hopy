package com.sphinx.hopy.order.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sphinx.hopy.order.dao.OrderItemDao;
import com.sphinx.hopy.order.domain.Order;
import com.sphinx.hopy.order.domain.OrderItem;
import com.sphinx.hopy.product.domain.Product;

@Repository("orderItemDao")
public class OrderItemDaoImpl implements OrderItemDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addOrderItem(OrderItem orderItem, String orderId) {
		String sql = "Insert into orderItem(orderItemId, quantity, productId, orderId)"
				+ " values(?,?,?,?)";
		//String productId = orderItem.getProduct().getProductId();
		//productId로 그냥 넣어주고 있었는데 이제 교체 할게요
		jdbcTemplate.update(sql, new Object[] { orderItem.getOrderItemId(),
				orderItem.getQuantity(), orderItem.getProduct().getProductId(), orderId });

	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		String sql = "UPDATE orderItem SET quantity = ?, del = ? "
				+ "where orderItemId = ?";
		int jdbcCount = jdbcTemplate.update(sql,
				new Object[] { orderItem.getQuantity(), orderItem.isDel(),
						orderItem.getOrderItemId() });
		if (jdbcCount == 0) {
			throw new NullPointerException();
		}
	}

	@Override
	public OrderItem getOrderItemByOrderItemId(String orderItemId) {
		String sql = "select orderItemId, quantity, productId, orderId "
				+ "from orderItem where del=false and orderItemid=?";
		OrderItem orderItem = jdbcTemplate.queryForObject(sql,
				new RowMapper<OrderItem>() {
					@Override
					public OrderItem mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						OrderItem orderItem = new OrderItem();
						orderItem.setOrderItemId(rs.getString(1));
						orderItem.setQuantity(rs.getInt(2));
						Product product = new Product();
						product.setProductId(rs.getString(3));
						orderItem.setProduct(product);

						return orderItem;
					}
				}, orderItemId);
		return orderItem;
	}

	@Override
	public List<OrderItem> getOrderItemListByOrderId(String orderId) {
		String sql = "select orderItemId, quantity, productId, orderId "
				+ "from orderItem where del=false and orderId=?";
		List<OrderItem> orderItemList = jdbcTemplate.query(sql,
				new RowMapper<OrderItem>() {
					@Override
					public OrderItem mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						OrderItem orderItem = new OrderItem();
						orderItem.setOrderItemId(rs.getString(1));
						orderItem.setQuantity(rs.getInt(2));
						Product product = new Product();
						product.setProductId(rs.getString(3));
						orderItem.setProduct(product);

						return orderItem;
					}
				}, orderId);
		return orderItemList;
	}

}
