package com.sphinx.hopy.order.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sphinx.hopy.order.dao.OrderDao;
import com.sphinx.hopy.order.domain.Order;

@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addOrder(Order order) {
		String sql = "Insert into hopyorder(orderId, orderDate, state,"
				+ " totalprice, customerId, recipient, recipientAddr, memo)"
				+ " values(?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,
				new Object[] { order.getOrderId(), order.getOrderDate(),
						order.getState(), order.getTotalPrice(),
						order.getCustomerId(), order.getRecipient(),
						order.getRecipientAddr(), order.getMemo() });
	}

	@Override
	public void updateOrder(Order order) {
		String sql = "UPDATE HOPYORDER SET ORDERDATE=?, STATE=?, TOTALPRICE=?,"
				+ " recipient=?,recipientAddr=?,memo=?, del=? WHERE ORDERID=?";
		// ORDERID와 CUSTOMERID는 변경되지 않는다.
		// CUSTOMERID가 변경되는 거면 새로운 주문이니까 변경하지 않는다
		int jdbcCount = jdbcTemplate.update(sql,
				new Object[] {
						new java.sql.Date(order.getOrderDate().getTime()),
						order.getState(), order.getTotalPrice(),
						order.getRecipient(), order.getRecipientAddr(),
						order.getMemo(), order.isDel(), order.getOrderId() });
		if (jdbcCount == 0) {
			throw new NullPointerException();
		}
	}

	@Override
	public Order getOrderByOrderId(String orderId) {
		String sql = "select orderId, orderDate, state, totalprice, customerId, "
				+ "recipient, recipientAddr, memo from hopyorder where orderId = ? and del=false";
		Order order = jdbcTemplate.queryForObject(sql, new RowMapper<Order>() {

			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
				Order order = new Order();
				order.setOrderId(rs.getString("orderId"));
				order.setOrderDate(rs.getDate("orderDate"));
				// date가 될까? sql date랑 util date인거 같은데?
				order.setState(rs.getString("state"));
				order.setTotalPrice(rs.getDouble("totalprice"));
				order.setCustomerId(rs.getString("customerId"));
				order.setRecipient(rs.getString("recipient"));
				order.setRecipientAddr(rs.getString("recipientAddr"));
				order.setMemo(rs.getString("memo"));
				return order;
			}

		}, orderId);
		return order;
	}

	@Override
	public List<Order> getOrderByCustomerId(Date startDate, Date endDate,
			String customerId, int start, int amount) {
		String sql = "select orderId, orderDate,state,totalprice,"
				+ "customerId,recipient,recipientAddr,memo "
				+ "from hopyorder where customerId=? and orderDate>=? and orderDate<=? "
				+ "order by orderDate desc offset ? rows fetch next ? rows only";
		List<Order> orderList = jdbcTemplate.query(sql, new RowMapper<Order>() {
			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
				Order order = new Order();
				order.setOrderId(rs.getString(1));
				order.setOrderDate(rs.getDate(2));
				order.setState(rs.getString(3));
				order.setTotalPrice(rs.getDouble(4));
				order.setCustomerId(rs.getString(5));
				order.setRecipient(rs.getString(6));
				order.setRecipientAddr(rs.getString(7));
				order.setMemo(rs.getString(8));
				return order;
			}
		}, customerId, startDate, endDate, start, amount);
		return orderList;
	}

	@Override
	public List<Order> getOrderByDate(Date startDate, Date endDate, int start,
			int amount) {
		String sql = "select orderId, orderDate,state,totalprice,"
				+ "customerId,recipient,recipientAddr,memo "
				+ "from hopyorder where orderDate>=? and orderDate<=? "
				+ "order by orderDate desc offset ? rows fetch next ? rows only";
		List<Order> orderList = jdbcTemplate.query(sql, new RowMapper<Order>() {
			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
				Order order = new Order();
				order.setOrderId(rs.getString(1));
				order.setOrderDate(rs.getDate(2));
				order.setState(rs.getString(3));
				order.setTotalPrice(rs.getDouble(4));
				order.setCustomerId(rs.getString(5));
				order.setRecipient(rs.getString(6));
				order.setRecipientAddr(rs.getString(7));
				order.setMemo(rs.getString(8));
				return order;
			}
		}, startDate, endDate, start, amount);
		return orderList;
	}
	
	@Override
	public List<Order> getOrderByProductId(String productId, int start, int amount){
		String sql = "SELECT hopyorder.orderId, orderDate,state,totalprice, " + 
				"customerId,recipient,recipientAddr,memo " + 
				"FROM hopyorder WHERE hopyorder.orderId " + 
				"IN (SELECT orderId FROM orderItem " + 
				"	WHERE orderItemId IN " + 
				"	(SELECT orderItemId FROM orderItem WHERE productId = ?)) " + 
				"ORDER BY orderDate DESC offset ? ROWS fetch next ? rows only";
		List<Order> orderList = jdbcTemplate.query(sql, new RowMapper<Order>() {
			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
				Order order = new Order();
				order.setOrderId(rs.getString(1));
				order.setOrderDate(rs.getDate(2));
				order.setState(rs.getString(3));
				order.setTotalPrice(rs.getDouble(4));
				order.setCustomerId(rs.getString(5));
				order.setRecipient(rs.getString(6));
				order.setRecipientAddr(rs.getString(7));
				order.setMemo(rs.getString(8));
				return order;
			}
		}, productId, start, amount);
		return orderList;
	}
	
	@Override
	public int countOrderByProductId(String productId) {
		String sql = "SELECT COUNT(*) " + 
				"FROM hopyorder WHERE hopyorder.orderId " + 
				"IN (SELECT orderId FROM orderItem " + 
				"	WHERE orderItemId IN " + 
				"	(SELECT orderItemId FROM orderItem WHERE productId = ?))";
		
		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		int countOrder = 0;
		countOrder = jdbcTemplate.queryForObject(sql,
				new RowMapper<Integer>() {
					// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
					@Override
					public Integer mapRow(ResultSet rs, int arg1)// 뒤에 int는
																	// rowNum이래
							throws SQLException {
						int count = rs.getInt(1);
						return count;// 얘를 리턴하는구나
					}
				}, productId); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		return countOrder;
	}
	
	@Override
	public int countOrderByDate(Date startDate, Date endDate) {
		//
		String sql = "SELECT COUNT(*) FROM hopyorder "
				+ "WHERE orderDate>=? AND orderDate<=?";
		
		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		int countOrder = 0;
		countOrder = jdbcTemplate.queryForObject(sql,
				new RowMapper<Integer>() {
					// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
					@Override
					public Integer mapRow(ResultSet rs, int arg1)// 뒤에 int는
																	// rowNum이래
							throws SQLException {
						int count = rs.getInt(1);
						return count;// 얘를 리턴하는구나
					}
				}, startDate, endDate); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		return countOrder;
	}

	@Override
	public int countOrderByCustomerId(String customerId) {
		String sql = "SELECT count(*) as hopyorderCount FROM HOPYORDER WHERE customerId = ?";
		int count = jdbcTemplate.queryForObject(sql, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				int count = rs.getInt("hopyorderCount");
				return count;
			}
		}, customerId);
		return count;
	}

}
