<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<h2>업체 등록</h2>
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
						<h2>공급업체 등록</h2>
					</header>
					<form action="<%=request.getContextPath()%>/admin/supplier/add"
						method="post">
						<table>
							<tbody>
								<tr>
									<td>supplierName</td>
									<td><input type="text" name="supplierName" id="supplierName" value=""
									placeholder="supplierName" pattern=".{1,20}" title="업체명은 최소1자에서 최대20자 이내입니다."/></td>
								</tr>
								<tr>
									<td>phone</td>
									<td><input type="text" name="phone" id="phone" value=""
									placeholder="phone" pattern="(?=.*[0-9]).{1,11}" title="최대 11자리까지 입력가능합니다."/></td>
								</tr>
							</tbody>
						</table>



						<div class="align-center">
							<input type="submit" value="등록">
						</div>

					</form>
				</div>
			</div>
		</div>
	</section>

</body>
</html>