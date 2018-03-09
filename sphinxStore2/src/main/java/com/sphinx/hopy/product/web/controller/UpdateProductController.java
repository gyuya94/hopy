package com.sphinx.hopy.product.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;
import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.exception.NonExistProductException;
import com.sphinx.hopy.product.service.ProductService;
import com.sphinx.hopy.util.FileUpload;

@Controller
public class UpdateProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/admin/product/update", method = RequestMethod.GET)
	public String updateProductForm(
			@RequestParam("checkedProductId") String checkedProductId,
			Model model) {
		try {
			Product product = productService
					.getProductByProductId(checkedProductId);

			model.addAttribute("product", product);
		} catch (NonExistProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String CONTENT = "admin/product/update_form.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	@ModelAttribute("categoryList")
	public List<Category> referenceData() {
		List<Category> categoryList = categoryService
				.getLowestRankCategoryList();

		return categoryList;
	}

	@RequestMapping(value = "/admin/product/update", method = RequestMethod.POST)
	public String updateProduct(Product product,
			@RequestParam("selectedCategory") String selectedCategoryId,
			@RequestParam("file") List<MultipartFile> fileList,
			@RequestParam("orignFile") String orignFile,
			MultipartHttpServletRequest request,
			/*
			 * @RequestParam("optionName") List<String> optionNameList,
			 * 
			 * @RequestParam("optionValueNum") List<Integer> optionValueNumList,
			 * 
			 * @RequestParam("optionValue") List<String> optionValueList,
			 * 
			 * @RequestParam("addPrice") List<Double> addPriceList,
			 */
			@RequestParam("ir1") String ir1, Model model) {

		// 일단 원본 product를 id를 가지고와서 할게요
		try {
			product = productService
					.getProductByProductId(product.getProductId());
		} catch (NonExistProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		// 메인 이미지 바꿨으면 하고
		List<String> fileNameList = product.getFileNameList();
		//이렇게 하면 기존에 있는거 덮어씌우는거라 될려나
		for (MultipartFile file : fileList) {
			// file칸 추가만 해놓고 첨부 안해놨을수도 있으니까
			if (!file.isEmpty()) {
				FileUpload fileUpload = new FileUpload();
				String fileName = fileUpload.fileUpload(request, file);
				//fileNameList.add(fileName);
				//아예 교체해야하니 add가 아니라 갈아야되나
				fileNameList.set(0, fileName);
			}
		}
		// product.setFileName(fileName);
		// 이제 List<String>fileNameList로 할거에여
		product.setFileNameList(fileNameList);

		// 3.스마트에디터 태그(상품설명) 넣어주기
		product.setContent(ir1);
		// 4.옵션 넣어주기
		/*
		 * product.setOptionList(objectizeOptions(optionNameList,
		 * optionValueNumList, optionValueList, addPriceList));
		 */
		// 5. product 실제로 넣어주기
		/**
		 * 아 optionId를 productService에서 넣어줘야하나? 일단 지금 option객체는 비었어요
		 */
		// productService.addProduct(product);
		System.out.println("update에 들어온 product");
		System.out.println(product);
		productService.updateProduct(product);
		model.addAttribute("product", product);

		String CONTENT = "admin/product/view_detail.jsp";
		model.addAttribute("CONTENT", CONTENT);

		return "template";
	}

	public List<Option> objectizeOptions(List<String> optionNameList,
			List<Integer> optionValueNumList, List<String> optionValueList,
			List<Double> addPriceList) {
		List<Option> optionList = new ArrayList<>();

		int optionValueIndex = 0; // 2번째 for Looping돌아도 살아있을 변수가 필요해서
		for (int optionCnt = 0; optionCnt < optionNameList
				.size(); optionCnt++) {
			Option option = new Option();
			option.setOptionName(optionNameList.get(optionCnt));
			for (int optionValueCnt = 0; optionValueCnt < optionValueNumList
					.get(optionCnt); optionValueCnt++) {

				option.getOptionValueMap().put(
						optionValueList.get(optionValueIndex),
						addPriceList.get(optionValueIndex));
				optionValueIndex++;
			}
			optionList.add(option);
		}
		return optionList;
	}
}
