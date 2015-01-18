<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="slider">
    <table>
        <tr>
            <td id="gallery">
                <c:forEach items="${sliderPictures}" var="sliderPicture">
                    <a href="${sliderPicture.href}" target="_blank">
                        <img src="${pageContext.request.contextPath}/filestorage/download/${sliderPicture.picture.urlName}-o.${sliderPicture.picture.extention}" />
                    </a>
                </c:forEach>
            </td>
            <td>
                <div id="thumbs">
                    <c:forEach items="${sliderPictures}" var="sliderPicture">
                        <img src="${pageContext.request.contextPath}/filestorage/download/${sliderPicture.picture.urlName}-s.${sliderPicture.picture.extention}" />
                    </c:forEach>
                </div>

                <a href="#" id="next"></a>
            </td>
        </tr>
    </table>
</div>