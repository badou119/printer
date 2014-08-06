<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

	<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/JQuery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/JQuery/themes/bootstrap/linkbutton.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/JQuery/themes/icon.css">
	<script type="text/javascript" src="<%=basePath%>/js/JQuery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/JQuery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/JQuery/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/LodopFuncs.js"></script>
	<script>

	var LODOP; //声明为全局变量
	
	$(function() {
		loadList();
	});

	//加载
	function loadList() {
		
		var query1 = $("#query1").val();
		var enterid = $("#enterid").val();
		
		$("#dataTable").datagrid({
				width:document.body.clientWidth,
				striped : true,//表格条纹化
				loadMsg : '加载中...',//加载时显示的信息
				url : "exampeopleList.action",//数据源
				queryParams:{
					type:'print',
					selectenterid:enterid,
					peoplename:query1
				},//参数
				remoteSort:false,//排序
				idField:'id',//主键字段
				fitColumns:true,//列自动适应大小
				singleSelect:true,//是否单选行，默认为多选行
				columns:[[
					{field:'jkzcode',title:'编号',width:200,rowspan:2,sortable:true,
							sorter:function(a,b){
									return (a>b?1:-1);
								}
					},
					{field:'name',title:'姓名',width:100,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'code',title:'身份证号',width:150,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'sex',title:'性别',width:80,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'age',title:'年龄',width:80,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'proname',title:'工种',width:100,rowspan:2,sortable:true,
					    sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'examtime',title:'体检时间',width:200,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'entername',title:'体检单位',width:300,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'remark',title:'备注',width:100,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'opt',title:'操作',width:200,align:'center',  
		                 		formatter:function(value,rowData,rowIndex){  
				                    if(rowData.id!=""){
					                       var e ='';// '<a  onclick="camwin(\''+rowData.id+'\')">照相</a>  ';  
					                    var d = '';
					                      if(rowData.jkzcode!="" && rowData.pic!=""){
					                    	 d = '<a  onclick="preview(\''+rowData.code+'\',\''
					               +rowData.jkzcode+'\',\''+rowData.name+'\',\''+rowData.sex+'\',\''
					               +rowData.age+'\',\''+rowData.profession+'\',\''+rowData.examtime+'\',\''
					               +rowData.pic+'\',\''+rowData.enterlogo+'\')">打印预览</a>';
					                      }
					                      return e+d;
				                      } 
		                    }
		            }
				]],//绑定数据列
				pagination:true,//是否分页
				pageSize:20,
				rownumbers:true,//显示行号
				toolbar: '#divQuery',//工具条
				onClickRow:function(rowIndex,rowData){
					
				}
		});
	}
	function camwin(id){
		parent.addTab('照  相','${ctx}toCam.action?id='+id,'mainFrameCamwin');
	}
	function preview(sfz,bh,xm,xb,nl,gz,ey,pic,zh){
			if(CreatePage(sfz,bh,xm,xb,nl,gz,ey,pic,zh)){
				LODOP.PREVIEW();		
			}else{
				$.messager.alert("提示","信息错误，请确认","error");
			}
	}
		
	function printDesign(){
		if(CreatePage('130101199011111111','20140730A0001','张三','男','25','建筑工程师','2014-5-6','file/userImg/test.jpg','file/enterlogo/testzh.jpg')){
			LODOP.PRINT_DESIGN();	
		}else{
			$.messager.alert("提示","信息错误，请确认","error");
		}
	}
	
	function CreatePage(sfz,bh,xm,xb,nl,gz,ey,pic,zh) {
			LODOP=getLodop();  
			LODOP.PRINT_INIT("健康证打印");
			//设置纸张大小
			//LODOP.SET_PRINT_PAGESIZE(2, 0,0,"");
			
			//背景图
			LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='<%=basePath %>file/jkz_bg.jpg'>");
			
			//设置打印项风格 
			LODOP.SET_PRINT_STYLE("FontName","宋体");
			LODOP.SET_PRINT_STYLE("FontSize","9");
			
			if(ey==""){
				return false;
			}
			var strs = ey.split("-");
			var yy = strs[0];
			var em = strs[1];
			var ed = strs[2];
			var ay = parseInt(yy)+1;
			var am = em;
			var ad = ed;
			//上边的字以及位置
			LODOP.ADD_PRINT_TEXT(128,150,179,26,""+sfz+"");
			LODOP.ADD_PRINT_TEXT(128,413,88,25,""+bh+"");
			
			LODOP.ADD_PRINT_TEXT(166,127,132,29,""+xm+"");
			LODOP.ADD_PRINT_TEXT(166,414,75,25,""+xb+"");
			
			LODOP.ADD_PRINT_TEXT(207,127,77,30,""+nl+"");
			LODOP.ADD_PRINT_TEXT(207,286,128,28,""+gz+"");

			LODOP.ADD_PRINT_TEXT(246,160,55,28,""+yy+"");
			LODOP.ADD_PRINT_TEXT(246,244,46,28,""+em+"");
			LODOP.ADD_PRINT_TEXT(246,316,41,28,""+ed+"");

			LODOP.ADD_PRINT_TEXT(285,160,55,28,""+ay+"");
			LODOP.ADD_PRINT_TEXT(285,244,46,28,""+am+"");
			LODOP.ADD_PRINT_TEXT(285,316,41,28,""+ad+"");
			//预览时，显示背景图
			LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);	
			//打印背景图
			LODOP.SET_SHOW_MODE("BKIMG_PRINT",1);
			//增加图片打印项 ADD_PRINT_IMAGE(Top,Left,Width,Height,strHtmlContent)
			//上边距，左边距，图片的宽度,图片的高度,(一是超文本代码内容；二是本地文件名内容；第三是WEB地址)
			//体检人照片
			if(pic != null &&pic !=""){
				console.info(pic);
				LODOP.ADD_PRINT_IMAGE(124 ,530 , 130, 185,"<img border='0' src='<%=basePath %>"+pic+"'>");	
			}else{
				console.error("照片不存在，请先照相！");
				return false;
			}
			//印章
			if(zh != null &&zh !=""){
				console.info(zh);
				LODOP.ADD_PRINT_IMAGE(277 ,374 , 152, 137,"<img border='0' src='<%=basePath %>"+zh+"'>");
			}else{
				console.error("单位公章没有设置！");
				return false;
			}
			//LODOP.SET_SHOW_MODE("BKIMG_LEFT",1);
			//LODOP.SET_SHOW_MODE("BKIMG_TOP",1);
		
			LODOP.SET_SHOW_MODE("BKIMG_WIDTH","183mm");
			//LODOP.SET_SHOW_MODE("BKIMG_HEIGHT","99mm"); //这句可不加，因宽高比例固定按原图的

			LODOP.SET_SHOW_MODE("PRINT_PAGE_PERCENT","Full-Width");
			return true;
		}
</script>
</head>
  
<body style="margin: 1px;">
<table id="dataTable" ></table>
<form id="form1" name="form1" action="" method="post" style="width: 99%" enctype="multipart/form-data">
</form>
	<div id="divQuery" style="padding-left:10px;height:70px;background-color:#F5F5DC;">
		<table style="font-size:12px;">
			<tr style="height:30px;">
				<td >姓名</td>
				<td>
					<input type="text" id="query1" name="query1" style="width:250px;height:25px;border:1px solid #e1e3e4;">
				</td>
				<td >乡镇单位</td>
				<td>
					<SELECT id="enterid" >
						<option value=""></option>
						<c:forEach items="${enterList}" var="enter" >
							<option value="${enter.id}">${enter.name}</option>
						</c:forEach>
					</SELECT>
				</td>
			</tr>
			<tr style="height:30px;">
				<td colspan="5">
					<a class="easyui-linkbutton" id="checkfilepass" onclick="loadList();">查    询</a>
				<!-- <a class="easyui-linkbutton" onclick="printDesign();"> 打印设计</a> -->	
				</td>
			</tr>
		</table>
	  </div>
</body>
</html>
