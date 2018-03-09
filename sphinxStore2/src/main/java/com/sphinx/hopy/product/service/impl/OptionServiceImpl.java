package com.sphinx.hopy.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sphinx.hopy.product.dao.OptionDao;
import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.exception.NonExistOptionException;
import com.sphinx.hopy.product.service.OptionService;
import com.sphinx.hopy.util.idGenerator.IdGenerator;
import com.sphinx.hopy.util.idGenerator.service.IdGeneratorService;
import com.sphinx.hopy.util.idGenerator.service.impl.IdGeneratorServiceImpl;

@Service
public class OptionServiceImpl implements OptionService {

	@Autowired
	private OptionDao optionDao;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	private IdGenerator idGenerator;
	
	@Override
	public void addOption(Option option, String productId) {
		//optionId가 없으면 넣어줍시당
		if(option.getOptionId()==null) {
			String usableId = idGeneratorService.getUsableId(idGenerator);
			option.setOptionId(usableId);
		}
		optionDao.addOption(option, productId);
	}

	@Override
	public void updateOption(Option option) {
		optionDao.updateOption(option);
	}

	@Override
	public void deleteOption(Option option) {
		optionDao.deleteOption(option);
	}

	@Override
	public Option getOptionById(String optionId) throws NonExistOptionException {
		return optionDao.getOptionById(optionId);
	}

	@Override
	public List<Option> getOptionListByProductId(String productId) throws NonExistOptionException {
		return optionDao.getOptionListByProductId(productId);
	}
	
	public void init() {
		this.idGenerator = new IdGenerator();
		idGenerator.setPrefix("OPT_");
		idGenerator.setIdLength(10);
		idGenerator.setTableName("optionIdTable");
		
	}

}
