<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<div id="card_content">
<h3 style="margin-left: 10px;">Заказ № <a href="${pageContext.request.contextPath}/order/${orderNum}">${orderNum}</a></h3>

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
                = <span id="multuPrice-${cardProduct.product.id}" class="card_price">
                <s:eval expression="@priceFormatter.formatInCard(cardProduct.count * cardProduct.product.price)"/></span>
                <span class="card_currency">${siteCurrency.shortName}</span>
            </td>
        </tr>
    </c:forEach>
</table>

<div style="font-size: 13px;">
    <table>
        <tr>
            <td style="vertical-align: top; font-weight: bold;">Цена без доставки:</td>
            <td>
                <span id="card_sum_price">${sumPrice}</span>
                <span class="card_sum_currency">${siteCurrency.shortName}</span>
                <input id="card_sum_price_hidden" type="hidden" name="card_sum" value="0"/>
            </td>
        </tr>
        <tr>
            <td style="vertical-align: top; font-weight: bold;">Цена доставки:</td>
            <td>
                <span id="delivery_price">${deliveryPrice}</span>
                <span class="card_sum_currency">${siteCurrency.shortName}</span>
                <input id="delivery_price_hidden" type="hidden" name="delivery_price" value="0"/>
            </td>
        </tr>
        <tr>
            <td style="vertical-align: top; font-weight: bold;">Суммарная стоимость:</td>
            <td>
                <span id="sum_price">${allPrice}</span>
                <span class="card_sum_currency">${siteCurrency.shortName}</span>
                <input id="sum_price_hidden" type="hidden" name="sum_price" value="0"/>
            </td>
        </tr>
        <tr>
            <td style="vertical-align: top; font-weight: bold;">Суммарный вес:</td>
            <td>
                <span id="card_sum_weight">${sumWeight}</span>
                <span class="card_sum_unit">${siteWeightUnit.abbr}</span>
            </td>
        </tr>
    </table>
</div>

<br/><br/>

<div style="font-size: 13px;">
    <h3>Ваши контактные данные</h3>

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
</div>

