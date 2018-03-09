<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/category/deleteCategory"
		method="post">
		<!-- One -->
		<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
			<p>HOPY</p>
			<h2>카테고리 삭제</h2>
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
					<h2>카테고리 삭제</h2>
					</header>
					<form
						action="<%=request.getContextPath()%>/category/deleteCategory"
						method="post">
						<div class="row uniform">
							<div class="6u 12u$(xsmall)">
								<input type="text" name="sphinxId" id="sphinxId" value=""
									placeholder="삭제할 카테고리 ID" />
							</div>
							<div class="6u$ 12u$(xsmall)">
								<input type="password" name="password" id="password" value=""
									placeholder="삭제할 카테고리  이름" />
							</div>
						</div>
						<br>
						<div class="align-center">
							<input type="submit" value="카테고리 삭제">
						</div>
					</form>
</body>
</html>