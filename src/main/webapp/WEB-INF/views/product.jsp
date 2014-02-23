<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="resources/css/style.css" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="resources/js/left-menu.js"></script>
<script src="resources/js/popup.js"></script>
</head>
<body>
	<div id="block_light"></div>

	<div id="dialog">
		<div style="text-align: right; font-size: 11px;">
			<a id="dialog_close" href="#">Закрыть</a>
		</div>
		<img id="sh_image" src="resources/img/jqzoom/imgProd/triumph_big1.jpg"
			title="triumph">
		<table style="margin: 0 auto;">
			<tr>
				<td><a href="#">&#60;&#60; Пред.</a></td>
				<td><a href="#">След. &#62;&#62;</a></td>
			</tr>
		</table>
	</div>

	<%@include file="parts/header.jsp"%>

	<%@include file="parts/collapsed-left-menu.jsp"%>

	<div id="search" style="margin-left: 305px;">
		<%@include file="parts/search-form.jsp"%>
	</div>

	<div id="product_content">
		<%@include file="parts/breadcrump.jsp"%>

		<%@include file="parts/product/details.jsp"%>
	</div>

	<%@include file="parts/product/comments.jsp"%>

	<%@include file="parts/footer.jsp"%>
</body>
</html>
