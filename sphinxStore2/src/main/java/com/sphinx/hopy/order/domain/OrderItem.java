package com.sphinx.hopy.order.domain;

import com.sphinx.hopy.product.domain.Product;

public class OrderItem {
	private String orderItemId;
	private Product product;
	private int quantity;//수량정보
	private boolean del;//삭제여부

	public OrderItem(String orderItemId, Product product, int quantity,
			boolean del) {
		super();
		this.orderItemId = orderItemId;
		this.product = product;
		this.quantity = quantity;
		this.del = del;
	}

	public OrderItem() {
		// TODO Auto-generated constructor stub
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", product=" + product
				+ ", quantity=" + quantity + ", del=" + del + "]";
	}

}
