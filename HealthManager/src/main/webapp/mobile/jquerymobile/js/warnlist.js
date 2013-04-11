$(document).bind("pageinit",function(){
	$.ajax({
		type : "get",
		url : "../../rest/alertmessages/",
		success : success,
		error : error,
		timeout : 5000
	});
	var menarray = new Array();
	function success(data){
		
		for(var i=0;i<data.length;i++){
			
			if(menarray.indexOf(data[i].memname) == -1){
				menarray.push(data[i].memname);
				
			}

		}
		
		//得到每个人报警邮件,短信的数量
		/*var alertnum = new Array(menarray.length);
		for(var i=0;i<menarray.length;i++){
			alertnum[i] = 0;
		}
		
		for(var j=0;j<menarray.length;j++){
			for(var i=0;i<data.length;i++){
				if(menarray[j] == data[i].memname){
					alertnum[j]++;
				}
			}
		}*/
		
	
		
		$("#warn").empty();
		for(var i=0;i<menarray.length;i++){
			
			var $div = $("<div data-role='collapsible' data-theme='b' data-content-theme='c' class='alertname'></div> ");
			
			var $h = $("<H3 class='h_member' extense='extense'></H3>");
			$("#warn").append($div);
	
			$div.append($h);
			$h.text(menarray[i]);
			
			
			
		}
		
		
		
		//通过预警人员查询
		
		$(".h_member").each(function(index){
			$(this).click(function(){
				var alert = ($(this).parent());
				$object = $(this);
				$("#warn").trigger("create");
				
				if($object.attr("extense")){
					
					ajaxTest($.proxy(ALERsuccess,alert),error,index);
					$object.removeAttr("extense");
					
					
				}else{
					$object.attr("extense","extense");
				}
				
			});
			$("#warn").trigger("create");
			
		});
		
		function ajaxTest(success,error,index){
			$.ajax({
				type : "get",
				url : "../../rest/alertmessages/key3/"+menarray[index],
				success : success,
				error : error,
				timeout : 5000
			});
		}
		
		
		function ALERsuccess(json){
			
			var ul = $("<ul data-role='listview' data-filter='true' data-filter-placeholder='Search ticker or firm name...' data-inset='true'></ul>");
			for(var i=0;i<json.length;i++){
				if(json[i].type ==2){
					json[i].type = "邮件";
				}else{
					json[i].type = "短信";
				}
				var li = $("<li data-filtertext='NASDAQ:ADBE Adobe Systems Incorporated'><a href='warnDetial.html?"+json[i].id+"' data-ajax='false'>"+json[i].type+"报警,   时间:"+json[i].joinTime+"</a></li>");
				ul.append(li);
			}
			
			var $this = this;
			var child = $($this.children()[1]);
			child.empty();
			child.append(ul).trigger("create");
			//$("ul").listview('refresh');
			
		}
		
		
	}
	
	
	
	function error(){
		console.log("出错啦");
	}
});