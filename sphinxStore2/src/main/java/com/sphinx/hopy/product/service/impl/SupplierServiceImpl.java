package com.sphinx.hopy.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sphinx.hopy.product.dao.SupplierDao;
import com.sphinx.hopy.product.domain.Supplier;
import com.sphinx.hopy.product.service.SupplierService;
import com.sphinx.hopy.util.idGenerator.IdGenerator;
import com.sphinx.hopy.util.idGenerator.service.IdGeneratorService;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	private IdGenerator idGenerator;
	
	@Override
	public void addSupplier(Supplier supplier) {
		if(supplier.getSupplierId() == null) {
			String usableId = idGeneratorService.getUsableId(idGenerator);
			supplier.setSupplierId(usableId);
		}
		supplierDao.addSupplier(supplier);
	}

	public void deleteSupplier(String supplierId) {
		supplierDao.deleteSupplier(supplierId);
	}

	public void updateSupplier(Supplier supplier) {
		supplierDao.updateSupplier(supplier);
	}

	@Override
	public Supplier findSupplierById(String supplierId) {
		return supplierDao.findSupplierById(supplierId);
	}

	@Override
	public List<Supplier> findSupplierByName(String supplierName) {
		return supplierDao.findSupplierByName(supplierName);
	}

	@Override
	public List<Supplier> getSupplierListByPaging(int start, int amount) {
		return supplierDao.getSupplierListByPaging(start, amount);
	}

	@Override
	public int countSupplier() {
		int countSupplier = supplierDao.countSupplier();
		return countSupplier;
	}
	
	public void init() {
		this.idGenerator = new IdGenerator();
		idGenerator.setPrefix("SUPP");
		idGenerator.setIdLength(10);
		idGenerator.setTableName("supplierIdTable");
	}
}
