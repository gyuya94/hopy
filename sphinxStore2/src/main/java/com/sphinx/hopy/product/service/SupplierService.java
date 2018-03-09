package com.sphinx.hopy.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sphinx.hopy.product.domain.Supplier;

public interface SupplierService {
	public void addSupplier(Supplier supplier);

	public void deleteSupplier(String supplierId);

	public void updateSupplier(Supplier supplier);

	public Supplier findSupplierById(String supplierId);

	public List<Supplier> findSupplierByName(String supplierName);

	/**
	 * 업체를 페이징해서 가져옴, start: 시작하는 번째, amount: 몇개를 가져올건지
	 */
	public List<Supplier> getSupplierListByPaging(int start, int amount);

	public int countSupplier();
}
