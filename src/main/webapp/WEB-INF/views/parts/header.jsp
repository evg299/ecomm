<%@page session="false" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="header">
	<div id="logo">
		<a href="#">Название магазина</a> <br /> <span>слоган компании</span>
		<br /> <span>The time on the server is ${serverTime}.</span>
	</div>

	<div id="card">
		<a href="#">Корзина</a> <br /> <span>Товары: 0 шт.</span> <br /> <span>Отложено:
			0 шт.</span>
	</div>

	<ul id="header_menu" class="horlist">
		<li><a href="/context/detail/id/2930837/">Как заказать</a></li>
		<li><a href="/context/detail/id/200890/">Оплата</a></li>
		<li><a href="/context/detail/id/8154009/">Доставка</a></li>
		<li><a href="/context/help/">Помощь</a></li>
		<li><a href="https://www.ozon.ru/context/login/" title="Вход"
			class="">Вход</a></li>
		<li><a href="/context/newuser/" title="Регистрация" class="">Регистрация</a>
		</li>
	</ul>

	<a href="#"><img id="top_banner" src="${pageContext.request.contextPath}/resources/img/_banner_.gif" /></a>
</div>