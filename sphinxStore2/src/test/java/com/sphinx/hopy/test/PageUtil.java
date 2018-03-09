package com.sphinx.hopy.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class PageUtil {
	private int count = 0; // 총 주문수
	private int amount = 0; // 자료를 보여줄 갯수
	private int totalPage = 0; // 총 페이지 수
	private int showNum = 0; // 10개씩 보여주기(10page보여주기)페이지 보여줄 숫자

	public PageUtil() {

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<Integer> getOffset(int amount, int totalPage) {
		List<Integer> offsetList = new ArrayList<>();
		int offset = 0;
		for (int page = 1; page <= totalPage; page++) {
			offset = amount * page - amount;
			// offset = amount(page-1); //안됨
			offsetList.add(offset);
		}
		return offsetList;
	}

	public int getMiddlePage(int showNum) {
		int middle = 0;
		if (showNum % 2 == 0) {
			middle = showNum / 2;
		} else {
			middle = showNum / 2 + 1;
		}
		return middle;
	}

	public List<Integer> getVariablePageList(int totalPage, int clickPage,
			int showNum) {
		// clickPage 입력한 page
		List<Integer> variablePage = new ArrayList<>();
		int middle = getMiddlePage(showNum);
		// middle = 5
		if (totalPage > showNum) { // 총 페이지가 10(showNum)보다 클 경우
			// middle이 바뀌어야함
			if (clickPage > middle) {
				// 총 페이지가 10(showNum)보다 크고 클릭한 페이지가 middle보다 클 경우 middle을 옮겨야함
				for (int newPage = clickPage
						- (middle - 1); newPage <= clickPage
								+ middle; newPage++) {
					/*
					 * ex)middle=5, clickPage=9 이면, 처음페이지는 6 마지막 페이지는 14
					 */
					if (newPage <= totalPage) {
						variablePage.add(newPage);
					}
				}
				middle = clickPage;
			} else {
				// 중간보다 작은 숫자를 눌렀을 경우
				for (int newPage = 1; newPage <= showNum; newPage++) {
					variablePage.add(newPage);
				}
			}
		} else {
			// 총페이지가 10보다 작을 경우
			for (int newPage = 1; newPage <= totalPage; newPage++) {
				variablePage.add(newPage);
			}
		}
		return variablePage;
	}

	public int getTotalPage(int count, int amount) {
		if (count % amount == 0) {
			totalPage = count / amount;
		} else {
			totalPage = count / amount + 1;
		}
		return totalPage;
	}

	public static void main(String[] args) {
		PageUtil page = new PageUtil();
		page.setCount(90);
		page.setAmount(5);
		int amount = page.getAmount();
		int count = page.getCount();
		page.setShowNum(10);
		int showNum = page.getShowNum();
		int middle = page.getMiddlePage(showNum);
		int totalPage = page.getTotalPage(count, amount);
		List<Integer> offsetList = page.getOffset(amount, totalPage);
		System.out.println("middle : " + middle);
		System.out.println("offsetList : " + offsetList);
		System.out.println("----------------------------------------------");
		int clickPage;
		Scanner scan = new Scanner(System.in);
		clickPage = scan.nextInt();

		List<Integer> variablePage = page.getVariablePageList(totalPage,
				clickPage, showNum);
		System.out.println("totalPage : " + totalPage);
		System.out.println("clickPage : " + clickPage + "\n" + "variablePage : "
				+ variablePage);
		scan.close();
	}

	public int getShowNum() {
		return showNum;
	}

	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}

}
