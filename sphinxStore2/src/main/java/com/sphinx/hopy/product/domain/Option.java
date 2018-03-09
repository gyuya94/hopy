package com.sphinx.hopy.product.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Option {
	private String optionId;
	private String optionName;// 예: 사이즈, 색상 등등
	private Map<String, Double> optionValueMap = new LinkedHashMap<>();
	private boolean del;// 삭제여부

	public Option(String optionId, String optionName,
			Map<String, Double> optionValueMap, boolean del) {
		super();
		this.optionId = optionId;
		this.optionName = optionName;
		this.optionValueMap = optionValueMap;
		this.del = del;
	}

	public Option(String optionId, String optionName, boolean del) {
		super();
		this.optionId = optionId;
		this.optionName = optionName;
		this.del = del;
	}

	public Option() {
		super();
	}

	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public Map<String, Double> getOptionValueMap() {
		return optionValueMap;
	}

	public void setOptionValueMap(Map<String, Double> optionValueMap) {
		this.optionValueMap = optionValueMap;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	@Override
	public String toString() {
		return "Option [optionId=" + optionId + ", optionName=" + optionName
				+ ", optionValueMap=" + optionValueMap + ", del=" + del + "]";
	}

}
