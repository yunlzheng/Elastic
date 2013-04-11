$(document).bind("pageinit",function(){
	var url = window.location.search;
	var id = url.substring(url.indexOf('?')+1,url.length);

	$.ajax({
		type : "get",
		url : "../../rest/members/"+id,
		success : success,
		error : error,
		timeout : 5000
	});
	
	function success(data){
		$("#code").val(id);
		$("#creator").val(data.creater);
		$("#creat_time").val(data.joinTime);
		$("#email").val(data.email);
		$("#phone").val(data.tell);
		$("h1").text(data.name+"用户详细信息"); 
	}
	
	function error(){
		console.log("error");
	}
	
	
	//alertmember
	$.ajax({
		type : "get",
		url : "../../rest/alertmembers/",
		success : alertsuccess,
		error : error,
		timeout : 5000
	})
	
	function alertsuccess(json){
		
		for(var i=0;i<json.length;i++){
			
			if(json[i].mid == id){
				$("#ip").val(json[i].ip);
				$("#alertemail").val(json[i].email);
				$("#alertphone").val(json[i].tele);
			}
		}
		
	}
	
	
	
});
















