<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<script type="text/javascript">
    document.productID = ${product.id};

    $(document).ready(function () {
        checkInCard();

        $('#thumbs_image img').click(function (e) {
            var urlName = $(this).attr("urlName");
            var extention = $(this).attr("extention");
            var title = $(this).attr("title");
            $('#view_image img').attr({
                src: "${pageContext.request.contextPath}/filestorage/download/" + urlName + "-m." + extention,
                title: title
            });

            var bigImg = $("#sh_image");
            bigImg.attr({
                src: "${pageContext.request.contextPath}/filestorage/download/" + urlName + "-b." + extention
            });
        });

        $('#view_image img').click(function (e) {
            var winH = screen.height; // $(window).height();
            var winW = $(window).width();

            console.log("winH", winH);

            var top = winH / 2 - $("#dialog").height() / 2;
            var left = winW / 2 - $("#dialog").width() / 2;

            if (0 > top)
                top = 0;
            if (0 > left)
                left = 0;

            $("#dialog").css('top', top);
            $("#dialog").css('left', left);

            $("#dialog, #block_light").show();
        });

        $('#dialog_close, #block_light').click(function (e) {
            $("#dialog, #block_light").hide();
        });

        $('#bay_button').click(function (e) {
            var variant = $('#variantForm').serialize();
            var count = $('#count_product').val();

            if (variant)
                variant += "&";
            variant += "count=" + count + "&date=" + new Date().getTime();
            setCookie("card-" + document.productID, variant);

            hideBay();
        });

        // отображение возможности добавить товар
        function checkInCard() {
            var cookies = getCookies();
            for (var key in cookies) {
                if (key.startsWith("card-")) {
                    var id = key.substr("card-".length);
                    if (document.productID == id) {
                        hideBay();
                        return;
                    }
                }
            }
        }

        function hideBay() {
            $('#bay_button').hide();
            $('#productCounter').hide();
            $('#bay_notification').show();
        }

    });
</script>

<div id="block_light"></div>

<div id="dialog">
    <div style="text-align: right; font-size: 11px;">
        <a id="dialog_close" href="#">Закрыть</a>
    </div>
    <img id="sh_image"
         src="${pageContext.request.contextPath}/filestorage/download/${product.picture.urlName}-b.${product.picture.extention}"
         title="${product.name}">
</div>

<table id="product_view">
    <tr>
        <td style="width: 610px;">
            <div id="view_image">
                <img src="${pageContext.request.contextPath}/filestorage/download/${product.picture.urlName}-m.${product.picture.extention}"
                     urlName="${product.picture.urlName}" extention="${product.picture.extention}"
                     title="${product.picture.title}">
            </div>
            <div id="thumbs_image">
                <img urlName="${product.picture.urlName}" extention="${product.picture.extention}"
                     title="${product.picture.title}"
                     src="${pageContext.request.contextPath}/filestorage/download/${product.picture.urlName}-s.${product.picture.extention}">
                <c:forEach items="${additionalPictures}" var="additionalPicture">
                    <img urlName="${additionalPicture.urlName}" extention="${additionalPicture.extention}"
                         title="${additionalPicture.title}"
                         src="${pageContext.request.contextPath}/filestorage/download/${additionalPicture.urlName}-s.${additionalPicture.extention}">
                </c:forEach>
            </div>
        </td>
        <td>
            <h3>${product.name}</h3>

            <p class="idItems">ID товара: ${product.uuid}</p>

            <c:if test="${fn:length(productProperties) ne 0}">
                <h5>Характеристики товара:</h5>

                <div class="product_detail">
                    <c:forEach items="${productProperties}" var="productProperty">
                        <div>
                            <b>${productProperty.name}:</b> ${productProperty.value} ${productProperty.unit.name}
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${fn:length(notSelectVariants) + fn:length(selectVariantDTOs) ne 0}">
                <form id="variantForm">
                    <h5>Выберите нужную вам разновидность товара:</h5>

                    <div class="product_detail">
                        <table style="vertical-align: top;">
                            <c:forEach items="${notSelectVariants}" var="notSelectVariant">
                                <tr>
                                    <td style="text-align: right; margin-right: 3px;"><b>${notSelectVariant.name}:</b>
                                    </td>
                                    <td><input id="nsv-${notSelectVariant.id}" class="prod_variant" type="text"
                                               name="field-${notSelectVariant.id}"
                                               value="${notSelectVariant.defaultValue}"/> ${notSelectVariant.unit.name}
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:forEach items="${selectVariantDTOs}" var="selectVariantDTO">
                                <tr>
                                    <td style="text-align: right; margin-right: 3px;">
                                        <b>${selectVariantDTO.productVariant.name}:</b></td>
                                    <td>
                                        <select name="field-${selectVariantDTO.productVariant.id}">
                                            <c:forEach items="${selectVariantDTO.productVariantOptions}"
                                                       var="productVariantOption">
                                                <option value="val-${productVariantOption.id}">${productVariantOption.value} ${productVariantOption.unit.name}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </form>
            </c:if>


            <div class="bay">
                <div>
                    <b>Цена:</b> <s:eval expression="@priceFormatter.format(product.price)" /> ${siteCurrency.shortName}
                </div>
                <div id="productCounter">
                    <b>Кол-во:</b> <input id="count_product" type="number" min="1" value="1" style="width: 100px;"/>
                </div>

                <div id="bay_button" class="button prod_button" style="display: block;">Добавить в корзину</div>
                <div id="bay_notification" style="display: none;">Товар уже в корзине</div>
            </div>
        </td>
    </tr>
</table>

<%@include file="recommended.jsp" %>

<div class="separator"></div>

<h4>Дополнительная информация</h4>

<div class="add_info">
    ${product.description}
</div>