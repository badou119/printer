<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="images/ico.ico" type="image/vnd.microsoft.icon">
	<link rel="icon" href="images/icon.png" type="<%=basePath%>image/vnd.microsoft.icon">
    <title></title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/MenuStyle.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/JQuery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/JQuery/themes/icon.css">
	<script type="text/javascript" src="<%=basePath%>js/JQuery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/initMenuAndTabs.js"></script>
	<script type="text/javascript">
		var _menus=null;
	   	_menus =<%=session.getAttribute("menu")%>;
		$(function(){
			//初始化菜单
			InitMenu();
			//加载时钟
			clockon();
			$("#loginOut").click(function(){
				var pramaValue = getQueryString("page");
				window.location = "logout.action?page="+pramaValue;
			});
		});
		function getQueryString(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) return unescape(r[2]); return null;
		}

		
	</script>
	<script type="text/javascript" src="./js/JQuery/jquery.easyui.min.js"></script>
	<style type="text/css">
	.header-top{
	  background-color: #3366CC;
	  background: -webkit-linear-gradient(top,#3366FF 0,#3366CC 100%);
	  background: -moz-linear-gradient(top,#3366FF 0,#3366CC 100%);
	  background: -o-linear-gradient(top,#3366FF 0,#3366CC 100%);
	  background: linear-gradient(to bottom,#3366FF 0,#3366CC 100%);
	  background-repeat: repeat-x;
	  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#3366FF,endColorstr=#3366CC,GradientType=0);
	}
	</style>
  </head>
  
  <body class="easyui-layout">
	<noscript>
	<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
	    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
	</div>
	</noscript>
	<!-- 头部 background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;-->
	<div region="north" class="header-top" style="overflow: hidden; height: 30px;line-height: 20px;color: #fff;font-family: Verdana, 微软雅黑,黑体;">
		<table style="width:100%;height:30px;">
		<tr>
		<td>
		<span style="float:right; padding-right:20px;" class="head">
	    <span id="bgclock"></span>&nbsp;&nbsp;&nbsp;&nbsp;       
	           欢迎"${loginUser.name}" 
	    &nbsp;&nbsp;&nbsp;&nbsp;
	    <a target="mainFrameuppwd"  href="jsp/uppwd.jsp" id="editpass">修改密码</a>
	    &nbsp;&nbsp;&nbsp;
	    <a href="javascript:void(null)" id="loginOut">安全退出</a>
	    
	    </span>
	    <span style="padding-left:10px; font-size: 14px;font-weight:bold; ">
	    <img src="images/blocks.gif" width="20" height="20" align="absmiddle" />
	    </span>
		</td>
		</tr>
		</table>
	</div>
	<!-- 底部 -->
	<div region="south"  style="height:30px;padding-top:7px;text-align:center;background: #D2E0F2;">
		<span style="font-size:12px;font-weight:bold;color:#15428B;">
		Copyright © 2014    版权所有
		</span>
	</div>
	<div region="east" style="width:1px;">
		
	</div>
	<div region="west" split="true" title="导航菜单" style="width:200px;padding:1px;overflow:hidden;">
		<div class="easyui-accordion" fit="true" border="false">
		</div>
	</div>
	<div id="mainPanle" region="center" style="overflow:hidden;">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="首页" style="padding:20px;overflow:hidden;"> 
				<span>欢迎使用</span>
			</div>
		</div>
	</div>
</body>

</html>