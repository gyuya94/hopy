package com.sphinx.hopy.product.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sphinx.hopy.category.domain.Category;

public class Product {
	private String productId;
	private String productName;
	private double price;// 원가?판매가격?
	private List<Category> categoryList;// 이제 한 제품은 여러 카테고리를 가져요
	private List<Option> optionList;// 제품의 옵션들
	private Date regDate;// 제품등록날짜
	private boolean del;// 삭제여부
	private String content;// 상품설명
	private List<String> fileNameList;// 상품 메인 이미지 경로
	private String comment;// 상품에 대한 짧은 설명
	private Supplier supplier;

	public Product(String productId, String productName, double price,
			List<Category> categoryList, List<Option> optionList, Date regDate,
			boolean del, String content, List<String> fileNameList,
			String comment) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.categoryList = categoryList;
		this.optionList = optionList;
		this.regDate = regDate;
		this.del = del;
		this.content = content;
		this.fileNameList = fileNameList;
		this.comment = comment;
	}

	public Product(String productId, String productName, double price,
			Date regDate, boolean del, String content, String comment) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.regDate = regDate;
		this.del = del;
		this.content = content;
		this.comment = comment;
	}

	public Product() {
		super();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Option> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/** DB에 넣을용으로 합쳐서 하는거*/
	public String getCombineFileName() {
		String conbineFileName = "";
		//String 하나로 다 합쳐줍시당
		int cnt = fileNameList.size();
		if(fileNameList!= null) {
			for(int i=0; i<cnt; i++) {
				//마지막번째 얘를 알아서 , 안붙이려하니 이렇게 해야하넹
				conbineFileName += fileNameList.get(i);
				if(i < cnt-1) {
					conbineFileName += ", ";
				}
			}
		}
		return conbineFileName;
	}
	
	/** DB에 받아온거 쪼개주는 함수*/
	public void setSpliteFileName(String fileName){
		fileNameList = new ArrayList<>();//할당해줘야하넹
		String[] splitedStringArr = fileName.split(",");
		for(String str : splitedStringArr) {
			fileNameList.add(str.trim());
		}
	}
	
	
	public List<String> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName="
				+ productName + ", price=" + price + ", categoryList="
				+ categoryList + ", optionList=" + optionList + ", regDate="
				+ regDate + ", del=" + del + ", content=" + content
				+ ", fileNameList=" + fileNameList + ", comment=" + comment
				+ "]";
	}

}
