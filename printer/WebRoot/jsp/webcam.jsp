<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="common/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title></title>

	<script type="text/javascript" src="${ctx}js/jQuery-webcam-master/jquery.webcam.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/LodopFuncs.js"></script>
	
<style type="text/css">
.jkzfont{
	font: 900;
	font-size: 17px;
	font-family: '楷体';
}
.jkzfont2{
	font-size: 15px;
	font-family: '宋体';
}
</style>
<script>

var LODOP; //声明为全局变量

$(function() {
	LODOP=getLodop();  
	var pos = 0, ctx = null, image = [];
	var ctxh = null;
	$("#webcam").webcam({
		width: 320,//320
		height: 240,//240
		mode: "callback",
		swffile: "${ctx}js/jQuery-webcam-master/jscam_canvas_only.swf",
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
			webcam.save();
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

});
 	
	// 上传图片，jQuery版
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
			  exampeopleid: $("#exampeopleid").val(),
			  jkzseq: $("#jkzseq").val(),
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
					$("#pic").val(json[0].filepath);
					LODOP.ADD_PRINT_IMAGE(124 ,530 , 130, 185,"<img border='0' src='<%=basePath %>json[0].filepath'>");	
					document.getElementById("printdiv").style.display = "block";
				}else{
					$.messager.alert("提示",json[0].message,"error");
				}
			}
		});
	}
	
function preview(){
	
		var pic = $("#pic").val();
		if(pic ==null || pic.length <0){
			$.messager.alert("提示","请先上传照片！","info");
		}else{
			console.info(pic);
			CreatePage();
			LODOP.ADD_PRINT_IMAGE(124 ,530 , 130, 185,"<img border='0' src='<%=basePath %>"+pic+"'>");	
			LODOP.PREVIEW();	
		}
			
}
	
function printDesign(){
		var pic = $("#pic").val();
		if(pic ==null || pic.length <0){
			$.messager.alert("提示","请先上传照片！","info");
		}else{
			CreatePage();
			console.info(pic);
			LODOP.ADD_PRINT_IMAGE(124 ,530 , 130, 185,"<img border='0' src='<%=basePath %>'"+pic+">");	
			LODOP.PRINT_DESIGN();
		}
}

function printjkz(){
	var pic = $("#pic").val();
	if(pic ==null || pic.length <0){
		$.messager.alert("提示","请先上传照片！","info");
	}else{
		CreatePage();
		console.info(pic);
		LODOP.ADD_PRINT_IMAGE(124 ,530 , 130, 185,"<img border='0' src='<%=basePath %>'"+pic+">");	
		LODOP.PRINT();	
	}
}
function CreatePage() {
		LODOP.PRINT_INIT("打印");
		//设置纸张大小
		//LODOP.SET_PRINT_PAGESIZE(2, 0,0,"");
		
		//背景图
		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0'  src='<%=basePath %>file/jkz_bg.jpg'>");
		
		//设置打印项风格 
		LODOP.SET_PRINT_STYLE("FontName","宋体");
		LODOP.SET_PRINT_STYLE("FontSize","9");
		
		
		//上边的  字 以及位置
		LODOP.ADD_PRINT_TEXT(128,150,179,26,'${examPeoplePo.code}' );
		LODOP.ADD_PRINT_TEXT(128,413,88,25, '${examPeoplePo.jkzcode }' );
		
		LODOP.ADD_PRINT_TEXT(166,127,132,29, '${examPeoplePo.name }' );
		LODOP.ADD_PRINT_TEXT(166,414,75,25, '${examPeoplePo.sex }' );
		
		LODOP.ADD_PRINT_TEXT(207,127,77,30, '${examPeoplePo.age }' );
		LODOP.ADD_PRINT_TEXT(207,286,128,28,' ${examPeoplePo.profession }' );

		LODOP.ADD_PRINT_TEXT(246,160,55,28,'${examPeoplePo.examtimeY }' );
		LODOP.ADD_PRINT_TEXT(246,244,46,28,'${examPeoplePo.examtimeM }' );
		LODOP.ADD_PRINT_TEXT(246,316,41,28,'${examPeoplePo.examtimeD }' );

		LODOP.ADD_PRINT_TEXT(285,160,55,28,'${examPeoplePo.validtimeY }' );
		LODOP.ADD_PRINT_TEXT(285,244,46,28,'${examPeoplePo.validtimeM }' );
		LODOP.ADD_PRINT_TEXT(285,316,41,28,'${examPeoplePo.validtimeD }' );
		//预览时，显示背景图
		LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);	
		//打印背景图
		LODOP.SET_SHOW_MODE("BKIMG_PRINT",1);
		//增加图片打印项 ADD_PRINT_IMAGE(Top,Left,Width,Height,strHtmlContent)
		
		//体检人照片
		//LODOP.ADD_PRINT_IMAGE(124 ,530 , 130, 185,"<img border='0' src='<%=basePath %>${examPeoplePo.pic}'>");	
		//印章
		console.info(${examPeoplePo.enterlogo});
		LODOP.ADD_PRINT_IMAGE(277 ,374 , 152, 137,"<img border='0' src='<%=basePath %>${examPeoplePo.enterlogo}'>");
		
		//LODOP.SET_SHOW_MODE("BKIMG_LEFT",1);
		//LODOP.SET_SHOW_MODE("BKIMG_TOP",1);
	
		LODOP.SET_SHOW_MODE("BKIMG_WIDTH","183mm");
		//LODOP.SET_SHOW_MODE("BKIMG_HEIGHT","99mm"); //这句可不加，因宽高比例固定按原图的
		//图片截取缩放模式。0--截取图片 1--扩展（可变形）缩放 2--按原图长和宽比例
		LODOP.SET_PRINT_STYLE("Stretch","2");
		LODOP.SET_SHOW_MODE("PRINT_PAGE_PERCENT","Full-Width");
	}
