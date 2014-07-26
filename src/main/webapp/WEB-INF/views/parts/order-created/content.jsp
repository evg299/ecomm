<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<div id="card_content">
    <h3 style="margin-left: 10px;">Заказ № ${orderNum} создан</h3>

    <div id="card_card">
        <table id="card_table">
            <tr>
                <th>Название товара</th>
                <th>Вес</th>
                <th>Кол-во</th>
                <th style="width: 145px;">Стоимость</th>
            </tr>
            <c:forEach items="${cardProducts}" var="cardProduct">
                <tr id="rowid-${cardProduct.product.id}" class="product_row">
                    <td>
                        <table>
                            <tr>
                                <td>
                                    <img
                                            src="${pageContext.request.contextPath}/filestorage/download/${cardProduct.product.picture.urlName}-m.${cardProduct.product.picture.extention}"
                                            alt="${cardProduct.product.name}"
                                            style="width: 60px; border-width: 0px;"/>
                                </td>
                                <td>
                                    <div class="card_product_name">
                                        <a href="${pageContext.request.contextPath}/products/${cardProduct.product.uuid}"
                                           target="_blank">${cardProduct.product.name}</a>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td style="text-align: center;">
                        <c:choose>
                            <c:when test="${cardProduct.product.weight ne null}">
                                <span id="weight-${cardProduct.product.id}">${cardProduct.product.weight}</span>
                                <span id="weight-unit-${cardProduct.product.id}">${siteWeightUnit.abbr}</span>
                            </c:when>
                            <c:otherwise>
                                не указан
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="text-align: center;">
                        <input id="picker-${cardProduct.product.id}" name="picker-${cardProduct.product.id}"
                               type="hidden"
                               value="${cardProduct.count}"/>
                        <input id="price-${cardProduct.product.id}" name="price-${cardProduct.product.id}"
                               type="hidden"
                               value="<s:eval expression="@priceFormatter.formatInCard(cardProduct.product.price)" />"/>
                        <span style="padding: 5px; font-weight: bold;">${cardProduct.count}</span>
                        <span style="padding: 5px;">x</span>
                        <s:eval expression="@priceFormatter.formatInCard(cardProduct.product.price)"/>
                    </td>
                    <td style="text-align: center;">
                        = <span id="multuPrice-${cardProduct.product.id}" class="card_price"><s:eval
                            expression="@priceFormatter.formatInCard(cardProduct.price)"/></span>
                        <span class="card_currency">${siteCurrency.shortName}</span>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <table style="width: 100%; margin-top: 15px;">
            <tr>
                <td class="card_score">
                    <div>
                        <b>Цена без доставки:</b> <span id="card_sum_price">${sumPrice}</span>
                        <span class="card_sum_currency">${siteCurrency.shortName}</span>
                        <input id="card_sum_price_hidden" type="hidden" name="card_sum" value="0"/>
                    </div>
                    <div>
                        <b>Цена доставки:</b> <span id="delivery_price">${deliveryPrice}</span>
                        <span class="card_sum_currency">${siteCurrency.shortName}</span>
                        <input id="delivery_price_hidden" type="hidden" name="delivery_price" value="0"/>
                    </div>
                    <div>
                        <b>Суммарная стоимость:</b> <span id="sum_price">${allPrice}</span>
                        <span class="card_sum_currency">${siteCurrency.shortName}</span>
                        <input id="sum_price_hidden" type="hidden" name="sum_price" value="0"/>
                    </div>
                    <div id="sum_weight">
                        <b>Суммарный вес:</b> <span id="card_sum_weight">${sumWeight}</span>
                        <span class="card_sum_unit">${siteWeightUnit.abbr}</span>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <hr/>

    <div>
        Служба доставки: ${deliveryService.deliveryName}
    </div>

    <div>
        Адрес доставки: ${deliveryAddress}
    </div>

    <div>
        Статус доставки: ожидает поступления в службу доставки, (Доставляется, Доставлено)
    </div>

    <hr/>

    <div>
        Статус оплаты: не оплачен (оплачен)
    </div>

    <div>
        Оплатить: <img src="https://www.paypalobjects.com/ru_RU/RU/Marketing/i/btn/btn_buynow_150wx70h.gif"/>
    </div>
</div>