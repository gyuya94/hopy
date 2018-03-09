package com.sphinx.hopy.product.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.customer.exception.NonExistCustomerException;
import com.sphinx.hopy.product.dao.ProductDao;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.exception.NonExistProductException;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addProduct(Product product) {
		// 일단 product만 넣어주고요
		String sql = "INSERT INTO PRODUCT(productId,productName,price,"
				+ "regDate,content,fileName,comment) "
				+ "VALUES (?, ?,?,CURRENT_DATE,?, ?,?)";

		jdbcTemplate.update(sql,
				new Object[] { product.getProductId(), product.getProductName(),
						product.getPrice(), product.getContent(),
						product.getCombineFileName(), product.getComment() });

		// product와 category를 연결할 join테이블에 넣어줄게요
		String categoryProductJoinSql = "INSERT INTO categoryProductJoin(categoryId, productId) "
				+ "values(?, ?)";
		// 한 product는 여러개의 category를 가질수도 있으니까
		for (Category category : product.getCategoryList()) {
			// 혹시나 product가 categoryList==null인지 체크해야하나
			jdbcTemplate.update(categoryProductJoinSql,
					category.getCategoryId(), product.getProductId());
		}

		// 그리고 이제 product가 가지고 있던 List<Option>들을 넣어줄게요
		// String productOptionSql = "";
		// 아니네 option은 service를 갖고있으니
		// productService에서 optionService를 call해주면 되겠당

	}

	@Override
	public void updateProduct(Product product) {
		String sql = "UPDATE PRODUCT SET productName=?,content=?,fileName=?,"
				+ "comment=? WHERE productId = ?";

		jdbcTemplate.update(sql, product.getProductName(), product.getContent(),
				product.getCombineFileName(), product.getComment(),
				product.getProductId());
		// 위에는 product의 내용만 수정한거고
		// 이 product가 연결되는 category들도 바뀔수가 있는데
		// 걔는 categoryProductJoinTable에서 기존에거 그냥 지워버리고
		// 새로 추가해 줄게요

		// 일단 지우고
		String deleteCategoryProductJoinSql = "DELETE FROM categoryProductJoin "
				+ "WHERE productId = ?";
		jdbcTemplate.update(deleteCategoryProductJoinSql,
				product.getProductId());

		// categoryProductJoin에 다시 새롭게 넣어줘요
		String insertCategoryProductJoinSql = "INSERT INTO categoryProductJoin(categoryId, productId) "
				+ "values(?, ?)";
		// 한 product는 여러개의 category를 가질수도 있으니까
		for (Category category : product.getCategoryList()) {
			// 혹시나 product가 categoryList==null인지 체크해야하나
			jdbcTemplate.update(insertCategoryProductJoinSql,
					category.getCategoryId(), product.getProductId());
		}
		// option은 그냥 service단에서 optionService에 method를 call하면 될거에요
	}

	@Override
	public void deleteProduct(Product product) {
		String sql = "UPDATE PRODUCT SET del=true WHERE productId = ?";
		jdbcTemplate.update(sql, product.getProductId());
		// 음 진짜 삭제는 아니니까
		// product를 그냥 del=true로만 해두고
		// join도 그냥 놔둬도 될거같기두하고
	}

	@Override
	public List<Product> getProductListByCategoryId(int start, int amount,
			String categoryId) {
		// 움 페이징하는걸 따로 빼면 인자가 좀 달라지긴 하겠는데
		// 음음
		String sql = "select product.productId, productName, price,regDate,"
				+ "content,fileName,comment " + 
				"from product, categoryProductJoin " + 
				"where categoryid=?" + 
				"AND categoryProductJoin.productId = product.productId " + 
				"order by regDate desc offset ? rows fetch next ? rows only";
		List<Product> productList = jdbcTemplate.query(sql, new RowMapper<Product>() {
			
			@Override
			public Product mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Product product = new Product();
				product.setProductId(rs.getString(1));
				product.setProductName(rs.getString(2));
				product.setPrice(rs.getDouble(3));
				product.setRegDate(rs.getDate(4));
				product.setContent(rs.getString(5));
				product.setSpliteFileName(rs.getString(6));
				product.setComment(rs.getString(7));
				return product;
			}

		}, categoryId, start, amount);
		return productList;
	}
	
	@Override
	public List<Product> getProductListByCategoryGroup(
			int start, int amount, String group){
		// 움 페이징하는걸 따로 빼면 인자가 좀 달라지긴 하겠는데
				// 음음
				String sql = "select product.productId, productName, price,"
						+ "regDate,content,fileName,comment " + 
						"from product, categoryProductJoin " + 
						"where categoryid IN " + 
						"(select categoryId FROM category where categoryGroup=?) " + 
						"AND categoryProductJoin.productId = product.productId "
						+ "AND product.del=false " + 
						"order by regDate desc offset ? rows fetch next ? rows only";
				List<Product> productList = jdbcTemplate.query(sql, new RowMapper<Product>() {
					
					@Override
					public Product mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Product product = new Product();
						product.setProductId(rs.getString(1));
						product.setProductName(rs.getString(2));
						product.setPrice(rs.getDouble(3));
						product.setRegDate(rs.getDate(4));
						product.setContent(rs.getString(5));
						product.setSpliteFileName(rs.getString(6));
						product.setComment(rs.getString(7));
						return product;
					}

				}, group, start, amount);
				return productList;
	}

	@Override
	public Product getProductByProductId(String productId)
			throws NonExistProductException {
		String sql = "select productId,productName,price,regDate,content,fileName,comment "
				+ "FROM PRODUCT WHERE productId=?";

		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		Product product = null;
		try {
			product = jdbcTemplate.queryForObject(sql,
					new RowMapper<Product>() {
						// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
						@Override
						public Product mapRow(ResultSet rs, int arg1)// 뒤에 int는
																		// rowNum이래
								throws SQLException {
							// rooping도는거라 생각하면돼
							Product product = new Product();
							product.setProductId(rs.getString("productId"));
							product.setProductName(rs.getString("productName"));
							product.setPrice(rs.getDouble("price"));
							product.setRegDate(rs.getDate("regDate"));
							product.setContent(rs.getString("content"));
							product.setSpliteFileName(rs.getString("fileName"));
							product.setComment(rs.getString("comment"));
							return product;// 얘를 리턴하는구나
						}
					}, productId); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		} catch (Exception e) {
			throw new NonExistProductException();
		}
		// 맨위에 Supplier supplier를 받으면 되는거야! 조금 어렵당

		return product;// 위에 바로 return써도 되는데 그럼 지금은 너무 어려웡
	}

	@Override
	public int countProductByCategoryId(String categoryId) {
		String sql = "SELECT COUNT(productId) FROM categoryProductJoin "
				+ "WHERE categoryId=?";

		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		int countProduct = 0;
		countProduct = jdbcTemplate.queryForObject(sql,
				new RowMapper<Integer>() {
					// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
					@Override
					public Integer mapRow(ResultSet rs, int arg1)// 뒤에 int는
																	// rowNum이래
							throws SQLException {
						int count = rs.getInt(1);
						return count;// 얘를 리턴하는구나
					}
				}, categoryId); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		return countProduct;
	}
	
	@Override
	public int countProductByCategoryGroup(String group) {
		String sql = "select COUNT(product.productId) " + 
				"from product, categoryProductJoin " + 
				"where categoryid IN " + 
				"(select categoryId FROM category where categoryGroup=?) " + 
				"AND categoryProductJoin.productId = product.productId "
				+ "AND del=false";

		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		int countProduct = 0;
		countProduct = jdbcTemplate.queryForObject(sql,
				new RowMapper<Integer>() {
					// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
					@Override
					public Integer mapRow(ResultSet rs, int arg1)// 뒤에 int는
																	// rowNum이래
							throws SQLException {
						int count = rs.getInt(1);
						return count;// 얘를 리턴하는구나
					}
				}, group); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		return countProduct;
	}
	
	@Override
	public int countProductByKeyword(String keyword) {
		String sql = "select COUNT(productId)" + 
						" from product where productName like (?) "
						+ "AND del=false";

		// 근데 queryForObject는 무조건 1개이상이 있다고 하는거라
		// search에는 적합하지 않을수있어
		int countProduct = 0;
		countProduct = jdbcTemplate.queryForObject(sql,
				new RowMapper<Integer>() {
					// RowMapper는 인터페이스라서 안의 추상메소드 구현해야해요
					@Override
					public Integer mapRow(ResultSet rs, int arg1)// 뒤에 int는
																	// rowNum이래
							throws SQLException {
						int count = rs.getInt(1);
						return count;// 얘를 리턴하는구나
					}
				},  "%" + keyword + "%"); // ?표 안의 조건을 채워줘야하잖아 그러니 마지막에 써줍시당
		return countProduct;
	}

	@Override
	public List<Product> searchProductListByKeyword(int start, int amount,
			String keyword) {
		String sql = "select productId, productName,price,regDate,"
				+ "content,fileName,comment,del"
				+ " from product where productName like (?) "
				+ "AND del=false "
				+ "order by regDate desc offset ? rows fetch next ? rows only";
		List<Product> productList = jdbcTemplate.query(sql, new RowMapper<Product>() {
			
			@Override
			public Product mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Product product = new Product();
				product.setProductId(rs.getString(1));
				product.setProductName(rs.getString(2));
				product.setPrice(rs.getDouble(3));
				product.setRegDate(rs.getDate(4));
				product.setContent(rs.getString(5));
				product.setSpliteFileName(rs.getString(6));
				product.setComment(rs.getString(7));
				return product;
			}

		}, "%" + keyword + "%", start, amount);
		
		for(Product product : productList) {
			System.out.println(product);
		}
		return productList;
	}

}
