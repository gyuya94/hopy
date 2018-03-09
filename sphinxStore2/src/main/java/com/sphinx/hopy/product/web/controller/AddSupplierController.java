package com.sphinx.hopy.product.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sphinx.hopy.product.dao.SupplierDao;
import com.sphinx.hopy.product.domain.Supplier;
import com.sphinx.hopy.product.service.SupplierService;

@Controller
public class AddSupplierController {

	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping(value="/admin/supplier/add", method=RequestMethod.GET)
	public String addSupplierForm(Model model) {
		
		String CONTENT = "admin/supplier/add_form.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
	
	@RequestMapping(value="/admin/supplier/add", method=RequestMethod.POST)
	public String addSupplier(Supplier supplier, Model model) {
		supplierService.addSupplier(supplier);
		model.addAttribute("supplier", supplier);
		String CONTENT = "admin/supplier/view_detail.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
	
}
