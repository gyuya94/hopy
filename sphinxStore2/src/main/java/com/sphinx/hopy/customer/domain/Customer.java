package com.sphinx.hopy.customer.domain;

import java.util.Date;

public class Customer {
	private String customerId;// PK, 순차적으로 증가하는 id
	private boolean isAdmin;// 관리자인지 여부
	private String sphinxId;// 실제 사용자들이 입력한 계정Id
	private String password;
	private boolean del;
	private String name;
	private String phone;
	private String address;// 주소
	private Date joinDate;// 가입일

	public Customer(String customerId, boolean isAdmin, String sphinxId,
			String password, boolean del, String name, String phone,
			String address, Date joinDate) {
		super();
		this.customerId = customerId;
		this.isAdmin = isAdmin;
		this.sphinxId = sphinxId;
		this.password = password;
		this.del = del;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.joinDate = joinDate;
	}

	public Customer() {
		super();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getSphinxId() {
		return sphinxId;
	}

	public void setSphinxId(String sphinxId) {
		this.sphinxId = sphinxId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", isAdmin=" + isAdmin
				+ ", sphinxId=" + sphinxId + ", password=" + password + ", del="
				+ del + ", name=" + name + ", phone=" + phone + ", address="
				+ address + ", joinDate=" + joinDate + "]";
	}

}
