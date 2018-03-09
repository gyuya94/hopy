<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<h2>회원가입</h2>
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
						<h2>회원가입을 축하드립니다.</h2>
					</header>
					<table>
						<tbody class="align-center">
							<tr>
								<td>sphinxId</td>
								<td>${customer.sphinxId}</td>
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
						</tbody>
					</table>

					<div class="align-center">
						<ul class="actions">
							<li><a href="<%=request.getContextPath()%>/"
								class="button special">메인</a></li>
							<li><form action="<%=request.getContextPath()%>/customer/auth/login" method="post">
							<input type="submit" value="해당 id로 로그인">
							<input type="hidden" name="sphinxId" value="${customer.sphinxId }">
							<input type="hidden" name="password" value="${customer.password }">
							</form></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>

</body>
</html>