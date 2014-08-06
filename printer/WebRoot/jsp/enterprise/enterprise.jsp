<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
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
			namespan.innerHTML="*单位名称不能为空 !";
			flag = false;
			//名称必须符合格式
		} else {
			var part = /^[A-Za-z0-9-_()（）.\u4e00-\u9fa5]{0,100000}$/;
			var f = part.test(feild);
			if (f == false) {
				//  alert("企业名称格式错误!");
				span.innerHTML="*单位名称格式错误!只能包含中文、字母、数字";
				flag = false;
			} else if (f == true) {
				if (span.length > 50) {
					span.innerHTML="*单位名称长度超长 !";
					flag = false;
				}else{
					span.innerHTML="";
					flag = true;
				}
			}
		}
		return flag;
	}
	
	function check_entercode() {
		var feild = $("#entercode").val();
		var span=document.getElementById("entercodespan");
		var flag = false;
		if (feild.length == 0) {
			span.innerHTML="*单位代号为空!";
			flag = false;
		} else {
			span.innerHTML="";
			flag = true;
		}
		return flag;
	}
	
	function check_address() {
		var feild = $("#address").val();
		var span=document.getElementById("addressspan");
		var flag = false;
		if (feild.length == 0) {
			span.innerHTML="*单位地址为空!";
			flag = false;
		} else {
			span.innerHTML="";
			flag = true;
		}
		return flag;
	}
	function check_form() {
		return check_name() && check_entercode() &&check_address();
	}
	
	function upload(){
		 var file = document.getElementById("fileInput");
       var uploadFormElement = document.getElementById("uploadForm");
       //显示进度条
       document.getElementById("processDiv").style.display = "block"; // the progress div
       //复制图片数据
       uploadFormElement.removeChild(uploadFormElement.fileInput);
       uploadFormElement.appendChild(file);
       document.getElementById("uploadImageDiv").innerHTML = '<span id="uploadImg"> <input type="file" id="fileInput" name="fileInput" size="1" onchange="upload()"> <input type="button" class="easyui-linkbutton" value="更换印章"></input> </span>';
       //提交图片数据
       uploadFormElement.submit();
	}
	function uploadImageResponse(response) {
       document.getElementById("processDiv").style.display = "none"; // hide progresss div
       var errLabel = document.getElementById("uploadMessage");
       errLabel.innerHTML = "";
       var json=eval(response);
       if (json[0].status == 1)
           errLabel.innerHTML = "请先选择文件再上传";
       else if (json[0].status == 2)
           errLabel.innerHTML = "抱歉，目前仅支持jpg,png,jpeg格式的图片";
       else if (json[0].status == 3)
           errLabel.innerHTML = "抱歉，您上传的文件不得大于300KB";
       else{
       		errLabel.innerHTML ="";
       		console.info(json[0].message);
	       document.getElementById("logo").value=json[0].message;
           var grouplogo=document.getElementById("logopic");
           grouplogo.src="./"+json[0].message;
       }
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
<form id="form1" action="addEnterprise.action" method="post">
	<s:hidden id="id" name="enterprise.id" />
	<table style="margin-top:1px;margin-left:40px;text-align: left;align:center;">
	<tr >
		<td colspan="3" align="center"><h2>乡镇单位信息录入</h2></td>
	</tr>
	<tr style="height:30px;">
	<td style="width:80px;">单位代号</td>
	<td>
	<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
		id="entercode" name="enterprise.entercode" 
		value="${enterprise.entercode}" onblur="check_entercode()"></input>
	</td>
	<td style="width:150px;">
	<span style="color: red;" id="entercodespan" >*</span>
	</td>
	</tr>
	<tr style="height:30px;">
	<td style="width:80px;">单位名称</td>
	<td>
	<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
		id="name" name="enterprise.name" 
		value="${enterprise.name}" onblur="check_name()"></input>
	</td>
	<td>
	<span style="color: red;" id="namespan" >*</span>
	</td>
	</tr>
		
	<tr style="height:30px;">
	<td style="width:80px;">单位地址</td>
	<td>
		<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
			id="address" name="enterprise.address" value="${enterprise.address}" 
			onblur="check_address()" ></input>
	</td>
	<td>
	<span style="color: red;" id="addressspan" >*</span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">联系人</td>
	<td>
		<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
			id="linkperpon" name="enterprise.linkperpon" 
			value="${enterprise.linkperpon}" ></input>
	</td>
	<td>
	<span style="color: red;" id="linkperponspan" ></span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">联系电话</td>
	<td>
		<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
		id="linkphone" name="enterprise.linkphone" 
		value="${enterprise.linkphone}" ></input>
	</td>
	<td>
	<span style="color: red;" id="linkphonespan" ></span>
	</td>
	</tr>
	<tr style="height:30px;">
	<td style="width:80px;">印章</td>
	<td>
	<table>
		<tr>
			<td>
		<img style="width: 100px;height: 100px; margin-top: 8px;" id="logopic" src="${pageContext.request.contextPath}${enterprise.logo}" onerror="javascript:this.src='${pageContext.request.contextPath}/file/enterlogo/testzh.jpg' "></img>
	</td>
	<td id="uploadImageDiv" style="width: 80px;height: 20px;">
		<span id="uploadImg"> 
				<input type="file" id="fileInput" name="fileInput" size="1" onchange="upload()"> 
				<input type="button" class="easyui-linkbutton" value="更换印章"></input> 
		</span>
	</td>
	</tr>
	</table>
	</td>
	<td style = "text-align: left;">
	<input class="easyui-validatebox" type="hidden" id="logo" name="enterprise.logo" value=""></input> 
	<span id="uploadMessage" style="font-size:12px; border: 0px solid #cccccc; color: Red;">
	</span>
	</td>
	</tr>
	<tr style="height:30px;">
	<td style="width:80px;">备注</td>
	<td>
	<textarea rows="4" cols="60" id="remark" name="enterprise.remark" >${enterprise.remark}</textarea>
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

<div id="processDiv" style="display: none; color: #660066; font-family: Arial;">
            <img src="/images/loading.gif" alt="uploading" style="display: none"/>
        </div>
	 <div style="display: none;">
        <iframe name="uploadResponse"></iframe>
        <form id="uploadForm" action="uploadPic.action" target="uploadResponse"  method="post" enctype="multipart/form-data">
        <input type="file" name="fileInput" value="" />
        </form>
    </div>
</body>
</html>
