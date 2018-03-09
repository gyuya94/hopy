package com.sphinx.hopy.product.dao;

import java.util.List;

import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.exception.NonExistOptionException;

public interface OptionDao {
	public void addOption(Option option, String productId);
	
	public void updateOption(Option option);
	
	public void deleteOption(Option option);
	
	public Option getOptionById(String optionId) throws NonExistOptionException;
	
	public List<Option> getOptionListByProductId(String productId) throws NonExistOptionException;
}
