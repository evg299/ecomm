<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table id="pers_content">
	<tr>
		<td class="pers_links">
            <b>Личная информация</b> <br />
            <a href="#">Регистрационные данные</a> <br />
            <a href="#">Изменить пароль</a> <br />
			<b>Заказы</b> <br />
            <a href="#">Моя корзина</a> <br />
            <a href="#">Мои заказы</a> <br />
            <b>Рассылки и оповещения</b> <br />
            <a href="#">Новые сообщения</a> <br />
            <a href="#">Управление подписками</a></td>

        <td class="pers_content">
			<div class="pers_photo">
				<img src="${pageContext.request.contextPath}/filestorage/download/${person.picture.urlName}-b.${person.picture.extention}" />
                <a href="${pageContext.request.contextPath}/private/personinfo/newphoto">Загрузить новое фото</a>
			</div>
			<div class="pers_data">
				<div class="pers_data_head">Общее</div>
				<b>ФИО:</b> ${person.lname} ${person.fname} ${person.mname} <br />

                <b>Пол:</b>
                <c:choose>
                    <c:when test="${person.gender}">
                        мужской
                    </c:when>
                    <c:otherwise>
                        женский
                    </c:otherwise>
                </c:choose>
                <br />

                <b>Дата рождения:</b> <s:eval expression="@formatDateService.formatTimestamp(person.birthDate)" /> (<s:eval expression="@formatDateService.countYears(person.birthDate)" />) <br />

                <b>Адрес:</b>
                ${person.address}

				<div style="min-height: 6px;"></div>

                <c:choose>
                    <c:when test="${fn:length(personContacts) ne 0}">
                        <div class="pers_data_head">Контакты</div>
                        <c:forEach items="${personContacts}" var="personContact">
                            <b>${personContact.contactType.name}:</b> ${personContact.value} <br />
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        Контактов не оставлено.
                    </c:otherwise>
                </c:choose>

				<div style="margin-top: 10px; text-align: center;">
					<a href="${pageContext.request.contextPath}/private/personinfo/change">Изменить</a>
				</div>
			</div>
		</td>
	</tr>
</table>