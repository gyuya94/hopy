package com.sphinx.hopy.product.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.derby.tools.sysinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sphinx.hopy.product.dao.SupplierDao;
import com.sphinx.hopy.product.domain.Supplier;

@Repository("supplierDao")
public class SupplierDaoImpl implements SupplierDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	// 얜 거기 설정파일에 해줬으니 autowired안해두 되나봐요

	public void addSupplier(Supplier supplier) {
		String sql = "INSERT INTO Supplier(supplierId, supplierName, phone, del) "
				+ "VALUES(?, ?, ?, false)";
		// excute.update였는데
		jdbcTemplate.update(sql, new Object[] { supplier.getSupplierId(),
				supplier.getSupplierName(), supplier.getPhone() });
		//값도 set해주던걸 저렇게 그냥넣는대!
	}
	
	public void deleteSupplier(String supplierId) {
		String sql = "UPDATE Supplier " + 
				"SET del=true " + 
				"WHERE supplierId = ?";

		jdbcTemplate.update(sql, supplierId);
	}
	
	public void updateSupplier(Supplier supplier) {
		String sql = "UPDATE Supplier " + 
				"SET supplierName=?,phone=? " + 
				"WHERE supplierId = ?";

		jdbcTemplate.update(sql,supplier.getSupplierName(), supplier.getPhone(),
				supplier.getSupplierId());
	}
	public Supplier findSupplierById(String supplierId) {
		String sql = "SELECT supplierId, supplierName, phone FROM Supplier "
				+ "WHERE supplierId = ? "
				+ "AND del=false";
		
		//id로 찾으니 1건만 나오겠죠? 그럼 object야
		Supplier supplier = jdbcTemplate.queryForObject(sql, new RowMapper<Supplier>() {// Supplier로 RowMapper 만듦
			//RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
			@Override
			public Supplier mapRow(ResultSet rs, int arg1)//뒤에 int는 rowNum이래
					throws SQLException {
				//rooping도는거라 생각하면돼
				Supplier supplier = new Supplier();
				supplier.setSupplierId(rs.getString("supplierId"));
				supplier.setSupplierName(rs.getString("supplierName"));
				supplier.setPhone(rs.getString("phone"));
				return supplier;//얘를 리턴하는구나
			}
		}, supplierId);	//?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		//맨위에 Supplier supplier를 받으면 되는거야! 조금 어렵당
		return supplier;//위에 바로 return써도 되는데 그럼 지금은 너무 어려웡
	}
	
	public List<Supplier> findSupplierByName(String supplierName) {
		String sql = "SELECT supplierId, supplierName, phone FROM Supplier "
				+ "WHERE supplierName = ? "
				+ "AND del=false";
		
		List<Supplier> supplierList = jdbcTemplate.query(sql, new RowMapper<Supplier>() {
			//RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
			@Override
			public Supplier mapRow(ResultSet rs, int arg1)//뒤에 int는 rowNum이래
					throws SQLException {
				//rooping도는거라 생각하면돼
				Supplier supplier = new Supplier();
				supplier.setSupplierId(rs.getString("supplierId"));
				supplier.setSupplierName(rs.getString("supplierName"));
				supplier.setPhone(rs.getString("phone"));
				return supplier;//얘를 리턴하는구나
			}
		}, supplierName);
		//맨위에 Supplier supplier를 받으면 되는거야! 조금 어렵당
		return supplierList;//위에 바로 return써도 되는데 그럼 지금은 너무 어려웡
	}

	@Override
	public List<Supplier> getSupplierListByPaging(int start, int amount){
		String sql = "SELECT supplierId, supplierName, phone " + 
				"FROM Supplier "
				+ "WHERE del=false "
				+ "order by supplierId " + 
				"OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
		
		List<Supplier> supplierList = jdbcTemplate.query(sql, new RowMapper<Supplier>() {
			//RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
			@Override
			public Supplier mapRow(ResultSet rs, int arg1)//뒤에 int는 rowNum이래
					throws SQLException {
				//rooping도는거라 생각하면돼
				Supplier supplier = new Supplier();
				supplier.setSupplierId(rs.getString("supplierId"));
				supplier.setSupplierName(rs.getString("supplierName"));
				supplier.setPhone(rs.getString("phone"));
				return supplier;//얘를 리턴하는구나
			}
		}, start, amount);
		//맨위에 Supplier supplier를 받으면 되는거야! 조금 어렵당
		return supplierList;//위에 바로 return써도 되는데 그럼 지금은 너무 어려웡
	}
	// 프로퍼티로 해줬으니 이거 있어야겠죠?
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int countSupplier() {
		String sql = "SELECT COUNT(*) FROM supplier "
				+ "WHERE del=false";

		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		int countSupplier = 0;
		countSupplier = jdbcTemplate.queryForObject(sql,
				new RowMapper<Integer>() {
					// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
					@Override
					public Integer mapRow(ResultSet rs, int arg1)// 뒤에 int는
																	// rowNum이래
							throws SQLException {
						int count = rs.getInt(1);
						return count;// 얘를 리턴하는구나
					}
				}); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		return countSupplier;
	}
	
	
}
