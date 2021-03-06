<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
		<h2>제품 정보</h2>
		</header>
	</div>
	</section>

	<form action="">
		<!-- Two -->
		<section id="two" class="wrapper style2">
		<div class="inner">
			<div class="box">
				<header class="align-center">
				<h2>${category.categoryName }</h2>
				<div id="subCategory">
					<p>
						<c:forEach var="subCategory" items="${category.subCategory}">
							<a
								href="<%=request.getContextPath()%>/admin/product/view_list?categoryId=${subCategory.categoryId}&clickPage=1">
								${subCategory.categoryName}</a>
						</c:forEach>
					</p>
				</div>
				</header>
				<div class="gallery">
					<c:forEach var="product" items="${productList}">

						<div class="image fit">

							<c:forEach var="fileName" items="${product.fileNameList }">
								<a
									href="<%=request.getContextPath()%>/product/viewDetailProduct?productId=${product.productId}">
									<img alt="사진 어디갔니"
									src="<%=request.getContextPath()%>/resources/images/${fileName }"
									width="400px">
								</a>
							</c:forEach>
							<div class="align-center">
								<b>${product.productName }</b><br>
								<p>${product.comment }</p>
								<b><fmt:formatNumber type="currency" value = "${product.price }" /></b>
							</div>
						</div>

					</c:forEach>
				</div>

				<div id="pageList" class="align-center">
					<jsp:include page="/WEB-INF/views/util/page_result.jsp" />
				</div>

				<div class="align-center">
					<ul class="actions">
						<li><a href="<%=request.getContextPath()%>/"
							class="button special">메인</a></li>
					</ul>
				</div>
			</div>
		</div>
		</section>

		<input type="hidden" name="categoryId" value="${category.categoryId }">
	</form>
</body>
</html>