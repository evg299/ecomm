<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<script type="text/javascript">
    var fillData = function () {
        var formData = $("#card_form").serializeArray();

        var orderUrl = "${pageContext.request.contextPath}/order?";
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

                orderUrl += "pr" + id + "=" + count + "&";

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

        orderUrl = orderUrl.substr(0, orderUrl.length - 1);
        $("a#toOrderLink")[0].href = orderUrl;
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
    <h3 style="margin-left: 10px;">Оформление заказа</h3>

    <div id="card_card">
        <form id="card_form">
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
                                   value="${cardProduct.count}" />
                            <input id="price-${cardProduct.product.id}" name="price-${cardProduct.product.id}"
                                   type="hidden"
                                   value="<s:eval expression="@priceFormatter.formatInCard(cardProduct.product.price)" />"/>
                            <span style="padding: 5px; font-weight: bold;">${cardProduct.count}</span>
                            <span style="padding: 5px;">x</span>
                            <s:eval expression="@priceFormatter.formatInCard(cardProduct.product.price)" />
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
                </tr>
            </table>
        </form>
    </div>

    <div style="padding: 10px; margin: 8px;">
        <table style="width: 100%; border-spacing: 15px;">
            <tr style="vertical-align: top;">
                <td style="width: 50%; background-color: #E0EDF5; padding: 10px;">
                    <h3>Выберите способ доставки</h3>
                    <c:set var="count" value="0" scope="page" />
                    <c:forEach items="${deliveries}" var="delivery">
                        <div>
                            <c:choose>
                                <c:when test="${count eq 0}">
                                    <input type="radio" name="delivery" id="d-${count}" value="${delivery.unicName}" checked="true"/><label for="d-${count}">${delivery.deliveryName}</label>
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" name="delivery" id="d-${count}" value="${delivery.unicName}"/><label for="d-${count}">${delivery.deliveryName}</label>
                                </c:otherwise>
                            </c:choose>

                        </div>
                        <c:set var="count" value="${count + 1}" scope="page"/>
                    </c:forEach>

                    <div class="address">
                        <b>Адрес доставки:</b>
                        <table>
                            <tr>
                                <td>Страна:</td>
                                <td>
                                    <select name="hero[]">
                                        <option disabled>Выберите страну</option>

                                        <option value="Чебурашка">Чебурашка</option>
                                        <option value="Крокодил Гена">Крокодил Гена</option>
                                        <option value="Шапокляк">Шапокляк</option>
                                        <option value="Крыса Лариса">Крыса Лариса</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Регион:</td>
                                <td>
                                    <select name="hero[]">
                                        <option disabled>Выберите регион</option>

                                        <option value="Чебурашка">Чебурашка</option>
                                        <option value="Крокодил Гена">Крокодил Гена</option>
                                        <option value="Шапокляк">Шапокляк</option>
                                        <option value="Крыса Лариса">Крыса Лариса</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Город:</td>
                                <td>
                                    <select name="hero[]">
                                        <option disabled>Выберите город</option>

                                        <option value="Чебурашка">Чебурашка</option>
                                        <option value="Крокодил Гена">Крокодил Гена</option>
                                        <option value="Шапокляк">Шапокляк</option>
                                        <option value="Крыса Лариса">Крыса Лариса</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Адрес:</td>
                                <td>
                                    <input style="width: 300px;" />
                                </td>
                            </tr>
                        </table>
                    </div>

                    <table style="width: 100%; margin-top: 15px;">
                        <tr>
                            <td class="card_score">
                                <div>
                                    <b>Цена доставки:</b> <span>12.34</span>
                                    <span class="card_sum_currency">${siteCurrency.shortName}</span>
                                </div>
                                <div>
                                    <b>Цена вместе с доставкой:</b> <span>124.68</span>
                                    <span class="card_sum_unit">${siteCurrency.shortName}</span>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
                <td style="background-color: #E0EDF5; padding: 10px;">
                    <h3>Выберите способ оплаты</h3>

                    <div><input type="radio" name="a1" id="a11" value="1" checked="true"/><label for="a11">Оплата при получении</label></div>
                    <div><input type="radio" name="a1" id="a21" value="2" /><label for="a21">Yandex деньги</label></div>
                    <div><input type="radio" name="a1" id="a31" value="3" /><label for="a31">Webmoney</label></div>
                    <div><input type="radio" name="a1" id="a41" value="4" /><label for="a41">PayPal</label></div>
                    <div><input type="radio" name="a1" id="a51" value="4" /><label for="a51">Visa или Mastercard</label></div>
                </td>
            </tr>
            <tr style="vertical-align: top;">
                <td style="width: 50%; background-color: #E0EDF5; padding: 10px;">
                    <h3>Ваши контактные данные:</h3>

                    <div class="pers_photo">
                        <img src="${pageContext.request.contextPath}/filestorage/download/${person.picture.urlName}-b.${person.picture.extention}" />
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

                        <div style="min-height: 20px;"></div>
                    </div>
                </td>
                <td style="background-color: #E0EDF5; padding: 10px;">
                    <h3>Примечание к заказу:</h3>

                    <textarea style="width: 100%; height: 120px;"></textarea>
                </td>
            </tr>
        </table>
    </div>

    <div style="text-align: right; padding-right: 35px;">
        <a id="toOrderLink" class="button">
            Оформить заказ
        </a>
    </div>

</div>