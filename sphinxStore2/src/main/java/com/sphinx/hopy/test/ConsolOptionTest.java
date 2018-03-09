package com.sphinx.hopy.test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.sphinx.hopy.product.dao.OptionDao;
import com.sphinx.hopy.product.domain.Option;
import com.sphinx.hopy.product.exception.NonExistOptionException;

public class ConsolOptionTest {

	public static void main(String[] args) {
		// bean설정
		String config = "com/sphinx/hopy/test/test.xml";
		AbstractApplicationContext context = new GenericXmlApplicationContext(
				config);
		OptionDao optionDao = context.getBean("optionDao", OptionDao.class);
		
		Option option = new Option("OPT_000004", "사이즈", false);
		Map<String, Double> optionValueMap = new LinkedHashMap<>();
		optionValueMap.put("S", -1000.0);
		optionValueMap.put("M", 0.0);
		optionValueMap.put("L", 1000.0);
		option.setOptionValueMap(optionValueMap);
		
		System.out.println("---addOption----");
		//optionDao.addOption(option, "P_0002_PNK");
		
		System.out.println("---getOption----");
		try {
			System.out.println(optionDao.getOptionById("OPT_000004"));
			
			
			System.out.println("---updateOption----");
			option.setOptionName("이름도 바꾸고요");
			optionValueMap.put("L", 2000.0);
			option.setOptionValueMap(optionValueMap);
			optionDao.updateOption(option);
			System.out.println(optionDao.getOptionById("OPT_000004"));
			
			System.out.println("---getOptionList----");
			List<Option> optionList = optionDao.getOptionListByProductId("P_0002_PNK");
			for(Option opt : optionList) {
				System.out.println(opt);
			}
			
			
		} catch (NonExistOptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
