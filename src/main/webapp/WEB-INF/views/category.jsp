<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="resources/css/style.css" />
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="resources/js/left-menu.js"></script>
</head>
<body>
	<%@include file="parts/header.jsp"%>
	<table id="main" style="width: 100%;">
		<tr>
			<td style="width: 300px; vertical-align: top;">
				<%@include file="parts/left.jsp"%>
			</td>
			<td style="vertical-align: top;">
				<div id="content">
					<div id="search">
						<%@include file="parts/search-form.jsp"%>
					</div>

					<%@include file="parts/breadcrump.jsp"%>

					<%@include file="parts/category/sublinks.jsp"%>

					<%@include file="parts/category/products.jsp"%>
				</div>
			</td>
		</tr>
	</table>
	<%@include file="parts/footer.jsp"%>
</body>
</html>
