<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function btn_click(str){
		if(str==1){
			document.click.action = "<%=request.getContextPath()%>/category/deleteCategory";
		}
		document.click.submit();
	}
	
	function sendByGet(){
		//스크립트 안에서도 보낼수있어
		alert('들어오닝');
		var checkBox = document.getElementsByName("categoryIdCheck");
		var chkList = "";
		for (var i = 0; i < checkBox.length; i++) {
			if (checkBox[i].checked == true) {
				chkList += checkBox[i].value + " ";
			}
		}
		document.location.href='<%=request.getContextPath()%>
	/category/updateCategory?categoryIdCheck='
				+ chkList;
	}
</script>
</head>
<body>
	<!-- One -->
	<section id="One" class="wrapper style3">
	<div class="inner">
		<header class="align-center">
		<p>HOPY</p>
		<h2>카테고리 조회</h2>
		</header>
	</div>
	</section>
	<section id="comment" class="wrapper style2">
	<div class="inner">
		<div class="box">
			<div class="content">
				<header class="align-center">
				<p>HOPY</p>
				<h2>발생했던 이슈</h2>
				<blockquote class="align-center">
					<ul class="align-center">
						<li>카테고리의 계층구조를 나타내기 위해 parentId를 표기합니다.</li>
						<li></li>
					</ul>
				</blockquote>
				</header>
			</div>
		</div>
	</div>
	</section>
	<!-- Two -->
	<section id="two" class="wrapper style2">
	<div class="inner">
		<div class="box">
			<div class="content">
				<header class="align-center">
				<p>HOPY</p>
				<h2>
					카테고리 조회 페이지<br>
				</h2>
				</header>
				<table class="align-center">
					<thead>
						<tr>
							<th class="align-center"></th>
							<th class="align-center">categoryId</th>
							<th class="align-center">categoryName</th>
							<th class="align-center">parentId</th>
							<th class="align-center">group</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${type=='list' }">
								<c:forEach items="${category }" var="category">
									<tr>
										<td></td>
										<td>${ category.value.categoryId}</td>
										<td>${ category.value.categoryName}</td>
										<td>${ category.value.parentId}</td>
										<td>${ category.value.group}</td>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<form id="click" name="click" method="post">
									<c:forEach items="${category }" var="category">
										<tr>
											<td class="align-center"><input type="checkbox"
												name="categoryIdCheck" id="${category.key }"
												value=${category.key } /> <label for="${category.key }"></label></td>
											<td>${category.value.categoryId }</td>
											<td>${ category.value.categoryName}</td>
											<td>${ category.value.parentId}</td>
											<td>${ category.value.group}</td>
										</tr>
									</c:forEach>
								</form>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<c:if test="${type=='delete'}">
					<div class="align-center">
						<input type="button" value="삭제하기" onclick="btn_click(1);"
							class="align-center" />
					</div>
				</c:if>
				<c:if test="${type=='update'}">
					<div class="align-center">
						<a href="javascript:void(0);" onclick="sendByGet()"
							class="button special">수정하기</a>
					</div>
				</c:if>
</body>
</html>