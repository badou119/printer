document.write("<style type='text/css'>"); 
document.write(".div_RightMenu{ position:absolute; list-style:none;width:100px; font-size:12px;}"); 
document.write(".div_RightMenu div{background:#bbb;position:relative;}"); 
document.write(".div_RightMenu ul{position:relative;background:#fff; border:1px solid #999;left:-2px;top:-2px; margin:0; padding:1px 0;}");
document.write(".div_RightMenu ul li{ list-style:none; margin:0 1px;padding:0;line-height:20px;height:20px;text-indent:10px; background-repeat:no-repeat;cursor:pointer;border-width:1px;border-style:solid;border-color:#fff;}"); 
document.write(".div_RightMenu ul li.RM_mouseover{ background-color:#B6BDD2; border-color:#0A246A;}"); 
document.write("</style>");   
$(function(){ 
	document.oncontextmenu=function(){return false;}
	//屏蔽右键
	document.onmousemove=mouseMove;
	//记录鼠标位置
}); 
var mx=0,my=0; 
function  mouseMove(ev){Ev=ev||window.event;var mousePos=mouseCoords(Ev);mx=mousePos.x;my=mousePos.y;} 
function mouseCoords(ev){ 
	if(ev.pageX||ev.pageY){
		return{x:ev.pageX,y:ev.pageY};
	} 
	return{x:ev.clientX,y:ev.clientY+$(document).scrollTop()}; 
}   
$.fn.extend({RightMenu: function(id,options){
		options = $.extend({menuList:[]},options);
		var menuCount=options.menuList.length; 
		if (!$("#"+id)[0]){ 
			var divMenuList="<div id=\""+id+"\" class=\"div_RightMenu\"><div><ul class='ico'>"; 
			for(var i=0;i<menuCount;i++){ 
				divMenuList+="<li class=\"RMli_"+options.menuList[i].menuclass+"\" onclick=\""+options.menuList[i].clickEvent+"\">"+options.menuList[i].menuName+"</li>"; 
			} 
			divMenuList += "</ul></div><div>"; 
			$("body").append(divMenuList).find("#"+id).hide().find("li").bind("mouseover",function(){
					$(this).addClass("RM_mouseover");
				}).bind("mouseout",function(){
					$(this).removeClass("RM_mouseover");
				}); 
			$(document).click(function(){
				$("#"+id).hide();
			});
		} 
		return this.each(function(){ 
			this.oncontextmenu=function(){ 
				var mw=$('body').width(),
					mhh=$('html').height(),
					mbh=$('body').height(), 
					w=$('#'+id).width(),
					h=$('#'+id).height(), 
					mh=(mhh>mbh)?mhh:mbh;
				//最大高度 比较html与body的高度 
				if(mh<h+my){my=my-h;}
				//超 高
				if(mw<w+mx){mx=mx-w;}
				//超 宽 
				$("#"+id).hide().css({top:my,left:mx}).show(); 
			}
		});     
	} 
});
