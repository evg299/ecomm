<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<script type="text/javascript">
    $(document).ready(function () {
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


            <h5>Типы товара:</h5>

            <div class="product_detail">
                <div>
                    <b>Автор:</b> Дэвид Хеффельфингер
                </div>
                <div>
                    <b>Переводчик:</b> Е. Карышев
                </div>
                <div>
                    <b>Языки:</b> Русский
                </div>
                <div>
                    <b>Издательство:</b> ДМК Пресс
                </div>
                <div>ISBN 978-5-94074-914-1, 978-5-94074-914-1; 2013 г.</div>
            </div>

            <div class="bay">
                <b>Цена:</b> <span class="price">${productPrice.integralPart}<sup> ${productPrice.fractionalPart}</sup></span> ${productPrice.currency}
                <div class="button prod_button">Добавить в корзину</div>
            </div>
        </td>
    </tr>
</table>

<div class="separator"></div>

<%@include file="recommended.jsp" %>

<div class="separator"></div>

<h4>Дополнительная информация</h4>

<div class="add_info">
    ${product.description}
</div>