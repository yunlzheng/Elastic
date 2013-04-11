$(document).bind("pageinit",function(){
	var url = window.location.search;
	var id = url.substring(url.indexOf('?')+1,url.length);
	
	$.ajax({
		type : "get",
		url : "../../rest/alertmessages/"+id,
		success : success,
		error : error,
		timeout : 5000
	});
	
	function success(data){
		console.log(data.ip);
		$("#ip").val(data.ip);
		$("#joinTime").val(data.joinTime);
		$("#mamname").val(data.memname);
		$("#message").val(data.message);
		$("#username").val(data.username);
		
		if(data.type == 2){
			$("#type").val("邮件");
		}else{
			$("#type").val("短信");
		}
	}
	
	function error(){
		console.log("出错啦");
	}
});