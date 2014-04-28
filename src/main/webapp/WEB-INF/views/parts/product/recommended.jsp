<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:if test="${fn:length(relatedProducts) ne 0}">
    <div class="separator"></div>

    <h4>Связанные товары</h4>
    <c:forEach items="${relatedProducts}" var="product">
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
                <a href="#${product.uuid}" class="button" product-id="${product.id}"/>В корзину</a>
            </div>
        </div>
    </c:forEach>
</c:if>