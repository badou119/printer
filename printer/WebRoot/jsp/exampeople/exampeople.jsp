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
	<script type="text/javascript" src="<%=basePath %>js/jQuery-webcam-master/jquery.webcam.min.js"></script>
	
<script>
$(function() {
	
	var pos = 0, ctx = null, image = [];
	var ctxh = null;
	$("#webcam").webcam({
		width: 320,//320
		height: 240,//240
		mode: "callback",
		swffile: "<%=basePath %>js/jQuery-webcam-master/jscam_canvas_only.swf",
		onSave: function(data) {
		    var col = data.split(";");
		    var img = image;
		    // for(var i = 0; i < 320; i++) {
		    for(var i = 0; i < 320; i++) {
		        var tmp = parseInt(col[i]);
		        img.data[pos + 0] = (tmp >> 16) & 0xff;
		        img.data[pos + 1] = (tmp >> 8) & 0xff;
		        img.data[pos + 2] = tmp & 0xff;
		        img.data[pos + 3] = 0xff;
		        pos+= 4;
		    }
			//if (pos >= 4 * 320 * 240) {
		    if (pos >= 4 * 320 * 240) {
		    	
		    	//ctxh.clearRect(0, 0, 320, 240);
		    	ctxh.putImageData(img, 0, 0);
		    	image = ctxh.getImageData(0, 0, 320, 240);

		    	ctx.clearRect(0, 0, 160, 200);
		        ctx.putImageData(image,-80,-40,80,40,160,200);
		      
		        pos = 0;
		    }
		},
		onCapture: function () {
			//webcam.save();
			jQuery("#flash").css("display", "block");
			jQuery("#flash").fadeOut("fast", function () {
				jQuery("#flash").css("opacity", 1);
			});
			webcam.save();
		}
	});

	window.addEventListener("load", function() {

	 jQuery("body").append("<div id=\"flash\"></div>");
	 var canvas = document.getElementById("canvas");

	 if (canvas.getContext) {
		 ctx = document.getElementById("canvas").getContext("2d");
		 ctxh = document.getElementById("canvashidden").getContext("2d");
		
		 ctx.clearRect(0, 0, 160, 200);
		 ctxh.clearRect(0, 0,320, 240);
		 var img = new Image();
		 
		 img.src = "<%=basePath %>${examPeoplePo.pic}";
		 
		 img.onload = function() {
		 	ctx.drawImage(img, 0, 0);
		 	ctxh.drawImage(img, 152, 50);
		 }
		 image = ctxh.getImageData(0, 0, 320, 240);
	 }
	}, false);
	
	$('#examtime').datebox({
	    required:true ,
	    current:new Date(),
	    formatter:function(date){
	    	var y = date.getFullYear();
	    	var m = date.getMonth()+1;
	    	var d = date.getDate();
	    	return y+'-'+m+'-'+d;
	    }
	});  
});
//上传图片，jQuery版
function sendImage(){
  // 获取 canvas DOM 对象
  var canvas = document.getElementById("canvas");
  // 获取Base64编码后的图像数据，格式是字符串
  // "data:image/png;base64,"开头,需要在客户端或者服务器端将其去掉，后面的部分可以直接写入文件。
  var dataurl = canvas.toDataURL("image/png");
  // 为安全 对URI进行编码
  // data%3Aimage%2Fpng%3Bbase64%2C 开头
  var imagedata =  encodeURIComponent(dataurl);
 
  var data = {
		  exampeopleid: $("#sid").val(),
          fileinput: imagedata
   };
  $.ajax({
		type : "POST",
		url : "uploadExamPic.action",
		data : data,
		beforeSend : function(XMLHttpRequest) {
			//loading();
		},
		success : function(data) {
			var json = eval(arguments[2].responseText);
			if (json[0].result == "ok") {
				var span=document.getElementById("picspan");
				span.innerHTML=""+json[0].message;
				$("#pic").val(json[0].filepath);
			}else{
				$.messager.alert("提示",json[0].message,"error");
			}
		}
	});
}

	function save(){
			
			if(check_form()){
				$("#form1").submit();
			}
			else{
				$.messager.alert("提示","信息存在错误或不完整，请确认！","error");
			}
		}
	
	function clearInfo(){
		$("#name").val("");
		$("#jkzcode").val("");
		$("#code").val("");
		$("#age").val("");
		$("#profession").val("");
		
		$("#examtime").val("");
	}

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
	
	function check_code(){
		var code = $("#code").val();
		var span = document.getElementById("codespan");
		var flag = false;
		var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		  
		if (code.length == 0) {
			span.innerHTML = "*身份证号不能为空!";
			flag = false;
		} else if(reg.test(code) === false){
			span.innerHTML = "*请输入正确的身份证号!";
			flag = false;
		} else {
			span.innerHTML="";
			flag = true;
		}
		return flag;
	}
	function check_age(){
		
		var feild = $("#age").val();
		var span=document.getElementById("agespan");
		var flag = false;
		
		if (feild.length == 0) {
			span.innerHTML="*年龄不能为空!";
			flag = false;
		} else if(isNaN(feild)||feild<=0||feild>=120){
			span.innerHTML="*请输入正确的年龄!";
			flag = false;
		}else {
			span.innerHTML="";
			flag = true;
		}
		return flag;
	}
	function  check_examtime(){
		
		var feild = $('#examtime').datebox('getValue');
		var span=document.getElementById("examtimespan");
		var flag = false;
		
		if (feild.length == 0) {
			span.innerHTML="*体检时间不能为空!";
			flag = false;
		}else {
			var t = Date.parse(feild);
			if (!isNaN(t)){
				span.innerHTML="*请选择正确的体检时间!";
				flag = false;
			}else{
				span.innerHTML="";
				flag = true;
			}
		}
		return flag;
	}
	
	function check_pic(){
		
		var feild = $("#pic").val();
		var span=document.getElementById("picspan");
		var flag = false;
		
		if (feild.length == 0) {
			span.innerHTML="*请先照相并上传照片!";
			flag = false;
		} else {
			span.innerHTML="";
			flag = true;
		}
		return flag;
	}
	function check_form() {
		return check_name() && check_code() &&check_pic()&& check_age()&&check_examtime();//&&check_pic()
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
<div  style="float: left;" >
<table>
<tr>
	<td>
	<form id="form1" action="addExampeople.action" method="post">
	<s:hidden id="enterid" name="examPeoplePo.enterid" />
	<s:hidden id="id" name="examPeoplePo.id" />
	<input type="hidden"  id="pic" name="examPeoplePo.pic" value="${examPeoplePo.pic}" /> 
	<input type="hidden"  id="sid" name="sid" value="${examPeoplePo.id}" /> 
	<table style="margin-top:1px;margin-left:40px;text-align: left;align:center;">
	<tr >
		<td colspan="3" align="center"><h2>体检人员信息录入</h2></td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</td>
	<td>
	<input type="text" readonly="readonly"  style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
		id="jkzcode" name="examPeoplePo.jkzcode" 
		value="${examPeoplePo.jkzcode}" ></input>
	</td>
	<td style="width:120px;">
	<span style="color: red;"  id="picspan">*</span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</td>
	<td>
	<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
		id="name" name="examPeoplePo.name" 
		value="${examPeoplePo.name}" onblur="check_name()"></input>
	</td>
	<td style="width:150px;">
	<span style="color: red;" id="namespan" >*</span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">身份证号</td>
	<td>
	<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
		id="code" name="examPeoplePo.code" 
		value="${examPeoplePo.code}" onblur="check_code()"></input>
	</td>
	<td>
	<span style="color: red;" id="codespan" >*</span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</td>
	<td>
		<input type="radio" style="width:20px;height:25px;border: solid 1px #DCDCDC;" 
		 	name="examPeoplePo.sex" 
			value="男"  checked="checked">男</input>
		<input type="radio" style="width:20px;height:25px;border: solid 1px #DCDCDC;" 
		 name="examPeoplePo.sex" value="女" >女</input>
	</td>
	<td>
	<span style="color: red;" id="sexspan" ></span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</td>
	<td>
		<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
			id="age" name="examPeoplePo.age" value="${examPeoplePo.age}" 
			onblur="check_age()"></input>
	</td>
	<td>
	<span style="color: red;" id="agespan" >*</span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">体检时间</td>
	<td>
		<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
			id="examtime" name="examPeoplePo.examtime" 
			value="${examPeoplePo.examtimeStr}" ></input>
	</td>
	<td>
	<span style="color: red;" id="examtimespan" >*</span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;种</td>
	<td>
		<SELECT id="profession" name="examPeoplePo.profession" style="width:200px;height:25px;border: solid 1px #DCDCDC;" >
			<c:forEach items="${proList}" var="pro" >
				<c:choose >
					<c:when test="${examPeoplePo.profession eq pro.id}">
						<option value="${pro.id}" selected="selected">${pro.name}</option>
					</c:when>
					<c:otherwise>
						<option value="${pro.id}">${pro.name}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</SELECT>
	</td>
	<td>
	<span style="color: red;" id="professionspan" >*</span>
	</td>
	</tr>
	
	<tr style="height:30px;">
	<td style="width:80px;">体检单位</td>
	<td>
	<input type="text" readonly="readonly" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
			id="entername" name="entername" value="${examPeoplePo.entername}"></input>
	</td>
	<td style = "text-align: left;">
	</td>
	</tr>
	<!-- 
	<tr style="height:30px;">
	<td style="width:80px;">移动电话</td>
	<td>
	<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" id="phone"
	 name="examPeoplePo.phone" value="${examPeoplePo.phone}" onblur="check_phone()"></input>
	</td>
	<td>
	<span style="color: red;" id="phonespan" >*</span>
	</td>
	<tr style="height:30px;">
	<td style="width:80px;">家庭住址</td>
	<td>
	<input type="text" style="width:400px;height:25px;border: solid 1px #DCDCDC;" 
	id="address" name="examPeoplePo.address" value="${examPeoplePo.address}" 
	onblur="check_address()"></input>
	</td>
	<td>
	<span style="color: red;" id="addressspan" >*</span>
	</td>
	</tr>
	-->
	<tr style="height:30px;">
	<td style="width:80px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</td>
	<td>
	<textarea rows="4" cols="60" id="remark" name="examPeoplePo.remark" >${examPeoplePo.remark}</textarea>
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
	</td>
	<td>
	<div style="float: right;padding: 5px;margin: 5px;" >
		<a  class="easyui-linkbutton" 
					 	href="javascript:webcam.capture();void(0);">照&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;相</a>
					 	&nbsp;&nbsp;
		<a class="easyui-linkbutton" onclick="sendImage()" >上传照片</a>
		<br>
		<div id="webcam" ></div>
		<br>
		<div>
			<canvas id="canvas" width="160" height="200"></canvas>
		</div>
		 <div style="display: none;">
		 <canvas id="canvashidden" width="320" height="240" ></canvas>
		 </div>
	</div>
	</td>
</tr>
</table>
</div>
</body>
</html>
