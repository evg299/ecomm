<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="resources/css/style.css" />
</head>
<body>
	<div style="margin: 0 auto; width: 666px;">
		<div>
			<a href="#">Название магазина</a> 
			<br /> 
			<big><s:message code="registration" /></big>
		</div>
		<br /> <br />
		<form:form id="regform" method="post" commandName="regform">
			<form:errors path="*" cssClass="error" />
			<table id="registration_tbl">
				<tr>
					<td>*Имя:</td>
					<td><form:input path="fname" /></td>
					<td><form:errors cssClass="error" path="fname" /></td>
				</tr>
				<tr>
					<td>*Фамилия:</td>
					<td><form:input path="lname" /></td>
					<td><form:errors cssClass="error" path="lname" /></td>
				</tr>
				<tr>
					<td>*E-mail:</td>
					<td><form:input path="email" /></td>
					<td><form:errors cssClass="error" path="email" /></td>
				</tr>
				<tr>
					<td>*Пароль:</td>
					<td><form:password path="pwd1" /></td>
					<td><form:errors cssClass="error" path="pwd1" /></td>
				</tr>
				<tr>
					<td>*Повтор пароля:</td>
					<td><form:password path="pwd2" /></td>
					<td><form:errors cssClass="error" path="pwd2" /></td>
				</tr>
				<tr>
					<td></td>
					<td><form:checkbox path="distribution" /> Подписаться на
						Email-уведомления об акциях.</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td><form:checkbox path="acceptRules" /> Нажимая кнопку
						"Продолжить", я подтверждаю свою дееспособность, даю согласие на
						обработку моих персональных данных.</td>
					<td><form:errors path="acceptRules" /></td>
				</tr>
			</table>
			<div style="width: 200px; margin: 5px;" class="button"
				onclick="document.forms['regform'].submit();">Продолжить</div>
		</form:form>
	</div>


	<div id="sub_footer">
		© 1998-2013 ООО "Интернет Решения". <br /> Все права защищены.
	</div>
</body>
</html>