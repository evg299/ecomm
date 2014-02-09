<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="breadcrump">
	<a href="${pageContext.request.contextPath}/">Главная</a>
	<c:forEach var="hyperLink" items="${breadcrump.hyperLinks}">
		>> <a href="${hyperLink.url}">${hyperLink.text}</a>
	</c:forEach>
</div>