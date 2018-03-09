package com.sphinx.hopy.client;

import java.util.HashMap;
import java.util.Map;

import com.sphinx.hopy.order.domain.Cart;

public class Test {
	 
	public static void main(String[] args) {
		Map <String, String> orderItemMap = new HashMap<>();
		orderItemMap.put("1", "1번입니다");
		orderItemMap.put("1", "2번입니다");
		orderItemMap.remove("1");
		System.out.println(orderItemMap.get("1"));
		Cart cart = new Cart();
	}

}
