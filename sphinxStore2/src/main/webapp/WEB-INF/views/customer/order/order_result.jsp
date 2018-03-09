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

	<!-- One -->
	<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<p>HOPY</p>
				<h2>주문 결과</h2>
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
						<h2>주문이 완료되었습니다.</h2>
					</header>



					<table>
						<tbody>
							<tr>
								<td> </td>
								<td class="align-center">${order.orderId }</td>
							</tr>

							<c:forEach var="orderItem" items="${order.orderItemList }">
								<tr>
									<td>
									<img alt="사진 어디갔니"
										src="<%=request.getContextPath()%>/resources/images/${orderItem.product.fileNameList.get(0) }"
										width="100px">
										</td>
										<td>
										 ${orderItem.product.productName } <c:forEach
											var="option" items="${orderItem.product.optionList }">
 										 ${option.optionValueMap.keySet() }
 									</c:forEach></td>
								</tr>
							</c:forEach>



						</tbody>
					</table>

					<div class="align-center">
						<ul class="actions">
							<li><a href="<%=request.getContextPath()%>/"
								class="button special">메인</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>

</body>
</html>