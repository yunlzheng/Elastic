$(document).bind("pageinit",function(){
	//添加select自定义样式
	//$.mobile.selectmenu.prototype.options.nativeMenu = false;
	function ajaxFun(success,error){
		$.ajax({
			type : "get",
			url : "../../rest/alertmessages/",
			success : success,
			error : error,
			timeout : 5000
		});
	}
	

	
	$(function(){
		$("#server").click();
	});
	
	
	
	$("#username").bind("click",function(){
		
		ajaxFun(successUser,error);
		
		var menarray = new Array();
		function successUser(data){
			
			for(var i=0;i<data.length;i++){
				
				if(menarray.indexOf(data[i].memname) == -1){
					menarray.push(data[i].memname);
					
				}

			}
		
			$("#menmane").empty();
			$("#selectItem").empty();
			
			var li0 = "<li data-role='listdivider' data-theme='b'>报警人员</li>"
			$("#menmane").empty().append(li);
			$("#menmane").append(li0);
			for(var i=0;i<menarray.length;i++){
				
				var li = $("<li data-filtertext=''><a href='Userwarn.html?"+menarray[i]+"' data-ajax='false'>"+menarray[i]+"</a></li>")
				
				$("#menmane").append(li);
				
				
			}
			$("#menmane").trigger("create");
			$("#menmane").listview('refresh')
		}
		
		
		
	});
	
	$("#server").bind("click",function(){
		ajaxFun(successServer,error);
		
		var IParray = new Array();
		function successServer(data){
			for(var i=0;i<data.length;i++){
				if(IParray.indexOf(data[i].ip) == -1){
					IParray.push(data[i].ip);
				}
			}
			
			
			$("#selectItem").empty();
			$("#menmane").empty();
			var $label = $("<label for='selectchoice' class='select'>请选择对应的IP查询:</label>");
			var $select = $("<select name='selectchoice' id='selectchoice'></select>");
			var button = $("<a href='#' data-ajax='false' data-role='button' data-theme='b' id='search'>查询</a>")
			for(var i=0;i<IParray.length;i++){
				var option = $("<option value='"+IParray[i]+"'>IP:"+IParray[i]+"</option>");
				$select.append(option);
			}
			
			
			$("#selectItem").append($label);
			$("#selectItem").append($select);
			$("#selectItem").append(button).trigger("create");
			
			
			var link;
			var url;
			$("#selectchoice").bind('change',function(){
				url = $(this).children("option:selected").text();
				link = "IPwarn.html?"+url;
			});
			
			$('#selectchoice').selectmenu('refresh');
			
			url = $("#selectchoice").children("option:selected").text();
			link = "IPwarn.html?"+url;
			
			$("#search").bind("click",function(){
				$(this).attr("href",link);
			});
			
			//给label添加css样式
			$(".select").css("font-weight","bold");
		}	
		
	});
	
	
	
	
	function error(){
		console.log("出错啦");
	}
	
	
});