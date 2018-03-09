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
	 var sHTML = '<img src=<%=request.getContextPath()%>/resources/images/'
					+ filepath + '>';
			oEditors.getById["ir1"].exec("PASTE_HTML", [ sHTML ]);
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
					<header class="align-center">
						<p>HOPY</p>
						<h2>제품 정보</h2>
					</header>
					
					<table class="align-center">
							<tbody >
								<tr>
									<td>제품 이름</td>
									<td colspan="2" >${product.productName }</td>
								</tr>
								<tr>
									<td>판매가격</td>
									<td colspan="2">${product.price } </td>
								</tr>
								<tr>
									<td>카테고리</td>
									<td colspan="2">
										${product.categoryList.get(0).categoryName }
									</td>
								</tr>
								<tr>
									<td>옵션</td>
									<td>${product.optionList }</td>
										
								</tr>
								<c:forEach var="fileName" items="${product.fileNameList }">
								<tr>
									<td>상품 메인 이미지</td>
									<td colspan="2" >
									<img alt="사진 어디갔니" 
										src="<%=request.getContextPath()%>/resources/images/${fileName }"
										width="400px"></td>
								</tr>
								</c:forEach>
								<tr>
									<td>상품 코멘트</td>
									<td colspan="2">${product.comment }</td>
								</tr>
								<tr>
									<td colspan="3">상품 설명</td>
								</tr>
								<tr>
									<td colspan="3" >${product.content }</td>
								</tr>
							</tbody>
							
						</table>
						
					<div class="align-center">
						<ul class="actions">
							<li><a
								href="<%=request.getContextPath()%>/admin/product/add"
								class="button special">제품 추가</a></li>
							<li><a href="<%=request.getContextPath()%>/"
								class="button special">메인</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>

</body>
</html>