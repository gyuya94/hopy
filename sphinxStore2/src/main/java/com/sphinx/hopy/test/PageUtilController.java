package com.sphinx.hopy.test;

import java.util.List;
import com.sphinx.hopy.util.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageUtilController {
	/*	@RequestMapping(value = "/test/pageTest", method = RequestMethod.GET)
		public String getPageGet(Model model,@RequestParam("clickPage") int clickPage) {
			int c= clickPage;
			System.out.println(c);
			String CONTENT = "test/page_test.jsp";
			model.addAttribute("CONTENT", CONTENT);
			return "template";
		}*/

	@RequestMapping(value = "/test/pageTest", method = RequestMethod.POST)
	public String getPagePost(Model model,
			@RequestParam("clickPage") int clickPage) {
		int f = clickPage;
		System.out.println("f : "+f);
		PageUtil pageUtil = new PageUtil();
		System.out.println("post진입");
		// 싱글턴으로 변경하자
		pageUtil.setCount(70);
		pageUtil.setAmount(4);
		pageUtil.setShowNum(10);
		int count = pageUtil.getCount();
		int amount = pageUtil.getAmount();
		int showNum = pageUtil.getShowNum();
		int totalPage = pageUtil.getTotalPage(count, amount);
		List<Integer> pageList = pageUtil.getVariablePageList(totalPage,
				clickPage, showNum);
		model.addAttribute("clickPage", clickPage);
		model.addAttribute("pageList", pageList);
		String CONTENT = "test/page_result.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
}
