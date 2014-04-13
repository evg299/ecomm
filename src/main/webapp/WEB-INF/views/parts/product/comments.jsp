<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="comments">
	<h3>Отзывы</h3>

    <c:choose>
        <c:when test="${fn:length(comments) ne 0}">
            <c:forEach items="${comments}" var="comment">
                <div class="comment">
                    <h5>${comment.title}</h5>
                    <div class="datePublished"><s:eval expression="@dateFormatService.formatTimestamp(comment.creationDate)" /></div>
                    <div class="author">
                            ${comment.person.fname} ${comment.person.mname} ${comment.person.lname} (<s:eval expression="@dateFormatService.countYears(comment.creationDate, comment.person.birthDate)" />)
                            <a href="${pageContext.request.contextPath}/person/${comment.person.id}/comments" class="">все отзывы</a>
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

	<div id="addComment">
		<h4>Оставить отзыв о товаре</h4>
		<form id="addCommentForm">
			<b>Заголовок:</b>
            <br />
            <input name="title" type="text" style="width: 633px;" />
            <br />

            <b>Содержание:</b> <br />
			<textarea name="content" style="width: 633px; height: 300px;"></textarea>
            <br />

            <input type="submit" value="Отправить" style="margin-top: 5px;" />
		</form>
	</div>

</div>