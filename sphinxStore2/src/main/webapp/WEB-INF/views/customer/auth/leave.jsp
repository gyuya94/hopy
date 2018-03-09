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
				<h2>회원탈퇴</h2>
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
						<h2>회원탈퇴</h2>
					</header>
					<form action="<%=request.getContextPath()%>/customer/auth/leave"
						method="post">
						<p>안녕</p>
							<div class="align-center">
								<input type="submit" value="탈퇴">
							</div>
					</form>
				</div>
			</div>
		</div>
	</section>

</body>
</html>