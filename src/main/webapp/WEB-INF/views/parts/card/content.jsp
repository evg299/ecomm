<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<script type="text/javascript">
    var fillData = function () {
        var formData = $("#card_form").serializeArray();

        var orderUrl = "${pageContext.request.contextPath}/order/new/?";
        var sumPrice = 0;
        var sumWeight = 0;
        $(".product_row").each(function (index) {
            var rowId = $(this).attr("id");
            console.log(rowId);
            if (rowId.startsWith("rowid-")) {
                var id = rowId.substr("rowid-".length);

                var countCnt = $("#picker-" + id)[0];
                var priceCnt = $("#price-" + id)[0];
                var multiPriceCnt = $("#multuPrice-" + id)[0];

                var count = parseInt(countCnt.value);
                var price = parseFloat(priceCnt.value);

                var variant = "count=" + count + "&date=" + new Date().getTime();
                setCookie("card-" + id, variant);

                console.log(countCnt.value, priceCnt.value, multiPriceCnt);

                var multi = count * price;
                sumPrice += multi;
                multiPriceCnt.innerHTML = Math.round(multi * 100) / 100;

                var weightCnt = $("#weight-" + id);
                if (weightCnt.length) {
                    sumWeight += parseFloat(weightCnt[0].innerHTML) * parseFloat(countCnt.value);
                }
            }
        });

        $("#card_sum_price")[0].innerHTML = Math.round(sumPrice * 100) / 100;
        $("#card_sum_weight")[0].innerHTML = Math.round(sumWeight * 100) / 100;
    };

    $(document).ready(function () {
        fillData();

        $(".count_picker").change(function (eventObject) {
            var value = eventObject.target.value;
            if (!$.isNumeric(value) || 0 >= value) {
                eventObject.target.value = 1;
            }

            fillData();
        });

        $("#check_all").click(function () {
            $('.card_product_checkbox').prop('checked', $(this).prop("checked"));
        });

        $('.card_product_checkbox').click(function () {
            var allEnabled = true;
            $(".card_product_checkbox").each(function (index) {
                allEnabled = allEnabled && $(this).prop("checked");
            });

            if (allEnabled) {
                $("#check_all").prop('checked', true);
            } else {
                $("#check_all").prop('checked', false);
            }
        });

        $("#product_delete").click(function () {
            $(".card_product_checkbox").each(function (index) {
                if ($(this).prop("checked")) {
                    var row = $(this).parents(".product_row");
                    console.log(row);

                    var rowId = row.attr("id");
                    if (rowId.startsWith("rowid-")) {
                        var id = rowId.substr("rowid-".length);
                        deleteCookie("card-" + id);
                    }

                    row.remove();

                    fillData();
                }
            });
        });
    });
</script>

<div id="card_content">
    <h3 style="margin-left: 10px;">Корзина</h3>

    <div id="card_card">
        <form id="card_form" action="${pageContext.request.contextPath}/order/new/">
            <table style="width: 100%;">
                <tr>
                    <td><input id="check_all" type="checkbox"/> <big>Выделить все</big></td>
                    <td style="text-align: right;"><a id="product_delete" class="delete_from_card">Удалить</a></td>
                </tr>
            </table>

            <table id="card_table">
                <tr>
                    <th></th>
                    <th>Название товара, когда добавлен</th>
                    <th>Вес</th>
                    <th>Наличие</th>
                    <th>Кол-во</th>
                    <th style="width: 145px;">Стоимость</th>
                </tr>
                <c:forEach items="${cardProducts}" var="cardProduct">
                    <tr id="rowid-${cardProduct.product.id}" class="product_row">
                        <td><input id="product-${cardProduct.product.id}" name="product-${cardProduct.product.id}"
                                   type="checkbox" class="card_product_checkbox"/></td>
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
                                        <div class="card_product_added">Добавлен ${cardProduct.addedDate}</div>
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
                        <td style="text-align: center;">На складе</td>
                        <td style="text-align: center;">
                            <input id="picker-${cardProduct.product.id}"
                                   class="count_picker" type="number" value="${cardProduct.count}" min="1"
                                   style="width: 40px;"/>
                            <input id="price-${cardProduct.product.id}"
                                   type="hidden"
                                   value="<s:eval expression="@priceFormatter.formatInCard(cardProduct.product.price)" />"/>
                            <span style="padding: 5px;">x</span> <s:eval
                                expression="@priceFormatter.formatInCard(cardProduct.product.price)"/>
                        </td>
                        <td style="text-align: center;">
                            = <span id="multuPrice-${cardProduct.product.id}" class="card_price">0</span>
                            <span class="card_currency">${siteCurrency.shortName}</span>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <table style="width: 100%; margin-top: 15px;">
                <tr>
                    <td class="card_score">
                        <div>
                            <b>Цена без доставки:</b> <span id="card_sum_price"></span>
                            <span class="card_sum_currency">${siteCurrency.shortName}</span>
                        </div>
                        <div id="sum_weight">
                            <b>Суммарный вес:</b> <span id="card_sum_weight"></span>
                            <span class="card_sum_unit">${siteWeightUnit.abbr}</span>
                        </div>
                    </td>
                    <td style="text-align: right; vertical-align: bottom;">
                        <a id="toOrderLink" class="button" onclick="document.getElementById('card_form').submit();">
                            Заказать
                        </a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>