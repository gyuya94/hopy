package com.sphinx.hopy.customer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.customer.service.CustomerService;

@Controller
public class LeaveCustomerController {
	@Autowired
	private CustomerService customerService;
	//Autowired는 상관없는데 @Service이거는 각 구현체마다 달아줘야해
	
	@RequestMapping(value = "/customer/auth/leave",method = RequestMethod.GET)
	public ModelAndView leaveForm(Customer customer) {
		ModelAndView mav = new ModelAndView();
		String CONTENT = "customer/auth/leave.jsp";
		mav.addObject("CONTENT", CONTENT);
		mav.setViewName("template");
		return mav;
	}
	
	@RequestMapping(value = "/customer/auth/leave",method = RequestMethod.POST)
	public ModelAndView leave(Customer customer) {
		ModelAndView mav = new ModelAndView();
		
		/** 나중에 구현해야하는 부분*/
		//..
		//customerService.deleteCustomer(customer);
		
		//임시로 테스트하는 부분
		///////////////////////////////////////////////////
		
		//이러면 걍 index들어가있는 home으로 가니 괜찮네요
		mav.setViewName("home");
		return mav;
	}
}
