package com.sphinx.hopy.category.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;

public class Category {
	private String categoryId;
	private String categoryName;
	private List<Category> subCategory = new ArrayList<>();
	private String parentId;
	private boolean del = false;
	private String group;

	public Category() {

	}
	
	
	/*Date date = changeStringToDate(request.getParameter("regDate"));*/

	public Category(String categoryId, String categoryName,
			List<Category> subCategory, String parentId, boolean del,
			String group) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.subCategory = subCategory;
		this.parentId = parentId;
		this.del = del;
		this.group = group;
	}

	public Category(String categoryId) {
		super();
		this.categoryId = categoryId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Category> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<Category> subCategory) {
		this.subCategory = subCategory;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", subCategory=" + subCategory + ", parentId="
				+ parentId + ", del=" + del + ", group=" + group + "]";
	}

}
