package com.sphinx.hopy.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.product.dao.ProductDao;
import com.sphinx.hopy.product.dao.SupplierDao;
import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.exception.NonExistProductException;
import com.sphinx.hopy.product.service.ProductService;

public class ConsolProductTest {
	public static void main(String[] args) {
		// bean설정
		String config = "com/sphinx/hopy/test/test.xml";
		AbstractApplicationContext context = new GenericXmlApplicationContext(
				config);
		ProductDao productDao = context.getBean("productDao", ProductDao.class);
		ProductService productService = context.getBean("productService", ProductService.class);
		// bean설정해줘야하나
		/*Product product = new Product();
		List<String> fileNameList = new ArrayList<>();
		
		
		 * fileNameList.add("1번 파일.jpg"); fileNameList.add("2번 파일.png");
		 * fileNameList.add("3번 파일.jpg"); product.setFileNameList(fileNameList);
		 * 
		 * System.out.println(product.getCombineFileName());
		 
		System.out.println("-------------");
		String fileName = "1번 파일.jpg, 2번 파일.png, 3번 파일.jpg";
		product.setSpliteFileName(fileName);
		System.out.println(product.getFileNameList());

		Product product2 = new Product("P_0003_PNK", "멍멍이 분홍 패딩", 2000,
				new Date(), false, "스마트에디터로 만든 태그가 들어가요", "이거 짱귀여움");
		product2.setSpliteFileName(fileName);
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(new Category("CLO_110000"));
		categoryList.add(new Category("CLO_120000"));
		product2.setCategoryList(categoryList);
		///// option채워넣는중....////////////////////////////
		Option option = new Option("OPT_000005", "사이즈", false);
		Map<String, Double> optionValueMap = new LinkedHashMap<>();
		optionValueMap.put("S", -1000.0);
		optionValueMap.put("M", 0.0);
		optionValueMap.put("L", 1000.0);
		option.setOptionValueMap(optionValueMap);
		Option option2 = new Option("OPT_000006", "목줄추가", false);
		Map<String, Double> optionValueMap2 = new LinkedHashMap<>();
		optionValueMap2.put("추가 안함", 0.0);
		optionValueMap2.put("추가", 1000.0);
		option2.setOptionValueMap(optionValueMap2);
		List<Option> optionList = new ArrayList<>();
		optionList.add(option);
		optionList.add(option2);
		product2.setOptionList(optionList);
		////////////////////////////////////////////////////////

		// productDao.addProduct(product2);
		System.out.println("-------------");
		try {
			Product product3 = productDao.getProductByProductId("P_0002_PNK");
			System.out.println("P_0002_PNK 를 찾아와 주세여!");

			System.out.println("-------------");
			product3.setComment("다시 되돌아간 코멘트에여");
			//productDao.updateProduct(product3);
			System.out.println(productDao.getProductByProductId("P_0001_PNK"));

			System.out.println("---이제 Service로 테스트 할거에여---");
			
			System.out.println("---addProduct---");
			//productService.addProduct(product2);
			
			System.out.println("---getProduct---");
			System.out.println(productService.getProductByProductId("P_0003_PNK"));
			
			System.out.println("---countProductByCategoryId---");
			System.out.println(productService.countProductByCategoryId("CLO_110000"));
			
		} catch (NonExistProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		List<Product> productList = productService.getProductListByCategoryGroup(0, 5, "CLO");
		for(Product product : productList) {
			System.out.println(product);
		}
	}
}
