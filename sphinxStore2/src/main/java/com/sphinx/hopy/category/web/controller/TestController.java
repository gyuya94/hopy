package com.sphinx.hopy.category.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;

@Controller
public class TestController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/category/test", method = RequestMethod.GET)
	public String updateCategoryForm(Model model,
			@ModelAttribute("command") Category category) {
		System.out.println("TestController get 진입");
		String CONTENT = "admin/category/test.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	@RequestMapping(value = "/category/test", method = RequestMethod.POST)
	public String updateCategory(@ModelAttribute("command") Category category,
			Model model) {
		String categoryName = category.getCategoryName();
		System.out.println(categoryName);
		List<Category> categoryResult = categoryService
				.getCategoryListByProductId("P_0001_BLK");
		
		System.out.println("TestController post 진입");
		System.out.println(categoryResult);
		model.addAttribute("category", categoryResult);
		String CONTENT = "admin/category/test_result.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

}
