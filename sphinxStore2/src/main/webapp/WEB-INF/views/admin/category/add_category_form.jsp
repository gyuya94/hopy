<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
		<h2>카테고리 추가</h2>
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
				<h2>카테고리 추가</h2>
				</header>
				<div class="align-center">
					<c:if test="${errorMsg!=null }">
						<c:forEach items="${errorMsg }" var="errorMsg">
							<h4 style="color: red">${errorMsg }</h5>
						</c:forEach>
					</c:if>
				</div>
				<form:form> <%-- modelAttribute="category" --%><!-- 이름을 변경가능 -->
					<!--form:form의 기본 method는 post -->
					<table class="align-center">
						<tbody>
							<tr>
								<td>카테고리 Id</td>
								<!-- path를 쓰면 get진입시 path에 대한 모델이 있어야한다(category)  -->
								<td colspan="2"><form:input path="categoryId"
										placeholder="categoryId" /></td>
							</tr>
							<tr>
								<td>카테고리 이름</td>
								<td colspan="2"><form:input path="categoryName"
										placeholder="categoryName" /></td>
							</tr>
							<tr>
								<td>상위 카테고리Id</td>
								<td colspan="2"><form:input path="parentId"
										placeholder="parentId" /></td>
							</tr>
							<tr>
								<td>카테고리 그룹</td>
								<td colspan="2"><form:input path="group"
										placeholder="group" /></td>
							</tr>

						</tbody>

					</table>
					<div class="align-center">
						<form:button>
						추가요
					</form:button>
					</div>
					<div class="align-center">
						<input type="submit" value="카테고리 추가">
					</div>
				</form:form>
</body>
</html>