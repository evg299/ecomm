<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">
        document.menuIsOpen = true;
    </script>
    <script src="${pageContext.request.contextPath}/resources/js/left-menu.js"></script>
</head>
<body>
<%@include file="parts/header.jsp" %>
<table id="main" style="width: 100%;">
    <tr>
        <td style="width: 300px; vertical-align: top;">
            <%@include file="parts/left.jsp" %>
        </td>
        <td style="vertical-align: top;">
            <div id="content">
                <div id="search">
                    <%@include file="parts/search-form.jsp" %>
                </div>

                <%@include file="parts/search/products.jsp" %>
            </div>
        </td>
    </tr>
</table>
<%@include file="parts/footer.jsp" %>
</body>
</html>
