<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/left-menu.js"></script>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<%@include file="parts/header.jsp"%>

<%@include file="parts/collapsed-left-menu.jsp"%>

<div id="search" style="margin-left: 305px;">
    <%@include file="parts/search-form.jsp"%>
</div>

<%@include file="parts/order/content.jsp"%>

<%@include file="parts/footer.jsp"%>
</body>
</html>