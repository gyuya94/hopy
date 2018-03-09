<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function calls() {

		var f = document.getElementById("pageNum"); // form 엘리멘트 생성 
		f.submit();
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body onload="calls()">

	<form action="<%=request.getContextPath()%>/test/pageTest"
		method="post" id="pageNum">
		<input type="hidden" id="clickPage" value="1"/>
	</form>


</body>
</html>