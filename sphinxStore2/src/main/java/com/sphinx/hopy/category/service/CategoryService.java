package com.sphinx.hopy.category.service;

import java.util.List;
import java.util.Map;

import com.sphinx.hopy.category.domain.Category;

public interface CategoryService {
	/**오호홍**/
	public void addCategory(Category category);

	public void updateCategory(Category category);

	public void deleteCategory(Category category);

	/** categoryId로 찾아옴 */
	public Category getCategoryById(String categoryId);

	public List<Category> getCategoryByName(String categoryName);

	public List<Category> getSubCategoryList(String categoryId);

	public List<Category> getRootCategoryList();

	//
	public Map<String, Category> getAllCategoryList();

	public List<Category> getCategoryListByProductId(String productId);

	public List<Category> getCategoryListByCouponId(String couponId);
	
	public void cacheCategory();
	
	public void sortByCategory();
	
	/** 최하위의 카테고리를 알고싶을때, 
	 * product등록 시 뿌려주기 편하게 하려고*/
	public List<Category> getLowestRankCategoryList();
}
