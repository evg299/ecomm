<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<h3>Поиск по запросу «${query}»</h3>

<table id="sorting">
    <tr>
        <td>Сортировать:</td>
        <c:choose>
            <c:when test="${'price' == order}">
                <td><span>По цене</span></td>
            </c:when>
            <c:otherwise>
                <td><a href="?page=${page}&query=${query}&order=price">По цене</a></td>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${'receipt' == order}">
                <td><span>По дате поступления</span></td>
            </c:when>
            <c:otherwise>
                <td><a href="?page=${page}&query=${query}&order=receipt">По дате поступления</a></td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>

<div id="products" class="clear">
    <h3>Найдено товаров: ${productsCount}</h3>

    <c:if test="${fn:length(products) ne 0}">
        <c:forEach items="${products}" var="product">
            <div class="product_showcase">
                <a href="${pageContext.request.contextPath}/products/${product.uuid}" target="_blank">
                    <img src="${pageContext.request.contextPath}/filestorage/download/${product.picture.urlName}-s.${product.picture.extention}"/>
                </a>

                <div class="prod_name">
                    <a href="${pageContext.request.contextPath}/products/${product.uuid}" title="${product.name}"
                       target="_blank">${product.name}</a>
                </div>
                <div class="prod_prise">
                    <s:eval expression="@priceFormatter.format(product.price)"/>
                    <small>${siteCurrency.shortName}</small>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${product.containsVariants}">
                            <a href="${pageContext.request.contextPath}/products/${product.uuid}"
                               class="button product_list_button" product-id="${product.id}"/>Подробнее</a>
                        </c:when>
                        <c:otherwise>
                            <a class="button product_list_button add_to_card" product-id="${product.id}"/>В корзину</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </c:if>

</div>

<div id="total_products">Найдено товаров: ${productsCount}</div>

<div id="paginator">
    Страницы:
    <c:forEach items="${paginator.items}" var="item">
        <c:choose>
            <c:when test="${true == item.current}">
                <b>${item.page + 1}</b>
            </c:when>

            <c:otherwise>
                <a href="?page=${item.page}&order=${order}">${item.page + 1}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>