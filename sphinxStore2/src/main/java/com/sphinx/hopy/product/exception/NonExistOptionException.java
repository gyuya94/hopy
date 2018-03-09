package com.sphinx.hopy.product.exception;

public class NonExistOptionException extends Exception {
	public NonExistOptionException() {
		super("존재하지 않는 옵션");
	}
}
