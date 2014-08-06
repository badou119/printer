<%@ page language="java" import="java.util.*" 
 pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("ctx",basePath);
%>
 
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="">
	
<link rel="stylesheet" type="text/css" href="<%=basePath %>/js/JQuery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>/js/JQuery/themes/bootstrap/linkbutton.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>/js/JQuery/themes/icon.css">
	
<script type="text/javascript" src="<%=basePath %>/js/JQuery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/JQuery/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=basePath %>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/JQuery/locale/easyui-lang-zh_CN.js"></script>
	