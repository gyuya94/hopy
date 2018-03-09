<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<!--
	Hielo by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>
<head>
<title>HOPY</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/assets/css/main.css" />
</head>
<body>

	<!-- Header -->
	<header id="header" class="alt">
		<div class="logo">
			<a href="<%=request.getContextPath()%>/">HOPY <span>by
					HOPY</span></a>

			<form method="post"
				action="<%=request.getContextPath()%>/search/product?page=1">
				<div class="row uniform">
					<div class="11u 14u$(small)">
						<input type="text" name="query" id="query" value=""
							style="border-color: white;" placeholder="찾고 싶은 상품을 검색해보세요!"
							class="image right" />
					</div>
					<div class="1u$ 14u$(small)">
						<input type="submit" value="Search" class="small" />
					</div>
				</div>
			</form>

		</div>




		<a href="#menu">Menu</a>
	</header>

	<!-- Nav -->
	<nav id="menu">

		<ul class="links">
			<li><a href="<%=request.getContextPath()%>/">Home</a></li>
			<li><a href="<%=request.getContextPath()%>/cart/listCart">cart</a></li>
			<c:forEach var="category" items="${rootCategoryList }">
				<li><a
					href="<%=request.getContextPath()%>/admin/product/view_list?categoryId=${category.categoryId}&clickPage=1">${category.categoryName }</a></li>
			</c:forEach>

			<c:choose>
				<c:when test="${! empty sessionScope.customer }">
					<c:choose>
						<c:when test="${sessionScope.customer.isAdmin == true }">
							<li>카테고리
								<ul class="subMenu">
									<li><a
										href="<%=request.getContextPath()%>/category/listCategory?type=list">카테고리
											조회</a></li>
									<li><a
										href="<%=request.getContextPath()%>/category/addCategory">카테고리
											추가</a></li>
									<li><a
										href="<%=request.getContextPath()%>/category/listCategory?type=update">카테고리
											수정</a></li>
									<li><a
										href="<%=request.getContextPath()%>/category/listCategory?type=delete">카테고리
											삭제</a></li>
									<li><a href="<%=request.getContextPath()%>/category/test">test</a></li>
									<li><a
										href="<%=request.getContextPath()%>/test/orderItemListTest">여러개
											주문</a></li>
								</ul>
							</li>

							<li>제품
								<ul class="subMenu">

									<li><a
										href="<%=request.getContextPath()%>/admin/product/add">제품
											등록</a></li>
									<li><a
										href="<%=request.getContextPath()%>/admin/product/select_list?clickPage=1">제품
											수정 및 삭제</a></li>
								</ul>
							</li>

							<li>업체
								<ul class="subMenu">
									<li><a
										href="<%=request.getContextPath()%>/admin/supplier/view_list?clickPage=1">업체
											조회</a></li>
									<li><a
										href="<%=request.getContextPath()%>/admin/supplier/add">업체
											등록</a></li>
									<li><a
										href="<%=request.getContextPath()%>/admin/supplier/select_list?clickPage=1">업체
											수정 및 삭제</a></li>
								</ul>
							</li>

							<li>회원 관리
								<ul class="subMenu">
									<li><a
										href="<%=request.getContextPath()%>/customer/auth/logout">로그아웃</a></li>
									<li><a
										href="<%=request.getContextPath()%>/customer/auth/update">회원정보
											수정</a></li>
									<li><a
										href="<%=request.getContextPath()%>/customer/auth/leave">회원탈퇴</a></li>
								</ul>
							</li>

							<li>주문 관리
								<ul class="subMenu">
									<li><a
										href="<%=request.getContextPath()%>/admin/order/selectViewList?clickPage=1">주문조회</a></li>
								</ul>
							</li>
						</c:when>
						<c:otherwise>
							<li>회원 정보 관리
								<ul class="subMenu">
									<li><a
										href="<%=request.getContextPath()%>/customer/order_inquiry">주문조회</a></li>
										<li><a
										href="<%=request.getContextPath()%>/customer/auth/logout">로그아웃</a></li>
										<li><a
										href="<%=request.getContextPath()%>/customer/auth/update">회원정보
											수정</a></li>
										<li><a
										href="<%=request.getContextPath()%>/customer/auth/leave">회원탈퇴</a></li>
										<li><a
										href="<%=request.getContextPath()%>/customer/auth/update_admin">관리자
											승격</a></li>
								</ul>
							</li>
						</c:otherwise>
					</c:choose>




				</c:when>
				<c:otherwise>
					<li>회원
						<ul class="subMenu">
							<li><a
								href="<%=request.getContextPath()%>/customer/auth/login">로그인</a></li>
							<li><a
								href="<%=request.getContextPath()%>/customer/auth/join">회원가입</a></li>
						</ul>
					</li>
				</c:otherwise>
			</c:choose>





		</ul>
	</nav>

	<!-- Content -->
	<div>
		<%
			String content = request.getParameter("CONTENT");
		%>
		<c:choose>
			<c:when test="${empty CONTENT }">
				<jsp:include page="<%=content%>" flush="false" />
			</c:when>

			<c:otherwise>
				<jsp:include page="${CONTENT }" flush="false" />
			</c:otherwise>
		</c:choose>
	</div>

	<!-- Footer -->
	<footer id="footer">
		<div class="container">
			<ul class="icons">
				<li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
				<li><a href="#" class="icon fa-facebook"><span
						class="label">Facebook</span></a></li>
				<li><a href="#" class="icon fa-instagram"><span
						class="label">Instagram</span></a></li>
				<li><a href="#" class="icon fa-envelope-o"><span
						class="label">Email</span></a></li>
			</ul>
		</div>
		<div class="copyright">&copy; Untitled. All rights reserved.</div>
	</footer>

	<!-- Scripts -->
	<script
		src="<%=request.getContextPath()%>/resources/assets/js/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/assets/js/jquery.scrollex.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/assets/js/skel.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/assets/js/util.js"></script>
	<script src="<%=request.getContextPath()%>/resources/assets/js/main.js"></script>

</body>
</html>