package com.sphinx.hopy.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;
import com.sphinx.hopy.product.dao.ProductDao;
import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.exception.NonExistOptionException;
import com.sphinx.hopy.product.exception.NonExistProductException;
import com.sphinx.hopy.product.service.OptionService;
import com.sphinx.hopy.product.service.ProductService;
import com.sphinx.hopy.util.idGenerator.IdGenerator;
import com.sphinx.hopy.util.idGenerator.service.IdGeneratorService;
import com.sphinx.hopy.util.idGenerator.service.impl.IdGeneratorServiceImpl;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private OptionService optionService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	private IdGenerator idGenerator;

	@Override
	public void addProduct(Product product) {
		//여기서 id넣는거 해줄까요
		if(product.getProductId() == null) {
			//product.setProductId(productDao.);
			String usableId = idGeneratorService.getUsableId(idGenerator);
			product.setProductId(usableId);
		}
		
		productDao.addProduct(product);
		
		for (Option option : product.getOptionList()) {
			optionService.addOption(option, product.getProductId());
		}
	}

	@Override
	public void updateProduct(Product product) {
		productDao.updateProduct(product);
		// option 도 해줘야지
		for (Option option : product.getOptionList()) {
			optionService.updateOption(option);
		}
	}

	@Override
	public void deleteProduct(Product product) {
		productDao.deleteProduct(product);
		// option 굳이 지워줘야하나
	}

	@Override
	public List<Product> getProductListByCategoryId(int start, int amount,
			String categoryId) {
		List<Product> productList = productDao.getProductListByCategoryId(start,
				amount, categoryId);
		
		return productList;
	}
	
	public List<Product> getProductListByCategoryGroup(
			int start, int amount, String group){
		List<Product> productList = productDao.
				getProductListByCategoryGroup(
						start, amount, group);
		
		return productList;
	}

	@Override
	public Product getProductByProductId(String productId)
			throws NonExistProductException {
		Product product = productDao.getProductByProductId(productId);
		// 위에 저거는 product만 차있어서
		// option도 채워주고
		try {
			product.setOptionList(optionService
					.getOptionListByProductId(product.getProductId()));
			product.setCategoryList(categoryService.getCategoryListByProductId(product.getProductId()));
			// product가 없는 예외는 던지고 option이 없는 예외는 여기서
			// 잡아두 괜찮을거같은데
			// 모르것당
		} catch (NonExistOptionException e) {
			e.printStackTrace();
		}
		// category도 채워줘야하는뎅
		// product.setCategoryList(categoryService.getCategoryListByProductId
		// (product.getProductId()));
		// 내가 한부분이 아니라 저거 채워져있나 모르겠네요
		return product;
	}

	@Override
	public int countProductByCategoryId(String categoryId) {
		return productDao.countProductByCategoryId(categoryId);
	}
	
	@Override
	public int countProductByCategoryGroup(String group) {
		return productDao.countProductByCategoryGroup(group);
	}
	
	@Override
	public int countProductByKeyword(String keyword) {
		return productDao.countProductByKeyword(keyword);
	}

	@Override
	public List<Product> searchProductListByKeyword(int start, int amount,
			String keyword) {
		List<Product> productList = productDao.searchProductListByKeyword(start,
				amount, keyword);
		return productList;
	}

	@Override
	public void objectification(Product product) {
		// TODO Auto-generated method stub

	}
	
	public void init() {
		this.idGenerator = new IdGenerator();
		idGenerator.setPrefix("PRO_");
		idGenerator.setIdLength(10);
		idGenerator.setTableName("productIdTable");
	}
	
	

}
