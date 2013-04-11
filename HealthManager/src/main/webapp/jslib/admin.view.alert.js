$(document).ready(function() {
	$.ajaxSetup({cache:false});
	/**
	 * 滚动加载
	 * */
	$(window).scroll(function(){
		
		var $body = $("body");
		if(($(window).height()+$(window).scrollTop())>=$body.height()){
			
			setTimeout(function(){
				
				$("#mode").empty().html("<img src='../assets/images/ajax-loader.gif'/><span>数据加载中...</span>");
				$("#more").click();
	
			},500);
			
		}
		
	});
	
	Highcharts.getOptions().colors = $.map(Highcharts.getOptions().colors, function(color) {
	    return {
	        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
	        stops: [
	            [0, color],
	            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
	        ]
	    };
	});
	
	chartData(chartSuccess,error);
	
	function chartSuccess(data){
		
		var mailnum = data.mailNum;
		var smsnum = data.smsNum;
		charthandle(mailnum,smsnum);
		
	}
		
		
		
	   function success(data){
		  
		  //日期选择
		  var date = new Date();
		  var start_date = date.getMonth()+"/" + date.getDay() + "/" + date.getFullYear();
		  
		 var start = new  choosedate("time_start",start_date,data);
		 start.init();
		 start.datepicker();
		 
		 var end = new choosedate("time_end",start_date,data);
		 end.init();
		 end.datepicker();
		 
		 $("html,body").animate({scrollTop:$("body").height()},2000);
		 
	   }
	   
	   
	  
	   function error(){
		   //console.log("error");
	   }
	   
	   
	  
		  var displayer = function(){
			  
			   var _value = $("#typeselect").attr("value");
			   //console.log("value:"+_value);
			   getAll($.proxy(getAllSuccess,{"id":_value}),error,_value);
			   
			   $("#more").bind("click",function(){
				   getAll($.proxy(getAllSuccess,{"id":_value}),error,_value);
			   });
			   
			   $("#typeselect").change(function(){
				   
				   $("#body_content").empty();
				   $("#more").empty().html('<img src="../assets/images/ajax-loader-bigger.gif"/>');
				   $("#more").unbind("click");
				   options.offset = 0;
				   var nid = $(this).attr("value");
				   //console.log("change value:"+nid);
				   getAll($.proxy(getAllSuccess,{"id":nid}),error,nid);
				   $("#more").bind("click",function(){
					   
					   getAll($.proxy(getAllSuccess,{"id":nid}),error,nid);
					   
				   });
				   
			   });
			   

		   };
		   
		   displayer();
	 
	   
	   function getAllSuccess(data){
		   var id = this.id;
		   if(data.length < options.length && data.length != 0){
			   if(id == "display_by_emails"){
				   displaybycondition(data,id,options.offset);
			   }else if(id == "display_by_phones"){
				   displaybycondition(data,id,options.offset);
			   }else if(id=="display_all"){
				   displaybycondition(data,id,options.offset);
			   }
			   options.offset = options.offset + data.length;
			   $("#more").text("没有更多数据了");
		   }else if(data.length == 0){
			   $("#more").text("没有更多数据了");
		   }else{
			   if(id == "display_by_emails"){
				   displaybycondition(data,id,options.offset);
			   }else if(id == "display_by_phones"){
				   displaybycondition(data,id,options.offset);
			   }else if(id=="display_all"){
				   displaybycondition(data,id,options.offset);
			   }
			   options.offset = options.offset+options.length;
		   }
		   
		   
		
			   
		   
		   
	   }
	   

		/**查看异常详细信息相关操作*/
		$("a[excId]").live("click",function(){
			
			var excId = $(this).attr("excId");
			showExcDetail(excId,showExcDetailSuccess,showExcDetailError);
			
		});
		
		function showExcDetail(id,success,error){
			
			var url = "../rest/alertmessages/"+id;
			
			$.ajax({

				type : "GET",
				url : url,
				success : success,
				error : error,
				timeout : 3000

			});
			
			blockParent();
			art.dialog({
			    content: document.getElementById('excDetailDialog'),
		    	title:'详情',
				lock:true,
				top:'150px',
				width:'400px',
				height:'170px',
				okValue:'确认',
				ok:function(){
					
				},
				close:function(){
					unblockParent();
					$("#excDetailDialog").find(".loading").show();
					$("#excDetailDialog").find(".information").empty().hide();
				},
				time:0
			});		
			
			
		}
		
		function showExcDetailSuccess(data){

			var info = EXCVIEW.detail
				.replace(/{identifiekey}/g,data["id"])
				.replace(/{startTime}/g,data["joinTime"])
				.replace(/{address}/g,data["key"])
				.replace(/{username}/g,data["memname"])
				.replace(/{message}/g,data["message"]);
			setTimeout(function(){
				
				$("#excDetailDialog").find(".loading").hide();
				$("#excDetailDialog").find(".information").empty().append(info).fadeIn(500);
				
			},1000);
			
		}
		function showExcDetailError(){
			
		}
		
		var EXCVIEW = {
				
				detail:
					'<div class="item"><label>发生时间:</label><span>{startTime}</span></div>'+
					'<div class="item"><label>IP地址:</label><span>{address}</span></div>'+
					'<div class="item"><label>用户名称:</label><span>{username}</span></div>'+
					'<div class="item"><label>详细信息:</label><p>{message}</p></div>'
				
		}
	   
});

