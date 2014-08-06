<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
   
    <link rel="shortcut icon" href="images/ico.ico" type="image/vnd.microsoft.icon">
	<link rel="icon" href="images/icon.png" type="image/vnd.microsoft.icon">
    
    <title></title>
    <META http-equiv=Content-Type content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="">
	
	<link rel="stylesheet" type="text/css" href="js/JQuery/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="js/JQuery/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="css/coin-slider-styles.css" />
    
    <script type="text/javascript" src="js/JQuery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="js/coin-slider.min.js"></script>
    <script type="text/javascript" src="js/jquery.placeholder1.js"></script>
	<script type="text/javascript" src="js/JQuery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/JQuery/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	
    <style type="text/css">
		html, body, div, p, span, a, strong, form, table, th, td, ul, li, ol, dl, dt, dd, h1, h2, h3, h4, h5, h6, fieldset, iframe, object, pre, img, b, i
        {
            border: 0 none;
            margin: 0;
            outline: 0 none;
            padding: 0;
        }
        body, table, input, textarea, button, select
        {
            color: #666666;
            font-family: "微软雅黑";
            font-size: 12px;
        }
        a
        {
            color: #666666;
            text-decoration: none;
        }
        .header
        {
            height: 65px;
            min-width: 980px; /*  overflow: hidden;*/
            width: 100%;
            background-color: #FFFFFF;
            position: relative;
            z-index: 1000;
            border-bottom: 1px solid #A9912B;
            
        }
        .h-b
        {
            height: 100%;
            margin: auto;
            width: 980px;
        }
        .logo
        {
            float: left;
            margin-right: 4px;
        }
        .logo1
        {
            float: left;
        }
        .logo2
        {
            float: left;
            margin-right: 20px;
        }
        
        .img-ecm-logo1
        {
            display: block;
            width: 205px;
            height: 57px;
            background: transparent url('images/logo1.png') no-repeat;
            _background: transparent url('images/logo1.png') no-repeat; /*** IE6 css hack ***/
        }
        .img-ecm-logo2
        {
            display: block;
            width: 175px;
            height: 36px;
            background: transparent url('images/logo2.png') no-repeat;
            _background: transparent url('images/logo2.png') no-repeat; /*** IE6 css hack ***/
        }
         .img-ecm-logo3
        {
            display: block;
            width: 236px;
            height: 90px;
            background: transparent url('images/logo3.png') no-repeat;
            _background: transparent url('images/logo3.png') no-repeat; /*** IE6 css hack ***/
        }
        #ecm-menu
        {
            float: left;
        }
        ul#nav
        {
            margin: 0 auto;
            text-align: center;
            overflow: hidden;
        }
        ul#nav li
        {
            float: left;
            list-style: none;
        }
        ul#nav li a
        {
            display: block;
            width: 100px; /*width: 100px; height: 70px;*/
            padding: 30px 10px 0 10px;
            margin: 0;
            font: bold 18px Helvetica, Arial, Sans-Serif;
            text-transform: uppercase; /*color: #9c5959;*/
            color: #3290C3;
            text-shadow: 0 1px 3px #c4bda6;
            text-decoration: none;
        }
        ul#nav li a:hover
        {
            color: #045496; /*非IE背景藍色*/
            text-shadow: 0 2px 3px #4c2222;
        }
        /*
        	中间图片切换部分样式
        */
        #wrapper
        {
            background: url('images/bodyback.png');
            margin: 0;
            z-index: 100000;
        }
        #login, #download
        {
            position: absolute;
            z-index: 99999;
        }
       
        #download
        {
            color: White;
            font-size: 12px;
            top: 175px;
            line-height: 20px;
        }
        #download .android
        {
            background: url("images/down.png") no-repeat scroll 0 0 transparent;
            height: 83px;
            width: 234px;
            margin-top: 3px;
        }
        #download .login
        {
            background: url("images/content-page3_05.png") no-repeat scroll 0 0 transparent;
            height: 46px;
            width: 155px;
            margin-top: 3px;
        }
        #download .btns .iphone, #download .btns .ipad, #download
        {
            background: url("/resource/img/index/download-btns.png") no-repeat scroll 0 0 transparent;
            height: 30px;
            margin-top: 15px;
        }
        #download .btns a
        {
            display: block;
            float: left;
        }
        
        #login
        {
            background: url("images/login-bg.png") no-repeat scroll 0 0 transparent;
            height: 328px;
            padding-left: 0px;
            right: -180px;
            top: 75px;
            width: 364px;
        }
        #login a:link, a:visited
        {
            color: #0088CC;
            text-decoration: none;
        }
        #login a:hover, a:active
        {
            color: #599100;
            text-decoration: underline;
        }
        
        #login h1
        {
            color: #0953A6;
            font: 20px "Microsoft Yahei" , "微软雅黑" ,SimSun,sans-serif;
            margin-top: 28px;
        }
        #login .login-item input
        {
            background: url("images/point.gif") repeat scroll 0 0 transparent;
            border: medium none;
            font-size: 16px;
            left: 10px;
            padding: 1px 0;
            position: absolute;
            top: 3px;
            width: 210px;
            z-index: 20;
        }
        #login .input-bg-focus, #login .input-bg
        {
            background: url("images/input-bg.png") no-repeat scroll 0 0 transparent;
            display: block;
            border-color:black;
            height: 32px;
            margin-bottom: 10px;
            padding: 1px;
            position: relative;
            width: 272px;
        }
        #login .input-hover
        {
            background-position: 0 -41px;
        }
        #login .input-focus
        {
            background-position: 0 -82px;
        }
        #login .btn-login
        {
            background-color: Green;
            background: url("images/btns-login.png") no-repeat scroll 0 0 transparent;
            border: medium none;
            cursor: pointer;
            height: 43px;
            overflow: hidden;
            width: 272px;
        }
        #login .btn-login-onfucs
        {
            background-color: Green;
            background: url("images/btns-login-hove.png") no-repeat scroll 0 0 transparent;
            border: medium none;
            cursor: pointer;
            height: 43px;
            overflow: hidden;
            width: 272px;
        }
        #login .login-error
        {
            background: url("images/error.png") no-repeat scroll left center transparent;
            color: red;
            height: 20px;
            line-height: 20px;
            padding-left: 20px;
        }
        #login .regist
        {
            border-top: 1px dotted #CCCCCC;
            color: #999999;
            left: 36px;
            padding-top: 10px;
            position: absolute;
            top: 257px;
            width: 240px;
        }        
    </style>
