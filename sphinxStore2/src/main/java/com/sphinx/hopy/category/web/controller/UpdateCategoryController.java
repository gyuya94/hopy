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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;

@Controller
public class UpdateCategoryController  implements ApplicationContextAware{
	@Autowired
	private CategoryService categoryService;
	private WebApplicationContext context;

	// 커맨드 방식
	@RequestMapping(value = "/category/updateCategory", method = RequestMethod.GET)
	public String updateCategoryForm(Model model,
			@RequestParam("categoryIdCheck") String categoryIdCheck,
			@ModelAttribute("command") Category categorymod) {
		System.out.println("UpdateCategoryController get 진입");
		System.out.println("categoryIdCheck");
		System.out.println(categoryIdCheck);
		Category category = categoryService.getCategoryById(categoryIdCheck);
		model.addAttribute("category", category);
		String CONTENT = "admin/category/update_category_form.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	@RequestMapping(value = "/category/updateCategory", method = RequestMethod.POST)
	public String updateCategory(Model model,
			@ModelAttribute("command") Category categorymod,
			@RequestParam ("beforeCategoryId") String categoryId) {
		System.out.println("UpdateCategoryController post 진입");
		System.out.println("수정할 카테고리 : " + categorymod);
		/*Category category = categoryService.getCategoryById(categoryId);*/
		//원래 카테고리 id 넣기 카테고리 id는 변경 안되니까
		categorymod.setCategoryId(categoryId);
		categoryService.updateCategory(categorymod);
		if(categorymod.getParentId().equals("0")){//자기의 상위 카테고리가 0 일 때 최상위
			List<Category> rootCategoryList = null;
			rootCategoryList = new ArrayList<>();//공간 할당해주고
			rootCategoryList=(List<Category>) context.getServletContext().getAttribute("rootCategoryList");
			//context.getServletContext()=applicationScope
			//의류, 용품
			//rootcategoryList 각각 중 수정 된 것 을 바꿔줌
			for(int i =0 ; i<rootCategoryList.size();i++){
				if(rootCategoryList.get(i).getCategoryId().equals(categorymod.getCategoryId())){
					rootCategoryList.set(i, categorymod);
				}
			}
			context.getServletContext().setAttribute("rootCategoryList", rootCategoryList);
		}
		System.out.println("UpdateCategoryController------------------------------");
		Map<String, Category> categoryMap = categoryService
				.getAllCategoryList();
		System.out.println("수정후 카테고리 리스트 : " + categoryMap);
		model.addAttribute("categoryMap", categoryMap);
		model.addAttribute("type", "list");
		System.out.println("-------------------------------------");
		String CONTENT = "admin/category/list_category.jsp";
		model.addAttribute("CONTENT", CONTENT);
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
