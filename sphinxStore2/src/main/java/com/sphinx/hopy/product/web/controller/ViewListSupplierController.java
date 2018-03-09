package com.sphinx.hopy.product.web.controller;

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
public class ViewListSupplierController {

	@Autowired
	private SupplierService supplierService;

	@RequestMapping(value = "/admin/supplier/view_list", method = RequestMethod.GET)
	public String viewListSupplier(@RequestParam("clickPage") int clickPage,
			Model model) {
		// 테스트로 일단 5개 단위로 해봅시당
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
		// 클릭한 페이지에 해당하는 업체 출력
		model.addAttribute("supplierList", supplierList);
		String CONTENT = "admin/supplier/view_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
}
