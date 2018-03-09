package com.sphinx.hopy.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;
/*@Component*/
public class PageUtil {
	private int count = 0; // 총 주문수
	private int amount = 0; // 자료를 보여줄 갯수
	private int totalPage = 0; // 총 페이지 수
	private int showNum = 0; // 10개씩 보여주기(10page보여주기)페이지 보여줄 숫자

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

	public List<Integer> getVariablePageList(int totalPage, int clickPage, int showNum) {
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

	public int getShowNum() {
		return showNum;
	}

	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}

	/*public static void main(String[] args) {
		PageUtil page = new PageUtil();
		page.setCount(90);
		page.setAmount(5);
		int amount = page.getAmount();
		int count = page.getCount();
		page.setShowNum(10);
		int showNum= page.getShowNum();
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
				clickPage,showNum);
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
	}*/

}


/*package com.sphinx.hopy.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

	private int pages = 10; // 페이지 숫자 보여주는거 한덩어리 (총 페이지 수)
	private int amount = 5; // 목록에 뿌려줄 게시글 갯수
	private int maxPage = 1; // 마지막 페이지
	
	//count 총 주문 수
	public List<Integer> getPages(int count, int page) {
		// count 총 회원수
		// 해당 페이지 번호
		int middle = 1; // 페이지의 중간 숫자
		if (pages % 2 == 0) {
			middle = pages / 2;
		} else {
			middle = pages / 2 + 1;
		}

		List<Integer> pageList = new ArrayList<>();
		int maxPage=getMaxPage(count);
		if (maxPage <= pages) { // page가 pages개 이하일 경우
			pages = maxPage;
			for (int c = 1; c <= maxPage; c++) {
				pageList.add(c);
			} // 페이지 숫자 표현
		} else { // page가 pages초과일 경우
			if (page <= middle) {
				// middle 숫자 중간에 오게 하는 것
				for (int c = 1; c <= pages; c++) {
					pageList.add(c); //1~10까지 저장
				}
			} else {
				// 짝수일 때
				//중간 숫자 보다 크게 클릭했을 시 
				//middle = 7, 내가 클릭한 page = 8
				//maxPage = 14
				if (pages % 2 == 0) {
					if (page + middle > maxPage) {
						for (int c = page - (middle - 1); c <= maxPage; c++) {
							pageList.add(c);
						}
					} else {
						for (int c = page - (middle - 1); c <= page
								+ middle; c++) {
							pageList.add(c);
						}
					}

				} else {
					// 홀수 일 때
					if (page + (middle - 1) > maxPage) {
						for (int c = page - (middle - 1); c <=maxPage; c++) {
							pageList.add(c);
						}
					} else {
						for (int c = page - (middle - 1); c <= page
								+ (middle - 1); c++) {
							pageList.add(c);
						}
					}
				}
			}
		}
		return pageList;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getMaxPage(int count) {
		if (count % amount == 0) {
			maxPage = count / amount;
		} else {
			maxPage = count / amount + 1;
		}
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
}
*/