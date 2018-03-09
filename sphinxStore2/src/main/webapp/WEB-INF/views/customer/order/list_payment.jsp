<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- One -->
	<section id="One" class="wrapper style3">
	<div class="inner">
		<header class="align-center">
		<p>HOPY</p>
		<h2>Payment</h2>
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
				<h2>
					Payment<br>
				</h2>
				</header>
				<form action="<%=request.getContextPath()%>/order/addOrder" method="post">
					<dl>
						<dt>Buyer Information</dt>
						<dd>
							<p>
								<b> Name : ${customer.name }<br> Addr :
									${customer.address }<br> Phone : ${customer.phone }
								</b>
							</p>
						</dd>

					</dl>
					<dl>
						<dt>Recipient Information</dt>
						<dd>
							<b>Recipient<input type="text" name="Recipient"
								placeholder="Recipient" /></b>
						</dd>
						<dd>
							<b>RecipientAddr<input type="text" name="RecipientAddr"
								placeholder="RecipientAddr" /></b>
						</dd>
						<dd>
							<b>Memo<input type="text" name="Memo" placeholder="Memo" /></b>
						</dd>
					</dl>

					<hr>
					<dl>
						<dt>Product Detail</dt>
						<c:forEach var="orderItem" items="${orderItemList}" >
							<dd>
								<img alt="" width="100px"
								src="<%=request.getContextPath()%>/resources/images/${orderItem.product.fileNameList.get(0)}">
								<p>${orderItem.product.productName },${orderItem.quantity }개</p>
							</dd>
						
 							<c:forEach var="option" items="${orderItem.product.optionList }">
 								${option.optionValueMap}
 							</c:forEach>
						</c:forEach>
						<dt>Payment Information</dt>
						<dd>
							<p style="font-size: 30px; color: red;">
							TotalPrice : <fmt:formatNumber maxFractionDigits = "3"  value = "${orderTotalPrice }" /></p>
						</dd>
					</dl>
					<div class="align-center" id="submit">
						<input type="submit" value="Payment" />
					</div>
				</form>
			</div>
		</div>
	</div>

	<hr>
</body>
</html>