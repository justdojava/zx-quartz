<%@ page pageEncoding="UTF-8" isELIgnored ="false" %>
<%@include file="/page/public/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${title }</title>
<%@include file="/page/public/csslib.jsp"%>
</head>
<body>
<div class="page-container f-bg-f5f5f5">
    <div class="f-wma f-mt-20 f-mb-40">
        <div class="page-main">
            <div class="m-panel" style="min-height:942px;">
                <ul class="nav-tabs">
                    <li class="z-active">${title }</li>
                </ul>
                <div class="layer-edit-address layer-add-address">
			    <div class="content">
			        <form id="taskForm" method="post" action="${ctx }/scheduler/edit">
			        <div class="form-edit-address f-cf">
			            <div class="item">
			                <div class="label">任务编号：</div>
			                <div class="info">
			                    <div class="u-text">
			                        <input type="text" name="taskNo" id="taskNo" value="${taskInfo.taskNo }" maxlength="50"/>
			                    </div>
			                    <div class="u-tips" style="display:block;" id="taskNoErr"></div>
			                </div>
			            </div>
			            <div class="item">
			                <div class="label">任务名称：</div>
			                <div class="info">
			                    <div class="u-text">
			                        <input type="text" id="taskName" name="taskName" value="${taskInfo.taskName }" maxlength="50"/>
			                    </div>
			                    <div class="u-tips" style="display:block;" id="taskNameErr"></div>
			                </div>
			            </div>
			            <div class="item">
			                <div class="label">定时配置：</div>
			                <div class="info">
			                    <div class="u-text">
			                        <input type="text" id="schedulerRule" name="schedulerRule" value="${taskInfo.schedulerRule }" maxlength="50"/><span>(如：0 0 12 * * ? 每天中午12点触发)</span>
			                    </div>
			                    <div class="u-tips" style="display:block;" id="schedulerRuleErr"></div>
			                </div>
			            </div>
			            <div class="item">
			                <div class="label">冻结状态：</div>
			                <div class="info">
			                    <div class="u-text">
			                        <select id="frozenStatus" name="frozenStatus">
			                        	<option value="FROZEN" <c:if test="${taskInfo.frozenStatus ==  'FROZEN'}">selected="selected"</c:if>>冻结</option>
			                        	<c:if test="${null != taskInfo.id && 0 != taskIndo.id }">
			                        		<option value="UNFROZEN" <c:if test="${taskInfo.frozenStatus ==  'UNFROZEN'}">selected="selected"</c:if>>解冻</option>
			                        	</c:if>
			                        </select>
			                    </div>
			                </div>
			            </div>
			            <div class="item">
			                <div class="label">执行方：</div>
			                <div class="info">
			                    <div class="u-text">
			                       <input type="text" id="executorNo" name="executorNo" value="${taskInfo.executorNo }" maxlength="50"/>
			                    </div>
			                    <div class="u-tips" style="display:block;" id="executorNoErr"></div>
			                </div>
			            </div>
			            <div class="item">
			                <div class="label">执行方式：</div>
			                <div class="info">
			                    <div class="u-text">
			                        <select id="sendType" name="sendType">
			                        	<option value="mq" <c:if test="${taskInfo.sendType ==  'mq'}">selected="selected"</c:if>>mq</option>
			                        	<option value="http" <c:if test="${taskInfo.sendType ==  'http'}">selected="selected"</c:if>>http</option>
			                        </select>
			                    </div>
			                </div>
			            </div>
			            <div class="item">
			                <div class="label">url：</div>
			                <div class="info">
			                    <div class="u-text">
			                       <input type="text" id="url" name="url" value="${taskInfo.url }" maxlength="64"/>
			                    </div>
			                    <div class="u-tips" style="display:block;" id="urlErr"></div>
			                </div>
			            </div>
			            <div class="item">
			                <div class="label">参数：</div>
			                <div class="info">
			                    <div class="u-text">
			                       <textarea rows="10" cols="40" id="executeParamter" name="executeParamter" maxlength="2000">${taskInfo.executeParamter }</textarea>
			                    </div>
			                </div>
			            </div>
			            <div class="item">
			                <div class="label">timeKey：</div>
			                <div class="info">
			                    <div class="u-text">
			                       <input type="text" id="timeKey" name="timeKey" value="${taskInfo.timeKey }" /><span>(如：yyyy-MM-dd HH:mm)</span>
			                    </div>
			                    <div class="u-tips" style="display:block;" id="timeKeyErr"></div>
			                </div>
			            </div>
			            <div class="item">
			                <div class="info">
			                    <input type="button" id="edit" value="保存" class="u-button" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                    <input type="button" id="closeBtn" value="关闭" class="u-button" />
			                    <div class="u-tips" style="display:block;"></div>
			                </div>
			            </div>
			        </div>
			        <input type="hidden" id="id" name="id" value="${taskInfo.id }"/>
			        <input type="hidden" id="version" name="version" value="${taskInfo.version }"/>
			        <input type="hidden" id="taskNoSource" value="${taskInfo.taskNo }"/>
			        </form>
			    </div>
			</div>
             </div>
         </div>
      </div>
