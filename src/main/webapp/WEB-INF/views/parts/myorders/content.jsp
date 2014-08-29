<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<div id="static_content">
    <h3>Заказы</h3>

    <div id="card_types">
		<span>
            <c:choose>
                <c:when test="${status eq 'all'}">
                    Все (${fn:length(userOrders)})
                </c:when>
                <c:otherwise>
                    <a href="?">Все (${fn:length(userOrders)})</a>
                </c:otherwise>
            </c:choose>
        </span>
        <span>
            <c:choose>
                <c:when test="${status eq 'active'}">
                    Активные (${countActive})
                </c:when>
                <c:otherwise>
                    <a href="?status=active">Активные (${countActive})</a>
                </c:otherwise>
            </c:choose>
        </span>
        <span>
            <c:choose>
                <c:when test="${status eq 'complete'}">
                    Выполненные (${countDone})
                </c:when>
                <c:otherwise>
                    <a href="?status=complete">Выполненные (${countDone})</a>
                </c:otherwise>
            </c:choose>
        </span>
    </div>

    <c:forEach items="${userOrders}" var="userOrder">
        <c:if test="${fn:length(userOrder.orderProducts) gt 0}">
            <div class="order">
                <div class="order_name">
                    Заказ № ${userOrder.uuid} <span><s:eval
                        expression="@dateFormatter.formatDate(userOrder.creationDate)"/></span>
                </div>
                <table id="card_table">
                    <tr>
                        <th>Название товара</th>
                        <th>Вес</th>
                        <th>Кол-во</th>
                        <th>Стоимость</th>
                    </tr>
                    <c:forEach items="${userOrder.orderProducts}" var="orderProduct">
                        <tr>
                            <td>
                                <table>
                                    <tr>
                                        <td>
                                            <img
                                                    src="${pageContext.request.contextPath}/filestorage/download/${orderProduct.product.picture.urlName}-m.${orderProduct.product.picture.extention}"
                                                    alt="${orderProduct.product.name}"
                                                    style="width: 60px; border-width: 0px;"/>
                                        <td>
                                            <div class="card_product_name">
                                                <a href="${pageContext.request.contextPath}/products/${orderProduct.product.uuid}"
                                                   target="_blank">${orderProduct.product.name}</a>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td style="text-align: center;">
                                <c:choose>
                                    <c:when test="${orderProduct.product.weight ne null}">
                                        <span id="weight-${orderProduct.product.id}">${orderProduct.product.weight}</span>
                                        <span id="weight-unit-${orderProduct.product.id}">${siteWeightUnit.abbr}</span>
                                    </c:when>
                                    <c:otherwise>
                                        не указан
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="text-align: center;">
                                <span style="padding: 5px; font-weight: bold;">${orderProduct.count}</span>
                            </td>
                            <td style="text-align: center;">
                                <s:eval expression="@priceFormatter.formatInCard(orderProduct.product.price)"/>
                                <span class="card_currency">${siteCurrency.shortName}</span>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <table class="order_detail">
                    <tr>
                        <td>
                            <b>Доставка</b>
                            <br/>
                            Служба доставки: <i><s:eval expression="@deliveryFormatter.format(userOrder.delivery)"/></i>.
                            <br/>
                                ${userOrder.delivery.address}
                        </td>
                        <td>
                            <div class="order_detail_sum">
                                <b>Стоимость товаров:</b> ${userOrder.coast} ${siteCurrency.shortName}
                            </div>
                            <div class="order_detail_sum">
                                <b>Суммарный вес:</b> ${userOrder.delivery.weight} ${siteWeightUnit.abbr}
                            </div>
                            <div class="order_detail_sum">
                                <b>Доставка:</b> ${userOrder.delivery.coast} ${siteCurrency.shortName}
                            </div>
                            <div>
                                <b>К оплате:</b> ${userOrder.sumCoast} ${siteCurrency.shortName}
                            </div>
                        </td>
                    </tr>
                </table>
                <div style="text-align: right;">
                    <c:if test="${userOrder.expects}">
                        Ожидает поступления в службу доставки
                    </c:if>
                    <c:if test="${userOrder.delivered}">
                        Доставляется
                    </c:if>
                    <c:if test="${userOrder.done}">
                        <div style="display: inline-block;" class="button">
                            Повторить заказ
                        </div>
                    </c:if>
                </div>
            </div>
        </c:if>
    </c:forEach>
</div>