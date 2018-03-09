package com.sphinx.hopy.category.dao;

import java.util.List;

import com.sphinx.hopy.category.domain.Category;

public interface CategoryDao {
	public void addCategory(Category category);
	
	public void updateCategory(Category category);
	
	/** categoryId로 찾아옴*/
	public Category getCategoryById(String categoryId);
	
	public List<Category> getCategoryByName(String categoryName);
	
	public List<Category> getSubCategoryListByParentId(String parentId);
	
	public List<Category> getRootCategoryList();
	
	//
	public List<Category> getAllCategoryList();
	
	public List<Category> getCategoryListByProductId(String productId);
	
	public List<Category> getCategoryListByCouponId(String couponId);
}