<script type="text/javascript">
	if (self != top) {
		window.top.location.href = "login.jsp";
	}
	$(document).ready(function() { 
			$('#games').coinslider( {
				width : 825,
				height : 435,
				hoverPause : true,
				links : false,
				effect : 'rain',
				navigation : false
			});
		});
</script>

<script type="text/javascript">
   
	$(function(){
	    $(document).keydown(function() {
		   if (event.keyCode == 13 ) {
		       login();
		   }
		});
		var checkname="${check}";
		if (checkname!=""){
			    var checkbox=document.getElementById("rePassWord");
				checkbox.checked=true;
		}
        var obj = document.getElementById("lpassword");
		if (obj.value != ""){
		    document.getElementById("lpasswordNone").style.display = "none";
			document.getElementById("lpassword").style.display = "";
		}
	
		$("#loginSubmit").click(function() {
			login();
		});
		
		if(!CookieEnable())
	    {
	        document.getElementById("cookiediv").style.display="block";
	    }
		
	});

	function login(){
		    if ($("#loginAccount").val() == ""||$("#loginAccount").val()=="账号：用户名") {
				$.messager.alert("操作提示","用户名不能为空","warning");
				return;
			}
			if ($("#lpassword").val() == "") {
				$.messager.alert("操作提示","密码不能为空","warning");
				return;
			}
			$.ajax( {
				type : "POST",
				url : "login.action",
				data : $("#form1").formSerialize(),
				beforeSend : function(XMLHttpRequest) {
				},
				success : function(data) {
					var json = eval(arguments[2].responseText);
					
					if (json[0].result == "ok") {
					    window.location = "index.jsp";
					} else if(json[0].result == "actionerror"){
						//$.messager.alert("操作提示","您的账号尚未被分配权限，请与管理员联系！","warning");
						alert("您的账号尚未被分配权限，请与管理员联系！");
					} else{
						//$.messager.alert("操作提示","用户名或密码错误！","warning");
						alert("用户名或密码错误！");
					}
				}
		    });
	}
