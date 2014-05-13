<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/cookie-control.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var cookies = getCookies();
        $(".product_list_button").each(function (index) {
            var prodId = $(this).attr("product-id");
            for (var key in cookies) {
                if (key.startsWith("card-")) {
                    var id = key.substr("card-".length);
                    if (prodId == id) {
                        $(this).parent().append($("<div id='bay_notification_small'>Товар уже в корзине</div>"));
                        $(this).remove();
                        break;
                    }
                }
            }
        });

        $(".add_to_card").click(function (e) {
            setCookie("card-" + $(this).attr("product-id"), "count=1&date=" + new Date().getTime());
            $(this).parent().append($("<div id='bay_notification_small'>Товар уже в корзине</div>"));
            $(this).remove();
        });
    });
</script>

<script type="text/javascript">
    $(document).ready(function () {
        setInterval(function () {
            dispalayCountCard();
        }, 100);

        // отображение количества товаров в корзине
        function dispalayCountCard() {
            var cookies = getCookies();
            var countCard = 0;
            for (var key in cookies) {
                if (key.startsWith("card-"))
                    countCard++;
            }
            $('#countCard').text(countCard);
        }

    });
</script>
<div id="header">
    <div id="logo">
        <a href="${pageContext.request.contextPath}/">${siteName}</a>
        <br/>

        <div class="site_desc">${siteDesc}</div>
    </div>

    <div id="card">
        <a href="${pageContext.request.contextPath}/card">Корзина</a>
        <br/>
        <span>Товары: <b id="countCard"> </b> шт.</span>
    </div>

    <ul id="header_menu" class="horlist">
        <c:choose>
            <c:when test="${isAuth}">
                <li style="font-size: 15px; margin-right: 100px;">Вы вошли как <a href="${pageContext.request.contextPath}/private/" class="">${currentUser.login}</a></li>
            </c:when>
        </c:choose>

        <li><a href="${pageContext.request.contextPath}/static/howto">Как заказать</a></li>
        <li><a href="${pageContext.request.contextPath}/static/pay">Оплата</a></li>
        <li><a href="${pageContext.request.contextPath}/static/delivery">Доставка</a></li>
        <li><a href="${pageContext.request.contextPath}/static/help">Помощь</a></li>
        <c:choose>
            <c:when test="${isAuth}">
                <li><a href="${pageContext.request.contextPath}/logout" title="Выход" class="">Выход</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/login" title="Вход" class="">Вход</a></li>
                <li><a href="${pageContext.request.contextPath}/registration" title="Регистрация" class="">Регистрация</a></li>
            </c:otherwise>
        </c:choose>
    </ul>

    <a href="#"><img id="top_banner" src="${pageContext.request.contextPath}/resources/img/_banner_.gif"/></a>
</div>

