package com.sphinx.hopy.customer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sphinx.hopy.customer.dao.CustomerDao;
import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.customer.exception.NonExistCustomerException;
import com.sphinx.hopy.product.domain.Supplier;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addCustomer(Customer customer) {
		String sql = "insert into Customer"
				+ "(customerId,isAdmin,sphinxId,password,"
				+ "name,address,phone,del,joinDate)"
				+ " values(?,?,?,?,?,?,?,?,CURRENT_DATE)";

		jdbcTemplate.update(sql,
				new Object[] { customer.getCustomerId(), customer.getIsAdmin(),
						customer.getSphinxId(), customer.getPassword(),
						customer.getName(), customer.getAddress(),
						customer.getPhone(), customer.getDel() });
	}

	@Override
	public void updateCustomer(Customer customer) {
		String sql = "UPDATE Customer "
				+ "SET isAdmin=?,password=?,name=?,address=?,phone=?,del=? "
				+ "WHERE customerId = ?";

		jdbcTemplate.update(sql,customer.getIsAdmin(), customer.getPassword(),
						customer.getName(), customer.getAddress(),
						customer.getPhone(), customer.getDel() ,
				customer.getCustomerId());
	}

	@Override
	public void deleteCustomer(Customer customer) {
		String sql = "UPDATE Customer " + "SET del=true "
				+ "WHERE customerId = ?";

		jdbcTemplate.update(sql, customer.getCustomerId());
	}

	@Override
	public Customer getCustomerBySphinxId(String sphinxId) throws NonExistCustomerException {
		String sql = "SELECT customerId,sphinxId,isAdmin,password,name,address,"
				+ "phone,del,joinDate FROM Customer " + "WHERE sphinxId = ?";

		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		Customer customer = null;
		try {
			customer = jdbcTemplate.queryForObject(sql,
					new RowMapper<Customer>() {
						// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
						@Override
						public Customer mapRow(ResultSet rs, int arg1)// 뒤에 int는
																		// rowNum이래
								throws SQLException {
							// rooping도는거라 생각하면돼
							Customer customer = new Customer();
							customer.setCustomerId(rs.getString("customerId"));
							customer.setSphinxId(rs.getString("sphinxId"));
							customer.setIsAdmin(rs.getBoolean("isAdmin"));
							customer.setPassword(rs.getString("password"));
							customer.setName(rs.getString("name"));
							customer.setAddress(rs.getString("address"));
							customer.setPhone(rs.getString("phone"));
							customer.setDel(rs.getBoolean("del"));
							customer.setJoinDate(rs.getDate("joinDate"));
							return customer;// 얘를 리턴하는구나
						}
					}, sphinxId); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		} catch (Exception e) {
			throw new NonExistCustomerException();
		}
		// 맨위에 Supplier supplier를 받으면 되는거야! 조금 어렵당

		return customer;// 위에 바로 return써도 되는데 그럼 지금은 너무 어려웡
	}

	@Override
	public Customer getCustomerByCustomerId(String customerId) {
		String sql = "SELECT customerId,sphinxId,isAdmin,password,name,address,"
				+ "phone,del,joinDate FROM Customer " + "WHERE customerId = ?";

		Customer customer = jdbcTemplate.queryForObject(sql,
				new RowMapper<Customer>() {
					// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
					@Override
					public Customer mapRow(ResultSet rs, int arg1)// 뒤에 int는
																	// rowNum이래
							throws SQLException {
						// rooping도는거라 생각하면돼
						Customer customer = new Customer();
						customer.setCustomerId(rs.getString("customerId"));
						customer.setSphinxId(rs.getString("sphinxId"));
						customer.setIsAdmin(rs.getBoolean("isAdmin"));
						customer.setPassword(rs.getString("password"));
						customer.setName(rs.getString("name"));
						customer.setAddress(rs.getString("address"));
						customer.setPhone(rs.getString("phone"));
						customer.setDel(rs.getBoolean("del"));
						customer.setJoinDate(rs.getDate("joinDate"));
						return customer;// 얘를 리턴하는구나
					}
				}, customerId); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		// 맨위에 Supplier supplier를 받으면 되는거야! 조금 어렵당
		return customer;// 위에 바로 return써도 되는데 그럼 지금은 너무 어려웡
	}

	@Override
	public String getNextCustomerId() {
		String nextCustomerId = "";
		String sql = "select MAX(customerId) " + "FROM Customer";
		nextCustomerId = jdbcTemplate.queryForObject(sql,
				new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int arg1)
							throws SQLException {
						String maxCustomerId = rs.getString(1);
						return maxCustomerId;
					}

				});
		return nextCustomerId;
	}
}
