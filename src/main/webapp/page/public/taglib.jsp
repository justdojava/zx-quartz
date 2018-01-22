<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%   
response.setHeader("Pragma","No-Cache");   
response.setHeader("Cache-Control","No-Cache");   
response.setDateHeader("Expires",   0); 
%> 