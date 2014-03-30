<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div style="width: 300px; position: absolute;">
    <div id="main_menu_head" class="bottom_radius" style="height: 25px; line-height: 25px;"
            <c:if test="${fn:length(subCategories) eq 0}"> class="bottom_radius" </c:if> >
        <c:out value="${categoryName}"/>
    </div>
    <c:if test="${fn:length(subCategories) ne 0}">
        <div id="main_menu" style="display: none">
            <c:forEach items="${subCategories}" var="subCategory">
                <div id="sm_<c:out value="${subCategory.id}"/>" class="main_menu_item">
                    <a href="${pageContext.request.contextPath}/categories/<c:out value="${subCategory.urlName}"/>">
                        <c:out value="${subCategory.name}"/>
                    </a>
                </div>
            </c:forEach>
        </div>
        <div id="sm_popup" style="top: 33px; left: 300px;"></div>
    </c:if>
</div>