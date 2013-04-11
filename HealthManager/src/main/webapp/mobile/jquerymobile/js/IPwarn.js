$(document).bind("pageinit",function(){
	var url = window.location.search;
	var ip = url.substring(url.lastIndexOf(':')+1,url.length);
	console.log(ip);
	var offset=0;
	var len = 10;
	var dataLength=0;
	
	function ajax(success,offset,len,error){
		$.ajax({
			type : "get",
			url : "../../rest/alertmessages/"+offset+"/"+len+"?eqs=ip:"+ip,
			success : success,
			error : error,
			timeout : 5000,
			dataType:'json'
		});
	}
	
	getDatalen(ip);

	ajax(success,offset,len,error);
	
	//取数据的长度
	function getDatalen(ip){
		$.ajax({
			type : "get",
			url : "../../rest/alertmessages/key1/"+ip,
			success : sucGetLen,
			error : error,
			timeout : 5000
		});
	}
	
	function sucGetLen(json){
		dataLength = json.length;
		console.log(dataLength);
	}
	
	
	
	function success(data, textStatus, jqXHR){
			console.log(jqXHR.responseText.length);
			var li0 = "<li data-role='listdivider' data-theme='f'>报警信息</li>";
			
			$("#Ipwarnlist").empty().append(li);
			$("#Ipwarnlist").append(li0);
			
		
			
			for(var i=0;i<data.length;i++){
				if(data[i].type == 2){
					data[i].type = "邮件";
				}else{
					data[i].type = "短信";
				}
				var li = $("<li data-filtertext=''><a href='Dialog.html'  class='roll' data-rel='dialog' data-transition='pop'>报警人员:"+data[i].memname+",报警方式:"+data[i].type+"</a></li>");
				$("#Ipwarnlist").append(li);
				
				//dialog显示详细信息
				$("#IPdetial").text(data[i].message);
			}
				
			$("#Ipwarnlist").trigger("create");
			$("#Ipwarnlist").listview('refresh');
			
			
	   }
	
	$("#more").bind("click",function(){
		
		len +=10;
		console.log(len);
		ajax(success,offset,len,error);
		
		if(len >= dataLength){
			$("#more").text("没有数据了");
		}
	});
	
	
	
	
	
	
	
	
	
	
	
	function error(){
		console.log("出错啦");
	}
});

