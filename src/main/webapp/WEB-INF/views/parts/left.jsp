<%@ page session="false" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<div id="left">
    <div id="main_menu_head" style="height: 25px; line-height: 25px;"
            <c:if test="${fn:length(subCategories) eq 0}"> class="bottom_radius" </c:if> >
        <c:out value="${categoryName}"/>
    </div>
    <c:if test="${fn:length(subCategories) ne 0}">
        <div id="main_menu">
            <c:forEach items="${subCategories}" var="subCategory">
                <div id="sm_<c:out value="${subCategory.id}"/>" class="main_menu_item">
                    <a href="${pageContext.request.contextPath}/categories/<c:out value="${subCategory.urlName}"/>"> <c:out
                            value="${subCategory.name}"/> </a>
                </div>
            </c:forEach>
        </div>
        <div id="sm_popup"></div>
    </c:if>

    <img style="margin-top: 30px;" src="${pageContext.request.contextPath}/resources/img/vk.png"/>
</div>