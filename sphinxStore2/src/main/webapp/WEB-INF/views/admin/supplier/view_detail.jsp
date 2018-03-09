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
				<h2>업체 정보</h2>
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
						<h2>공급업체 정보</h2>
					</header>
					<form>
						<table>
							<tbody>
								<tr>

								</tr>
								<tr>
									<td>supplierId</td>
									<td>${supplier.supplierId }</td>
								</tr>
								<tr>
									<td>supplierName</td>
									<td>${supplier.supplierName }</td>
								</tr>
								<tr>
									<td>phone</td>
									<td>${supplier.phone }</td>
								</tr>
							</tbody>
						</table>



						<div id="buttons" class="align-center">
							<ul class="actions">
								<li><a
									href="<%=request.getContextPath()%>/admin/supplier/add"
									class="button special">업체 추가 등록</a></li>
								<li><a
									href="<%=request.getContextPath()%>/admin/supplier/view_list?clickPage=1"
									class="button special">업체 목록</a></li>
								<li><a href="<%=request.getContextPath()%>/"
									class="button special">메인 화면</a></li>
							</ul>
						</div>

					</form>
				</div>
			</div>
		</div>
	</section>

</body>
</html>