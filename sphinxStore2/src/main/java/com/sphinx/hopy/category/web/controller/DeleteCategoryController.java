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
public class DeleteCategoryController {
	@Autowired
	private CategoryService categoryService;

/*	@RequestMapping(value = "/category/deleteCategory", method = RequestMethod.GET)
	public String deleteCategoryForm(Category category, Model model) {
		System.out.println("deleteCategoryController get 진입");
		String CONTENT = "admin/category/list_category.jsp";
		String delete = "delete";
		model.addAttribute("CONTENT", CONTENT);
		model.addAttribute("delete", delete);
		Map<String, Category> categoryMap = categoryService
				.getAllCategoryList();
		model.addAttribute("categoryMap", categoryMap);
		return "template";
	}*/

	@RequestMapping(value = "/category/deleteCategory", method = RequestMethod.POST)
	// Model로 만듦
	public String deleteCategory(Model model
	/* @RequestParam("cid") int cid */,
			@RequestParam("categoryIdCheck") String categoryIdCheck) {
		System.out.println("deleteCategoryController post 진입");

		// jsp에서 선택한 카테고리 id
		String categoryId = categoryIdCheck;
		System.out.println("delete시 선택한 카테고리Id : " + categoryId);
		// 가져온 id를 이용하여 객체 생성
		Category deleteCategory = categoryService.getCategoryById(categoryId);
		System.out.println("deleteCategory 객체 생성 : " + deleteCategory);
		// 카테고리 삭제(del=true)
		
		categoryService.deleteCategory(deleteCategory);
		// 삭제하고 캐시 업데이트
		categoryService.cacheCategory();
		String CONTENT = "admin/category/list_category.jsp";// 다음 경로
		
		Map<String, Category> categoryMap = categoryService
				.getAllCategoryList();
		model.addAttribute("category", categoryMap);
		model.addAttribute("type", "list");
		model.addAttribute("CONTENT", CONTENT); // 경로 까지 request
		return "template"; // 무조건 template으로 가면 됨
	}

}
