package com.sphinx.hopy.product.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;
import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.service.ProductService;
import com.sphinx.hopy.util.FileUpload;

@Controller
public class AddProductController implements ApplicationContextAware {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	private WebApplicationContext context;
	private int sizeLimit = 1024 * 1024 * 15;// 파일 사이즈는 일단 15Mb

	@RequestMapping(value = "/admin/product/add", method = RequestMethod.GET)
	public String addProductForm(Model model) {
		String CONTENT = "admin/product/add_form_test.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	@ModelAttribute("categoryList")
	public List<Category> referenceData() {
		List<Category> categoryList = categoryService
				.getLowestRankCategoryList();

		return categoryList;
	}

	@RequestMapping(value = "/admin/product/add", method = RequestMethod.POST)
	public String addProduct(Product product,
			@RequestParam("selectedCategory") String selectedCategoryId,
			@RequestParam("file") List<MultipartFile> fileList,
			MultipartHttpServletRequest request,
			@RequestParam("optionName") List<String> optionNameList,
			@RequestParam("optionValueNum") List<Integer> optionValueNumList,
			@RequestParam("optionValue") List<String> optionValueList,
			@RequestParam("addPrice") List<Double> addPriceList,
			@RequestParam("ir1") String ir1, Model model) {

		// 1. 카테고리 넣어주기
		Category category = categoryService.getCategoryById(selectedCategoryId);
		// List<Category>로 해놨네
		// form에서는 일단 1개만 받도록 해놨는뎅
		// domain에서 new로 할당 안해놔줘서 여기서 하고있음
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(category);
		product.setCategoryList(categoryList);

		// 2. 메인 이미지 넣어주기
		// 파일 업로드 결과값을 path로 받아온다(이미 fileUpload() 메소드에서 해당 경로에 업로드는 끝났음)
		// String uploadPath = fileUpload.fileUpload(request, fileNameTest);
		// path리턴 말고 그냥 fileName받을게요
		// FileUpload 객체 생성
		List<String> fileNameList = new ArrayList<>();
		for (MultipartFile file : fileList) {
			// file칸 추가만 해놓고 첨부 안해놨을수도 있으니까
			if (!file.isEmpty()) {
				//System.out.println("for문이 한번밖에 안돌아요??");
				FileUpload fileUpload = new FileUpload();
				String fileName = fileUpload.fileUpload(request, file);
				fileNameList.add(fileName);
			}
		}

		// product.setFileName(fileName);
		// 이제 List<String>fileNameList로 할거에여
		product.setFileNameList(fileNameList);
		// 3.스마트에디터 태그(상품설명) 넣어주기
		product.setContent(ir1);
		// 4.옵션 넣어주기
		List<Option> optionList = objectizeOptions(optionNameList, optionValueNumList,
				optionValueList, addPriceList);
		product.setOptionList(optionList);
		
		
		// 5. product 실제로 넣어주기
		/** 아 optionId를 productService에서 넣어줘야하나?
		 * 일단 지금 option객체는 비었어요 */
		productService.addProduct(product);
		
		model.addAttribute("product", product);

		String CONTENT = "admin/product/view_detail.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	public List<Option> objectizeOptions(List<String> optionNameList,
			List<Integer> optionValueNumList, List<String> optionValueList,
			List<Double> addPriceList) {
		List<Option> optionList = new ArrayList<>();

		int optionValueIndex = 0; //2번째 for Looping돌아도 살아있을 변수가 필요해서
		for(int optionCnt = 0; optionCnt<optionNameList.size(); optionCnt++) {
			Option option = new Option();
			option.setOptionName(optionNameList.get(optionCnt));
			for(int optionValueCnt = 0; 
					optionValueCnt<optionValueNumList.get(optionCnt);
					optionValueCnt++) {
				
				option.getOptionValueMap().put
				(optionValueList.get(optionValueIndex), 
						addPriceList.get(optionValueIndex));
				
				optionValueIndex++;
			}
			optionList.add(option);
		}
		return optionList;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = (WebApplicationContext) context;
	}
}
