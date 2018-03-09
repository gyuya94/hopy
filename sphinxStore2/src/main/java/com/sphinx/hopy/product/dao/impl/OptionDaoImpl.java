package com.sphinx.hopy.product.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sphinx.hopy.product.dao.OptionDao;
import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.domain.Supplier;
import com.sphinx.hopy.product.exception.NonExistOptionException;
import com.sphinx.hopy.product.exception.NonExistProductException;

@Repository
public class OptionDaoImpl implements OptionDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addOption(Option option, String productId) {
		// 먼저 option만 넣고
		String sql = "INSERT INTO HopyOption(optionId,productId,optionName,del) "
				+ "	VALUES (?, ?, ?, ?)";

		jdbcTemplate.update(sql, option.getOptionId(), productId,
				option.getOptionName(), option.isDel());

		// 그다음 optionValueTable도 채워줄게요
		String optionValueSql = "INSERT INTO OptionValue"
				+ "(optionId,OptionValue,addPrice,del) "
				+ "VALUES (?, ?, ?, false)";
		Set<String> optionValueSet = option.getOptionValueMap().keySet();
		for (String optionValue : optionValueSet) {
			jdbcTemplate.update(optionValueSql, option.getOptionId(),
					optionValue, option.getOptionValueMap().get(optionValue));
		}

	}

	@Override
	public void updateOption(Option option) {
		String sql = "UPDATE HopyOption " + "SET optionName=?,del=? "
				+ "WHERE optionId = ?";

		jdbcTemplate.update(sql, option.getOptionName(), option.isDel(),
				option.getOptionId());

		// 아래는 optionValueUpdate
		// 근데 update귀찮으니 그냥 삭제시켜버리고
		// 걍 새로넣읍시당ㅎㅎ

		// 지우고
		String deleteOptionValueSql = "UPDATE OptionValue " + "SET del=true "
				+ "WHERE optionId = ?";
		jdbcTemplate.update(deleteOptionValueSql, option.getOptionId());

		// 다시 새로넣음
		String addNewOptionValue = "INSERT INTO OptionValue"
				+ "(optionId,OptionValue,addPrice,del) "
				+ "VALUES (?, ?, ?, false)";
		Set<String> optionValueSet = option.getOptionValueMap().keySet();
		for (String optionValue : optionValueSet) {
			jdbcTemplate.update(addNewOptionValue, option.getOptionId(),
					optionValue, option.getOptionValueMap().get(optionValue));
		}
	}

	@Override
	public void deleteOption(Option option) {
		String sql = "UPDATE HopyOption " + "SET del=true"
				+ "WHERE optionId = ?";
		jdbcTemplate.update(sql, option.getOptionId());
		// option이랑 optionValue차례로 지워줍니당
		String deleteOptionValueSql = "UPDATE OptionValue " + "SET del=true "
				+ "WHERE optionId = ?";
		jdbcTemplate.update(deleteOptionValueSql, option.getOptionId());
	}

	@Override
	public Option getOptionById(String optionId)
			throws NonExistOptionException {
		String sql = "select optionId,productId,optionName,del "
				+ "FROM HopyOption " + "WHERE optionId=? AND del = false";

		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		Option option = null;
		try {
			option = jdbcTemplate.queryForObject(sql, new RowMapper<Option>() {
				// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
				@Override
				public Option mapRow(ResultSet rs, int arg1)// 뒤에 int는
															// rowNum이래
						throws SQLException {
					// rooping도는거라 생각하면돼
					Option option = new Option();
					option.setOptionId(rs.getString("optionId"));
					option.setOptionName(rs.getString("optionName"));
					option.setDel(rs.getBoolean("del"));

					return option;// 얘를 리턴하는구나
				}
			}, optionId); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
			/* 위에 친구는 그냥 optionTable에거만 채우는거고 */
			// 그냥 나눠서 써줄까요
			String optionValueSql = "select OptionValue,addPrice "
					+ "FROM OptionValue " + "WHERE optionId=? AND del = false";
			List<List<Object>> optionValueMapList = jdbcTemplate
					.query(optionValueSql, new RowMapper<List<Object>>() {
						// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
						@Override
						public List<Object> mapRow(ResultSet rs, int arg1)// 뒤에
																			// int는
																			// rowNum이래
								throws SQLException {
							// rooping도는거라 생각하면돼
							List<Object> keyAndValue = new ArrayList<>();
							keyAndValue.add(rs.getString("optionValue"));
							keyAndValue.add(rs.getDouble("addPrice"));
							return keyAndValue;// 얘를 리턴하는구나
						}
					}, optionId);
			// Map형태로 넣어주는걸 여기서 해야할까요
			for (List<Object> keyAndValue : optionValueMapList) {
				option.getOptionValueMap().put((String) keyAndValue.get(0),
						(Double) keyAndValue.get(1));
				// key와 value의 쌍으로 되있으니 그냥 넣어주긴하는데
				// 2차원 배열 생각하면됌
				// 근데 음음 좀 별로네
				// 0번지가 key고 1번지가 value라고 하고 걍 하드코딩으로 박아버린거라
			}
			// 솔직히 저 소스코드 너무 별로긴한데 음음
		} catch (Exception e) {
			throw new NonExistOptionException();
		}
		// 맨위에 Supplier supplier를 받으면 되는거야! 조금 어렵당

		return option;// 위에 바로 return써도 되는데 그럼 지금은 너무 어려웡
	}

	@Override
	public List<Option> getOptionListByProductId(String productId) throws NonExistOptionException {
		String sql = "SELECT optionId,productId,optionName,del "
				+ "FROM HopyOption " + "WHERE productId=?";

		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		List<Option> optionList = null;
		try {
			optionList = jdbcTemplate.query(sql, new RowMapper<Option>() {
				// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
				@Override
				public Option mapRow(ResultSet rs, int arg1)// 뒤에 int는
															// rowNum이래
						throws SQLException {
					// rooping도는거라 생각하면돼
					Option option = new Option();
					option.setOptionId(rs.getString("optionId"));
					option.setOptionName(rs.getString("optionName"));
					option.setDel(rs.getBoolean("del"));

					return option;// 얘를 리턴하는구나
				}
			}, productId); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
			/* 위에 친구는 그냥 optionTable에거만 채우는거고 */
			// 그냥 나눠서 써줄까요

			// option만 넣은거라 optionValue들도 채워줘야해요
			// 이 방식 넘 별로인거같아
			for (Option option : optionList) {
				String optionValueSql = "select OptionValue,addPrice "
						+ "FROM OptionValue "
						+ "WHERE optionId=? AND del = false";
				List<List<Object>> optionValueMapList = jdbcTemplate
						.query(optionValueSql, new RowMapper<List<Object>>() {
							// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
							@Override
							public List<Object> mapRow(ResultSet rs, int arg1)// 뒤에
																				// int는
																				// rowNum이래
									throws SQLException {
								// rooping도는거라 생각하면돼
								List<Object> keyAndValue = new ArrayList<>();
								keyAndValue.add(rs.getString("optionValue"));
								keyAndValue.add(rs.getDouble("addPrice"));
								return keyAndValue;// 얘를 리턴하는구나
							}
						}, option.getOptionId());
				// Map형태로 넣어주는걸 여기서 해야할까요
				for (List<Object> keyAndValue : optionValueMapList) {
					option.getOptionValueMap().put((String) keyAndValue.get(0),
							(Double) keyAndValue.get(1));
					// key와 value의 쌍으로 되있으니 그냥 넣어주긴하는데
					// 2차원 배열 생각하면됌
					// 근데 음음 좀 별로네
					// 0번지가 key고 1번지가 value라고 하고 걍 하드코딩으로 박아버린거라
				}
				// 솔직히 저 소스코드 너무 별로긴한데 음음
			}
		} catch (Exception e) {
			throw new NonExistOptionException();
		}

		return optionList;// 위에 바로 return써도 되는데 그럼 지금은 너무 어려웡
	}

}
