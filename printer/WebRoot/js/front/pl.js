String.prototype.trim = function(){ return this.replace(/(^\s*)|(\s*$)/g, "");}
function chk(frm){
	if(frm.Content.value.trim()=="")
	{
		alert("请输入评论内容，谢谢！");
		frm.Content.focus();
		return false;
	}
	if(frm.Content.value.trim().length>250)
	{
		alert("评论内容不能超过250个字符，谢谢！");
		frm.Content.focus();
		return false;
	}
	var userId=$('#userId').val();
	if(userId==null||userId==""){
			loginform($('#contentNews').val());
		return false;
	}
	frm.bntSubmit.disabled = true;
	frm.submit();
}

function loginform(contentid){
	$('#login_dlg').show();
	$('#login_form').form('load',{"contentId":contentid});
}

function userLogin(){
	var f=serializeObject($('#login_form').form());
	if(f.Name==null||f.Name==""){
		$.messager.alert('错误','请输入账号','warning');
		return false;
	}
	if(f.Password==null||f.Password==""){
		$.messager.alert('错误','请输入密码','warning');
		return false;
	}
	else{
		$.post('weblogin.action', serializeObject($('#login_form').form()),
			function(r) {
				$('#login_form').form('clear');
		//		$('#login_dlg').dialog('close');
				$('#login_dlg').hide();
				
				$.messager.show({
					title : '提示',
					msg : r.msg
				});
				 window.setTimeout("window.location.reload()",2000);
		//		window.location.reload();
			}, 'json');
	}
}
var i=0;
function addrefer(commentId,name,referbody){
	var referid=$('#refersId').val();
	referid=referid+commentId+",";
	var tb=document.getElementById("refer_tb");
	var row=tb.insertRow(tb.rows.length);
	var cell=row.insertCell(row.cells.length);
    cell.innerHTML="<div class=\"content_refer\"><div class=\"refer_title\">引用自["+name+"]的评论:</div><div class=\"refer_body\">"+referbody+"</div></div>";
	$('#refersId').val(referid);
	
}
//序列化表单
function serializeObject(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
}
