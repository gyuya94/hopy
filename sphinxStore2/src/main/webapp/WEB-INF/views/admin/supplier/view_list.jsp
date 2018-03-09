<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<body>

	<!-- One -->
	<section id="One" class="wrapper style3">
		<div class="inner">
			<hea der class="align-center">
				<p>HOPY</p>
				<h2>업체 조회</h2>
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
						<h2>업체 조회</h2>
					</header>
					<form action="">
						<table class="align-center">
							<thead>
								<tr>
									<th class="align-center">supplierId</th>
									<th class="align-center">supplierName</th>
									<th class="align-center">phone</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="supplier" items="${supplierList }">
									<tr>
										<td>${supplier.supplierId }</td>
										<td>${supplier.supplierName}</td>
										<td>${supplier.phone}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<div id="pageList" class="align-center">
							<jsp:include page="/WEB-INF/views/test/page_result.jsp" />
						</div>
						
					</form>






				</div>
			</div>
		</div>
	</section>

</body>
</html>