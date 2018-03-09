package com.sphinx.hopy.product.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sphinx.hopy.product.domain.Supplier;
import com.sphinx.hopy.product.service.SupplierService;
import com.sphinx.hopy.util.PageUtil;

@Controller
public class UpdateSupplierController {

	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping(value="/admin/supplier/update", method=RequestMethod.GET)
	public String updateSupplierForm(@RequestParam("checkedSupplierId") 
		String checkedSupplierId, Model model) {
		
		Supplier supplier = supplierService.findSupplierById(checkedSupplierId);
		model.addAttribute("supplier", supplier);
		
		String CONTENT = "admin/supplier/update_form.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
	
	@RequestMapping(value="/admin/supplier/update", method=RequestMethod.POST)
	public String updateSupplier(Supplier modifiedSupplier, Model model) {
		//수정해주고
		supplierService.updateSupplier(modifiedSupplier);
		
		//그 다음 select list supplier 페이지 갑시당
		/*
		int clickPage = 1;
		PageUtil pageUtil = new PageUtil();
		// 싱글턴으로 변경하자
		int countSupplier = supplierService.countSupplier();
		pageUtil.setCount(countSupplier);
		pageUtil.setAmount(5);
		pageUtil.setShowNum(10);
		int count = pageUtil.getCount(); //모든 업체수
		int amount = pageUtil.getAmount(); // 한페이지에 보여줄 업체 갯수
		int showNum = pageUtil.getShowNum(); // 페이지 번호 
		int totalPage = pageUtil.getTotalPage(count, amount); // 페이지 계산
		List<Integer> pageList = pageUtil.getVariablePageList(totalPage,
				clickPage, showNum); // 업체수에 따른 페이지 갯수 설정
		model.addAttribute("clickPage", clickPage);
		model.addAttribute("pageList", pageList);
		
		
		List<Supplier> supplierList = supplierService
				.getSupplierListByPaging((clickPage - 1) * amount, amount);
		model.addAttribute("supplierList", supplierList);

		String CONTENT = "admin/supplier/select_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		*/  
		 
		//이런식으로 url써도 된다는데요??
		return "forward:/admin/supplier/select_list?clickPage=1";
	}
}
