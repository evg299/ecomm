<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="breadcrump">
	<a href="${pageContext.request.contextPath}">Главная</a> 
	<c:forEach var="hyperLink" items="${breadcrump.hyperLinks}">
		>> <a href="${hyperLink.url}">${hyperLink.text}</a>
	</c:forEach>
</div>