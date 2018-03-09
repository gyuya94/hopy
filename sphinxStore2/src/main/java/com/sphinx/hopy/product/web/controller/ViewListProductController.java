package com.sphinx.hopy.product.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.service.ProductService;
import com.sphinx.hopy.util.PageUtil;

@Controller
public class ViewListProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@RequestMapping(value="/admin/product/view_list")
	public String viewListProduct(@RequestParam("clickPage") int clickPage,
			@RequestParam("categoryId") String categoryId,
			Model model) {
		//page test에여
		PageUtil pageUtil = new PageUtil();
		// 싱글턴으로 변경하자
		int countProduct = 0;
		pageUtil.setAmount(4);
		int amount = pageUtil.getAmount(); // 한페이지에 보여줄 업체 갯수
		
		Category category = categoryService.getCategoryById(categoryId);
		List<Product> productList = new ArrayList<>();
		if(category.getParentId().equals("0")) {
			//만약 최상위이면 group으로 밑에 걍 다 찾아오고
			countProduct = productService.countProductByCategoryGroup(category.getGroup());
			productList = 
					productService.getProductListByCategoryGroup
						((clickPage - 1) * amount, amount, category.getGroup());
		}else {
			//아니면 그 해당 카테고리의 상품들만 가져갑시당
			countProduct = productService.countProductByCategoryId(categoryId);
			productList = productService.getProductListByCategoryId(
					(clickPage - 1) * amount, amount, category.getCategoryId());
		}
		model.addAttribute("productList", productList);
		model.addAttribute("category", category);
		
		
		pageUtil.setCount(countProduct);
		//pageUtil.setAmount(5);
		pageUtil.setShowNum(10);
		int count = pageUtil.getCount(); //모든 업체수
		int showNum = pageUtil.getShowNum(); // 페이지 번호 
		int totalPage = pageUtil.getTotalPage(count, amount); // 페이지 계산
		List<Integer> pageList = pageUtil.getVariablePageList(totalPage,
				clickPage, showNum); // 업체수에 따른 페이지 갯수 설정
		model.addAttribute("clickPage", clickPage);
		model.addAttribute("pageList", pageList);

		String CONTENT = "admin/product/view_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
	
	@RequestMapping(value="/customer/productView")
	public String viewListProductByCustomer(@RequestParam("clickPage") int clickPage,
			@RequestParam("categoryId") String categoryId,
			Model model) {
		// 테스트로 일단 5개 단위로 해봅시당
		System.out.println("고객전용 productView 진입");
		Category category = categoryService.getCategoryById(categoryId);
		List<Product> productList = 
				productService.getProductListByCategoryId
					((clickPage - 1) * 5, 5, categoryId);
		System.out.println(productList);
		model.addAttribute("productList", productList);
		model.addAttribute("category", category);
		String CONTENT = "customer/product/view_product_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
	
	@RequestMapping(value="/search/product" , method=RequestMethod.POST)
	public String viewListProductBySearch(@RequestParam("page") int page,
			@RequestParam("query") String keyword, Model model) {
		// 테스트로 일단 5개 단위로 해봅시당
		System.out.println("searchProduct 진입");
		System.out.println(page);
		List<Product> searchedProductList = productService.searchProductListByKeyword((page - 1) * 5, 5, keyword);
		model.addAttribute("productList", searchedProductList);
		String CONTENT = "customer/product/view_product_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
}
