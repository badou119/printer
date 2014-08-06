
		
function loaddata(loadurl, success_func){
	$.ajax({  
        url: loadurl,
        type: "post", 
        dataType: "json", 
        success:function(data){
        	success_func(data); 
        }  
	})
}

function initAction(actionString)
{
	
		actionidarray = actionString.split(",");				
		for (i = 0; i < actionidarray.length; i++) {
		//if(actionidarray[i]!=null){
			actionid = actionidarray[i].trim();
			url = 'selectAct-selectInfo.action?actionid='+actionid;
			loaddata(url, addAction);	
					
		}
	
}

function addAction(rowData){
	if($("#"+rowData.actionid).length>0)
	{
		return false;
	}
	else
	{
		var styles="font-size:12px;height:25px;padding-left:5px;padding-top:5px;margin-left:5px;margin-top:5px;";
			styles+="border:1px solid #a3c25f; background-color:#cae296;float:left;";
		var texts="<span style='float:left;'>"+rowData.actionname+"</span>";
		var closeBtn="<img alt='取消' src='./images/close2.gif' onmouseover='mouseOverClose(this)' onmouseout='mouseOutClose(this)' onclick='delElement(\""+rowData.actionid+"\",\"action\")' />";
		var person="<div id='"+rowData.actionid+"' style='"+styles+"' >";
			person+=texts;
			person+="&nbsp;&nbsp;"+closeBtn+"&nbsp;";
			person+="</div> ";
		var personText=$("#action").val();
		if(personText=="")
		{
			$("#action").val(rowData.actionid);
		}
		else
		{
			$("#action").val(personText+","+rowData.actionid);
		}
		$("#divAction").append(person);
		//var htmls = $("#test").val();
		//$("#test").val(htmls+person);
	}
}

//删除已选的部门或人员
function delElement(id,controlId){
	$("#"+id).remove();
	var str=$("#"+controlId).val();
	var i=str.indexOf(id+',');
	if(i!=-1)
	{
		str=str.replace(id+',','');
		
	}
	else
	{
		str=str.replace(id,'');
	}
	var j=str.charCodeAt(str.length-1);
	if(j==44)
	{
		str=deleteLastChar(str,',');
	}
	$("#"+controlId).val(str);
}

//当鼠标经过人员或部门中的关闭按钮时
function mouseOverClose(id)
{
	$(id).css("border","1px solid gray");
}
//当鼠标离开人员或部门中的关闭按钮时
function mouseOutClose(id)
{
	$(id).css("border","none");
}

//移除最后的指定的字符
function deleteLastChar(str,c){
	var reg=new RegExp(c+"([^"+c+"]*?)$");
	return str.replace(reg,function(w){if(w.length>1){return w.substring(1);}else{return "";}});
}
