package com.sphinx.hopy.product.domain;

public class Supplier {
	private String supplierId;
	private String supplierName;
	private String phone;
	private boolean del;

	public Supplier(String supplierId, String supplierName, String phone,
			boolean del) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.phone = phone;
		this.del = del;
	}
	
	public Supplier() {
		super();
	}



	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierName="
				+ supplierName + ", phone=" + phone + ", del=" + del + "]";
	}

}
