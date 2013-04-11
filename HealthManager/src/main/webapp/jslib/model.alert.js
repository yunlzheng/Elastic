
var options = {
	offset:0,
	length:10
}

/**
 * 定义租户信息
 */

var tenantMember = function(mailNum,messageNum,name){
	this.mailNum = mailNum;
	this.messageNum = messageNum;
	this.name = name;
}


 /**
  * 请求数据获取 chart 参数
  */

function chartData(success,error){
	
	$.ajax({

		type : "get",
		url : "../rest/combin/alertstatistics",
		success : success,
		error : error,
		timeOut : 3000

	});
	
}


	var getalertByList = function(success, error){
	$.ajax({

		type : "get",
		url : "../rest/alertmessages/"+options.offset+"/"+options.length,
		success : success,
		error : error,
		timeOut : 3000

	});
	};
	
	var getAll = function(success,error,id){
		var url = "";
		
		if(id=="display_by_emails"){
			url = "../rest/alertmessages/"+ options.offset +"/"+options.length + "/?eqs=type:2";

		}else if(id == "display_by_phones"){
			url = "../rest/alertmessages/"+ options.offset +"/"+options.length + "/?eqs=type:1";

		}else if(id == "display_all"){
			url = "../rest/alertmessages/"+ options.offset +"/"+ (options.length);
		}
		$.ajax({

			type : "get",
			url : url,
			success : success,
			error : error,
			timeOut : 3000

		});
		
	}
	
	var getNotices = function(success, error){
		$.ajax({

			type : "get",
			url : "../rest/alertmessages/",
			success : success,
			error : error,
			timeOut : 3000

		});
	}

	var getalertmessages = function(success, error) {
	 
		$.ajax({

			type : "get",
			url : "../rest/alertmessages/"+options.offset+"/"+options.length,
			success : success,
			error : error,
			timeOut : 3000

		});

	}
  
  
  //抽象出'警告'对象
  function alertmembers(name,type){
	  this.name = name;
	  this.type = type;
	  this.emailcount = 0;
	  this.phonecount = 0;
	  
	  this.addemail = function(){
		  this.emailcount++;
	  };
	  this.addphone = function(){
		  this.phonecount++;
	  };
	  this.getemailcount = function(){
		  return this.emailcount;
	  };
	  this.getphonecount = function(){
		  return this.phonecount;
	  }
	  
  }
  
  //在数组中添加自己的去重方法
  Array.prototype.moverepeat = function(){
	  var n = {};
	  var r = []; //创建一个临时数组
	  
	  for(var i = 0 ; i < this.length ; i ++){
		  if(!n[this[i].name]){
			  
			  n[this[i].name] = true;
			  
			  if(this[i].type == 1){ //type=1 表示记录的是手机的警告信息
				  this[i].addphone();
			  }else if(this[i].type == 2){ //type=2 表示记录的是邮件的警告信息
				  this[i].addemail();
			  }
			  
			  r.push(this[i]);
			  
		  }else{
			  for(var j = 0 ; j < r.length ; j++){
				  
				  if(r[j].name == this[i].name){
					  
					  if(this[i].type == 1){
						  r[j].addphone();
					  }else if(this[i].type == 2){
						  r[j].addemail();
					  }
					  
				  }
			  }
			  
		  }
	  }
	  
	  return r;
  }
  
  
  //图表处理
  
  function charthandle(name_arr,email_arr,phone_arr){
	  
	//  $("#alert_chart").empty();
	  
	  /**图表处理*/
	   var chart;
	
		chart = new Highcharts.Chart(
				{
					chart : {
						backgroundColor: '#F5F7F6',
						renderTo : 'alert_chart',
						type : 'column',
						margin : [ 50, 50, 100, 80 ]
					},
					title : {
						text : ' 报警信息统计'
					},
					xAxis : {
						categories : name_arr,
						labels : {
							rotation : -45,
							align : 'right',
							style : {
								fontSize : '13px',
								fontFamily : 'Verdana, sans-serif'
							}
						}
					},
					yAxis : {
						min : 0,
						title : {
							text : '通知数量（条）'
						}
					},
					legend : {
						enabled : false
					},
					series : [
							{
								name : '邮件通知',
								data : email_arr,
								dataLabels : {
									enabled : true,
									rotation : -90,
									color : '#FFFFFF',
									align : 'right',
									x : -3,
									y : 10,
									formatter : function() {
										return this.y;
									},
									style : {
										fontSize : '13px',
										fontFamily : 'Verdana, sans-serif'
									}
								}
							},
							{
								name : '短信通知',
								data : phone_arr,
								dataLabels : {
									enabled : true,
									rotation : -90,
									color : '#FFFFFF',
									align : 'right',
									x : -3,
									y : 10,
									formatter : function() {
										return this.y;
									},
									style : {
										fontSize : '13px',
										fontFamily : 'Verdana, sans-serif'
									}
								}
							} ]
				});
  }
  
  
  //处理table
  function addrecord(data,index){  //显示所有的通知
	  //$("#body_content").empty();
	  
	  for(var i = 0 ; i < data.length ; i++){
		  if(data[i].type == 1){
			  index+=1;
			 $("<tr><td>"+(index)+"</td><td>" + data[i].joinTime + "</td>" +
				   		"<td>短信通知</td>" +
				   		"<td>" + data[i].memname + "</td>" +
				   		"<td title='"+data[i].message+"'>" + data[i].message + "</td><td><a excId='" + data[i]["id"]  + "' class='alertdetail'>详情</a></td></tr>").appendTo("#body_content");
			  
		  }else if(data[i].type == 2){
			  index+=1;
			  $("<tr><td>"+(index)+"</td><td>" + data[i].joinTime + "</td>"
						+ "<td> 邮件通知</td>"
						+ "<td>" + data[i].memname + "</td>"
						+ "<td title='"+data[i].message+"'>" + data[i].message + "</td><td><a excId='" + data[i]["id"]  + "' class='alertdetail'>详情</a></td></tr>").appendTo("#body_content");
			
		  } 
	  }
  }
  

  
  
  function displaybycondition(data,name,index){
	  
	  if(name == "display_by_phones"){
		  
		  for(var i = 0 ; i < data.length; i++){
			  
			   index++;
			   if(data[i]["type"] == 1){
				   
				   appendMessage(index,data[i]);
				  
			   }
		  }
		  
	  }else if(name == "display_by_emails"){
		  for(var i = 0 ; i < data.length; i++){
			  index++;
			  if(data[i]["type"] == 2){
				  appendMessage(index,data[i]);
				  
			  }
		  }
		  
	  }else if(name == "display_all"){
		  for(var i = 0 ; i < data.length ; i++){
			 
			  		index++;
			  		appendMessage(index,data[i]);
			 
			 
		  }
	  }
	  
  }
  
  function appendMessage(index,data){
	  
	  $("<tr><td>"+(index)+"</td>" +
			    "<td>" + data.memname + "</td>" +
			    "<td>"+tracsform(data.type)+"</td>" +
	  			"<td>" + data.joinTime + "</td>" +
		   		"<td>"+data.key+"</td>"+
		   		"<td title='"+data.message+"'>" + data.message + "</td><td><a excId='" + data["id"]  + "'  class='alertdetail'>详情</a></td></tr>").appendTo("#body_content");
	  
  }
  
  /**
   * 准换通知类型参数为字符串
   * */
  function tracsform(key){
	  
	  key = parseInt(key);
	  
	  switch (key) {
		case 1:
			return "短息通知"
			break;
		case 2:
			return "邮件通知"
			break;
		default:
			return "未知"
			break;
		}
		  
	}

  
  //日期选择
  function choosedate(object,date,data){
	  
	  this.object = object;
	  this.date = date;
	  this.init = function(){
		 this.object = "." + this.object;
		  $(this.object).val(this.date);
	  };
	  
	  this.datepicker = function (){
		  var object = this.object;
		  var date = this.date;
		  var array = new Array();
		 
		  
		  $(object).DatePicker({
	 			format:'m/d/Y',
	 			date: ['2008-07-28','2008-07-31'],
	 			current: '2008-07-31',
	 			starts: 1,
	 			position: 'r',
	 			onBeforeShow: function(){
	 				$(object).DatePickerSetDate($(object).val(), true);
	 			},
	 			onChange: function(formated, dates){
	 				
	 				$(object).val(formated);
	 				$(object).DatePickerHide();
	 				
	 				 var start = $(".time_start").val();
	 				 var end = $(".time_end").val();
	 				  
	 				//如果输入的为结束日期那么判断一下 开始日期和结束日期的大小
	 				
	 					if(Date.parse(start) - Date.parse(end) > 0){
	 						if(object == ".time_start"){
	 							$(object).val(date);
	 						}
	 						alert("对不起  输入的结束时间应该在起始时间之后");
	 					}else{
	 						//如果符合要求  就从后台传送过来的数据中查询符合条件的数据
	 						
	 						for(var i = 0 ; i < data.length; i++){
	 							 var newdate = data[i]["joinTime"];
	 							 
	 							 if(Date.parse(newdate) - Date.parse(start) > 0 && Date.parse(newdate) - Date.parse(end) < 0){
	 								 array.push( data[i]);
	 							 }
	 						}
	 						
	 						addrecord(array);
	 						
	 					}
	 				
	 			}
	 		});
	  }
	  
  }
   
   
