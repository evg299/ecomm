<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page session="false" contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/mocha.js"></script>
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

                <%@include file="parts/main/slider.jsp" %>

                <%@include file="parts/main/products.jsp" %>
            </div>
        </td>
    </tr>
</table>

<%@include file="parts/footer.jsp" %>
</body>
</html>