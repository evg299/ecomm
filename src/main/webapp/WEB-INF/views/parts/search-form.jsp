<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form action="${pageContext.request.contextPath}/search">
	<table style="width: 100%;">
		<tr>
			<td><input type="text" style="width: 450px;" placeholder="Введите запрос" name="query" value="${query}" />
                <input type="submit" value="Найти" /></td>

            <td style="text-align: right; padding-right: 5px;"><a
				href="/context/detail/id/2930837/">Заказы</a> &nbsp;&nbsp; <a
				href="/context/detail/id/200890/">Личный кабинет</a></td>
		</tr>
	</table>
</form>