</div>
<%@include file="/page/public/footer.jsp"%>
<script type="text/javascript">
$(function(){
	$("#edit").click(function(){
		if(validate() && checkTaskNo()){
			$.ajax({
		    	url: '${ctx}/scheduler/edit',
		    	data: $("#taskForm").serialize(),
		        type: 'POST',
				dataType : "text",
		        success:function (data, textStatus) {
		        	if("success" == data){
		        		if(null == $("#id").val() || "" == $("#id").val()){
		        			alert("创建成功");
		        		}else{
		        			alert("编辑成功");	
		        		}
		        		window.location.href = '${ctx}/scheduler/list/1';
		        	}else if("reload" == data){
		        		alert("任务启动失败，请手动启动");
		        		window.location.href = '${ctx}/scheduler/list/1';
		        	}else{
		        		if(null == $("#id").val() || "" == $("#id").val()){
		        			alert("创建失败");
		        		}else{
		        			alert("编辑失败");	
		        		}
		        	}
		        }
		    });	
		}
	});
	
	$("#closeBtn").click(function(){
		window.location.href = "${ctx}/scheduler/list/1";
	});
});

function checkTaskNo(){
	if($("#taskNoSource").val() != $("#taskNo").val()){
		var msg = "";
		$.ajax({
	    	url: '${ctx}/scheduler/checkTaskNo/'+$("#taskNo").val(),
	    	data: '',
	        type: 'POST',
	        async:false, 
			dataType : "text",
	        success:function (data, textStatus) {
	        	msg = data;
	        }
	    });	
		if("success" == msg){
			$("#taskNoErr").html("");
    		$("#taskNo").removeClass("z-error");
    		return true;
    	}else{
    		$("#taskNoErr").html("任务编号重复");
    		$("#taskNo").addClass("z-error");
    		return false;
    	}
	}else{
		return true;
	}
}

function validate(){
	if($("#taskNo").val().length == 0){
		$("#taskNoErr").html("请填写任务编号");
		$("#taskNo").addClass("z-error");
		return false;
	}else{
		$("#taskNoErr").html("");
		$("#taskNo").removeClass("z-error");
	}
	if($("#taskName").val().length == 0){
		$("#taskNameErr").html("请填写任务名称");
		$("#taskName").addClass("z-error");
		return false;
	}else{
		$("#taskNameErr").html("");
		$("#taskName").removeClass("z-error")
	}
	if($("#schedulerRule").val().length == 0){
		$("#schedulerRuleErr").html("请填写定时配置");
		$("#schedulerRule").addClass("z-error");
		return false;
	}else{
		$("#schedulerRuleErr").html("");
		$("#schedulerRule").removeClass("z-error");
	}
	if($("#executorNo").val().length == 0){
		$("#executorNoErr").html("请填写执行方");
		$("#executorNo").addClass("z-error");
		return false;
	}else{
		$("#executorNoErr").html("");
		$("#executorNo").removeClass("z-error");
	}
	if($("#sendType").val() == "http"){
		if($("#url").val().length == 0){
			$("#urlErr").html("请填写utl地址");
			$("#url").addClass("z-error");
			return false;
		}else{
			$("#urlErr").html("");
			$("#url").removeClass("z-error");
		}
	}
	if($("#timeKey").val().length == 0){
		$("#timeKeyErr").html("请填写timeKey");
		$("#timeKey").addClass("z-error");
		return false;
	}else{
		$("#timeKeyErr").html("");
		$("#timeKey").removeClass("z-error");
	}
	return true;
}
</script>
</body>
</html>