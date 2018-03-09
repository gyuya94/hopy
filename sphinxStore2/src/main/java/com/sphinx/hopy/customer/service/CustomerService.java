package com.sphinx.hopy.customer.service;

import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.customer.exception.NonExistCustomerException;

public interface CustomerService {
	public void addCustomer(Customer customer);
	
	public void updateCustomer(Customer customer);
	
	public void deleteCustomer(Customer customer);
	
	/** 사용자의 계정(sphinxId)로 계정찾아옴
	 * @throws NonExistCustomerException */
	public Customer getCustomerBySphinxId(String sphinxId) throws NonExistCustomerException;
	
	/** 우리가 준 PK인 customerId로 Customer를 찾아옴*/
	public Customer getCustomerByCustomerId(String customerId);
	
	//public String getNextCustomerId();
}
