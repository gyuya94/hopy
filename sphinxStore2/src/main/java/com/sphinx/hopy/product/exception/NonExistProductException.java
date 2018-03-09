package com.sphinx.hopy.product.exception;

public class NonExistProductException extends Exception {
	public NonExistProductException() {
		super("존재하지 않는 제품");
	}
	
}
