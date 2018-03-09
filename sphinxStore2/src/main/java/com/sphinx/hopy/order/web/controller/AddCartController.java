package com.sphinx.hopy.order.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sphinx.hopy.category.domain.Category;
import com.sphinx.hopy.category.service.CategoryService;
import com.sphinx.hopy.order.domain.Cart;
import com.sphinx.hopy.order.domain.OrderItem;
import com.sphinx.hopy.order.service.OrderService;
import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.domain.Product;
import com.sphinx.hopy.product.exception.NonExistProductException;
import com.sphinx.hopy.product.service.ProductService;

@Controller
public class AddCartController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/cart/addCart", method = RequestMethod.POST)
	public String addCart(@RequestParam("productId") String productId,
			@RequestParam("option") List<String> optionList,
			HttpServletRequest request, Model model, HttpSession session,
			@RequestParam("quantity") int quantity) {
		Product product = null;
		System.out.println(optionList);
		try {
			product = productService.getProductByProductId(productId);
			Product newOptionProduct = productService.getProductByProductId(productId);
			/** option친구들 정리하는 아이에요 */
			if(! optionList.get(0).equals("non")) {
				// optionList.get(0).equals("non") 
				//이 제품에는 option이 없다는 거에여
				// 앞에 !붙였으니 옵션이 있는 친구들만 해주겠다는거져
				arrangeProductOption(newOptionProduct, optionList);
			}
				

			// 임시 카테고리 설정
			Category category = categoryService.getCategoryById("cate01");
			List<Category> categoryList = new ArrayList<>();
			categoryList.add(category);
			product.setCategoryList(categoryList);
			Cart cart = new Cart();
			OrderItem orderItem = new OrderItem();
			//이거 수정해줘야하네
			orderItem.setOrderItemId(orderService.getNewOrderItemId());
			orderItem.setProduct(newOptionProduct);
			orderItem.setQuantity(quantity);

			// cart에 담을 때는 1개만 담김
			cart.addOrderItem(orderItem);
			if (session.getAttribute("cart") == null) {
				session.setAttribute("cart", cart);
				double totalPrice = cart.getTotalPrice();
				session.setAttribute("totalPrice", totalPrice);
			} else {
				Cart cartTmp = (Cart) session.getAttribute("cart");
				//음 만약 추가하려는게 옵션이 같은 친구면
				//수량을 합쳐줘야 하는게 맞겠죠?
				boolean isAlreadyIn = compareCartAndOrderItem(cartTmp, orderItem);
				if(isAlreadyIn) {
					//이미 같은 option으로 있으면
					//걔의 수량을 늘려주고
					
				}else {
					//아님 그냥 넣어줍니당
					cartTmp.addOrderItem(orderItem);
				}
				
				double totalPrice = cartTmp.getTotalPrice();
				session.setAttribute("cart", cartTmp);
				session.setAttribute("totalPrice", totalPrice);
			}
			
			String CONTENT = "customer/product/view_detail.jsp";
			model.addAttribute("product", product);
			model.addAttribute("CONTENT", CONTENT);
		} catch (NonExistProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "template";
	}

	public void arrangeProductOption(Product product, List<String> optionList) {
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
	
	/** cart에 담긴 orderItem에 동일한 option으로 또 다시 담으려는지 비교
	 * false : cart에 동일한거 없음
	 * true : 똑같은게 있음 */
	public boolean compareCartAndOrderItem(Cart cart, OrderItem orderItem) {
		boolean isAlreadyIn = false;
		//그럼 cart에 어떤 친구들이 담겼는지 확인해봐야해요
		Set<String> orderItemIdSet = cart.getOrderItemMap().keySet();
		String detectedOrderItemId = "";
		for(String orderItemId : orderItemIdSet) {
			//한 친구씩 꺼내서 그 친구의 옵션이 뭔지 확인해봅니다
			OrderItem cartOrderItem = cart.getOrderItemMap().get(orderItemId);
			//일단 제일 간단하게 비교해볼수 있는게
			//옵션이 몇개인지로 먼저 거를수 있겠죠?
			if(cartOrderItem.getProduct().getOptionList().size()
					== orderItem.getProduct().getOptionList().size()) {
				//일단 옵션갯수가 같으면 그 안의 값도 같은지 비교해봅시당
				for(int i=0; i<orderItem.getProduct().getOptionList().size(); i++) {
					//
					Option cartOption = cartOrderItem.getProduct().getOptionList().get(i);
					Option option = orderItem.getProduct().getOptionList().get(i);
					if(cartOption.getOptionValueMap().keySet().equals(
							option.getOptionValueMap().keySet())) {
						//keySet채로 비교가 되는지 모르겠네요
						isAlreadyIn = true;
						//어떤 orderItem이 중복됬는지 체크해둘거에요
						detectedOrderItemId = orderItemId;
					}else {
						isAlreadyIn = false;
						//아 여기서 break;해줘야해염
						break;
					}
					
				}
				//위에 for문에서 빠져나왔다?
				//어떤 한 orderItem에 대해서 비교가 끝남
				
				//음 그리구 아예 option이 없는 얘들도 합쳐줘야해요
				if(cartOrderItem.getProduct().getProductId().equals(orderItem.getProduct().getProductId())) {
					//같은 제품이고
					if(cartOrderItem.getProduct().getOptionList().isEmpty()) {
						//옵션이 없는 제품이면
						isAlreadyIn = true;
						detectedOrderItemId = orderItemId;
						//얘도 이미 있는거로 해야겠죠
					} 
				}
				if(isAlreadyIn) {
					//그런데 for를 계속돌면
					//cart에 담긴 다른친구 또 비교하니까 여기서 탈출해야해
					break;
				}
			}
			
		}
		
		if(isAlreadyIn) {
			//수량수정을 여기서 그냥 해줄게염
			OrderItem cartOrderItem = cart.getOrderItemMap().get(detectedOrderItemId);
			//새로 업데이트되야할 수량은 기존 cart에 있던 친구의 수량 + 새로 담은친구
			System.out.println(cartOrderItem);
			System.out.println(orderItem);
			System.out.println("cartOrderItem.getQuantity() " + cartOrderItem.getQuantity());
			System.out.println("orderItem.getQuantity() " + orderItem.getQuantity());
			int newQuantity = cartOrderItem.getQuantity() + orderItem.getQuantity();
			cartOrderItem.setQuantity(newQuantity);
		}
		
		return isAlreadyIn;
	}
}
