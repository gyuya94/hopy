package com.sphinx.hopy.category.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;

@Controller
public class AddCategoryController  implements ApplicationContextAware{
	@Autowired
	private CategoryService categoryService;
	private WebApplicationContext context;
	// ModelAndView로 만듦
	@RequestMapping(value = "/category/addCategory", method = RequestMethod.GET)
	public ModelAndView addCategoryForm(
			@ModelAttribute("command") Category category) {
		// get으로 들어왔는데 @ModelAttribute를 가진 객체가 있으면 다음 폼에서 그 객체안의 변수값을 넣을 
		//form이 나올 것이다.
		System.out.println("AddCategoryController get 진입");
		ModelAndView mav = new ModelAndView();
		String CONTENT = "admin/category/add_category_form.jsp";
		mav.setViewName("template");
		mav.addObject("CONTENT", CONTENT);
		return mav;
	}

	@RequestMapping(value = "/category/addCategory", method = RequestMethod.POST)
	public String addCategory(@ModelAttribute("command") Category category,
			//post의 @ModelAttribute는 받아온 category객체를 사용하겠다고 선언한 것
			Model model) {
		System.out.println("AddCategoryController post 진입");
		String categoryId = category.getCategoryId();
		String categoryName = category.getCategoryName();
		String parentId = category.getParentId();
		String group = category.getGroup();
		List<String> errorMsg = new ArrayList<>();
		if (categoryId.isEmpty() || categoryName.isEmpty() || parentId.isEmpty()
				|| group.isEmpty()) {
			System.out.println("if 진입");
			errorMsg.add("필수사항을 모두 입력하세요");
			String CONTENT = "admin/category/add_category_form.jsp";
			model.addAttribute("CONTENT", CONTENT);
			model.addAttribute("errorMsg", errorMsg);
		} else {
			categoryService.addCategory(category);
			if(category.getParentId().equals("0")){//자기의 상위 카테고리가 0 일 때 최상위
				List<Category> rootCategoryList = null;
				rootCategoryList = new ArrayList<>();//공간 할당해주고
				rootCategoryList=(List<Category>) context.getServletContext().getAttribute("rootCategoryList");
				//context.getServletContext()=applicationScope
				rootCategoryList.add(category);
				context.getServletContext().setAttribute("rootCategoryList", rootCategoryList);
			}
			String CONTENT = "admin/category/list_category.jsp";
			model.addAttribute("type", "list");
			Map<String, Category> categoryMap = categoryService
					.getAllCategoryList();
			model.addAttribute("categoryMap", categoryMap);
			model.addAttribute("CONTENT", CONTENT);
		}
		return "template";
		
	}
		
		//ApplicationContextAware이 인터페이스라서
				//이 메소드를 정의해줘야함
				@Override
				public void setApplicationContext(ApplicationContext context)
						throws BeansException {
					//context가 ApplicationContext context이 변수로 자기를 집어넣어서
					//컴포넌트(여기서는 JoinCustomerController인 Bean)가 
					//자기가 놀고있는 컨텍스트를 가져오는거지
					
					this.context = (WebApplicationContext) context;
					//WebApplicationContext는 ApplicationContext의 자식이야
					//우린 지금 그냥 main써서 하는게 아니라
					//톰캣올려서 하는 웹에거 하는거니까 WebAppContext로 하는거고
					//그래서 나중에 applicationScope를 쓰고싶다면
					//((WebApplicationContext)context).setAttribute("냥냥");
					//이런식으로 하면 된다고하는데 왜 안될까
					//암튼 그렇대요
					
				}

}
