<%@ page pageEncoding="UTF-8" isELIgnored ="false" %>
<%@include file="/page/public/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>定时任务错误信息</title>
<%@include file="/page/public/csslib.jsp"%>
</head>
<body>
<div class="page-container f-bg-f8f8f8">
    <div class="f-wma f-mt-40 f-mb-100">
        <div class="order-detail">
            <div class="m-caption">定时任务错误信息详情</div>
            <div class="m-detail">
                <div class="item">
                    <div class="label"></div>
                    <div class="info">
                        <c:forEach var="errors" items="${list}" varStatus="vs">
                        	<p><span class="key">errorKey ：</span><span>${errors['errorKey'] }</span></p>
                        	<p><span class="key">errorValue ：</span><span>${errors['errorValue'] }</span></p>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="m-action"><a href="javascript:history.back(-1);" class="u-button">关闭</a> </div>
        </div>
    </div>
</div>
<%@include file="/page/public/footer.jsp"%>
</body>
</html>