<div style="font-size: 13px;">
    <h3>Доставка</h3>

    <table>
        <tr>
            <td style="vertical-align: top; font-weight: bold; width: 150px;">Служба доставки:</td>
            <td>
                ${deliveryService.deliveryName}
            </td>
        </tr>
        <tr>
            <td style="vertical-align: top; font-weight: bold;">Адрес доставки:</td>
            <td>
                <span id="delivery_address">${deliveryAddress}</span>
                <script src="//api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
                <script type="text/javascript">
                    ymaps.ready(init);

                    function init() {
                        var myMap = new ymaps.Map('map', {
                            center: [55.753994, 37.622093],
                            zoom: 9
                        });

                        var addr = $("#delivery_address").text();

                        // Поиск координат центра Нижнего Новгорода.
                        ymaps.geocode(addr, {
                            /**
                             * Опции запроса
                             * @see http://api.yandex.ru/maps/doc/jsapi/2.1/ref/reference/geocode.xml
                             */
                            // boundedBy: myMap.getBounds(), // Сортировка результатов от центра окна карты
                            // strictBounds: true, // Вместе с опцией boundedBy будет искать строго внутри области, указанной в boundedBy
                            results: 1 // Если нужен только один результат, экономим трафик пользователей
                        }).then(function (res) {
                            // Выбираем первый результат геокодирования.
                            var firstGeoObject = res.geoObjects.get(0),
                            // Координаты геообъекта.
                                    coords = firstGeoObject.geometry.getCoordinates(),
                            // Область видимости геообъекта.
                                    bounds = firstGeoObject.properties.get('boundedBy');

                            // Добавляем первый найденный геообъект на карту.
                            myMap.geoObjects.add(firstGeoObject);
                            // Масштабируем карту на область видимости геообъекта.
                            myMap.setBounds(bounds, {
                                checkZoomRange: true // проверяем наличие тайлов на данном масштабе.
                            });

                            /**
                             * Все данные в виде javascript-объекта.
                             */
                            console.log('Все данные геообъекта: ', firstGeoObject.properties.getAll());
                            /**
                             * Метаданные запроса и ответа геокодера.
                             * @see http://api.yandex.ru/maps/doc/geocoder/desc/reference/GeocoderResponseMetaData.xml
                             */
                            console.log('Метаданные ответа геокодера: ', res.metaData);
                            /**
                             * Метаданные геокодера, возвращаемые для найденного объекта.
                             * @see http://api.yandex.ru/maps/doc/geocoder/desc/reference/GeocoderMetaData.xml
                             */
                            console.log('Метаданные геокодера: ', firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData'));
                            /**
                             * Точность ответа (precision) возвращается только для домов.
                             * @see http://api.yandex.ru/maps/doc/geocoder/desc/reference/precision.xml
                             */
                            console.log('precision', firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData.precision'));
                            /**
                             * Тип найденного объекта (kind).
                             * @see http://api.yandex.ru/maps/doc/geocoder/desc/reference/kind.xml
                             */
                            console.log('Тип геообъекта: %s', firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData.kind'));
                            console.log('Название объекта: %s', firstGeoObject.properties.get('name'));
                            console.log('Описание объекта: %s', firstGeoObject.properties.get('description'));
                            console.log('Полное описание объекта: %s', firstGeoObject.properties.get('text'));

                            /**
                             * Если нужно добавить по найденным геокодером координатам метку со своими стилями и контентом балуна, создаем новую метку по координатам найденной и добавляем ее на карту вместо найденной.
                             */
                            /**
                             var myPlacemark = new ymaps.Placemark(coords, {
                                iconContent: 'моя метка',
                                balloonContent: 'Содержимое балуна <strong>моей метки</strong>'
                                }, {
                                preset: 'islands#violetStretchyIcon'
                                });

                             myMap.geoObjects.add(myPlacemark);
                             */
                        });
                    }
                </script>
                <div id="map"></div>
            </td>
        </tr>
        <tr>
            <td style="vertical-align: top; font-weight: bold;">Статус доставки:</td>
            <td><span style="font-weight: bold;">Ожидает поступления в службу доставки</span> → Доставляется → Доставлено</td>
        </tr>
    </table>
</div>

<div style="font-size: 13px;">
    <h3>Оплата</h3>
    <table>
        <tr>
            <td style="vertical-align: top; font-weight: bold;">Статус оплаты:</td>
            <td><span style="font-weight: bold;">Не оплачен</span> → Оплачен</td>
        </tr>
        <tr>
            <td style="vertical-align: top; font-weight: bold; width: 150px;">Система оплаты:</td>
            <td>
                <div>
                    ${paymentName}
                </div>
                <script type="text/javascript" src="https://auth.robokassa.ru/Merchant/PaymentForm/FormMS.js?MerchantLogin=demo&OutSum=11.00&InvoiceID=&Description=%d0%9e%d0%bf%d0%bb%d0%b0%d1%82%d0%b0+%d0%b7%d0%b0%d0%ba%d0%b0%d0%b7%d0%b0+%d0%b2+%d0%a2%d0%b5%d1%81%d1%82%d0%be%d0%b2%d0%be%d0%bc+%d0%bc%d0%b0%d0%b3%d0%b0%d0%b7%d0%b8%d0%bd%d0%b5+ROBOKASSA&shpItem=1&Culture=ru&Encoding=utf-8&SignatureValue=4ee757df58e80468402f3539b5e2922a"></script>
                <%--
                <br />
                <iframe frameborder="0" allowtransparency="true" scrolling="no" src="https://money.yandex.ru/embed/small.xml?account=410012402776089&quickpay=small&yamoney-payment-type=on&button-text=01&button-size=l&button-color=white&targets=qweqwe&default-sum=10&successURL=http%3A%2F%2Flocalhost%3A8080%2Forder%2FE89015BF-6EF0-4AD0-B8E7-85D721D64AB8" width="228" height="54"></iframe>
                --%>
            </td>
        </tr>
    </table>
</div>
</div>
</div>