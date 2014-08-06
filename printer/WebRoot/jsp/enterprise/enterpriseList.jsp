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

	<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/JQuery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/JQuery/themes/bootstrap/linkbutton.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/JQuery/themes/icon.css">
	<script type="text/javascript" src="<%=basePath%>/js/JQuery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/JQuery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/JQuery/locale/easyui-lang-zh_CN.js"></script>
	<script>

	$(function() {
		loadList();
	});

	//加载
	function loadList() {
		
		var query1 = $("#query1").val();
		
		$("#dataTable").datagrid({
				width:document.body.clientWidth,
				striped : true,//表格条纹化
				loadMsg : '加载中...',//加载时显示的信息
				url : "enterpriseList.action",//数据源
				queryParams:{
					peoplename:query1
				},//参数
				remoteSort:false,//排序
				idField:'id',//主键字段
				fitColumns:true,//列自动适应大小
				singleSelect:true,//是否单选行，默认为多选行
				columns:[[
					{field:'entercode',title:'单位代号',width:100,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'name',title:'单位名称',width:100,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'address',title:'单位地址',width:250,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'linkperpon',title:'联系人',width:50,rowspan:2,sortable:true,
						sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'linkphone',title:'联系电话',width:100,rowspan:2,sortable:true,
					    sorter:function(a,b){
							return (a>b?1:-1);
						}
					},
					{field:'opt',title:'操作',width:200,align:'center',  
		                 		formatter:function(value,rowData,rowIndex){  
				                    if(rowData.uuid!=""){
					                      var e = '<a href="toEditEnterprise.action?id='+rowData.id+'"  >编辑</a>  ';  
					                      var d = '<a href="javascript:void(null)"  onclick="delemploy(\''+rowData.id+'\')">删除</a>';  
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
	function toAdd(){
		var formObj=document.getElementById("form1");
        formObj.action="enterpriseToAdd.action";
        formObj.submit(); 
	} 
	
	function delemploy(id){
		$.messager.confirm('确认', '确认要删除吗?', function(r){
			if (r){
				$.ajax({
					type:'POST',
					url:'deleteEnterprise.action?id='+id,
					success:function(data){
						$("#dataTable").datagrid("reload");
					}
				});
			}
		});
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
				<td >单位名称</td>
				<td>
					<input type="text" id="query1" name="query1" style="width:250px;height:25px;border:1px solid #e1e3e4;">
				</td>
			</tr>
			<tr style="height:30px;">
				<td colspan="5">
					<a class="easyui-linkbutton" id="checkfilepass" onclick="loadList();">查    询</a>
					<a class="easyui-linkbutton" id="add" onclick="toAdd();">新  增</a>
				</td>
			</tr>
		</table>
	  </div>
</body>
</html>
