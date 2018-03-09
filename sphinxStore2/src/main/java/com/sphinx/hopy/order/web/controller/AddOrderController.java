package com.sphinx.hopy.order.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.order.domain.Cart;
import com.sphinx.hopy.order.domain.Order;
import com.sphinx.hopy.order.domain.OrderItem;
import com.sphinx.hopy.order.service.OrderService;

@Controller
public class AddOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/order/addOrder", method= RequestMethod.GET)
	public String testGet(Model model) {
		
		System.out.println("testGet진입");
		String CONTENT = "test/orderItemListTest.jsp";
				model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
	@RequestMapping(value="/order/addOrder", method= RequestMethod.POST)
	public String addOrder(Model model , HttpSession session,

			@RequestParam("Recipient") String recipient,
			@RequestParam("RecipientAddr") String recipientAddr,
			@RequestParam("Memo") String memo) {
		Cart newCheckedCart = new Cart();
		if (session.getAttribute("newCheckedCart") == null) {
			//만약 session에 cart안들었다면 예외처리 해줘야겠져
		} else {
			newCheckedCart = (Cart) session.getAttribute("newCheckedCart");
			//cart에 들어있는 orderItem들을 하나씩빼서 order에 넣어줍시당
			Order order = new Order();
			//이런거 생성을 controller단에서 하는게 맞을까
			order.setOrderDate(new Date());
			order.setState("상품준비중");
			
			order.setCustomerId(((Customer)session.getAttribute("customer")).getCustomerId());
			order.setDel(false);
			order.setRecipient(recipient);
			order.setRecipientAddr(recipientAddr);
			order.setMemo(memo);
			//orderItem넣기
			order.setOrderItemList(new ArrayList());
			Set<String> orderItemIdSet = newCheckedCart.getOrderItemMap().keySet();
			for(String orderItemId : orderItemIdSet) {
				order.getOrderItemList().add(newCheckedCart.getOrderItemMap().get(orderItemId));
			}
			
			//totalPrice를 받을까? client쪽에서 script로 하니 너무 불안정한거같아
			//order.setTotalPrice((double)session.getAttribute("totalPrice"));
			double totalPrice = 0;
			for(OrderItem orderItem : order.getOrderItemList()) {
				totalPrice += orderItem.getProduct().getPrice() * orderItem.getQuantity();
			}
			order.setTotalPrice(totalPrice);
			
			orderService.addOrder(order);
			model.addAttribute("order", order);
			
			//음 그리고 이전에 check해서 주문한 orderItem이외에 친구만 남겨줘야해서
			Cart cart = (Cart) session.getAttribute("cart");
			organizeCart(cart, newCheckedCart);
			//session들도 정리해 줍니당
			session.setAttribute("cart", cart);
			session.removeAttribute("newCheckedCart");
		}
		String CONTENT = "customer/order/order_result.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}
	
	/** 주문하고 남은친구들을 카트에서 정리해줄거에여*/
	public void organizeCart(Cart cart, Cart newCheckedCart) {
		Set<String> orderItemIdSet = newCheckedCart.getOrderItemMap().keySet();
		for(String orderItemId : orderItemIdSet) {
			cart.getOrderItemMap().remove(orderItemId);
		}
	}

}
