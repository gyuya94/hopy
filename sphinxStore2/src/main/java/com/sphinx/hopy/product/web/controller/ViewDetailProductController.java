package com.sphinx.hopy.product.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.exception.NonExistProductException;
import com.sphinx.hopy.product.service.ProductService;

@Controller
public class ViewDetailProductController {
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/product/viewDetailProduct", method = RequestMethod.GET)
	public String viewDetailProductByCustomer(Model model,
			@RequestParam("productId") String productId) throws NonExistProductException {
		
		Product product = productService.getProductByProductId(productId);
		String CONTENT = "customer/product/view_detail.jsp";
		System.out.println(product.getFileNameList());
		model.addAttribute("CONTENT", CONTENT);
		model.addAttribute("product", product);
		return "template";
	}
}
