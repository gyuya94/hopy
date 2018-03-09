package com.sphinx.hopy.customer.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutCustomerController {
	
	@RequestMapping(value="/customer/auth/logout", method=RequestMethod.GET)
	public String logoutCustomer(HttpSession session, Model model) {
		session.removeAttribute("customer");
		//로그아웃하면 남아있는 cart도 날려야하는데 깜빡했어
		session.removeAttribute("cart");
		return "home";
	}
}
