package com.sphinx.hopy.order.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sphinx.hopy.order.domain.Cart;
import com.sphinx.hopy.order.domain.OrderItem;
import com.sphinx.hopy.order.service.OrderService;
import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.exception.NonExistProductException;
import com.sphinx.hopy.product.service.ProductService;

@Controller
public class AddOrderItemListController {
	@Autowired
	ProductService productService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/order/paymentByProduct", method = RequestMethod.POST)
	public String payment(Model model, @RequestParam("quantity") int quantity,
			@RequestParam("productId") String productId, 
			@RequestParam("option") List<String> optionList,
			HttpSession session)
			throws NonExistProductException {
		Product product = productService.getProductByProductId(productId);
		/** option친구들 정리하는 아이에요 */
		arrangeProductOption(product, optionList);
		List<OrderItem> orderItemList = new ArrayList<>();
		OrderItem orderItem = new OrderItem();
		double orderTotalPrice = 0;
		orderItem.setOrderItemId(orderService.getNewOrderItemId());
		orderItem.setProduct(product);
		orderItem.setQuantity(quantity);
		
		orderItemList.add(orderItem);
		double price = orderItem.getProduct().getPrice();
		orderTotalPrice = quantity * price;
		if (session.getAttribute("customer") != null) {
			String CONTENT = "customer/order/list_payment.jsp";
			model.addAttribute("CONTENT", CONTENT);
			model.addAttribute("orderItemList", orderItemList);
			model.addAttribute("orderTotalPrice", orderTotalPrice);
			
			//아 넘나 귀찮네여...
			//꼼수이긴한데 1개짜리도 cart에 넣어줄게요
			Cart cart = new Cart();
			//뭐 그전에 cart담긴거 있었으면 걔도 챙겨서 넣어야죠
			if (session.getAttribute("cart") != null) {
				cart = (Cart) session.getAttribute("cart");
			} 
			Cart newCheckedCart = new Cart();
			cart.getOrderItemMap().put(orderItem.getOrderItemId(), orderItem);
			newCheckedCart.getOrderItemMap().put(orderItem.getOrderItemId(), orderItem);
			double totalPrice = cart.getTotalPrice();
			session.setAttribute("totalPrice", totalPrice);
			session.setAttribute("cart", cart);
			session.setAttribute("newCheckedCart", newCheckedCart);
		} else {
			String CONTENT = "customer/auth/login_form.jsp";
			String notLogin = "notLogin";
			model.addAttribute("errorMsg", "로그인을 하지 않았습니다. 로그인을 해주세요.");
			model.addAttribute("notLogin", notLogin);
			model.addAttribute("CONTENT", CONTENT);
		}
		return "template";
	}

