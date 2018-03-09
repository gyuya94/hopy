package com.sphinx.hopy.product.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sphinx.hopy.product.domain.Supplier;
import com.sphinx.hopy.product.service.SupplierService;
import com.sphinx.hopy.util.PageUtil;

@Controller
public class DeleteSupplierController {

	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping(value="/admin/supplier/delete")
	public String deleteSupplier(@RequestParam("checkedSupplierId") 
		List<String> checkedSupplierIdList,
			Model model) {
		System.out.println(checkedSupplierIdList);
		for(String checkedSupplierId : checkedSupplierIdList) {
			System.out.println(checkedSupplierId);
			supplierService.deleteSupplier(checkedSupplierId);
		}
		//잘받아오니까 여기 이제 지우는거 서비스에거 불러다 하고 
		//다시 리스트 보여주는 애들 하면 되겠당
		
		//음 다시 view_list페이지로 가는데 그 내용을 여기서 다시 해줘야하는가
		//잘 모르겠는데 일단은 여기 다시 해둡시당
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
		// 클릭한 페이지에 해당하는 업체 출력
		model.addAttribute("supplierList", supplierList);
		
		//그럼 여기건 redirect로 걸어서 가고싶은뎅 어캐해야하지
		String CONTENT = "admin/supplier/view_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
}
