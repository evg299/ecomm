<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:if test="${fn:length(productsLastVisited) ne 0}">
    <div id="products" class="clear">
        <h3>Вы недавно смотрели</h3>
        <c:forEach items="${productsLastVisited}" var="product">
            <div class="product_showcase">
                <a href="${pageContext.request.contextPath}/products/${product.uuid}" target="_blank">
                    <img src="${pageContext.request.contextPath}/filestorage/download/${product.picture.urlName}-s.${product.picture.extention}"/>
                </a>

                <div class="prod_name">
                    <a href="${pageContext.request.contextPath}/products/${product.uuid}" title="${product.name}" target="_blank">${product.name}</a>
                </div>
                <div class="prod_prise">
                    <s:eval expression="@priceFormatter.format(product.price)" /> <small>${siteCurrency.shortName}</small>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${product.containsVariants}">
                            <a href="${pageContext.request.contextPath}/products/${product.uuid}" class="button product_list_button" product-id="${product.id}"/>Подробнее</a>
                        </c:when>
                        <c:otherwise>
                            <a class="button product_list_button add_to_card" product-id="${product.id}"/>В корзину</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>

<c:if test="${fn:length(productsRecommended) ne 0}">
    <div id="products" class="clear">
        <h3>Рекомендуемые товары</h3>
        <c:forEach items="${productsRecommended}" var="product">
            <div class="product_showcase">
                <a href="${pageContext.request.contextPath}/products/${product.uuid}" target="_blank">
                    <img src="${pageContext.request.contextPath}/filestorage/download/${product.picture.urlName}-s.${product.picture.extention}"/>
                </a>

                <div class="prod_name">
                    <a href="${pageContext.request.contextPath}/products/${product.uuid}" title="${product.name}" target="_blank">${product.name}</a>
                </div>
                <div class="prod_prise">
                    <s:eval expression="@priceFormatter.format(product.price)" /> <small>${siteCurrency.shortName}</small>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${product.containsVariants}">
                            <a href="${pageContext.request.contextPath}/products/${product.uuid}" class="button product_list_button" product-id="${product.id}"/>Подробнее</a>
                        </c:when>
                        <c:otherwise>
                            <a class="button product_list_button add_to_card" product-id="${product.id}"/>В корзину</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>

<c:if test="${fn:length(productsMaxSell) ne 0}">
    <div id="products" class="clear">
        <h3>Бестселлеры</h3>
        <c:forEach items="${productsMaxSell}" var="product">
            <div class="product_showcase">
                <a href="${pageContext.request.contextPath}/products/${product.uuid}" target="_blank">
                    <img src="${pageContext.request.contextPath}/filestorage/download/${product.picture.urlName}-s.${product.picture.extention}"/>
                </a>

                <div class="prod_name">
                    <a href="${pageContext.request.contextPath}/products/${product.uuid}" title="${product.name}" target="_blank">${product.name}</a>
                </div>
                <div class="prod_prise">
                    <s:eval expression="@priceFormatter.format(product.price)" /> <small>${siteCurrency.shortName}</small>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${product.containsVariants}">
                            <a href="${pageContext.request.contextPath}/products/${product.uuid}" class="button product_list_button" product-id="${product.id}"/>Подробнее</a>
                        </c:when>
                        <c:otherwise>
                            <a class="button product_list_button add_to_card" product-id="${product.id}"/>В корзину</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>
