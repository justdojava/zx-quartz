<%@ page pageEncoding="UTF-8" isELIgnored ="false" %>
<%@include file="/page/public/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>定时任务详情</title>
<%@include file="/page/public/csslib.jsp"%>
</head>
<body>
<div class="page-container f-bg-f8f8f8">
    <div class="f-wma f-mt-40 f-mb-100">
        <div class="order-detail">
            <div class="m-caption">定时任务详情</div>
            <div class="m-detail">
                <div class="item">
                    <div class="label"></div>
                    <div class="info">
                        <p><span>任务编号 ：</span><span>${detail.taskNo }</span></p>
                        <p><span>任务名称 ：</span><span>${detail.taskName }</span></p>
                        <p><span>定时配置 ：</span><span>${detail.schedulerRule }</span></p>
                        <p><span>冻结状态 ：</span><span>${detail.frozenStatus }</span></p>
                        <p><span>执行方 ：</span><span>${detail.executorNo }</span></p>
                        <p><span>冻结时间 ：</span><span>${detail.frozenTime }</span></p>
                        <p><span>解冻时间 ：</span><span>${detail.unfrozenTime }</span></p>
                        <p><span>执行方式 ：</span><span>${detail.sendType }</span></p>
                        <p><span>url ：</span><span>${detail.url }</span></p>
                        <p><span>执行参数 ：</span><span>${detail.executeParamter }</span></p>
                        <p><span>timeKey ：</span><span>${detail.timeKey }</span></p>
                        <p><span>创建时间 ：</span><span>${detail.createTime }</span></p>
                        <p><span>修改时间 ：</span><span>${detail.lastModifyTime }</span></p>
                        <p><span>上一次执行时间 ：</span><span>${detail.preExecuteTime }</span></p>
                        <p><span>上一次执行状态 ：</span><span>${detail.taskStatus }</span></p>
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