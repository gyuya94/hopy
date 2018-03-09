<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
				<h2>로그인</h2>
			</header>
		</div>
	</section>

	<!-- Two -->
	<section id="two" class="wrapper style2">
		<div class="inner">
			<div class="box">
				<header class="align-center">
					<p>HOPY</p>
					<h2>로그인</h2>
						<b style="color: red">${errorMsg }</b>
				</header>
				<div class="content" style="max-width: 500px;">
					<form action="<%=request.getContextPath()%>/customer/auth/login"
						method="post">
						ID <input type="text" name="sphinxId"> <br> PW <input
							type="password" name="password"> <br> <input
							type="submit" value="login">

					</form>


				</div>
			</div>
		</div>
	</section>

</body>
</html>