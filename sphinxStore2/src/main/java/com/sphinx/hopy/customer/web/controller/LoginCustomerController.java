package com.sphinx.hopy.customer.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.customer.exception.NonExistCustomerException;
import com.sphinx.hopy.customer.service.CustomerService;

@Controller
public class LoginCustomerController {

	@Autowired
	private CustomerService customerService;
	// Autowired는 상관없는데 @Service이거는 각 구현체마다 달아줘야해

	@RequestMapping(value = "/customer/auth/login", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		ModelAndView mav = new ModelAndView();
		String CONTENT = "customer/auth/login_form.jsp";
		mav.addObject("CONTENT", CONTENT);
		mav.setViewName("template");
		return mav;
	}

	@RequestMapping(value = "/customer/auth/login", method = RequestMethod.POST)
	public ModelAndView login(Customer customer, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<String> errorMsg = new ArrayList<>();
		String CONTENT = "";
		// 기본을 실패했을경우의 페이지로 설정하고,
		// 성공했을경우에만 다음페이지가 바뀌는거로
		CONTENT = "customer/auth/login_form.jsp";
		// db에 그 id가 있나 찾아보는데

		Customer dbCustomer = null;
		if (customer.getSphinxId() == null || customer.getSphinxId().length() == 0 
				|| customer.getPassword() == null || customer.getPassword().length() == 0) {
			errorMsg.add("모든 항목을 입력해주세요");
		} else {
			try {
				// id는 바르게 입력함
				dbCustomer = customerService.getCustomerBySphinxId(customer.getSphinxId());

			} catch (NonExistCustomerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errorMsg.add("아이디 또는 비밀번호가 일치하지 않습니다.");
				mav.addObject("errorMsg", errorMsg);
				mav.addObject("CONTENT", CONTENT);
				mav.setViewName("template");
				return mav;
			}
			// 해당 customer가 db에 존재해야 여기까지 내려옴
			if (dbCustomer.getPassword().equals(customer.getPassword())) {
				// 비번을 제대로 입력한 경우에만
				// mav.addObject("customer", dbCustomer);
				// 움 세션 어캐넣지
				session.setAttribute("customer", dbCustomer);
				// 성공하면 그냥 인덱스로 갈까요?
				CONTENT = "index.jsp";
				// 이 전전페이지로 가고싶은건데 어캐가죳
			} else {
				errorMsg.add("아이디 또는 비밀번호가 일치하지 않습니다.");
			}
		}
		mav.addObject("errorMsg", errorMsg);
		mav.addObject("CONTENT", CONTENT);
		mav.setViewName("template");
		return mav;
	}

}
