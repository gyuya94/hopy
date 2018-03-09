package com.sphinx.hopy.product.dao;

import java.util.List;

import com.sphinx.hopy.product.domain.Supplier;

public interface SupplierDao {

	public void addSupplier(Supplier supplier);
	
	public void deleteSupplier(String supplierId);
	
	public void updateSupplier(Supplier supplier);
	
	public Supplier findSupplierById(String supplierId);
	
	public List<Supplier> findSupplierByName(String supplierName);
	
	public List<Supplier> getSupplierListByPaging(int start, int amount);
	
	public int countSupplier();
}
