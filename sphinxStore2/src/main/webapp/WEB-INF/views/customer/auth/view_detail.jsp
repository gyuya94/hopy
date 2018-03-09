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
				<h2>회원정보</h2>
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
						<h2>회원정보</h2>
					</header>
					<form action="<%=request.getContextPath()%>/">

						<div class="table-wrapper">
							<table>
								<tbody>
									<tr>
										<td>sphinxId</td>
										<td>${customer.sphinxId}</td>
									</tr>
									<tr>
										<td>password</td>
										<td>${customer.password }</td>
									</tr>
									<tr>
										<td>name</td>
										<td>${customer.name }</td>
									</tr>
									<tr>
										<td>address</td>
										<td>${customer.address }</td>
									</tr>
									<tr>
										<td>phone</td>
										<td>${customer.phone }</td>
									</tr>
									<c:if test="${customer.isAdmin == true}">
										<tr>
											<td>관리자 여부</td>
											<td>Ture</td>
										</tr>
									</c:if>

								</tbody>
							</table>
						</div>

						<br>
						<div class="align-center">
							<input type="submit" value="메인화면">
						</div>

					</form>
				</div>
			</div>
		</div>
	</section>

</body>
</html>