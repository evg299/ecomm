<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<script type="text/javascript">
    var fillData = function () {
        var formData = $("#card_form").serializeArray();

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
        $("#order_sum_price")[0].innerHTML = $("#card_sum_price")[0].innerHTML;
        $("#card_sum_price_hidden")[0].value = $("#card_sum_price")[0].innerHTML;
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

<form id="card_form" action="${pageContext.request.contextPath}/order-created" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                    <input id="card_sum_price_hidden" type="hidden" name="card_sum" value="0" />
                </div>
                <div id="sum_weight">
                    <b>Суммарный вес:</b> <span id="card_sum_weight"></span>
                    <span class="card_sum_unit">${siteWeightUnit.abbr}</span>
                </div>
            </td>
        </tr>
    </table>

</div>

<div style="padding: 10px; margin: 8px;">
<table style="width: 100%; border-spacing: 15px;">
<tr style="vertical-align: top;">
<td style="width: 50%; background-color: #E0EDF5; padding: 10px;">
<h3>Выберите способ доставки</h3>
<script type="text/javascript">
    var needMap = {};
    <c:forEach items="${deliveries}" var="delivery">
    needMap["${delivery.unicName}"] = ${delivery.needMap};
    </c:forEach>

    $(document).ready(function () {
        $("#select_delivery").change(function () {
            var name = $(this)[0].value;
            console.log(name);
            console.log(needMap[name]);

            checkDeliveryPrice();

            if(needMap[name]) {
                $("#address_selector").show();
            } else {
                $("#address_selector").hide();
            }
        });
    });
</script>

<select id="select_delivery" name="delivery">
    <c:forEach items="${deliveries}" var="delivery">
        <c:choose>
            <c:when test="${count eq 0}">
                <option value="${delivery.unicName}" selected="true">${delivery.deliveryName}</option>
            </c:when>
            <c:otherwise>
                <option value="${delivery.unicName}">${delivery.deliveryName}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>

<div id="address_selector" class="address">
<b>Адрес доставки:</b>
<table>
    <tr>
        <td>Страна:</td>
        <td>
            <input id="country_input" style="width: 300px;"/>
        </td>
    </tr>
    <tr>
        <td>Регион:</td>
        <td>
            <input id="region_input" style="width: 300px;"/>
        </td>
    </tr>
    <tr>
        <td>Район:</td>
        <td>
            <input id="raion_input" style="width: 300px;"/>
        </td>
    </tr>
    <tr>
        <td>Нас. пункт:</td>
        <td>
            <input id="location_input" style="width: 300px;"/>
        </td>
    </tr>
    <tr>
        <td>Адрес:</td>
        <td>
            <input id="street_input" style="width: 300px;"/>
        </td>
        <td>
            <input id="house_input" style="width: 100px;"/>
        </td>
    </tr>
    <tr>
        <td>Квартира:</td>
        <td>
            <input id="apartments_input" name="apartments_input" style="width: 300px;"/>
        </td>
    </tr>
</table>

<input type="hidden" id="geo_name" name="geo_name" />


<script src="//api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
<script type="text/javascript">
    ymaps.ready(init);

    function init() {
        var myMap;
        ymaps.geolocation.get().then(function (res) {
            var mapContainer = $('#map'),
                    bounds = res.geoObjects.get(0).properties.get('boundedBy'),
                    // Рассчитываем видимую область для текущей положения пользователя.
                    mapState = ymaps.util.bounds.getCenterAndZoom(
                            bounds,
                            [mapContainer.width(), mapContainer.height()]
                    );
            createMap(mapState);
        }, function (e) {
            // Если место положение невозможно получить, то просто создаем карту.
            createMap({
                center: [55.751574, 37.573856],
                zoom: 2
            });
        });

        function createMap(state) {
            state.controls = ['zoomControl', 'typeSelector', 'fullscreenControl'];
            myMap = new ymaps.Map('map', state);
            createPlaceMarkLogic();
        }

        function createPlaceMarkLogic() {
            var myPlacemark;
            myMap.cursors.push('arrow');

            // Слушаем клик на карте
            myMap.events.add('click', function (e) {
                var coords = e.get('coords');

                // Если метка уже создана – просто передвигаем ее
                if (myPlacemark) {
                    myPlacemark.geometry.setCoordinates(coords);
                }
                // Если нет – создаем.
                else {
                    myPlacemark = createPlacemark(coords);
                    myMap.geoObjects.add(myPlacemark);
                    // Слушаем событие окончания перетаскивания на метке.
                    myPlacemark.events.add('dragend', function () {
                        getAddress(myPlacemark.geometry.getCoordinates());
                    });
                }
                getAddress(coords);
            });

            // Создание метки
            function createPlacemark(coords) {
                return new ymaps.Placemark(coords, { }, {
                    preset: 'islands#violetStretchyIcon',
                    draggable: true
                });
            }

            // Определяем адрес по координатам (обратное геокодирование)
            function getAddress(coords) {
                ymaps.geocode(coords).then(function (res) {
                    var firstGeoObject = res.geoObjects.get(0);

                    // console.log(firstGeoObject.properties.get('name'));
                    // console.log(firstGeoObject.properties.get('metaDataProperty'));
                    var metaDataProperty = firstGeoObject.properties.get('metaDataProperty');
                    var addressDetails = metaDataProperty.GeocoderMetaData.AddressDetails;

                    $("#geo_name").val(firstGeoObject.properties.get('text'));

                    var fullAddress = {};

                    // Country
                    var currentNode = addressDetails.Country;
                    document.getElementById("country_input").value = currentNode.CountryName;
                    fullAddress.country = currentNode.CountryName;

                    // AdministrativeArea
                    currentNode = addressDetails.Country.AdministrativeArea;
                    document.getElementById("region_input").value = currentNode.AdministrativeAreaName;

                    if (currentNode.SubAdministrativeArea) {
                        document.getElementById("raion_input").value = currentNode.SubAdministrativeArea.SubAdministrativeAreaName;
                        currentNode = currentNode.SubAdministrativeArea;
                    } else {
                        document.getElementById("raion_input").value = "";
                    }

                    document.getElementById("location_input").value = currentNode.Locality.LocalityName;
                    currentNode = currentNode.Locality;

                    if (currentNode.DependentLocality) {
                        document.getElementById("location_input").value += ", " + currentNode.DependentLocality.DependentLocalityName;
                        currentNode = currentNode.DependentLocality;
                    }

                    document.getElementById("street_input").value = currentNode.Thoroughfare.ThoroughfareName;
                    currentNode = currentNode.Thoroughfare;

                    document.getElementById("house_input").value = currentNode.Premise.PremiseNumber;

                    myPlacemark.properties.set({
                        // iconContent: firstGeoObject.properties.get('name'),
                        balloonContent: firstGeoObject.properties.get('text')
                    });

                    checkDeliveryPrice();
                });
            }
        }

    }

    function checkDeliveryPrice() {
        var upAddress = {
            country: document.getElementById("country_input").value,
            region: document.getElementById("region_input").value,
            city: document.getElementById("location_input").value.split(", ")[0]
        };

        var form = $("#card_form").serializeArray();
        var delType = null;
        form.forEach(function (entry) {
            if ("delivery" == entry.name) {
                delType = entry.value;
            }
        });

        console.log(upAddress);
        console.log(form);

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: "/deliveryCalculate/" + delType,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(upAddress),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data) {
                console.log(data);
                $("#delivery_price").text(data["price"]);
                $("#delivery_price_hidden").val(data["price"]);
                var cardPrice = parseFloat($("#card_sum_price").text());
                $("#order_sum_price").text(data["price"] + cardPrice);
            },
            error: function(err) {
                console.log(err);
                alert("Ошибка расчета стоимости");
            }
        });
    }