</script> 
<script type="text/javascript">
     function CookieEnable(){
	       var result=false;
	       if(navigator.cookiesEnabled)
	         return true;
	       document.cookie = "testcookie=yes;";
	       var cookieSet = document.cookie;
	       if (cookieSet.indexOf("testcookie=yes") > -1)
	         result=true;
	       document.cookie = "";
	       return result;
     }
	function changeType(){
		document.getElementById("lpasswordNone").style.display = "none";
		document.getElementById("lpassword").style.display = "";
		document.getElementById("lpassword").focus();
	}
	function changeTypeNone(){
		var obj = document.getElementById("lpassword");
		if (obj.value == "") {
			document.getElementById("lpasswordNone").style.display = "";
			document.getElementById("lpassword").style.display = "none";
		}
	}
</script>

</head>
<body >
 <form id="form1" action="login.action" method="post" enctype="multipart/form-data">
    <div class="header" align="center">
        <div class="h-b" align="center">
            <a class="logo1" href="#" ><span class="img-ecm-logo1" style="margin-top: 0px"></span></a>
            <a class="logo2" href="#" ><span class="img-ecm-logo2" style="margin-top: 10px"></span></a>
           <!--  <div id="ecm-menu">
                <ul id="nav"  >
                    <li><a href="#">首页</a></li>
                </ul>
            </div>
             -->	
        </div>
    </div>
    
    <div id="wrapper" align="center">
        <div id="games" align="left">
             <a href="#" >
				<img src="./images/logobg3.jpg" alt="" />
			 </a>
			 <a href="#">
				<img src="./images/logobg4.jpg" alt="" />
			 </a>
            <div id="login" align="center"  style="border-radius:5px;">
            
                <div id="cookiediv" style="margin-top:55px;display: none;padding:6px; border:1px solid #FADC80; line-height:1.5; color:black; background:#FFF9E3;">你的浏览器不支持或已经禁止使用Cookie，导致无法记住账号和密码。
                </div>
                <div class="login-item" style="margin-top: 70px;">
                    <span class="input-bg-focus">
         				<input type="text" maxlength="100" id="loginAccount" tabindex="1" class="ipt tipinput1" autocomplete="off" name="username" placeholder="账号：用户名" value="${name}"/>
                    </span>
                </div>
                <div class="login-item">
                   <span class="input-bg">
                   <input  type="text" maxlength="20" id="lpasswordNone" tabindex="2" class="ipt tipinput1" autocomplete="off" name="pwdNone" value="密码"  onfocus="changeType();" style="display:" />
                        <input  type="password" maxlength="20" id="lpassword" tabindex="2" class="ipt tipinput1" autocomplete="off" name="password" style="display:none" onblur="changeTypeNone();" value="${pass}"/>
                   </span>
                </div>
                <div class="submit" align="center">
                    <div style="width: 272px;" align="right">
                    
                       <label style="color: #1E1E1E;margin-left: 35px" for="rePassWord">记住账号和密码</label>                                              
                       <input type="checkbox" id="rePassWord" name="rePassWord"  />
                   	<%--
                   	<label style="color: #1E1E1E;margin-left: 35px" ></label>   --%>                  
                   	</div>
                </div>
                    <div style="width: 272px;margin-top:20px;" align="center">
                        <input type="button"  name="loginSubmit" value="" id="loginSubmit" 
                        class="btn-login"   onmouseover="this.className='btn-login-onfucs'" onmouseout="this.className='btn-login'" />
                    </div>
                    
                    <div class="no-account" style=" margin-top: 4px; padding-top: 4px; width:272px;color: #1E1E1E;" align="right">
                    </div>
                    <div>
                        <div class="" id="error_tips">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <div id="divBottom" style="width:100%;color:gray;text-align: center;">
    	Copyright © 2014    版权所有
    </div>
    </form>
</body>
</html>
