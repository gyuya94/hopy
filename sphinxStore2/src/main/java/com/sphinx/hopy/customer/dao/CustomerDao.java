package com.sphinx.hopy.customer.dao;

import com.sphinx.hopy.customer.domain.Customer;
import com.sphinx.hopy.customer.exception.NonExistCustomerException;

public interface CustomerDao {
	public void addCustomer(Customer customer);
	
	public void updateCustomer(Customer customer);
	
	public void deleteCustomer(Customer customer);
	
	/** 사용자의 계정(sphinxId)로 계정찾아옴*/
	
	public Customer getCustomerBySphinxId(String sphinxId) throws NonExistCustomerException;
	
	/** 우리가 준 PK인 customerId로 Customer를 찾아옴*/
	public Customer getCustomerByCustomerId(String customerId);
	
	/** 다음 customerId를 가져옴, 근데 지금은 그냥 MAX(customerId)로 하고있어요*/
	public String getNextCustomerId();
}
