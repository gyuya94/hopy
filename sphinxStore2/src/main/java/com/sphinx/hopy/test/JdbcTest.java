/*package com.sphinx.hopy.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.sphinx.hopy.order.domain.Order;
import com.sphinx.hopy.order.domain.OrderItem;
import com.sphinx.hopy.order.service.OrderService;
import com.sphinx.hopy.product.domain.Product;

public class JdbcTest {

	public static void main(String[] args) {
		// bean설정
		String config = "com/sphinx/hopy/test/test.xml";
		AbstractApplicationContext context = new GenericXmlApplicationContext(
				config);
		
		 * SupplierDao supplierDao = context.getBean("supplierDao",
		 * SupplierDao.class);
		 
		OrderService orderService = context.getBean("orderService",
				OrderService.class);
		Order order = new Order();
		order.setCustomerId("cust01");
		order.setRecipientAddr("주소오오");
		order.setOrderDate(new Date());
		order.setOrderId("주문11");
		order.setState("배송중");
		order.setTotalPrice(30000);
		order.setRecipient("hoho");
		order.setMemo("경비실에 맡겨주세요");
		// 주문 추가
		 orderService.addOrder(order); 
		// 주문 수정
		// orderService.updateOrder(order);
		// 주문 삭제
		// orderService.deleteOrder(order);
		// orderId로 주문 가져오기
		// Order orderTest = orderService.getOrderByOrderId("주문9");
		

		//controller 단에서 실행해야함
		String date = "2017-01-01";
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date to =null;
		try {
			to = transFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// customerId로 order 페이징
		//List<Order> orderTest = orderService.getOrderByCustomerId(to, new Date(), "cust01", 0, 5);
		// 관리자모드 페이징
		//List<Order> orderTest =orderService.getOrderByDate(to, new Date(), 0, 5);
		Product product = new Product();
		product.setProductId("P_0001_BLK");
		product.setProductName("짱 귀여운 고양이 옷");
		product.setPrice(200000);
		product.setRegDate(new Date());
		product.setContent("경로와 글");
		product.setFileName("고양이 옷");
		product.setComment("이걸 입을 고양이는 슈퍼스타!!");
		OrderItem orderItem = new OrderItem("oitem2", product, 2, false);
		
		//orderItem 추가
		//orderService.addOrderItem(orderItem, "주문1");
		System.out.println(orderService.getOrderItemListByOrderId("주문1"));
		// 업체 추가
		
		 * Supplier testSupp = new Supplier("SUPP_00001", "제품업체1",
		 * "01067611840"); System.out.println(testSupp);
		 * supplierDao.addSupplier(testSupp);
		 

		
		 * System.out.println("------------------------");
		 * System.out.println(supplierDao.findSupplierById("SUPP_00001"));
		 

	}

}*/
