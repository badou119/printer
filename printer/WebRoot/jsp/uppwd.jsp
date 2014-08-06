<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>修改密码</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>js/JQuery/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/JQuery/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/demo.css">
    <script type="text/javascript" src="<%=basePath %>js/JQuery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/JQuery/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/JQuery/themes/bootstrap/linkbutton.css">
  <script type="text/javascript" src="<%=basePath %>js/JQuery/locale/easyui-lang-zh_CN.js"></script>
  
<script type="text/javascript">
function res_click() {
	if (oldpwd.value == null || oldpwd.value == '') {
	    $.messager.alert("提示","请输入原密码！","warning");
	    oldpwd.value="";
	    oldpwd.focus();
	    return;
	}
	if (pwd.value == null || pwd.value == '') {
	    $.messager.alert("提示","请输入密码！","warning");
	    pwd.focus();
	    return;
	}
	if (repwd.value == null || repwd.value == '') {
	    $.messager.alert("提示","请输入确认密码！","warning");
	    pwd.focus();
	    return;
	}
	
	if (pwd.value!=repwd.value){
		$.messager.alert("提示","两次输入的密码不同，请重新输入密码！","warning");
		pwd.focus();
		return;
	}                   
	        
	jQuery.ajax({
	     type: "POST",
	     url: "login_uppwd.action",
	     data: "pwd=" + pwd.value+"&oldpwd="+oldpwd.value+"",
	     beforeSend: function(XMLHttpRequest) {
	         loading();
	     },
	     success: function(data) {
	         document.getElementById("loading").style.display = "none";
	         var json = eval(arguments[2].responseText);
	
	         if (json[0].result == "ok") {
				$.messager.alert("提示","修改密码成功！","info");
				document.getElementById("pwd").value="";
				document.getElementById("repwd").value="";
	           	//  window.close();
	         } else if(json[0].result=="pwderror") {
	            $.messager.alert("提示","原密码错误！","error");
	         }else{
	              $.messager.alert("提示","修改密码失败，请与管理员联系！","error");
	         }
	     }
	 });
 }

function loading() {
    document.getElementById("loading").style.display = "block";
    //$('#loading').style.display="block";
    $('#loading').html('<img src="lib/loading.gif"/>');
}
 </script>
<style type="text/css">
body {
	margin: 0;
	color: #333333;
	text-align: center;
}
.loginbox {
	width: 200px;
	height: 21px;
	background-color: #f6f6f6;
	border: 1px #cccccc solid;
	font-size: 18px;
}
table
{
  border-collapse:collapse;
}

table,th, td
{
 	border: 1px solid #A4BED4;
 	height:30px;
}
</style>
</head>
<body>
<center>
	<table id="regtext" style="width:500px;">
	<tr>
	<td colspan="2" align="center"><h2>更新密码</h2></td>
	</tr>
	<tr>
	<td style="font-size:12px;">  原密码：</td>
	<td style="text-align: left;"><input id="oldpwd" type="password" class="loginbox" /></td>
	</tr>
	<tr>		
	<td style="font-size:12px;">  新密码：</td>
	<td style="text-align: left;"><input id="pwd" type="password" class="loginbox" /></td>
	</tr>	
	<tr>
	<td style="font-size:12px;">确认新密码：</td>
	<td style="text-align: left;"><input id="repwd" type="password" class="loginbox" /></td>
	</tr>
	<tr>
	<td colspan="2" style="font-size:12px;text-align:center;">
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="res_click()">确认</a>
	</td>
	</tr>
	</table>
	<div id="loading"
		style="display: none; position: absolute; border: 0px solid #000; height: 10%; top: 45%; width: 20%; left: 40%; text-align: center">
	</div>
</center>
</body>
</html>
