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
public class DeleteProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/admin/product/delete", method = RequestMethod.GET)
	public String deleteProductForm(Model model) {

		String CONTENT = "admin/product/delete.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	@RequestMapping(value = "/admin/product/delete", method = RequestMethod.POST)
	public String deleteProduct(
			@RequestParam("checkedProductId") String checkedProductId,
			Model model) {
		try {
			System.out.println("들어는 오니?");
			Product product = productService
					.getProductByProductId(checkedProductId);
			System.out.println();
			productService.deleteProduct(product);
		} catch (NonExistProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String CONTENT = "admin/product/select_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
}
