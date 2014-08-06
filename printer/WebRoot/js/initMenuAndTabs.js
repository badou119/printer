//初始化左侧菜单
function InitMenu() {
    $(".easyui-accordion").empty();
    var menulist = "";
    //循环加载菜单
    $.each(_menus.menus, function(i, n) {    
		if (n){
	        menulist += '<div title="'+n.menuname+'"  icon="'+n.icon+'" style="overflow:auto;">';
			menulist += '<ul>';
	        $.each(n.menus, function(index, o) {			
				if (o) {
					menulist += '<li><div><a target="mainFrame'+o.menuid+'" href="' + o.url + '" >'+
								'<span class="icon '+o.icon+'" ></span>' + o.menuname + '</a></div></li> ';
				}
	        })
	        menulist += '</ul></div>';
		}
    })
	$(".easyui-accordion").append(menulist);
	//给菜单加载一个点击事件
	$('.easyui-accordion li a').click(function(){
		var tabTitle = $(this).text();
		var url = $(this).attr("href");
		var target = $(this).attr("target");
		addTab(tabTitle,url,target);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	//$(".easyui-accordion").accordion();
	
	$('#editpass').click(function(){
		var tabTitle = $(this).text();
		var url = $(this).attr("href");
		var target = $(this).attr("target");
		addTab(tabTitle,url,target);
		
	});
	$('#updateLog').click(function(){
		var tabTitle = $(this).text();
		var url = $(this).attr("href");
		var target = $(this).attr("target");
		addTab(tabTitle,url,target);
		
	});
}
//新建或选中Tab标签
function addTab(subtitle,url,target){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url,target),
			closable:true,
			width:$('#mainPanle').width()-10,
			height:$('#mainPanle').height()-26
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		var selectedTab =  $('#tabs').tabs('getSelected');  
		$('#tabs').tabs('update',{
			tab: selectedTab,
			options: {
				content:createFrame(url,target)
			}
		});
	}
	
	loadOnDbClickForTab();
}
//创建IFrame
function createFrame(url,target)
{
	var s = '<iframe name="'+target+'" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}
//为Tab标签加载一个双击事件
function loadOnDbClickForTab()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children("span").text();
		$('#tabs').tabs('close',subtitle);
	})
}

//加载时钟
function clockon() {
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth();
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    var week;
    month = month + 1;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    if (hour < 10) hour = "0" + hour;
    if (minu < 10) minu = "0" + minu;
    if (sec < 10) sec = "0" + sec;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var time = "";
    time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;

    $("#bgclock").html(time);

    var timer = setTimeout("clockon()", 200);
}
