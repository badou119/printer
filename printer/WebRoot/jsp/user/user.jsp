<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="">
	
	<link rel="stylesheet" type="text/css" href="./js/JQuery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./js/JQuery/themes/bootstrap/linkbutton.css">
	<link rel="stylesheet" type="text/css" href="./css/demo.css">
	<script type="text/javascript" src="./js/JQuery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="./js/JQuery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="./js/jquery.form.js"></script>
	<script type="text/javascript" src="./js/JQuery/locale/easyui-lang-zh_CN.js"></script>

<script>
$(function() {
	
});

	function save(){
			if(check_form()){
				$("#form1").submit();
             <%--  $.ajax( {
					type : "POST",
					url : "employeeManage_checkEmploy.action",
					data : $("#form1").formSerialize(),
					beforeSend : function(XMLHttpRequest) {
						//loading();
					},
					success : function(data) {
						var json = eval(arguments[2].responseText);
						if (json[0].result == "ok") {
							$("#form1").submit();
						}else{
							$.messager.alert("提示","该体检信息已存在，保存失败！","error");
						}
					}
				});
				--%>
			}
			else{
				$.messager.alert("提示","信息存在错误，请修改！","error");
			}
		}
	function clearInfo(){
		$("#name").val("");
		$("#phone").val("");
		$("#code").val("");
		$("#age").val("");
		$("#profession").val("");
		$("#address").val("");
		$("#examtime").val("");
	}
	
</script>
<script type="text/javascript">

	function check_name() {
		var feild = $("#name").val();
		var span = document.getElementById("namespan");
		var flag = false;
		//名称不能为空 
		if (feild.length == 0) {
			// alert("企业名称不能为空 !");
			namespan.innerHTML="*姓名不能为空 !";
			flag = false;
			//名称必须符合格式
		} else {
			var part = /^[A-Za-z0-9-_()（）.\u4e00-\u9fa5]{0,100000}$/;
			var f = part.test(feild);
			if (f == false) {
				//  alert("企业名称格式错误!");
				span.innerHTML="*姓名格式错误!只能包含中文、字母、数字";
				flag = false;
			} else if (f == true) {
				if (span.length > 50) {
					span.innerHTML="*姓名长度超长 !";
					flag = false;
				}else{
					span.innerHTML="";
					flag = true;
				}
			}
		}
		return flag;
	}

	function check_phone() {
		var field = $("#phone").val();
		var span = document.getElementById("phonespan");
		var flag = false;
	
		if (field.length == 0) {
			span.innerHTML="*移动电话不能为空 !";
			flag = false;
			//联系电话必须符合格式
		} else {
			var part = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
			var f = part.test(field);
			if (f == false) {
				span.innerHTML="*移动电话格式错误 !";
			} else if (f == true) {
				span.innerHTML="";
				flag = true;
			}

		}
		return flag;
	}
	function check_account(){
		var feild = $("#account").val();
		var span=document.getElementById("accountspan");
		var flag = false;
		if (feild.length == 0) {
			span.innerHTML="*帐号不能为空!";
			flag = false;
		} else {
			span.innerHTML="";
			flag = true;
		}
		return flag;
	}
<%--
	function check_password(){
		var feild = $("#password").val();
		var repwd = $("#repassword").val();
		var span=document.getElementById("passwordspan");
		var flag = false;
		if (feild.length < 6 ) {
			span.innerHTML="*密码长度不能少于6位!";
			flag = false;
		} else {
			if(repwd.length >0){
				if(pwd != feild){
					span.innerHTML="*确认密码与密码不一致，请重新输入!";
					flag = false;
				}
			}else{
				span.innerHTML="";
				flag = true;
			}
		}
		return flag;
	}

	function check_repassword(){
		var feild = $("#repassword").val();
		var pwd = $("#password").val();
		var span=document.getElementById("repasswordspan");
		var flag = false;
		if (feild.length < 6 ) {
			span.innerHTML="*密码长度不能少于6位!";
			flag = false;
		} else if(pwd != feild){
			span.innerHTML="*确认密码与密码不一致，请重新输入!";
			flag = false;
		}else {
			span.innerHTML="";
			flag = true;
		}
		return flag;
	}
	--%>
	
	function check_form() {
		return check_name()&&check_account()&& check_phone();
	}
	
</script>
<style>
#uploadImg{ font-size:12px; overflow:hidden; position:absolute}
#fileInput{ position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}
</style>
</head>
  
<body style="margin:1px;">
<div  style="padding-top:0px;text-align: center; border-bottom: solid 1px #DCDCDC;">
<a class="easyui-linkbutton" onclick="return save();">保存</a>
</div>
<div  style="border-bottom:none; align:center;">

<form id="form1" action="addUser.action" method="post">

	<s:hidden id="id" name="user.id" />
	<table style="margin-top:1px;margin-left:40px;text-align: left;align:center;">
	<tr >
		<td colspan="3" align="center"><h2>乡镇单位帐号信息录入</h2></td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">帐号</td>
	<td>
	<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
		id="account" name="user.account" 
		value="${user.account}" onblur="check_account()"></input>
	</td>
	<td style="width:150px;">
	<span style="color: red;" id="accountspan" >*</span>
	</td>
	</tr>
	<%--
	<tr style="height:30px;">
		<td style="width:80px;">密码</td>
		<td>
		<input type="password" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
			id="password" name="user.password" 
			value="${user.password}" onblur="check_password()"></input>
		</td>
		<td style="width:150px;">
		<span style="color: red;" id="passwordspan" >*</span>
		</td>
		</tr>
		
		<tr style="height:30px;">
		<td style="width:80px;">确认密码</td>
		<td>
		<input type="password" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
			id="repassword" name="repassword" 
			onblur="check_repassword()"></input>
		</td>
		<td style="width:150px;">
		<span style="color: red;" id="repasswordspan" >*</span>
		</td>
	</tr>
	 --%>
	<tr style="height:30px;">
	<td style="width:80px;">乡镇单位</td>
	<td>
		<SELECT id="enterid" name="user.enterid">
			<c:forEach items="${enterList}" var="enter" >
				<c:choose >
					<c:when test="${user.enterid eq enter.id}">
						<option value="${enter.id}" selected="selected">${enter.name}</option>
					</c:when>
					<c:otherwise>
						<option value="${enter.id}">${enter.name}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</SELECT>
	</td>
	<td style="width:150px;">
	<span style="color: red;" id="enteridspan" >*</span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">姓名</td>
	<td>
	<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
		id="name" name="user.name" 
		value="${user.name}" onblur="check_name()"></input>
	</td>
	<td style="width:150px;">
	<span style="color: red;" id="namespan" >*</span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">移动电话</td>
	<td>
	<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" id="phone"
	 name="user.phone" value="${user.phone}" onblur="check_phone()"></input>
	</td>
	<td>
	<span style="color: red;" id="phonespan" >*</span>
	</td>
	
	<tr style="height:30px;">
	<td style="width:80px;">备注</td>
	<td>
	<textarea rows="4" cols="60" id="remark" name="user.remark" >${user.remark}</textarea>
	</td>
	<td style = "text-align: left;">
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td colspan="3">
	</td>
	</tr>
	</table>
</form>
</body>
</html>
