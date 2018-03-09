<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.productName {
	font-size: 30px;
	align-content: center;
}

.productPrice {
	font-size: 32px;
	align-content: left;
	padding: 5;
}

#price {
	font-size: 30px;
	color: red;
}
</style>
</head>
<body>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/se2/js/HuskyEZCreator.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/se2/photo_uploader/plugin/hp_SE2M_AttachQuickPhoto.js"
		charset="utf-8"></script>
	<!-- jQuery를 사용하기위해 jQuery라이브러리 추가 -->
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
	<!-- Smart Editor -->

	<script type="text/javascript">
var oEditors = [];
$(function(){
      nhn.husky.EZCreator.createInIFrame({
          oAppRef: oEditors,
          elPlaceHolder: "ir1", //textarea에서 지정한 id와 일치해야 합니다. 
          //SmartEditor2Skin.html 파일이 존재하는 경로
          sSkinURI: "<%=request.getContextPath()%>/resources/se2/SmartEditor2Skin.html",  
          htParams : {
              // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
              bUseToolbar : true,             
              // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
              bUseVerticalResizer : true,     
              // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
              bUseModeChanger : true,         
              fOnBeforeUnload : function(){
                   
              }
          }, 
          fOnAppLoad : function(){
              //기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
              oEditors.getById["ir1"].exec("PASTE_HTML", [""]);
          },
          fCreator: "createSEditor2"
      });
      
      //저장버튼 클릭시 form 전송
      $("#save").click(function(){
          oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
      if (document.getElementById("ir1").value.length <= 100000) {
    		
    			alert('상품이 등록되었습니다.');
    			$("#frm").submit();
    		
      } else {
    	  alert('최대 5000자를 초과 하였습니다.\n현재 글자수 : '+ document.getElementById("ir1").value.length +"자");
      }
      });    
});

//textArea에 이미지 첨부
function pasteHTML(filepath){
	 var sHTML = '<img src=<%=request.getContextPath()%>/resources/images/'+ filepath + '>';
			oEditors.getById["ir1"].exec("PASTE_HTML", [ sHTML ]);
		}

function quantityCount(quantityValue) {
			alert(quantityValue);
			var quantity = document.getElementById("quantity").value;
			var price = ${product.price};
			var totalPrice = price * quantity;
			var insertPrice = document.getElementById("price");
			insertPrice.innerHTML = totalPrice + '원';
	}
	
 function btn_click(str){
	var valid = validCheck();
	if(valid){
		if(str==1){
			alert("카트에 담겼습니다!!!");
			history.back();
			document.productDetail.action ="<%=request.getContextPath()%>/cart/addCart";
			
		}
		if(str==2){
			document.productDetail.action ="<%=request.getContextPath()%>/order/paymentByProduct";
					/* var quantity = document.getElementById("quantity").value;
					var price = ${product.price};
					var totalPrice = price * quantity;
					alert(quantity); */

				}
				document.productDetail.submit();
			}
		}

		$('#form_submit').click(function() {
			$.ajax({
				type : 'post',
				url : 'process.php',
				success : function(data) {
					alert(data);
				}
			});
			return false; //<- 이 문장으로 새로고침(reload)이 방지됨
		});

		function validCheck() {
			var isValid;
			//수량 체크
			var quantityValue = document.getElementById('quantity').value;
			if (quantityValue == 0) {
				alert('수량을 선택해주세요');
				isValid = false;
			} else {
				isValid = true;
			}
			//옵션 체크
			var option = document.getElementsByName('option');
			for(var i=0; i<option.length; i++){
				if(option[i].value == 'null'){
					alert('옵션을 선택해주세요');
					isValid = false;
					break;
				}
			}
			
			return isValid;
		}
	</script>

	<!-- One -->
	<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<p>HOPY</p>
				<h2>제품 정보</h2>
			</header>
		</div>
	</section>

	<!-- Two -->
	<section id="two" class="wrapper style2">
		<div class="inner">
			<div class="box">
				<div class="content">
					<form id="productDetail" name="productDetail" method="post">
						<div class="align-center">

<%-- 							<c:forEach var="fileName" items="${product.fileNameList }">

								<span class="image left"><img alt="이미지 출력"
									src="<%=request.getContextPath()%>/resources/images/${fileName }"></span>
							</c:forEach> --%>
							<span class="image left"><img alt="이미지 출력"
									src="<%=request.getContextPath()%>/resources/images/${product.fileNameList.get(0) }"></span>
							
							<div class="productName">${product.productName }</div>
							<div class="productPrice">
								<b><fmt:formatNumber maxFractionDigits = "3"  value = "${product.price }" />원</b>
							</div> 

							<div class="align-center">
								<ul class="actions">
									<li class="quantity"><select name="quantity" id="quantity"
										onchange="quantityCount(this.value);">
											<option value="0">quantity</option>
											<option value="1">- 1 -</option>
											<option value="2">- 2 -</option>
											<option value="3">- 3 -</option>
											<option value="4">- 4 -</option>
											<option value="5">- 5 -</option>
									</select></li>
									<li><b id="price"></b></li>
								</ul>
							</div>
							<div class="align-center">

								<c:choose>
									<c:when test="${empty product.optionList }">
										<input type="hidden" name="option" id="option" value="non">
									</c:when>
									<c:otherwise>
										<ul class="actions">
											<c:forEach var="option" items="${product.optionList }">
												<li><select name="option" id="option">
														<option value="null">- ${option.optionName} -</option>
														<c:forEach var="optionValue"
															items="${option.optionValueMap}">
															<c:choose>
																<c:when test="${optionValue.value == 0}">
																	<option value="${optionValue.key}"> ${optionValue.key}</option>
																</c:when>
																<c:otherwise>
																	<option value="${optionValue.key}"> ${optionValue.key} 추가금액 ${optionValue.value} </option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
												</select></li>
											</c:forEach>
										</ul>
									</c:otherwise>

								</c:choose>


							</div>
							<div>
								<input type="hidden" name="productId"
									value="${product.productId }" />
								<!--AddOrderController에 쓰임 -->
							</div>
							<div class="align-center">
								<b>${product.comment }</b>
							</div>
							<div class="align-center">
								<ul class="actions">
									<li><input type="button" value="ADD TO CART"
										onclick="btn_click(1);" /></li>
									<li><input type="button" value="PAYMENT"
										onclick="btn_click(2);" /></li>
								</ul>
							</div>

						</div>
					</form>
				</div>
				<div class="align-center">${product.content }</div>
			</div>
		</div>
	</section>

</body>
</html>