</script>
</head>
<body>
<table width="100%" height="100%">
	<tr style="padding-left:10px;height:70px;background-color:#F5F5DC;">
		<td colspan="2" align="center">
			<h1>证件打印-照相</h1>
		</td>
	</tr>
	<tr height="320">
		<td width="320px" align="center" valign="top">
		  <div >
			<a  class="easyui-linkbutton" 
			 	href="javascript:webcam.capture();void(0);">照&nbsp;&nbsp;&nbsp;&nbsp;相</a>
			 	&nbsp;&nbsp;
			<a class="easyui-linkbutton" onclick="sendImage()" >上传照片</a>
			</div>
			<c:choose>
				<c:when test="${examPeoplePo.pic == null or '' eq examPeoplePo.pic }">
					<div id="printdiv" style="display: none;">
				</c:when>
				<c:otherwise>
					<div id="printdiv" >
				</c:otherwise>
			</c:choose>
			<a class="easyui-linkbutton" onclick="preview()">打印预览</a>
			&nbsp;&nbsp;
			<a class="easyui-linkbutton" onclick="printjkz()">打&nbsp;&nbsp;&nbsp;&nbsp;印</a>
			</div>
			 <br>
			<div id="webcam" ></div>
			<br>
		</td>
		<td valign="top"  border="1">
		<div style="border: 1px solid;width:100%;">
			<table width="100%"  > 
				<tr style="padding-left:10px;height:60px;background-color:#CC33FF;">
					<td colspan="7" align="center" valign="middle" >
						<h2>体检人员信息</h2>
			<input type="hidden" id="exampeopleid" name="exampeopleid" value="${examPeoplePo.id}" >
       	 	<input type="hidden" id="jkzseq" name="jkzseq" value="${examPeoplePo.jkzcode}" >
       	 	<input type="hidden" id="pic" name="pic" value="${examPeoplePo.pic}" >
       	 	<input type="hidden" id="enterlogo" name="enterlogo" value="${examPeoplePo.enterlogo}" >
					</td>
				</tr>
				<tr>
					<td width="40px" ></td>
					<td width="120px" class="jkzfont" >身份证号：</td>
					<td class="jkzfont2" >${examPeoplePo.code }&nbsp;</td>
					<td width="70px" class="jkzfont" >编号：</td>
					<td class="jkzfont2" >${examPeoplePo.jkzcode }&nbsp;</td>
					<td rowspan="4" width="160px" align="center">
						<canvas id="canvas" width="160" height="200"></canvas>
						<br>
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td class="jkzfont" >姓名：</td>
					<td class="jkzfont2" >${examPeoplePo.name }&nbsp;</td>
					<td class="jkzfont" >性别：</td>
					<td class="jkzfont2" >${examPeoplePo.sex  }&nbsp;</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td class="jkzfont" >年龄：</td>
					<td class="jkzfont2" >${examPeoplePo.age  }&nbsp;</td>
					<td class="jkzfont" >工种：</td>
					<td class="jkzfont2" >${examPeoplePo.profession  }&nbsp;</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td class="jkzfont" >体检日期：</td>
					<td class="jkzfont2" colspan="3">
					${examPeoplePo.examtimeY  }&nbsp;年&nbsp;&nbsp;
					${examPeoplePo.examtimeM  }&nbsp;月&nbsp;&nbsp;
					${examPeoplePo.examtimeD  }&nbsp;日&nbsp;&nbsp;
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td class="jkzfont" >有效期止：</td>
					<td class="jkzfont2" colspan="3">
					${examPeoplePo.validtimeY  }&nbsp;年&nbsp;&nbsp;
					${examPeoplePo.validtimeM  }&nbsp;月&nbsp;&nbsp;
					${examPeoplePo.validtimeD  }&nbsp;日&nbsp;&nbsp;
					</td>
					<td></td>
				</tr>
				<tr style="padding-left:10px;height:40px;background-color:#CC33FF;">
					<td colspan="7"></td>
				</tr>
			</table>
			</div>
		</td>
	</tr>
	<tr style="padding-left:10px;height:40px;background-color:#F5F5DC;">
	<td colspan="2"></td></tr>
</table>
 <div style="display: none;">
 <canvas id="canvashidden" width="320" height="240" ></canvas>
 </div>
</body>
</html>