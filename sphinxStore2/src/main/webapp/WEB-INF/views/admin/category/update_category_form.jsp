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
		<p>HOPY</p>
		<h2>카테고리 수정</h2>
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
				<h2>카테고리 수정</h2>
				</header>
				<form action="<%=request.getContextPath()%>/category/updateCategory"
					method="post">
					<table class="align-center">
						<tbody>
							<tr>
								<td>카테고리 Id</td>
								<td colspan="2"><input type="text" name="categoryId"
									id="categoryId" value="" placeholder="기존 카테고리 Id : ${category.categoryId }" /></td>
							</tr>
							<tr>
								<td>카테고리 이름</td>
								<td colspan="2"><input type="text" name="categoryName"
									id="categoryName" value="" placeholder="기존 카테고리 Name : ${category.categoryName }" /></td>
							</tr>
							<tr>
								<td>상위 카테고리Id</td>
								<td colspan="2"><input type="text" name="parentId"
									id="parentId" value="" placeholder="기존 상위Id : ${category.parentId }" /></td>
							</tr>
							<tr>
								<td>카테고리 그룹</td>
								<td><input type="text" name="group" id="group" value=""
									placeholder="기존 카테고리  Group : ${category.group }" /></td>
							</tr>

						</tbody>

					</table>
					<div class="align-center">
							<input type="submit" value="카테고리 수정">
						</div>
				</form>
</body>
</html>