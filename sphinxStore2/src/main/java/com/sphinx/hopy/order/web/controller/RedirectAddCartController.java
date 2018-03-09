package com.sphinx.hopy.order.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sphinx.hopy.order.domain.Cart;
import com.sphinx.hopy.order.domain.OrderItem;

@Controller
public class RedirectAddCartController {
	@RequestMapping(value = "/cart/addCartSuccess")
	public String sendCart(Cart cart, Model model, HttpSession session) {
		//현재 페이지를 적어야함 가변적으로 변해야함
		System.out.println("redirect 후 controller 진입");
		Cart cartTest = (Cart) session.getAttribute("cart");
		for(String key : cartTest.getOrderItemMap().keySet()) {
			OrderItem orderItem = cartTest.getOrderItemMap().get(key);
			System.out.println(orderItem.getProduct().getCategoryList());
		}
		String CONTENT = "";
		/*String CONTENT = "customer/cart/list_cart.jsp";*/
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
}
