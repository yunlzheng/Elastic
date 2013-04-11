$(document).bind("pageinit",function(){
	$.ajax({
		type : "get",
		url : "../../rest/members/key1/guest@guest.com",
		success : success,
		error : error,
		timeout : 5000
	});
	
	function success(data){
		var li0 = "<li data-role='listdivider' data-theme='f'>监控人员</li>"
		
		$("#peolist").empty().append(li);
		$("#peolist").append(li0);
		for(var i=0;i<data.length;i++){
			var li = $("<li data-filtertext=''><a href='detialPeople.html?"+data[i].id+"' data-ajax='false'>"+data[i].name+"</a></li>")
			$("#peolist").append(li);
		}
		
		$("#peolist").trigger("create");
		$("#peolist").listview('refresh')
	}
	
	function error(){
		console.log("error");
	}
});