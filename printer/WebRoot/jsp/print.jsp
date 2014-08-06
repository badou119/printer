<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>WEB打印</title>
		<script language="javascript" src="<%=basePath %>js/LodopFuncs.js"></script>
		
<script language="javascript" type="text/javascript"> 
    var LODOP; //声明为全局变量
    function getImageFileName() {
		LODOP=getLodop(); 
		return  LODOP.GET_DIALOG_VALUE("LocalFileFullName","*.jpg;*.bmp;.jpeg");
    } 
    
    function preview(){
		CreatePage();
		LODOP.PREVIEW();		
	};
	
	function printDesign(){
		CreatePage();
		LODOP.SET_SHOW_MODE("BKIMG_LEFT",1);
		LODOP.SET_SHOW_MODE("BKIMG_TOP",1);
		LODOP.SET_SHOW_MODE("BKIMG_WIDTH","183mm");
		//LODOP.SET_SHOW_MODE("BKIMG_HEIGHT","99mm"); //这句可不加，因宽高比例固定按原图的
		LODOP.PRINT_DESIGN();
	};	

	function CreatePage() {
		LODOP=getLodop();  
		LODOP.PRINT_INIT("打印");
		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='<%=basePath %>file/jkz_bg.jpg'>");
		//上边的  字 以及位置
		LODOP.ADD_PRINT_TEXT(126,150,179,26,"身份证号");
		LODOP.ADD_PRINT_TEXT(126,413,88,25,"编号");
		LODOP.ADD_PRINT_TEXT(164,127,132,29,"姓名");
		LODOP.ADD_PRINT_TEXT(164,414,75,25,"性别");
		LODOP.ADD_PRINT_TEXT(205,127,77,30,"年龄");
		LODOP.ADD_PRINT_TEXT(205,286,128,28,"工种");

		LODOP.ADD_PRINT_TEXT(241,160,55,28,"1年");
		LODOP.ADD_PRINT_TEXT(241,244,46,28,"1月");
		LODOP.ADD_PRINT_TEXT(244,316,41,28,"1日");

		LODOP.ADD_PRINT_TEXT(283,160,55,28,"1年");
		LODOP.ADD_PRINT_TEXT(283,244,46,28,"1月");
		LODOP.ADD_PRINT_TEXT(283,316,41,28,"1日");
		
		LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);		
		LODOP.SET_SHOW_MODE("BKIMG_PRINT",1);
		//增加图片打印项 ADD_PRINT_IMAGE(Top,Left,Width,Height,strHtmlContent)
		//上边距，左边距，图片的宽度,图片的高度,(一是超文本代码内容；二是本地文件名内容；第三是WEB地址)
		LODOP.ADD_PRINT_IMAGE(124 ,530 , 130, 185,"<img border='0' src='<%=basePath %>file/userImg/test.jpg'>");	
		//LODOP.ADD_PRINT_IMAGE();
		//设置打印项风格 SET_PRINT_STYLE(strStyleName,varStyleValue)
		
		};
			
</script>
	</head>
	<body>
		<p>
			<a href="javascript:printDesign()">打印设计</a>
		</p>
		<p>
			<b>4、打印(和预览)时包含背景图</b>
		</p>
		<p>
			<a href="javascript:preview()">打印或预览</a>
		</p>
	</body>
</html>