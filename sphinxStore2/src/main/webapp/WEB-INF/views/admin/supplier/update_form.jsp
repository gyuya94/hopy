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
				<h2>업체 수정</h2>
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
						<h2>공급업체 수정</h2>
					</header>
					<form action="<%=request.getContextPath()%>/admin/supplier/update"
						method="post">
						<table>
							<tbody>
								<tr>
									<td>supplierId</td>
									<td><input type="hidden" name="supplierId" id="supplierId" value="${supplier.supplierId }">${supplier.supplierId }</td>
								</tr>
								<tr>
									<td>supplierName</td>
									<td><input type="text" name="supplierName" id="supplierName" 
									value="${supplier.supplierName }"/></td>
								</tr>
								<tr>
									<td>phone</td>
									<td><input type="text" name="phone" id="phone" 
									value="${supplier.phone }"/></td>
								</tr>
							</tbody>
						</table>

						<div class="align-center">
							<input type="submit" value="수정">
						</div>

					</form>
				</div>
			</div>
		</div>
	</section>

</body>
</html>