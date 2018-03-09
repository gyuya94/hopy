<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		function validCheck() {
			var sphinxId = document.getElementById('sphinxId').value;
			var password = document.getElementById('password').value;
			var name = document.getElementById('name').value;
			var address = document.getElementById('address').value;
			var errorMsg = ''; //에러 메세지 담는 변수
			if (sphinxId.length == 0 || sphinxId == null) {
				errorMsg += 'Id ';
			}
			if (password.length == 0 || password == null) {
				errorMsg += 'password ';

			}
			if (name.length == 0 || name == null) {
				errorMsg += 'name ';

			}
			if (address.length == 0 || address == null) {
				errorMsg += 'address ';

			}

			if (errorMsg.length > 0) {
				alert(errorMsg + "이(가) 비었습니다");

			} else {
				//errorMst가 암것도없다 == 제대로 입력했음
				document.joinForm.submit();
			}

		}
	</script>
	<!-- One -->
	<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<p>HOPY</p>
				<h2>회원가입</h2>
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
						<h2>회원가입</h2>
					</header>
					<form:form modelAttribute="customer" method="post" id="joinForm">
						<table>
							<tbody class="align-center">
								<tr>
									<td>sphinxId</td>
									<td><form:input id="sphinxId" path="sphinxId"
											placeholder="sphinxId" pattern="(?=.*[a-zA-Z0-9]).{4,15}"
											title="영문 숫자의 조합으로 최소 4자리 최대 15자리까지 입력가능합니다." /></td>
								</tr>
								<tr>
									<td>password</td>
									<td><form:password id="password" path="password"
											pattern=".{4,15}" title="최소 4자리 최대 15자리까지 입력가능합니다." /></td>
								</tr>
								<tr>
									<td>name</td>
									<td><form:input id="name" path="name" pattern=".{2,15}"
											title="최소 2자리 최대 15자리까지 입력가능합니다." /></td>
								</tr>
								<tr>
									<td>address</td>
									<td><form:input id="address" path="address"
											pattern=".{5,30}" title="최소 4자리 최대 15자리까지 입력가능합니다." /></td>
								</tr>
								<tr>
									<td>phone</td>
									<td>
										<div class="select-wrapper">
											<select name="phoneNum1" id="phoneNum1">
												<option value="">- phoneNum1 -</option>
												<c:forEach var="phoneNum" items="${phoneNum1 }">
													<option value="${phoneNum}">${phoneNum}</option>
												</c:forEach>
											</select>
										</div>
										<br>
										<div class="row uniform">
											<div class="6u 12u$(xsmall)">
												<input type="text" name="phoneNum2" id="phoneNum2" value=""
													placeholder="phoneNum2" pattern="(?=.*[0-9]).{3,4}" />
											</div>
											<div class="6u 12u$(xsmall)">
												<input type="text" name="phoneNum3" id="phoneNum3" value=""
													placeholder="phoneNum3" pattern="(?=.*[0-9]).{3,4}" />
											</div>
										</div>


									</td>
								</tr>
							</tbody>
						</table>

						<div class="align-center">
							<input type="submit" value="회원가입" onclick="validCheck();">
						</div>

					</form:form>
				</div>
			</div>
		</div>
	</section>

</body>
</html>