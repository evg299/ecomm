<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="header">
    <div id="logo">
        <a href="${pageContext.request.contextPath}/">${siteName}</a>
        <br/>

        <div class="site_desc">${siteDesc}</div>
    </div>

    <div id="card">
        <a href="${pageContext.request.contextPath}/card">Корзина</a>
        <br/>
        <span>Товары: 0 шт.</span>
        <br/>
        <span>Отложено: 0 шт.</span>
    </div>

    <ul id="header_menu" class="horlist">
        <li><a href="${pageContext.request.contextPath}/static/howto">Как заказать</a></li>
        <li><a href="${pageContext.request.contextPath}/static/pay">Оплата</a></li>
        <li><a href="${pageContext.request.contextPath}/static/delivery">Доставка</a></li>
        <li><a href="${pageContext.request.contextPath}/static/help">Помощь</a></li>
        <li><a href="${pageContext.request.contextPath}/static/login" title="Вход" class="">Вход</a></li>
        <li><a href="${pageContext.request.contextPath}/registration" title="Регистрация" class="">Регистрация</a></li>
    </ul>

    <a href="#"><img id="top_banner" src="${pageContext.request.contextPath}/resources/img/_banner_.gif"/></a>
</div>