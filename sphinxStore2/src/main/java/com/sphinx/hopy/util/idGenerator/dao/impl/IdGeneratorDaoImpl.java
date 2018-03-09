package com.sphinx.hopy.util.idGenerator.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sphinx.hopy.util.idGenerator.dao.IdGeneratorDao;


@Repository
public class IdGeneratorDaoImpl implements IdGeneratorDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getLastId(String tableName) {
		//지금 마지막을 찾는걸 일단 MAX(id)로 합시당
		//나중에 occupancy로 반납하고 그러면 좀 변경이 있어야겠지만
		//그냥 생산해내봐요
		
		//String sql = "SELECT Id, occupancy FROM " + tableName;
		String sql = "SELECT MAX(Id) FROM " + tableName;
		
		//id로 찾으니 1건만 나오겠죠? 그럼 object야
		String lastId = jdbcTemplate.queryForObject(sql, new RowMapper<String>() {// Supplier로 RowMapper 만듦
			//RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
			@Override
			public String mapRow(ResultSet rs, int arg1)//뒤에 int는 rowNum이래
					throws SQLException {
				//rooping도는거라 생각하면돼
				String lastId = rs.getString(1);
				return lastId;//얘를 리턴하는구나
			}
		});	//?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		//맨위에 Supplier supplier를 받으면 되는거야! 조금 어렵당
		return lastId;
	}
	
	public String getUsableMinId(String tableName) {
		
		//String sql = "SELECT Id, occupancy FROM " + tableName;
		String sql = "SELECT MIN(Id) FROM " + tableName 
				+ " WHERE occupancy = false";
		
		//id로 찾으니 1건만 나오겠죠? 그럼 object야
		String lastId = jdbcTemplate.queryForObject(sql, new RowMapper<String>() {// Supplier로 RowMapper 만듦
			//RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
			@Override
			public String mapRow(ResultSet rs, int arg1)//뒤에 int는 rowNum이래
					throws SQLException {
				//rooping도는거라 생각하면돼
				String lastId = rs.getString(1);
				return lastId;//얘를 리턴하는구나
			}
		});	//?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		//맨위에 Supplier supplier를 받으면 되는거야! 조금 어렵당
		return lastId;
	}

	@Override
	public void insertId(String id, String tableName) {
		String sql = "INSERT INTO " + tableName + "(Id, occupancy) "
				+ "VALUES(?, false)";
		// excute.update였는데
		jdbcTemplate.update(sql, id);
		//값도 set해주던걸 저렇게 그냥넣는대!
	}

	@Override
	public void updateId(String id, boolean occupancy, String tableName) {
		String sql = "UPDATE " + tableName + " SET occupancy=? "
				+ "WHERE id = ?";

		jdbcTemplate.update(sql, occupancy, id);

	}

}
