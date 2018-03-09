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
	<script type="text/javascript">
function btn_branch(branchOption){
	var selectForm = eval("document.selectForm");
	
	if(branchOption==1){//수정
		selectForm.action = "<%=request.getContextPath()%>/admin/supplier/update";
	}
	else if(branchOption==2){//삭제
		selectForm.action = "<%=request.getContextPath()%>/admin/supplier/delete";
	}
	selectForm.submit();
}
</script>

	<!-- One -->
	<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<p>HOPY</p>
				<h2>업체 수정 및 삭제</h2>
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
						<h2>업체 수정 및 삭제</h2>
					</header>
					<form id="selectForm" name="selectForm" action="">
						<table class="align-center">
							<thead>
								<tr>
									<th></th>
									<th class="align-center">supplierId</th>
									<th class="align-center">supplierName</th>
									<th class="align-center">phone</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="supplier" items="${supplierList }">
									<tr>
										<td><input type="checkbox" name="checkedSupplierId"
											id="${supplier.supplierId}" value=${supplier.supplierId } />
											<label for="${supplier.supplierId }"></label></td>
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
 
						<div id="buttons" class="align-center">
						<input type="button" value="수정" onclick="btn_branch(1);"
							class="button special" /> 
							<input type="button" value="삭제"
							onclick="btn_branch(2);" class="button special" />
</div>
					</form>


				</div>
			</div>
		</div>
	</section>

</body>
</html>