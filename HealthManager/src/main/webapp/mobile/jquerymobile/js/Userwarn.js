$(document).bind("pageinit",function(){
	var url = window.location.search;
	var name = url.substring(url.indexOf('?')+1,url.length);
	console.log(name);
	var offset=0;
	var len = 10;
	var dataLength=0;
	function ajax(success,offset,len,error){
		$.ajax({
			type : "get",
			url : "../../rest/alertmessages/"+offset+"/"+len+"?eqs=memname:"+name,
			success : success,
			error : error,
			timeout : 5000
		});
	}
	
	
	//取数据的长度
	function getDatalen(name){
		$.ajax({
			type : "get",
			url : "../../rest/alertmessages/key3/"+name,
			success : sucGetLen,
			error : error,
			timeout : 5000
		});
	}
	getDatalen(name);
	function sucGetLen(json){
		dataLength = json.length;
		console.log(dataLength);
	}
	
	
	
	ajax(success,offset,len,error);
	
	function success(data){
			
			    var li0 = "<li data-role='listdivider' data-theme='f'>报警信息</li>"
			    //var button = $("<a href='warnTab.html' data-ajax='false' data-role='button' data-theme='b'>返回</a>")	;
				$("#Userwarnlist").empty().append(li);
				$("#Userwarnlist").append(li0);
				for(var i=0;i<data.length;i++){
					if(data[i].type == 2){
						data[i].type = "邮件";
					}else{
						data[i].type = "短信";
					}
					var li = $("<li data-filtertext=''><a href='Dialog.html' class='roll' data-rel='dialog' data-transition='pop' >报警人员:"+data[i].memname+",报警方式:"+data[i].type+"</a></li>");
					$("#Userwarnlist").append(li);
					
					//dialog显示详细信息
					$("#Userdetial").text(data[i].message);
				}
				
				
				//$(".content-primary").append(button).trigger("create");
				$("#Userwarnlist").trigger("create");
				$("#Userwarnlist").listview('refresh');
				
				
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
		console.log("呵呵！出错了");
	}
});