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
	 var sHTML = '<img src=<%=request.getContextPath()%>/resources/images/'+ filepath + '>';
			oEditors.getById["ir1"].exec("PASTE_HTML", [ sHTML ]);
		}
		
	
	var optionCnt = 1;
	function addOption(){
		var str = "<tr id=\"option"+(optionCnt+1)+"\"><td>옵션"+(optionCnt+1)+"</td>"+
		"<td><input type=\"text\" /></td><td><input type=\"text\" /></td>"+
		"<td><input type=\"text\" /></td><td><input type=\"button\" value=\"추가\"  onclick=\"addOptionValue();\" class=\"button alt\"></td></tr>";
		
			alert(str);
				$('#option'+(optionCnt)).after(str);
		optionCnt++;
	}
	
	var optionValueCnt = 1;
	function addOptionValue(){
		var str = "<tr id=\"option"+(optionCnt+1)+"\"><td></td>"+
		"<td></td><td><input type=\"text\" /></td><td><input type=\"text\" /></td></tr>";
		
		alert(str);
		$('#option'+(optionCnt)).after(str);
		optionCnt++;
	}
	
	var mainImageCnt = 1;
	function addMainImage(){
		var str = "<tr id=\"mainImage"+(mainImageCnt+1)+"\"><td colspan=\"2\">상품 메인 이미지"+(mainImageCnt+1)+"</td>"+
		"<td colspan=\"2\" ><input type=\"file\" name=\"file\" /></td></tr>";
		
			alert(str);
				$('#mainImage'+(mainImageCnt)).after(str);
				mainImageCnt++;
	}
	
	$(document).ready(function(){
		 // 옵션추가 버튼 클릭시
	    $("#addItemBtn").click(function(){
	        // item 의 최대번호 구하기
	        var lastItemNo = $("#productInputTable tr:last").attr("class").replace("item", "");
	        var newitem = $("#productInputTable tr:eq("+(7+mainImageCnt-1)+")").clone();
	        newitem.removeClass();
	        newitem.find("td:eq(0)").attr("rowspan", "1");
	        newitem.find("td:eq(0) > .optionValueNum").attr("value", 1);
	        newitem.addClass("item"+(parseInt(lastItemNo)+1));

	        $("#productInputTable").append(newitem);
	    });



	     
	    $(document).on('click', '.addBtn', function() {
	        var clickedRow = $(this).parent().parent();
	        var cls = clickedRow.attr("class");

	        // tr 복사해서 마지막에 추가
	        var newrow = clickedRow.clone();
	        newrow.find("td:eq(0)").remove(); 
	        newrow.insertAfter($("#productInputTable ."+cls+":last"));
			//productInputTable 안에 newrow를 넣겠다
	        //option1에 해당하는 optionValue가 몇인지 알려고
	        /* var optionValueCnt = $("#productInputTable > tbody > tr> td > #optionValueNum").attr("value");
	        optionValueCnt++;
	        alert(optionValueCnt);
	        $("#productInputTable > tbody > tr> td > #optionValueNum").attr("value", optionValueCnt);
	         */
	        // rowspan 조정
	        resizeRowspan(cls);
	    	});
	    
	    $(document).on('click', '.delBtn', function() {
	    	 var clickedRow = $(this).parent().parent();
	         var cls = clickedRow.attr("class");
	          
	         // 각 항목의 첫번째 row를 삭제한 경우 다음 row에 td 하나를 추가해 준다.
	         if( clickedRow.find("td:eq(0)").attr("rowspan") ){
	             if( clickedRow.next().hasClass(cls) ){
	                 clickedRow.next().prepend(clickedRow.find("td:eq(0)"));
	             }
	         }

	         clickedRow.remove();

	         // rowspan 조정
	         resizeRowspan(cls);
	    	});



	    // cls : rowspan 을 조정할 class ex) item1, item2, ...
	    function resizeRowspan(cls){
	        var rowspan = $("."+cls).length;
	        $("."+cls+":first td:eq(0)").attr("rowspan", rowspan);
	        $("."+cls+":first td:eq(0) > .optionValueNum").attr("value", rowspan);
	    }
	});
	
	</script>

	<!-- One -->
	<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<p>HOPY</p>
				<h2>제품 등록</h2>
			</header>
		</div>
	</section>

	<!-- Two -->
	<section id="two" class="wrapper style2">
		<div class="inner">
			<div class="box">
			<header class="align-center">
						<p>HOPY</p>
						<h2>제품 등록</h2>
					</header>
				<div class="content">
					<form action="<%=request.getContextPath()%>/admin/product/add"
						method="post"  enctype="multipart/form-data">
					
						<table class="align-center" id="productInputTable">
							<tbody >
								<tr>
									<td colspan="2">제품 이름</td>
									<td colspan="2"><input type="text" name="productName" id="productName"
										value="" placeholder="productName" /></td>
								</tr>
								<tr>
									<td colspan="2">판매가격</td>
									<td colspan="2"> <input type="text" name="price" id="price"
										value="" placeholder="price" /></td>
								</tr>
								<tr>
									<td colspan="2">카테고리</td>
									<td colspan="2">
										<div class="select-wrapper">
												<select name="selectedCategory" id="selectedCategory">
													<option value="">- category -</option>
													<c:forEach var="category" items="${categoryList }">
														<option value="${category.categoryId}" >${category.categoryName}</option>
													</c:forEach>
												</select>
										</div><br>
									</td>
								</tr>
								
								<tr id="mainImage1">
									<td colspan="2">상품 메인 이미지 <input type="button" value="추가"  onclick="addMainImage();" class="button alt"></td>
									<td colspan="2" ><input type="file" name="file"></td>
								</tr>
								<tr>
									<td colspan="2">상품 코멘트</td>
									<td colspan="2"><input type="text" name="comment" id="comment"
										value="" placeholder="comment" /></td>
								</tr>
								<tr>
									<td colspan="4"><textarea id="ir1" cols="10" name="ir1" class="form-control"
								style="width: 650px; height: 300px;"></textarea></td>
								</tr>
								
								<tr><td><input type="button"  id="addItemBtn" value="옵션 추가" > </td>
								<td>optionValue</td><td>addPrice</td>
								</tr>
								
								<tr class="item1" >
									<td>
									<input type="hidden" class="optionValueNum" name="optionValueNum" value="1"/>
									<input type="text" name="optionName"  placeholder="optionName"/>
								<input type="button" class="addBtn" value="옵션값추가" ></td>
								<td><input type="text" name="optionValue" placeholder="optionValue"/></td>
								<td><input type="text" name="addPrice" placeholder="addPrice" value="0"/></td>
								<td><input type="button" class="delBtn" value="삭제"></td>
							</tr>
							</tbody>
							
						</table>

						<div class="align-center">
							<input type="submit" id="save" value="등록">
						</div>
						<div class="align-center">
							
								<a
									href="<%=request.getContextPath()%>/admin/product/add"
									class="button special">제품 목록</a>

							
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

</body>
</html>