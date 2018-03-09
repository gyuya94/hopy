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
	function phoneNumCombine(){
		alert('일단 들어옴');
		var phoneNum1 = document.getElementsByName("phoneNum1");
		var phoneNum2 = document.getElementsByName("phoneNum2");
		var phoneNum3 = document.getElementsByName("phoneNum3");
		alert(phoneNum1);
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
					<form action="<%=request.getContextPath()%>/customer/auth/join"
						method="post">
						<table>
							<tbody class="align-center">
							<tr>
								<td>sphinxId</td>
								<td><input type="text" name="sphinxId" id="sphinxId" value=""
								 placeholder="sphinxId"  
								 pattern="(?=.*[a-zA-Z0-9]).{4,15}" 
								 title="알파벳과 숫자의 조합으로 15자리 이내로 입력해주세요"/></td>
							</tr>
							<tr>  
								<td>password</td>
								<td><input type="password" name="password" id="password" value=""
									placeholder="password" pattern=".{4,15}" 
									title="최소 4자리 최대 15자리까지 입력가능합니다."/></td>
							</tr>
							<tr>
								<td>name</td>
								<td><input type="text" name="name" id="name" value=""
									placeholder="name" /></td>
							</tr>
							<tr> 
								<td>address</td>
								<td><input type="text" name="address" id="address" value=""
									placeholder="address" /></td>
							</tr>
								<tr>
									<td>phone</td>
									<td>
									<div class="select-wrapper">
												<select name="phoneNum1" id="phoneNum1">
													<option value="">- phoneNum1 -</option>
													<c:forEach var="phoneNum" items="${phoneNum1 }">
														<option value="${phoneNum}" >${phoneNum}</option>
													</c:forEach>
												</select>
										</div><br>
									<div class="row uniform">
										<div class="6u 12u$(xsmall)"><input type="text" name="phoneNum2" id="phoneNum2"
										value="" placeholder="phoneNum2" /></div>
										<div class="6u 12u$(xsmall)"><input type="text" name="phoneNum3" id="phoneNum3"
										value="" placeholder="phoneNum3" /></div>
									</div>
										
										
										</td>
								</tr>
							</tbody>
						</table>

						<div class="align-center">
							<input type="submit" value="회원가입" onclick="phoneNumCombine();">
						</div>

					</form>
				</div>
			</div>
		</div>
	</section>

</body>
</html>