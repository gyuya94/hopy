<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		function findCheckedProductId(opt) {
			
			//어떤 친구가 체크되있는지 for문돌며 찾는 아이에여
/* 			for (var i = 0; i < checkedProductIdList.length; i++) {
				if (checkedProductIdList[i].checked == true) {
					alert('i ' + i + 'value ' + checkedProductIdList[i].value);
					var testStr = '<input type="hidden" name="checkedProductId" value="'
				+ checkedProductIdList[i].value + '">';
					insertCheckedProductId.innerHTML = testStr;
					alert(testStr);
				}
			}
			 */
			 //할필요가 없대..
			var valid = validCheck();//유효성 검사해줄 친구
			if(valid){//제대로 됬을때만 submit합시당
				if(opt == 'mod'){
					//form하려면 id아니라 name이 필요한가봐
					document.productCheckForm.action="<%=request.getContextPath()%>/admin/product/update";
					document.productCheckForm.method = "get";
				}else if(opt == 'del'){
					//form하려면 id아니라 name이 필요한가봐
					document.productCheckForm.action="<%=request.getContextPath()%>/admin/product/delete";
					document.productCheckForm.method = "post";
				}
				document.productCheckForm.submit();
			}
		}
		function validCheck(){
			var checkBoxList = document.getElementsByName('checkedProductId');
			var isChecked = false;
			for (var i = 0; i < checkBoxList.length; i++) {
				if (checkBoxList[i].checked == true) {
					isChecked = true;
				}
			}
			//하나도 체크안했으면
			if(isChecked == false){
				alert('하나 이상의 제품을 체크해주세요');
				return false;
			}else{
				return true;
			}
		}
		
		
	</script>


	<!-- One -->
	<section id="One" class="wrapper style3">
	<div class="inner">
		<header class="align-center">
		<p>HOPY</p>
		<h2>제품 수정 및 삭제</h2>
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
				<h2>제품 수정 및 삭제</h2>
				</header>

				<form
					action="<%=request.getContextPath()%>/admin/product/select_list"
					method="post">
					<div id="searchProductInput">
						<input type="text" name="searchKeyword"> <input
							type="submit" value="검색"> <input type="hidden"
							name="clickPage" id="clickPage" value="1">
					</div>
				</form>

				<form action="" id="productCheckForm" name="productCheckForm">
					<table class="align-center">
						<thead>
							<tr>
								<th class="align-center"></th>
								<th class="align-center">productId</th>
								<th class="align-center">productName</th>
								<th class="align-center">comment</th>
								<th class="align-center">regDate</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="product" items="${productList }">
								<tr>
									<td><input type="checkbox" id="${product.productId }"
										name="checkedProductId" value="${product.productId }">
										<label for="${product.productId }"></label></td>
									<td>${product.productId }</td>
									<td>${product.productName }</td>
									<td>${product.comment }</td>
									<td>${product.regDate }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<div id="pageList" class="align-center">
						<jsp:include page="/WEB-INF/views/util/page_result.jsp" />
					</div>

					<div id="buttons">
						<input type="button" value="수정" onclick="findCheckedProductId('mod');"> 
						<input type="button" value="삭제" onclick="findCheckedProductId('del');">
					</div>
				</form>

			</div>
		</div>
	</div>
	</section>

</body>
</html>