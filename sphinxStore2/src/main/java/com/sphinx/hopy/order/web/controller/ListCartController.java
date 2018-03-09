package com.sphinx.hopy.order.web.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sphinx.hopy.order.domain.Cart;
import com.sphinx.hopy.order.domain.OrderItem;

@Controller
public class ListCartController {
	@RequestMapping(value = "/cart/listCart", method = RequestMethod.GET)
	public String listCart(Model model, HttpSession session) {
		// 먼저 totalPrice를 여기서 대충 계산해서 가봅시당
		Cart cart = (Cart) session.getAttribute("cart");
		double totalPrice = 0;
		// cart에 들어있는 orderItemMap 다 털어서 총가격 계산해봅시당
		if (cart!=null) {
			Set<String> orderItemIdSet = cart.getOrderItemMap().keySet();
			for (String orderItemId : orderItemIdSet) {
				OrderItem orderItem = cart.getOrderItemMap().get(orderItemId);
				totalPrice += orderItem.getProduct().getPrice() * orderItem.getQuantity();
			}
			model.addAttribute("totalPrice", totalPrice);
		}
		String CONTENT = "customer/cart/list_cart.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
}
