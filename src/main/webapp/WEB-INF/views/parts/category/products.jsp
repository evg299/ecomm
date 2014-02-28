<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<table id="sorting">
    <tr>
        <td>Сортировать:</td>
        <c:choose>
            <c:when test="${'price' == order}">
                <td><span>По цене</span></td>
            </c:when>
            <c:otherwise>
                <td><a href="?page=${page}&order=price">По цене</a></td>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${'receipt' == order}">
                <td><span>По дате поступления</span></td>
            </c:when>
            <c:otherwise>
                <td><a href="?page=${page}&order=receipt">По дате поступления</a></td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>

<div id="products" class="clear">
<h3>${productsCount} товаров</h3>

<c:if test="${fn:length(products) ne 0}">
        <c:forEach items="${products}" var="product">
            <div class="product_showcase">
                <a href="${pageContext.request.contextPath}/products/${product.uuid}" target="_blank">
                    <img src="${pageContext.request.contextPath}/filestorage/download/${product.picture.urlName}-s.${product.picture.extention}"/>
                </a>

                <div class="prod_name">
                    <a href="${pageContext.request.contextPath}/products/${product.uuid}" title="${product.name}" target="_blank">${product.name}</a>
                </div>
                <div class="prod_prise">
                    ${product.price.value} <small>${product.price.currency.shortName}</small>
                </div>
                <div>
                    <a href="#${product.uuid}" class="button" product-id="${product.id}"/>В корзину</a>
                </div>
            </div>
        </c:forEach>
</c:if>

</div>

<div id="total_products">Найдено ${productsCount} товаров</div>

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