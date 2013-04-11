

$(document).ready(
		function() {
			
			  var data_result = new Array();
			
				Highcharts.getOptions().colors = $.map(Highcharts.getOptions().colors, function(color) {
				    return {
				        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
				        stops: [
				            [0, color],
				            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
				        ]
				    };
				});
			  
			   function success(data){
				  
				   data_result = data_result.concat(data);
				   
				   var email_arr = new Array();  //创建多个数组 分别保存对应用户的邮件警告记录总数   电话警告总数  和 各自对应的名字 
				   var phone_arr = new Array();
				   var name_arr = new Array();
				   var memname = new Array();
				   
				   for(var i = 0 ; i < data_result.length; i++){
					   
					   var alertmember = new alertmembers(data_result[i]["memname"],data_result[i]["type"]);
					   memname.push(alertmember);
				   }
				   
				   var result = memname.moverepeat();
				   
				   for(var j = 0 ; j < result.length ; j++){
					   
					   name_arr.push(result[j].name);
					   email_arr.push(result[j].getemailcount());
					   phone_arr.push(result[j].getphonecount());
					  
				   }
				   
				   
				   charthandle(name_arr,email_arr,phone_arr);
				   
				   addrecord(data_result);
				   
				   $("[type=radio]").click(function(){
					   var id = this.id;
					   displaybycondition(data_result,id);
				   });
				   
				   

				  //日期选择
				  var date = new Date();
				  var start_date = date.getMonth()+"/" + date.getDay() + "/" + date.getFullYear();
				  
				 var start = new  choosedate("time_start",start_date,data_result);
				 start.init();
				 start.datepicker();
				 
				 var end = new choosedate("time_end",start_date,data_result);
				 end.init();
				 end.datepicker();
				 
				 
				 //日期选择结束
				 options.offset = options.offset + options.length;
				 
				 //document.getElementById("more").scrollIntoView();
				 $("html,body").animate({scrollTop:$("body").height()},2000);
				 
			   }
			  
			   
			   
			   function error(){
				   //console.log("error");
			   }
			   
			   getalertmessages(success,error);
			   
			   $("#more").click(function(){
				   
				   getalertmessages(success,error);
				   
			   });
			   
		});

