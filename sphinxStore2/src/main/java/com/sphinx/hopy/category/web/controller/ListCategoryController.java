package com.sphinx.hopy.category.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;

@Controller
public class ListCategoryController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/category/listCategory", method = RequestMethod.GET)
	public String ListCategory(@RequestParam("type") String type, Model model) {
		System.out.println("오오홍");
		Map<String, Category> categoryMap = categoryService
				.getAllCategoryList();
		// Map으로 다 받은 거 맞나요??
		System.out.println(type);
		String CONTENT = "admin/category/list_category.jsp";
		model.addAttribute("CONTENT", CONTENT);
		model.addAttribute("type", type);
		// 모든 카테고리 받아서 jsp로 넘기기
		model.addAttribute("category", categoryMap);
		
		return "template";
	}
}
