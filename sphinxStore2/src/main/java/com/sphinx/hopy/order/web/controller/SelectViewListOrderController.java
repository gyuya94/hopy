package com.sphinx.hopy.order.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.order.domain.Order;
import com.sphinx.hopy.order.service.OrderService;
import com.sphinx.hopy.util.PageUtil;

@Controller
public class SelectViewListOrderController {

	@Autowired
	private OrderService orderService;

	// 관리자 주문관리
	@RequestMapping(value = "/admin/order/selectViewList", method = RequestMethod.GET)
	public String selectViewListForm(@RequestParam("clickPage") int clickPage,
			Model model) {

		PageUtil pageUtil = new PageUtil();
		// 싱글턴으로 변경하자
		int countOrder = orderService.countOrderByDate(new Date(0), new Date());
		System.out.println("countOrder " + countOrder);
		pageUtil.setCount(countOrder);
		pageUtil.setAmount(5);
		pageUtil.setShowNum(10);
		int count = pageUtil.getCount(); // 모든 업체수
		int amount = pageUtil.getAmount(); // 한페이지에 보여줄 업체 갯수
		int showNum = pageUtil.getShowNum(); // 페이지 번호
		int totalPage = pageUtil.getTotalPage(count, amount); // 페이지 계산
		List<Integer> pageList = pageUtil.getVariablePageList(totalPage,
				clickPage, showNum); // 업체수에 따른 페이지 갯수 설정
		model.addAttribute("clickPage", clickPage);
		model.addAttribute("pageList", pageList);

		List<Order> orderList = orderService.getOrderByDate(new Date(0),
				new Date(), (clickPage - 1) * amount, amount);
		System.out.println("orderList " + orderList);

		model.addAttribute("orderList", orderList);

		String CONTENT = "admin/order/select_view_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	// 관리자 날짜 주문 관리
	@RequestMapping(value = "/admin/order/searchedSelectViewList", method = RequestMethod.GET)
	public String searchedSelectViewList(
			@RequestParam("clickPage") int clickPage,
			@RequestParam("productId") String productId,
			@RequestParam("startDate") String startDateStr,
			@RequestParam("endDate") String endDateStr, Model model) {
		PageUtil pageUtil = new PageUtil();
		// 싱글턴으로 변경하자
		int countOrder = 0;
		pageUtil.setAmount(5);
		pageUtil.setShowNum(10);
		// int clickPage = 1;
		int amount = pageUtil.getAmount(); // 한페이지에 보여줄 업체 갯수
		List<Order> orderList = new ArrayList<>();

		if (startDateStr == null || startDateStr.length() == 0
				|| endDateStr == null || endDateStr.length() == 0) {
			// 1. 어느 날짜도 없음 = 제품으로만 검색하겠다는거
			countOrder = orderService.countOrderByProductId(productId);
			System.out.println("countOrder " + countOrder);

			orderList = orderService.getOrderByProductId(productId,
					(clickPage - 1) * amount, amount);
		} else {
			// 2. 날짜가 있긴 있는데
			// 제품정보가 있는지 알아보고 조합해야됭
			System.out.println("productId?? " + productId);
			System.out.println("startDateStr ?? " + startDateStr);
			System.out.println("endDateStr ?? " + endDateStr);

			Date startDate = stringToDate(startDateStr);
			Date endDate = stringToDate(endDateStr);

			System.out.println("startDate " + startDate);
			System.out.println("endDate " + endDate);
		}

		pageUtil.setCount(countOrder);
		int count = pageUtil.getCount(); // 모든 업체수
		int showNum = pageUtil.getShowNum(); // 페이지 번호
		int totalPage = pageUtil.getTotalPage(count, amount); // 페이지 계산

		List<Integer> pageList = pageUtil.getVariablePageList(totalPage,
				clickPage, showNum); // 업체수에 따른 페이지 갯수 설정
		model.addAttribute("clickPage", clickPage);
		model.addAttribute("pageList", pageList);

		System.out.println("orderList " + orderList);

		model.addAttribute("orderList", orderList);

		String CONTENT = "admin/order/select_view_list.jsp";
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	// 사용자 주문관리 페이지 이동
	@RequestMapping(value = "/customer/order_inquiry", method = RequestMethod.GET)
	public String orderInquiry(Model model, HttpSession session) {
		String CONTENT = "customer/order/select_view_list.jsp";
		List<String> errorMsg = null;
		Date nowDate = new Date();
		Customer customer = (Customer) session.getAttribute("customer");
		System.out.println("customer 출력 : " + customer);
		long longDate = 2419199999l;
		long ageDateLong = nowDate.getTime()-longDate;
		Date ageDate = new Date(ageDateLong);
		System.out.println(ageDate);
		String customerId = customer.getCustomerId();
		int orderCount = orderService.countOrderByCustomerId(customerId);
		System.out.println("주문갯수 : " + orderCount);
		int clickPage = 1;
		PageUtil pageUtil = new PageUtil();
		pageUtil.setAmount(5);
		pageUtil.setCount(orderCount);
		pageUtil.setShowNum(10);
		int showNum = pageUtil.getShowNum();
		int count = pageUtil.getCount();
		int amount = pageUtil.getAmount();
		int totalPage = pageUtil.getTotalPage(count, amount); // 페이지 계산
		List<Integer> pageList = pageUtil.getVariablePageList(totalPage,
				clickPage, showNum); // 업체수에 따른 페이지 갯수 설정
		List<Order> orderList = orderService.getOrderByCustomerId(ageDate,
				nowDate, customerId, (clickPage - 1) * amount, amount);
		System.out.println("주문의 갯수: " + orderList.size());
		System.out.println("주문 : "+orderList);
		if (orderList.size() == 0) {
			errorMsg = new ArrayList<>();
			errorMsg.add("주문내역이 존재하지 않습니다.");
		}
		model.addAttribute("orderList", orderList);
		model.addAttribute("clickPage", clickPage);
		model.addAttribute("pageList", pageList);
		model.addAttribute("date", new java.sql.Date(nowDate.getTime()));
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	// 사용자 주문관리 페이지 이동 후 값 전달 받은 method
	@RequestMapping(value = "/customer/order/select_view_list", method = RequestMethod.POST)
	public String viewOrdersByDate(
			@RequestParam("customerId") String customerId,
			@RequestParam("startDate") String startDateStr,
			@RequestParam("endDate") String endDateStr,
			@RequestParam("clickPage") int clickPage,
			@RequestParam("date") java.sql.Date date, Model model)

			throws ParseException {
		List<String> errorMsg = null;
		// Date에 new를 안했는데도 들어갈까?
		System.out.println("clickPage : " + clickPage);
		System.out.println("startDateStr : " + startDateStr);
		System.out.println("endDateStr : " + endDateStr);
		System.out.println("date : " + date);
		Date startDate = null;
		Date endDate = null;
		// startDateStr, endDateStr 날짜 진입 시 판단 시작
		if (startDateStr.equals("") && endDateStr.equals("")) {
			// 날짜를 아무것도 입력 안했을 경우
			System.out.println("날짜를 아무것도 입력 안했을 경우");
			startDate = new Date(0001 - 01 - 01);
			endDate = new Date();
		} else if (startDateStr.equals("") || endDateStr.equals("")) {
			// 하나라도 날짜를 입력했을 경우
			if (startDateStr.equals("")) {
				System.out.println("끝 날짜만 입력했을 경우");
				// 끝 날짜만 입력했을 경우
				startDate = new Date(0001 - 01 - 01);
				endDate = stringToDate(endDateStr);
			} else {
				// 시작날짜만 입력했을 경우
				System.out.println("시작날짜만 입력했을 경우");
				startDate = stringToDate(startDateStr);
				endDate = new Date();
			}
		} else {
			// 시작날짜 끝날짜 다 입력했을 경우
			System.out.println("시작날짜 끝날짜 다 입력 했을 경우");
			System.out.println("다 입력 진입한 스타트 날짜 : " + startDateStr);
			System.out.println("다 입력 진입한 끝 날짜 : " + endDateStr);
			startDate = stringToDate(startDateStr);
			endDate = stringToDate(endDateStr);
		}
		System.out.println("최종적인 시작날짜 : " + startDate);
		System.out.println("최종적인 끝 날짜 : " + endDate);
		boolean result = validation(startDate, endDate);
		if (result) {
			// 유효성 검사가 통과 됐을 때 (시작날짜<끝날짜)
			int orderCount = orderService.countOrderByCustomerId(customerId);
			System.out.println("주문갯수 : " + orderCount);
			PageUtil pageUtil = new PageUtil();
			pageUtil.setAmount(5);
			pageUtil.setCount(orderCount);
			pageUtil.setShowNum(10);
			int showNum = pageUtil.getShowNum();
			int count = pageUtil.getCount();
			int amount = pageUtil.getAmount();
			int totalPage = pageUtil.getTotalPage(count, amount); // 페이지 계산
			List<Integer> pageList = pageUtil.getVariablePageList(totalPage,
					clickPage, showNum); // 업체수에 따른 페이지 갯수 설정
			List<Order> orderList = orderService.getOrderByCustomerId(startDate,
					endDate, customerId, (clickPage - 1) * amount, amount);
			System.out.println("orderList : " + orderList);
			if (orderList.size() == 0) {
				errorMsg = new ArrayList<>();
				errorMsg.add("해당 날짜에는 주문내역이 존재하지 않습니다.");
			}
			
			model.addAttribute("orderList", orderList);
			model.addAttribute("clickPage", clickPage);
			model.addAttribute("pageList", pageList);
		} else {
			errorMsg = new ArrayList<>();
			errorMsg.add("날짜를 잘못 입력했습니다.");
			errorMsg.add("ex) 2018-10-30 ~ 2018-09-30");
		}

		String CONTENT = "customer/order/select_view_list.jsp";
		model.addAttribute("date", date);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("startDateStr", startDateStr); // 처음 입력한 날짜를 저장하기 위해
															// add함
		model.addAttribute("endDateStr", endDateStr); // 처음 입력한 날짜를 저장하기 위해 add함
		model.addAttribute("CONTENT", CONTENT);
		return "template";
	}

	public Date stringToDate(String dateStr) {

		Date date = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = transFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	//날짜 유효성검사
	public boolean validation(Date startDate, Date endDate) {
		long startDateLong = startDate.getTime();
		long endDateLong = endDate.getTime();
		if (startDateLong <= endDateLong) {
			return true;
		}
		return false;
	}
}
