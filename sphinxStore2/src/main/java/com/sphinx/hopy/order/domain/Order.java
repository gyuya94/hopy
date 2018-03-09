package com.sphinx.hopy.order.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.sphinx.hopy.customer.domain.Customer;

public class Order {
	private String orderId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderDate;// 주문일
	private List<OrderItem> orderItemList;
	private String state;// 주문상태(예:상품준비중, 배송준비중 등등)
	private double totalPrice;//총가격
	private String customerId;//주문자Id
	//private Customer customer;
	private boolean del = false;//삭제여부
	private String recipient;//수령인
	private String recipientAddr;//수령주소
	private String memo;//주문 배송에 대한 간략한 메모

	public Order(String orderId, Date orderDate, List<OrderItem> orderItemList,
			String state, double totalPrice, String customerId, boolean del,
			String recipient, String recipientAddr, String memo) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.orderItemList = orderItemList;
		this.state = state;
		this.totalPrice = totalPrice;
		this.customerId = customerId;
		this.del = del;
		this.recipient = recipient;
		this.recipientAddr = recipientAddr;
		this.memo = memo;
	}

	public Order(String orderId, Date orderDate, String state,
			double totalPrice, String customerId, boolean del, String recipient,
			String recipientAddr, String memo) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.state = state;
		this.totalPrice = totalPrice;
		this.customerId = customerId;
		this.del = del;
		this.recipient = recipient;
		this.recipientAddr = recipientAddr;
		this.memo = memo;
	}

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getRecipientAddr() {
		return recipientAddr;
	}

	public void setRecipientAddr(String recipientAddr) {
		this.recipientAddr = recipientAddr;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate
				+ ", orderItemList=" + orderItemList + ", state=" + state
				+ ", totalPrice=" + totalPrice + ", customerId=" + customerId
				+ ", del=" + del + ", recipient=" + recipient
				+ ", recipientAddr=" + recipientAddr + ", memo=" + memo + "]";
	}

}
