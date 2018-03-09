<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.selected {
	text-decoration: line-through;
	font-weight: 700;
	color: red
}
</style>
<script type="text/javascript"
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.9.0/jquery.js"></script>

<script type="text/javascript">

function btn_click(str){
	//계속 구경하기
	if(str==1){
		//3단계 전의 페이지로 돌아가도록 합시당
		history.go(-3);
		<%-- document.selectButton.action ="<%=request.getContextPath()%>/customer/productView"; --%>
				//맨 마지막 product의 category페이지를 출력";
	}
	//계속 쇼핑하기 같은경우엔 유효성검사 필요없으니까
	
	else if(str == 2){
		var valid = validCheck();
		if(valid){
			//결제로 넘어가기
				document.cartForm.action ="<%=request.getContextPath()%>/order/payment";
					/* var quantity = document.getElementById("quantity").value;
					var price = ${product.price};
					var totalPrice = price * quantity;
					alert(quantity); */

				document.cartForm.submit();
		}
	}
	
}
	
	// 체크박스를 하나도 체크안했으면 유효성검사
function validCheck(){
	var checkBoxList = document.getElementsByName('orderItemCheckBox');
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

	var totalPrice = 0;
function checkedProduct(box) { // box this -> checkBox의 id를 의미함
	
	calcTotalPrice();
	
}
function changeNum(count, orderItemId) {	
	alert(orderItemId);
	var box = document.getElementById(orderItemId+'CheckBox');
	if(box.checked==true){
		//번호를 바꿨는데 박스가 체크되어 있으면 if문 시작
		alert('test');
		
	}
	
	var variableQuantity = document.getElementById(orderItemId+'Quantity');
	alert('Quantity'+variableQuantity);
	variableQuantity.innerHTML = '수량 : '+count+'개';
	 var variableCount = document.getElementById(orderItemId+"countHidden"); 
	 alert('variableCount?? ' + variableCount.value); 
	 variableCount.value = count; 
	 
	 calcTotalPrice();
	
}
function submited(str) {
	alert("d");
	var valid = validCheck();
	if(valid){
	btn_click(str);
	}
}

function calcTotalPrice(){
	var checkBoxList = document.getElementsByName("orderItemCheckBox");
	var priceList = document.getElementsByName("orderItemPrice");
	var quantityList = document.getElementsByName("countHidden");
	var totalPrice = 0;
	for (var i=0; i<checkBoxList.length;i++){
		if(checkBoxList[i].checked==true){
			totalPrice += priceList[i].value * quantityList[i].value;
		}
	}
	alert('calcTotalPrice ' + totalPrice);
	
	var insertPrice = document.getElementById("totalPrice");
	insertPrice.innerHTML = totalPrice + '원';

}

$(document).ready(function(){
	//일단 cart안에 든거 보여줄때 이미 다 체크되있는걸로 하고싶어요
	 $("#allCheck").prop("checked",true);
	 $("input[name=orderItemCheckBox]").prop("checked",true);
	 calcTotalPrice();
	 
    //최상단 체크박스 클릭 했을경우
    $("#allCheck").click(function(){
        //클릭되었으면
       
        if($("#allCheck").prop("checked")){
            //input태그의 name이 orderItem인 태그들을 찾아서 checked옵션을 true로 정의
            $("input[name=orderItemCheckBox]").prop("checked",true);
            //totalPrice = controllerTotalPrice.value;
            //저런식으로 하면 답이없다
            //다시 한 체크박스에 대해서 수량 가격정보 가져와서
            //각각을 계산해서 다 더해줘야하는거지
            //controller에서 계산한거 받아서 박아줘봐야 의미없음
            
            
        }else{
            //input태그의 name이 orderItem인 태그들을 찾아서 checked옵션을 false로 정의
            $("input[name=orderItemCheckBox]").prop("checked",false);
            
        }
      
        calcTotalPrice();

    })
    
})


</script>
</head>
<body>
	<!-- One -->
	<section id="One" class="wrapper style3">
	<div class="inner">
		<header class="align-center"> <br>
		<br>
		<h2>cart</h2>
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
				<h2>cart</h2>
				</header>
				<form id="cartForm" name="cartForm" method="post">
					<c:if test="${sessionScope.cart!=null }">
						<div>
							<input type="checkbox" name="allCheck" id="allCheck"
								onclick="allCheckClick(this);"><label for="allCheck"></label>
							<hr>
						</div>
						<c:forEach var="orderItem"
							items="${sessionScope.cart.orderItemMap}">
							<div id="orderItemDiv">
								<input type="checkbox" name="orderItemCheckBox"
									id="${orderItem.value.orderItemId}CheckBox"
									value="${orderItem.value.orderItemId}"
									onclick="checkedProduct(this);" /> <label
									for="${orderItem.value.orderItemId}CheckBox"></label> <input
									type="hidden" id="${orderItem.value.orderItemId}Price"
									name="orderItemPrice" value="${orderItem.value.product.price}">

								${orderItem.value.product.productName }, <b style="color: red;"
									id="${orderItem.value.orderItemId }Quantity">수량:${orderItem.value.quantity}
									개</b> <input type="number" style="width: 50px" name="countHidden"
									id="${orderItem.value.orderItemId }countHidden"
									value="${orderItem.value.quantity }" min="1"
									onclick="changeNum(this.value, '${orderItem.value.orderItemId }');" />
								<!-- name은 RequestParam에 사용 id는 getElementId에 사용 -->

								<div id="orderItemImages">
									<c:forEach var="fileName"
										items="${orderItem.value.product.fileNameList }">
										<a
											href="<%=request.getContextPath()%>/product/viewDetailProduct?productId=${orderItem.value.product.productId}">
											<img alt="사진 어디갔니"
											src="<%=request.getContextPath()%>/resources/images/${fileName }"
											width="100px">
										</a>
									</c:forEach>
								</div>

								<!--카테고리 id 히든으로 보내줌  -->
								<%-- <input type="hidden" name="categoryId"
									value="${orderItem.value.product.categoryList.get(0).categoryId}"> --%>

								<c:forEach items="${orderItem.value.product.optionList }"
									var="option">
									${option.optionName } : 
									<c:forEach items="${option.optionValueMap }"
										var="optionValueMap">
										${optionValueMap.key }  
										<c:if test="${optionValueMap.value != 0 }">
											추가 금액 : ${optionValueMap.value }
											<input type="hidden" name="${orderItem.value.orderItemId}AP"
												value="${optionValueMap.value }">
										</c:if>
									</c:forEach>
									<br>
								</c:forEach>

								<br>

								<hr>
							</div>
						</c:forEach>
						<!--ViewListProduct Controller로 갈 때 필요한 page hidden으로 보냄  -->
						<input type="hidden" name="page" value="1" />
						<div class="align-center" id="total_price"
							style="font-size: 30px;">
							Total Price :
							<%-- <input type="hidden" value="${totalPrice }" id="controllerTotalPrice"/>   --%>
							<b id="totalPrice" style="color: red;"></b>
							<!--AddOrderItemListController로 갈 때 필요한 page hidden으로 보냄  -->
							<!-- <input type="hidden" id="totalPriceHidden"
								name="totalPriceHidden" /> -->
						</div>
					</c:if>


					<div class="align-center">
						<ul class="actions">
							<li><input type="button" value="CONTINUE"
								onclick="btn_click(1);" /></li>
							<li><input type="button" value="PAYMENT"
								onclick="submited(2);" /></li>
						</ul>
					</div>
				</form>
</body>
</html>