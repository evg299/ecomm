<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="comments">
	<h3>Отзывы</h3>

    <c:choose>
        <c:when test="${fn:length(comments) ne 0}">
            <c:forEach items="${comments}" var="comment">
                <div class="comment">
                    <h5>${comment.title}</h5>
                    <div class="datePublished"><s:eval expression="@formatDateService.formatTimestamp(comment.creationDate)" /></div>
                    <div class="author">
                            ${comment.person.fname} ${comment.person.mname} ${comment.person.lname} (<s:eval expression="@formatDateService.countYears(comment.creationDate, comment.person.birthDate)" />)
                            <a href="${pageContext.request.contextPath}/comments/${comment.person.id}/" class="">все отзывы</a>
                    </div>
                    <div>
                            ${comment.content}
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            Отзывов пока нет.
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${isAuth}">
            <div id="addComment">
                <h4>Оставить отзыв о товаре</h4>
                <form:form id="addCommentForm" method="post" commandName="commentForm">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <table>
                        <tr style="vertical-align: top;">
                            <td style="text-align: right;">*Заголовок:</td>
                            <td><form:input path="title" style="width: 633px;" /></td>
                            <td><form:errors cssClass="error" path="title" /></td>
                        </tr>
                        <tr style="vertical-align: top;">
                            <td style="text-align: right;">*Содержание:</td>
                            <td><form:textarea path="text" style="width: 633px; height: 300px;" /></td>
                            <td><form:errors cssClass="error" path="text" /></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="Отправить" style="margin-top: 5px;" /></td>
                            <td></td>
                        </tr>
                    </table>
                </form:form>
            </div>
        </c:when>
        <c:otherwise>
            Чтобы оставить отзыв вам нужно залогиниться
        </c:otherwise>
    </c:choose>

</div>