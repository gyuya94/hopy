<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<h2>회원정보 수정</h2>
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
						<h2>회원정보 수정</h2>
					</header>
					<form action="<%=request.getContextPath()%>/customer/auth/update"
						method="post">
						<table>
							<tbody class="align-center">
								<tr>
									<td>sphinxId </td>
									<td>${sessionScope.customer.sphinxId }</td>
								</tr>
								<tr>
									<td>password</td>
									<td><input type="password" name="password" id="password"
										value="${sessionScope.customer.password }" placeholder="password" /></td>
								</tr>
								<tr>
									<td>name</td>
									<td><input type="text" name="name" id="name"
										value="${sessionScope.customer.name }" placeholder="name" /></td>
								</tr>
								<tr>
									<td>address</td>
									<td><input type="text" name="address" id="address"
										value="${sessionScope.customer.address }" placeholder="address" /></td>
								</tr>
								<tr>
									<td>phone</td>
									<td>
										<input type="text" name="phone" id="phone" value="${sessionScope.customer.phone }">
 
									</td>
								</tr>
							<c:if test="${sessionScope.customer.isAdmin == true}">
								<tr>
									<td>관리자 여부</td>
									<td>True</td>
								</tr>
							</c:if>
						</tbody>
						</table>
						<div class="align-center">
							<input type="submit" value="회원정보 수정">
						</div>

					</form>
				</div>
			</div>
		</div>
	</section>

</body>
</html>