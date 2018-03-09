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
		<h2>test</h2>
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
				<h2>test</h2>
				</header>
<%-- 				${category }<br> ${category.categoryName }<br>
				${category.categoryId }<br> ${category.parentId }<br> --%>
				<c:forEach items="${category }" var="category">
					${category }
				</c:forEach>
</body>
</html>