package com.sphinx.hopy.category.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sphinx.hopy.category.dao.CategoryDao;
import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;

import sun.util.resources.cldr.teo.CalendarData_teo_UG;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	private Map<String, Category> categoryMap = new LinkedHashMap<>();
	private List<Category> categoryList; // = categoryDao.getAllCategories()를 하면
											// 안됨
	// 그 이유는 categoryDao 객체가 만들어 지지 않았는데 바로 사용하려고 하기 때문
	// 그래서 categoryDao set, get을 만들어주고
	// bean에다가 categoryService안의 setCategoryDao(init)를 등록해준다.(등록이 객체 생성하는 것)

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void init() {
		this.categoryList = categoryDao.getAllCategoryList();
		cacheCategory();
	}

	@Override
	public void addCategory(Category category) {
		System.out.println("categoryService 진입");
		categoryDao.addCategory(category);
		cacheCategory();
	}

	@Override
	public void updateCategory(Category category) throws NullPointerException {
		// throws 컨트롤러 단으로 예외 던지기
		categoryDao.updateCategory(category);
		cacheCategory();

	}

	@Override
	public void deleteCategory(Category category) {
		category.setDel(true);
		categoryDao.updateCategory(category);
		//delete하고 map에도 삭제
		categoryMap.remove(category.getCategoryId(), category);
		cacheCategory();
	}

	@Override
	public Category getCategoryById(String categoryId) {
		cacheCategory();
		Category category = categoryMap.get(categoryId);
		return category;
	}

	@Override
	public List<Category> getCategoryByName(String categoryName) {
		List<Category> categoryList = categoryDao
				.getCategoryByName(categoryName);
		// null이면 예외처리 어떻게 하지?
		return categoryList;
	}

	@Override
	public List<Category> getSubCategoryList(String categoryId) {
		Category category = categoryMap.get(categoryId);
		// 이미 캐싱해서 category안에 subCategory가 들어있음
		List<Category> subCategoryList = category.getSubCategory();
		return subCategoryList;
	}

	@Override
	public List<Category> getRootCategoryList() {
		Set<String> categoryKeySet = categoryMap.keySet();
		List<Category> rootCategoryList = new ArrayList<>();
		// map의 키들을 모두 뽑아오기
		for (String categorykey : categoryKeySet) {
			Category category = categoryMap.get(categorykey);
			String parentId = category.getParentId();
			if (parentId.equals("0")) {
				rootCategoryList.add(category);
			}
		}

		return rootCategoryList;
	}

	@Override
	public Map<String, Category> getAllCategoryList() {
		cacheCategory();
		return categoryMap;
	}

	@Override
	public List<Category> getCategoryListByProductId(String productId) {
		// 조인테이블 사용 DB가야함
		List<Category> categoryList = categoryDao
				.getCategoryListByProductId(productId);
		return categoryList;
	}

	@Override
	public List<Category> getCategoryListByCouponId(String couponId) {
		List<Category> categoryList = categoryDao
				.getCategoryListByCouponId(couponId);
		return categoryList;
	}

	@Override
	public void cacheCategory() {
		// 카테고리가 업데이트 되거나 삭제 됐을 때 바뀐 카테고리를 다시 가져오기 위해서 씀
		categoryList = categoryDao.getAllCategoryList();
		if (categoryList == null) {

		} else {
			sortByCategory();
		}
		for (Category category : categoryList) {
			String categoryId = category.getCategoryId();
			categoryMap.put(categoryId, category);
			
		}
	}

	@Override
	public void sortByCategory() {
		// 리스트 정렬
		for (Category category : categoryList) {
			for (Category anotherCategory : categoryList) {
				if (category.getCategoryId()
						.equals(anotherCategory.getParentId())) {
					category.getSubCategory().add(anotherCategory);
				}
			}
		}

	}
	
	@Override
	public List<Category> getLowestRankCategoryList(){
		List<Category> lowestRankCategoryList = new ArrayList<>();
		
		Set<String> categoryIdSet = categoryMap.keySet();
		for(String categoryId : categoryIdSet) {
			//캐싱되어있는 map을 돌면서 만약 하위카테고리가 비었다면
			Category tempCategory = categoryMap.get(categoryId);
			if(tempCategory.getSubCategory().isEmpty()) {
				lowestRankCategoryList.add(tempCategory);
			}
		}
		return lowestRankCategoryList;
	}

}
