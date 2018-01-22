<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/page/public/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>欢迎页</title>
</head>
<body>
 
<!--  <div style="height: 30px;"></div>
  <h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hi guys!<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome...</h1>  -->


</body>
<script type="text/javascript" src="${ctx }/media/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
$(function(){
	window.location.href = "${ctx}/scheduler/list/1";
});
</script>
</html>