	@RequestMapping(value = "/order/payment", method = RequestMethod.POST)
	public String paymentByCart(Model model,
			@RequestParam("orderItemCheckBox") List<String> orderItemIdList,
			@RequestParam("countHidden") List<Integer> quantityList,
			// 옵션도 추가해야함 어떻게 추가하지 막막하네
			HttpSession session) throws NonExistProductException {
		System.out.println("AddOrderController 진입");
		System.out.println(orderItemIdList);
		System.out.println(quantityList);
		List<OrderItem> orderItemList = new ArrayList<>();
		Cart cart = (Cart) session.getAttribute("cart");
		//밑에 for문이 아마 카트 리스트에서 체크한 친구만 찾는거같은데
		//새 카트로 하나 담아줄게요
		Cart newCheckedCart = new Cart();
		//이 for문을 대체 왜이렇게했지??
		//이중 loop이 될필요가없는데 하나에 하나씩 매칭이니까
/*		for (String orderItemId : orderItemIdList) {
			OrderItem orderItem = cart.getOrderItemMap().get(orderItemId);
			for (int quantity : quantityList) {
				orderItem.setQuantity(quantity);
			}
			orderItemList.add(orderItem);
			//새 카트에도 넣어줘요
			newCheckedCart.getOrderItemMap().put(orderItemId, orderItem);
		}
		*/
		for(int i = 0; i<orderItemIdList.size(); i++){
			OrderItem orderItem = cart.getOrderItemMap().get(orderItemIdList.get(i));
			orderItem.setQuantity(quantityList.get(i));
			orderItemList.add(orderItem);
			//새 카트에도 넣어줘요
			newCheckedCart.getOrderItemMap().put(orderItemIdList.get(i), orderItem);
		}
		session.setAttribute("newCheckedCart", newCheckedCart);
 
		//totalPrice를 controller단에서 계산해줄게요
		//double orderTotalPrice = totalPrice;
		double orderTotalPrice = 0;
		for(OrderItem orderItem : orderItemList){
			System.out.println("orderItemQuantity " + orderItem.getQuantity());
			orderTotalPrice += orderItem.getProduct().getPrice() * orderItem.getQuantity();
		}

		/*
		 * for ( quantity : quantityList) { orderItem.setQuantity(quantity);
		 * orderItemList.add(orderItem);
		 * System.out.println("수량이 들어오냐? : "+orderItem);
		 */

		/*
		 * int quantity = orderItem.getQuantity(); double individualPrice =
		 * orderItem.getProduct().getPrice(); double individualTotalPrice =
		 * quantity * individualPrice; orderTotalPrice += individualTotalPrice;
		 */

		// 리스트로 받아야 cart에서 결제할 때도 이용가능
		if (session.getAttribute("customer") != null) { // !=로 변경
			String CONTENT = "customer/order/list_payment.jsp";
			model.addAttribute("CONTENT", CONTENT);
			model.addAttribute("orderItemList", orderItemList);
			model.addAttribute("orderTotalPrice", orderTotalPrice);
		} else {
			String CONTENT = "customer/auth/login_form.jsp";
			String notLogin = "notLogin";
			model.addAttribute("errorMsg", "로그인을 하지 않았습니다. 로그인을 해주세요.");
			model.addAttribute("notLogin", notLogin);
			model.addAttribute("CONTENT", CONTENT);
		}
		return "template";
	}
	
	public void arrangeProductOption(Product product, List<String> optionList) {
		// 넘나 더러웡
		// 여기서 product안의 option을 덮어씌워줄게요
		// 체크 안한 친구는 optionId를 받아와서
		// 일단 그 친구의 객체를 찾아내고
		List<Option> unCheckedOptionList = new ArrayList<>();
		for (Option option : product.getOptionList()) {
			for (String optionId : optionList) {
				if (option.getOptionId().equals(optionId)) {
					unCheckedOptionList.add(option);
				}
			}
		}
		if (!unCheckedOptionList.isEmpty()) {
			// 만약 체크 안한녀석을 넣어둔게 비었다?
			// =모든 옵션을 다 체크했다
			// 체크 안한 옵션이 있음
			for (int i = 0; i < product.getOptionList().size(); i++) {
				for (int j = 0; j < unCheckedOptionList.size(); j++) {
					Option option = product.getOptionList().get(i);
					Option unCheckedOption = unCheckedOptionList.get(j);
					if (option.getOptionId()
							.equals(unCheckedOption.getOptionId())) {
						product.getOptionList().remove(i);
					}
				}
			}
		}
		for (int i = 0; i < product.getOptionList().size(); i++) {
			// 제품의 옵션리스트들을 돌면서 어떤 옵션이
			// 어떤값으로 체크되있는지 확인하고
			// 체크한 값으로만되있는 맵으로 다시 갈아끼우는친구에여ㅜㅜ
			Option option = product.getOptionList().get(i);
			String newKey = optionList.get(i);
			Double newValue = option.getOptionValueMap().get(optionList.get(i));
			Map<String, Double> newOptionValueMap = new HashMap<>();
			newOptionValueMap.put(newKey, newValue);
			product.getOptionList().get(i).setOptionValueMap(newOptionValueMap);
		}
		///////////////////////////////////////////////////////////////
	}
}
