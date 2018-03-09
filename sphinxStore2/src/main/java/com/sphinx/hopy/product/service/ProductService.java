package com.sphinx.hopy.product.service;

import java.util.List;

import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.exception.NonExistProductException;

public interface ProductService {
	public void addProduct(Product product);
	
	public void updateProduct(Product product);
	
	public void deleteProduct(Product product);
	
	/** 어떤 카테고리에 해당하는 제품들을 찾아오는데 페이징해서 가져옴*/
	public List<Product> getProductListByCategoryId(
			int start, int amount, String categoryId);
	
	/** 어떤 카테고리에 해당하는 제품들을 찾아오는데 
	 * 하위의 카테고리들도 검색해 페이징해서 가져옴*/
	public List<Product> getProductListByCategoryGroup(
			int start, int amount, String group);
	
	public Product getProductByProductId(String productId) throws NonExistProductException;
	
	/** 어떤 카테고리에 해당하는 제품이 몇개나 있는지 총 갯수 셈*/
	public int countProductByCategoryId(String categoryId);
	
	public int countProductByCategoryGroup(String group);
	
	public int countProductByKeyword(String keyword);
	
	/** 제품검색*/
	public List<Product> searchProductListByKeyword(
			int start, int amount, String keyword);
	
	/** 객체화한다고 모아주는 함수 따로 뺐음*/
	public void objectification(Product product);
}
