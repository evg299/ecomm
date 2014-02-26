<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<table id="sorting">
    <tr>
        <td>Сортировать:</td>
        <td><a href="?order=price">По цене</a></td>
        <td><span>По дате поступления</span></td>
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
    <a href="?page=1">1</a>
    <b>2</b>
    <a href="?page=3">3</a>
    <a href="?page=4">4</a>
    <a href="?page=5">5</a>
</div>