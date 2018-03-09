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

		<div class="align-center">
				<c:forEach items="${pageList }" var="page">
					<c:choose>
						<c:when test="${clickPage==page }">
							<input type="submit" name="clickPage" value="${page }"
								class="button">
						</c:when>
						<c:otherwise>
							<input type="submit" name="clickPage" value="${page }"
								class="button alt">
						</c:otherwise>
					</c:choose>

				</c:forEach>
		</div>
</body>
</html>