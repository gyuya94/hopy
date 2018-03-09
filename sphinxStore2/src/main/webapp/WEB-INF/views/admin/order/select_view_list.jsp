<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
function btn_branch(branchOpt){
	if(branchOpt == 1){
		
	}else if(branchOpt == 2){
		
	}else if (branchOpt == 'search'){
		document.selectForm.action = "<%=request.getContextPath()%>" + 
		"/admin/order/searchedSelectViewList?";
		//여기서 예외를 잡아줄까
		validCheck();
		var searchOptions = document.getElementById('searchOptions');
		searchOptions.innerHTML = "<input type=\"hidden\" name=\"clickPage\" id=\"clickPage\" value=\"1\">"
	}
	document.selectForm.submit();
}

function validCheck(){
	var productId = document.getElementById('productId').value;
	var startDate = document.getElementById('startDate').value;
	var endDate = document.getElementById('endDate').value;
	
	if(productId.length == 0 ){
		document.getElementById('productId').value = null;
	} else if(startDate.length == 0){
		document.getElementById('startDate').value = null;
	} else if(endDate.length == 0){
		document.getElementById('endDate').value = null;
	}
}

function selectOption(select){
	alert(select.value);
	var productIdDiv = document.getElementById('productIdDiv');
	var dateDiv = document.getElementById('dateDiv');
	
	if(select.value == 'null'){
		productIdDiv.style="display: none;";
		dateDiv.style = "display: none;";
	}else if(select.value == 'productId'){
		productIdDiv.style="";
		dateDiv.style = "display: none;";
	}else if(select.value == 'date'){
		productIdDiv.style="display: none;";
		dateDiv.style = "";
	}else if(select.value == 'date+productId'){
		productIdDiv.style="";
		dateDiv.style = "";
	}
}
</script>
	<!-- One -->
	<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<p>HOPY</p>
				<h2>주문 조회</h2>
			</header>
		</div>
	</section>

	<!-- Two -->
	<section id="two" class="wrapper style2">
		<div class="inner">
			<div class="box">
				<div class="content">
					<header class="align-center">
						<p>HOPY</p>
						<h2>주문 조회</h2>
					</header>
					<form id="selectForm" name="selectForm" action="">

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
								<c:forEach var="order" items="${orderList }">
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

						<div id="pageList" class="align-center">
							<jsp:include page="/WEB-INF/views/test/page_result.jsp" />
						</div>

						<div id="buttons" class="align-center">
							<input type="button" value="수정" onclick="btn_branch(1);"
								class="button special" /> <input type="button" value="삭제"
								onclick="btn_branch(2);" class="button special" />
						</div>

						<div id="searchOptions" >
							<select name="searchOption" id="searchOption" onchange="selectOption(this)">
								<option value="null">검색옵션</option>
								<option value="date">date</option>
								<option value="productId">productId</option>
								<option value="date+productId">date+productId</option>
							</select> 
							
							<div id="productIdDiv" style="display: none;">
								<input type="text" name="productId" id="productId"> 
							</div>
							<div id="dateDiv" style="display: none;">
								<input type="date" name="startDate" id="startDate" > 
								<input type="date" name="endDate" id="endDate"> 
							</div>
							
							<input type="button" value="검색" onclick="btn_branch('search')">
						</div>
					</form>


				</div>
			</div>
		</div>
	</section>

</body>
</html>