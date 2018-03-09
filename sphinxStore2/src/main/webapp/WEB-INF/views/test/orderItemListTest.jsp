<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<p>HohoP</p>
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
				<p>HohoP</p>
				<h2>
					Payment<br>
				</h2>
				</header>
				<form action="<%=request.getContextPath()%>/test/orderItemListTest" method="post">
					<input type="text" name="productId" /> 
					<input type="text" name="quantity" /> 
					<input type="text" name="productId" /> 
					<input type="text" name="quantity" /> 
					<input type="submit" value="제출" />
				</form>
</body>
</html>