<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
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

	<link rel="stylesheet" type="text/css" href="<%=basePath %>/js/JQuery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/js/JQuery/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/js/JQuery/themes/bootstrap/linkbutton.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/demo.css">
	
	<script type="text/javascript" src="<%=basePath %>/js/JQuery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/JQuery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/JQuery/locale/easyui-lang-zh_CN.js"></script>
 
<script type="text/javascript">
function downmodel_btn(){
  	 url = "excelExport-down.action";
     window.open(url, "exportWindow");
}
function down_error(){
	 $.messager.progress({
		msg:'正在导出错误文件...',
		text:''
	 });
 	 $.ajax({
 		      dataType:"json",
              type: "POST",
              url: "excelExport-downError.action",
              success: function(data) {
               		$.messager.progress('close');
                 	var json = eval(data);
          			if (json[0].result == "ok") {
                      	url = "excelExport-downDept.action?filename="+json[1].file;
                       	window.open(url, "exportWindow");
                   	}else{
                        $.messager.alert("操作提示","导出失败！","error");
                   	}
                 
           	  }
     });  
}

</script>

<style>
#uploadImg{ font-size:12px; overflow:hidden; position:absolute}
#fileInput{ position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}
</style>
</head>
  
<body class="easyui-layout" style="margin:5px;">
<div region="center" style="padding:5px;border-bottom:none;">
<table style="width:100%;">
<tr>
<td style="height:30px;text-align: center;font-size:14px; font-weight:bold; background-color:#F5F5DC;">
导入体检人信息
</td>
</tr>
<tr>
<td style="height:30px;color: red;">
提示：点击开始上传，选择您的 体检人信息 文件（仅支持Excel、csv文件，并且Excel文件必须为标准模板，如果没有请下载）确定即可上传。
</td>
</tr>
<tr>
<td style="height:30px;color: red;">
提示：本系统中下载的错误文件为csv格式（该格式可用excel打开查看并编辑）。
</td>
</tr>
<tr>
<td style="height:60px;">
<div id="uploadImageDiv" >
<span id="uploadImg"> 
<input type="file" id="fileInput" name="fileInput" size="1" onchange="upload()" /> 
<a class="easyui-linkbutton" >开始上传</a>
</span>
</div>
</td>
</tr>
<tr>
<td style="height:30px;">
<span id="uploadMessage" style="color: Red;"></span>
</td>
</tr>
<tr>
<td style="height:30px;">
<a class="easyui-linkbutton" onclick="downmodel_btn()">下载模板</a>
<a class="easyui-linkbutton" onclick="down_error()">下载错误文件</a>
</td>
</tr>
</table>
<input id="depart" name="" style="display: none;" />
<div style="display: none;">
<iframe name="uploadResponse"></iframe>
<form id="uploadForm" action="upload.action" target="uploadResponse" method="post" enctype="multipart/form-data">
<input type="file" name="fileInput" value="" />
</form>
</div>
</div>
<script>
function upload(){
		var file = document.getElementById("fileInput");
	    var uploadFormElement = document.getElementById("uploadForm");
	    $.messager.progress({
			msg:'正在导入数据...',
			text:''
		});
	    //复制数据
        uploadFormElement.removeChild(uploadFormElement.fileInput);
        uploadFormElement.appendChild(file);
        document.getElementById("uploadImageDiv").innerHTML = '<span id="uploadImg"> <input type="file" id="fileInput" name="fileInput" size="1" onchange="upload()"> <input type="button" style="border: 1px solid #e1e3e4; height: 25px;"	value="选择文件"></input> </span>';

        //提交数据
        uploadFormElement.submit();
}
function uploadImageResponse(response) {
	$.messager.progress('close');
    var errLabel = document.getElementById("uploadMessage");
    errLabel.innerHTML = "";           	
    var json = eval(response);
    if (json[0].status == 1)
       errLabel.innerHTML = "请先选择文件再上传";
    else if (json[0].status == 2)
       errLabel.innerHTML = "抱歉，目前仅支持excel、csv文件导入";
    else if (json[0].status == 3)
        errLabel.innerHTML = "抱歉，只支持小于50M的文件导入";
    else{
   		errLabel.innerHTML ="";
   		if (json[1].result == "ok") {
   		      if(json[1].errorcount>0){
              	errLabel.innerHTML ='导入成功！共'+json[1].totalcount+'条记录格式正确；有'+json[1].errorcount+'条错误记录请点击\"下载错误文件\"下载错误记录，修改内容并重新提交。';
              }else{
              	errLabel.innerHTML ='导入成功！共'+json[1].totalcount+'条记录格式正确；体检人员数据全部导入成功。';
              }
        }else{
              errLabel.innerHTML ="导入失败！";
        }  
   }    
}		
</script>
</body>
</html>