</script>

<%--<input type="button" value="⇈"/> <input type="button" value="⇊"/>--%>

<div id="map"></div>
<input type="button" value="Расчитать" onclick="checkDeliveryPrice();"/>
</div>

<table style="width: 100%; margin-top: 15px;">
    <tr>
        <td class="card_score">
            <div>
                <b>Цена доставки:</b> <span id="delivery_price">0.0</span>
                <span class="card_sum_currency">${siteCurrency.shortName}</span>
                <input id="delivery_price_hidden" type="hidden" name="delivery_price" value="0" />
            </div>
            <div>
                <b>Цена вместе с доставкой:</b> <span id="order_sum_price">124.68</span>
                <span class="card_sum_unit">${siteCurrency.shortName}</span>
            </div>
        </td>
    </tr>
</table>
</td>
<td style="background-color: #E0EDF5; padding: 10px;">
    <h3>Выберите способ оплаты</h3>

    <select id="select_payment" name="payment">
        <c:forEach items="${payments}" var="payment">
            <c:choose>
                <c:when test="${count eq 0}">
                    <option value="${payment.unicName}" selected="true">${payment.paymentName}</option>
                </c:when>
                <c:otherwise>
                    <option value="${payment.unicName}">${payment.paymentName}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
</td>
</tr>
<tr style="vertical-align: top;">
    <td style="width: 50%; background-color: #E0EDF5; padding: 10px;">
        <h3>Ваши контактные данные:</h3>

        <div class="pers_photo">
            <img src="${pageContext.request.contextPath}/filestorage/download/${person.picture.urlName}-b.${person.picture.extention}"/>
        </div>
        <div class="pers_data">
            <div class="pers_data_head">Общее</div>
            <b>ФИО:</b> ${person.lname} ${person.fname} ${person.mname} <br/>

            <b>Пол:</b>
            <c:choose>
                <c:when test="${person.gender}">
                    мужской
                </c:when>
                <c:otherwise>
                    женский
                </c:otherwise>
            </c:choose>
            <br/>

            <b>Дата рождения:</b> <s:eval expression="@formatDateService.formatTimestamp(person.birthDate)"/>
            (<s:eval expression="@formatDateService.countYears(person.birthDate)"/>) <br/>

            <b>Адрес:</b>
            ${person.address}

            <div style="min-height: 6px;"></div>

            <c:choose>
                <c:when test="${fn:length(personContacts) ne 0}">
                    <div class="pers_data_head">Контакты</div>
                    <c:forEach items="${personContacts}" var="personContact">
                        <b>${personContact.contactType.name}:</b> ${personContact.value} <br/>
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
    <input type="submit" class="button" value="Оформить заказ" />
</div>
</form>
</div>