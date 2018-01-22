<%@ page pageEncoding="UTF-8" isELIgnored ="false" %>
<%@include file="/page/public/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>定时任务列表页面</title>
<%@include file="/page/public/csslib.jsp"%>
</head>
<body>
<div class="page-container f-bg-f5f5f5">
    <div class="f-wma f-mt-20 f-mb-40">
        
        <div class="page-main">
            <div class="m-panel" style="min-height:942px;">
                <ul class="nav-tabs">
                    <li class="z-active"><a href="${ctx }/scheduler/list/1">定时任务列表</a></li>
                    <li class="add">
                    	<div class="add-address">
	                    	<div class="buttons">
	                    		<a class="u-button" href="${ctx }/scheduler/taskEditPage/0">新增定时任务</a>
	                    		<!-- <a class="u-button" href="javascript:void(0)" onclick="restartAll()">全部重启</a> -->
	                    	</div>
                    	</div>
                    </li>
                </ul>
                <div class="acc-record">
                    <div class="m-table">
                        <table width="100%">
                            <thead>
                                <tr>
                                    <th width="">任务编号</th>
                                    <th width="">任务名称</th>
                                    <th width="">定时配置</th>
                                    <th width="">冻结状态</th>
                                    <th width="">执行方</th>
                                    <th width="">执行方式</th>
                                    <th width="">最后修改时间</th>
                                    <th width="">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="task" items="${page.list}" varStatus="vs">
                                <tr>
                                    <td align="center"><a href="${ctx }/taskRecords/getTaskRecordsListByTaskNo/${task.taskNo}/1/status" target="_blank">${task.taskNo}</a></td>
                                    <td align="center">${task.taskName }</td>
                                    <td align="center">${task.schedulerRule }</td>
                                    <td align="center">${task.frozenStatus }</td>
                                    <td align="center">${task.executorNo }</td>
                                    <td align="center">${task.sendType }</td>
                                    <td align="center">${task.lastModifyTime }</td>
                                    <td align="center">
                                    	<c:if test="${task.frozenStatus == '已冻结' }">
                                    		<a href="javascript:void(0)" onclick="start('${task.taskNo }')">启动</a>
                                    	</c:if>
                                    	<c:if test="${task.frozenStatus == '未冻结' }">
                                    		<%-- <a href="javascript:void(0)" onclick="restart('${task.taskNo }')">重启</a> --%>
                                    		<a href="javascript:void(0)" onclick="pause('${task.taskNo }')">暂停</a>	
                                    	</c:if>
                                    	<a href="javascript:void(0)" onclick="execute('${task.taskNo}')">立刻运行</a>   
                                    	<a href="${ctx }/scheduler/taskEditPage/${task.id}">修改</a>
                                    	<a href="${ctx }/scheduler/detail/${task.taskNo}">详情</a>
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="m-page f-mt-40" id="page"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="currentPage" value="${page.currentPage}"/>
<input type="hidden" id="totalPage" value="${page.totalPage}"/>
<%@include file="/page/public/footer.jsp"%>
<script type="text/javascript">
$(function(){
	page($("#currentPage").val(),$("#totalPage").val(),"changePage");
});

function pause(param){
	if(confirm("确定要暂停该定时任务？")){
		$.ajax({
	    	url: '${ctx}/scheduler/delScheduler/' + param,
	    	data: '',
	        type: 'POST',
			dataType : "text",
	        success:function (data, textStatus) {
	        	alert(data);
	        	window.location.href = '${ctx}/scheduler/list/1';
	        }
	    });
	}
}

function start(param){
	if(confirm("确定要启动该定时任务？")){
		$.ajax({
	    	url: '${ctx}/scheduler/addScheduler/' + param,
	    	data: '',
	        type: 'POST',
			dataType : "text",
	        success:function (data, textStatus) {
	        	alert(data);
	        	window.location.href = '${ctx}/scheduler/list/1';
	        }
	    });
	}
}

function restartAll(){
	if(confirm("确定要全部重启定时任务？")){
		$.ajax({
	    	url: '${ctx}/scheduler/resumeSchedulerAll',
	    	data: '',
	        type: 'POST',
			dataType : "text",
	        success:function (data, textStatus) {
	        	alert(data);
	        	window.location.href = '${ctx}/scheduler/list/1';
	        }
	    });
	}
}

function restart(param){
	if(confirm("确定要重启该定时任务？")){
		$.ajax({
	    	url: '${ctx}/scheduler/resumeScheduler/' + param,
	    	data: '',
	        type: 'POST',
			dataType : "text",
	        success:function (data, textStatus) {
	        	alert(data);
	        	window.location.href = '${ctx}/scheduler/list/1';
	        }
	    });
	}
}

function execute(param){
	if(confirm("确定要立即执行当前任务一次？")){
		$.ajax({
	    	url: '${ctx}/scheduler/executeOnce/' + param,
	    	data: '',
	        type: 'POST',
			dataType : "text",
	        success:function (data, textStatus) {
	        	alert(data);
	        	window.location.href = '${ctx}/scheduler/list/1';
	        }
	    });
	}
}

function changePage(param){
	$("#currentPage").val(param);
	window.location.href = '${ctx}/scheduler/list/' + $("#currentPage").val();
}
</script>
</body>
</html>