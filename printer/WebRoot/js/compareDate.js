var convertStr=function(s){
	var dt=s.split(/ /);
	var d=dt[0].split(/-/);
	var t;
	if(dt[1]){
		t=dt[1].split(/:/);
		t.push(0);
		t.push(0);
	}else{
		t=[0,0,0];
	}
	return new Date(d[0],d[1]-1,d[2],t[0],t[1],t[2]);
};
var convertDate=function(d){
	var Y=d.getFullYear();
	var M=d.getMonth()+1;
	(M<10)&&(M='0'+M);
	var D=d.getDate();
	(D<10)&&(D='0'+D);
	var h=d.getHours();
	(h<10)&&(h='0'+h);
	var m=d.getMinutes();
	(m<10)&&(m='0'+m);
	var s=d.getSeconds();
	(s<10)&&(s='0'+s);
	return Y+'-'+M+'-'+D+' '+h+':'+m+':'+s;
};