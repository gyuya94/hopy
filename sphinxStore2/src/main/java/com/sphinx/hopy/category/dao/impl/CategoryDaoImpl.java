package com.sphinx.hopy.category.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sphinx.hopy.category.dao.CategoryDao;
import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.product.domain.Supplier;

@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addCategory(Category category) {
		String sql = "INSERT INTO category(categoryId, categoryName, parentId, categoryGroup) "
				+ "VALUES(?, ?, ?, ?)";
		jdbcTemplate.update(sql,
				new Object[] { category.getCategoryId(),
						category.getCategoryName(), category.getParentId(),
						category.getGroup() });
	}

	@Override
	public void updateCategory(Category category) {
		String sql = "UPDATE category SET categoryName = ?, parentId = ?,"
				+ " del = ?, categoryGroup=? where categoryId = ?";

		int jdbc = jdbcTemplate.update(sql,
				new Object[] { category.getCategoryName(),
						category.getParentId(), category.isDel(),
						category.getGroup(), category.getCategoryId() });
		if (jdbc == 0) {
			throw new NullPointerException();
		}
	}

	@Override
	public Category getCategoryById(String categoryId) {
		String sql = "SELECT categoryId, categoryName, parentId, categoryGroup FROM category "
				+ "WHERE categoryId = ? AND del=false";
		Category category = jdbcTemplate.queryForObject(sql,
				new RowMapper<Category>() {

					@Override
					public Category mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Category category = new Category();

						category.setCategoryId(rs.getString("categoryId"));
						category.setCategoryName(rs.getString("categoryName"));
						category.setParentId(rs.getString("parentId"));
						category.setGroup(rs.getString("categoryGroup"));

						return category;
					}

				}, categoryId);

		System.out.println("dao ");
		System.out.println("categoryId " + categoryId);
		System.out.println("category " + category);
		return category;
	}

	@Override
	public List<Category> getCategoryByName(String categoryName) {
		String sql = "SELECT categoryId, categoryName, parentId, categoryGroup FROM category "
				+ "WHERE categoryName like (?) AND del=false";
		//키워드 검색으로 변경
		List<Category> categoryList = jdbcTemplate.query(sql,
				new RowMapper<Category>() {

					@Override
					public Category mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Category category = new Category();
						category.setCategoryId(rs.getString("categoryId"));
						category.setCategoryName(rs.getString("categoryName"));
						category.setParentId(rs.getString("parentId"));
						category.setGroup(rs.getString("categoryGroup"));
						return category;
					}

				}, "%" + categoryName + "%");
		return categoryList;
	}

	@Override
	public List<Category> getSubCategoryListByParentId(String parentId) {
		String sql = "SELECT categoryId, categoryName, parentId, categoryGroup FROM category "
				+ "WHERE parentId = ? AND del=false";
		List<Category> categoryList = jdbcTemplate.query(sql,
				new RowMapper<Category>() {

					@Override
					public Category mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Category category = new Category();
						category.setCategoryId(rs.getString("categoryId"));
						category.setCategoryName(rs.getString("categoryName"));
						category.setParentId(rs.getString("parentId"));
						category.setGroup(rs.getString("categoryGroup"));
						return category;
					}

				}, parentId);
		return categoryList;
	}

	@Override
	public List<Category> getRootCategoryList() {
		String sql = "SELECT categoryId, categoryName, parentId, categoryGroup FROM category "
				+ "WHERE parentId = '0' AND del=false";
		List<Category> categoryList = jdbcTemplate.query(sql,
				new RowMapper<Category>() {

					@Override
					public Category mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Category category = new Category();
						category.setCategoryId(rs.getString("categoryId"));
						category.setCategoryName(rs.getString("categoryName"));
						category.setParentId(rs.getString("parentId"));
						category.setGroup(rs.getString("categoryGroup"));
						return category;
					}

				});
		return categoryList;
	}

	@Override
	public List<Category> getAllCategoryList() {
		String sql = "SELECT categoryId, categoryName, parentId, categoryGroup FROM category "
				+ "WHERE del=false";
		List<Category> categoryList = jdbcTemplate.query(sql,
				new RowMapper<Category>() {

					@Override
					public Category mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Category category = new Category();
						category.setCategoryId(rs.getString("categoryId"));
						category.setCategoryName(rs.getString("categoryName"));
						category.setParentId(rs.getString("parentId"));
						category.setGroup(rs.getString("categoryGroup"));
						return category;
					}

				});
		return categoryList;
	}

	@Override
	public List<Category> getCategoryListByProductId(String productId) {
		String sql = "select c.categoryId, c.categoryName, c.parentId, "
				+ "c.categoryGroup from category c, categoryProductJoin j "
				+ "where c.categoryId = j.categoryId and j.productId=? "
				+ "and c.del=false";
		List<Category> categoryList = jdbcTemplate.query(sql,
				new RowMapper<Category>() {

					@Override
					public Category mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Category category = new Category();
						category.setCategoryId(rs.getString("categoryId"));
						category.setCategoryName(rs.getString("categoryName"));
						category.setParentId(rs.getString("parentId"));
						category.setGroup(rs.getString("categoryGroup"));
						return category;
					}

				}, productId);
		return categoryList;
	}

	@Override
	public List<Category> getCategoryListByCouponId(String couponId) {
		String sql = "select c.categoryId, c.categoryName, c.parentId, "
				+ "c.categoryGroup from category c, categoryCouponJoin cc "
				+ "where c.categoryId = j.categoryId and j.productId=? "
				+ "and c.del=false";
		List<Category> categoryList = jdbcTemplate.query(sql,
				new RowMapper<Category>() {

					@Override
					public Category mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Category category = new Category();
						category.setCategoryId(rs.getString("categoryId"));
						category.setCategoryName(rs.getString("categoryName"));
						category.setParentId(rs.getString("parentId"));
						category.setGroup(rs.getString("categoryGroup"));
						return category;
					}

				}, couponId);
		return categoryList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
