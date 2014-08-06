

function $F(id){
	return document.getElementById(id).value;
}
function getXhr() {
	var xhr = null;
	if (window.XMLHttpRequest) {
					
		xhr = new XMLHttpRequest();
	} else {
					
		xhr = new ActiveXObject("Microsoft.XMLHttp");
	}
	return xhr;
}

