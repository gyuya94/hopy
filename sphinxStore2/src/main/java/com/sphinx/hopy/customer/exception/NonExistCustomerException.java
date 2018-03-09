package com.sphinx.hopy.customer.exception;

public class NonExistCustomerException extends Exception {

	public NonExistCustomerException() {
		super("존재하지 않는 고객");
	}
}
