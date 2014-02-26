<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="sub_category_links">
    <c:set var="isFirstSubCat" scope="request" value="true"/>
    <c:forEach items="${subCategories}" var="subCategory">
        <c:choose>
            <c:when test="${true == isFirstSubCat}">
                <a href="${pageContext.request.contextPath}/categories/<c:out value="${subCategory.urlName}"/>"><c:out value="${subCategory.name}"/></a>
                <c:set var="isFirstSubCat" scope="request" value="false"/>
            </c:when>

            <c:otherwise>
                , <a href="${pageContext.request.contextPath}/categories/<c:out value="${subCategory.urlName}"/>"><c:out value="${subCategory.name}"/></a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>