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
public class SelectListProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/admin/product/select_list", method = RequestMethod.GET)
	public String selectListProductForm(Model model) {

		String CONTENT = "admin/product/select_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	@RequestMapping(value = "/admin/product/select_list", method = RequestMethod.POST)
	public String selectListProduct(
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("clickPage") int clickPage,
			Model model) {

		// page test에여
		PageUtil pageUtil = new PageUtil();
		// 싱글턴으로 변경하자
		int countProduct = productService.countProductByKeyword(searchKeyword);
		//현재 amount를 그냥 4로 해주고있는데
		pageUtil.setAmount(4);
		//그럼 무슨select들어간거 마다 아니지 페이징하려는거마다
		//그 총갯수를 세아려주는 함수가 있어야하는건가
		
		int amount = pageUtil.getAmount(); // 한페이지에 보여줄 업체 갯수

		List<Product> productList = new ArrayList<>();
		productList = productService.searchProductListByKeyword(
				(clickPage - 1) * amount, amount, searchKeyword);
		model.addAttribute("productList", productList);
		pageUtil.setCount(countProduct);
		pageUtil.setShowNum(10);
		int count = pageUtil.getCount(); // 모든 업체수
		int showNum = pageUtil.getShowNum(); // 페이지 번호
		int totalPage = pageUtil.getTotalPage(count, amount); // 페이지 계산
		List<Integer> pageList = pageUtil.getVariablePageList(totalPage,
				clickPage, showNum); // 업체수에 따른 페이지 갯수 설정
		
		model.addAttribute("clickPage", clickPage);
		model.addAttribute("pageList", pageList);

		String CONTENT = "admin/product/select_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

}
