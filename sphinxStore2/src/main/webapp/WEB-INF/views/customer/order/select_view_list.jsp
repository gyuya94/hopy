<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		//change dateCheck의 값이 변경됐을 때 실행
		$(".dateCheck").change(function() { // class dateCheck
			var date = '${date}';
			//그 전 controller에서 request로 받은 date를 var date에 넣는다.
			var nowDate = new Date(date);
			var selectDate = $(this).val();
			//this의 의미 : 내가 입력한 날짜 
			var transStartDate = new Date(selectDate);
			if (transStartDate > nowDate) {
				alert("현재 날짜보다 이릅니다!");
				$(this).val('');
				//내가 입력한 날짜를 다시 비우도록 변경
				return;
				//date값을 false로 받겠다
			}

			$(this).val(selectDate);
		});

		$('#search').click(function() {
			var dateSelect = $('#dateSelect');
			var code = '<input type="hidden" name="clickPage" value="1"/>';
			dateSelect.append(code);

			var date = document.getElementsByClassName('dateCheck');
			/* 			for (var i = 0; i < date.length; i++) {
			 alert(date[i].value);
			 } */

			$('#frm').submit();
		});

	});
</script>
</head>
<body>
	<section id="One" class="wrapper style3">
	<div class="inner">
		<header class="align-center">
		<p>HOPY</p>
		<h2>Order inquiry</h2>
		</header>
	</div>
	</section>
	<section id="comment" class="wrapper style2">
	<div class="inner">
		<div class="box">
			<div class="content">
				<header class="align-center">
				<p>HOPY</p>
				<h2>발생했던 이슈</h2>
				<blockquote class="align-center">
					<ul class="align-center">
						<li>사용자의 편의성을 반영하여 조회 시 최근 1달 간 사용자가 주문한 내역이 표시됩니다.</li>
						<li></li>
					</ul>
				</blockquote>
				</header>
			</div>
		</div>
	</div>
	</section>
	<!-- Two -->
	<section id="two" class="wrapper style2">
	<div class="inner">
		<div class="box">
			<div class="content">
				<header class="align-center">
				<p>HOPY</p>
				<h2>
					Order inquiry<br>
				</h2>
				</header>
				<div class="align-center" id="errorMsg">
					<c:if test="${!empty errorMsg }">
						<c:forEach items="${errorMsg }" var="errorMsg">
							<b style="color: red">${errorMsg }</b>
							<br>
						</c:forEach>
					</c:if>
					<hr>
				</div>
				<form
					action="<%=request.getContextPath()%>/customer/order/select_view_list"
					method="post" name="dateSubmit" id='frm'>
					<div class="align-center" id="dateSelect">
						${customer.name }님의 주문내역 <br> <input type="date"
							name="startDate" id="startDate" value="${startDateStr }"
							class="dateCheck" /> ~ <input type="date" name="endDate"
							value="${endDateStr }" id="endDate" class="dateCheck" /> <input
							type="hidden" name="customerId" value="${customer.customerId }" />
						<input type="hidden" name="date" value="${date }"> <input
							type="button" id='search' value="search" />
					</div>
					<div id="orderList" class="align-center">
						<table class="align-center">
							<thead>
								<tr>
									<th></th>
									<th class="align-center">orderId</th>
									<th class="align-center">orderDate</th>
									<th class="align-center">state</th>
									<th class="align-center">orderItem</th>
									<th class="align-center">customerId</th>
									<th class="align-center">totalPrice</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="order" items="${orderList}">
									<tr>
										<td><input type="checkbox" name="checkedOrderId"
											id="${order.orderId}" value=${order.orderId } /> <label
											for="${order.orderId }"></label></td>
										<td>${order.orderId }</td>
										<td>${order.orderDate }</td>
										<td>${order.state}</td>
										<td>${order.orderItemList.get(0).product.productName}<c:if
												test="${order.orderItemList.size() > 1}">
												외 ${order.orderItemList.size() -1 } 
											</c:if>
										</td>
										<td>${order.customerId }</td>
										<td>${order.totalPrice }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<c:if test="${!empty orderList}">
						<!--주문 내역 존재하지 않는다.  -->
						<div id="pageList" class="align-center">
							<%@include file="/WEB-INF/views/test/page_result.jsp"%>
						</div>
					</c:if>
				</form>
</body>
</html>