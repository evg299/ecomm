<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/left-menu.js"></script>
</head>
<body>
<%@include file="parts/header.jsp" %>

<%@include file="parts/collapsed-left-menu.jsp" %>

<div id="search" style="margin-left: 305px;">
    <%@include file="parts/search-form.jsp" %>
</div>

<div>
    <h1>Вход в систему</h1>

    <form name="f" action="${pageContext.request.contextPath}/j_spring_security_check" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table>
            <tr>
                <td>Username:</td>
                <td><input type='text' name='username'/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='password'></td>
            </tr>
            <tr>
                <td>Remember me:</td>
                <td><input type="checkbox" name="remember_me"></td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit">&nbsp;<input name="reset" type="reset"></td>
            </tr>
        </table>
    </form>
</div>

<%@include file="parts/footer.jsp" %>
</body>
</html>