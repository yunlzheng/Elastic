$(function() {

	$.ajaxSetup({cache:false});
	
	scaleRequest(scaleSuccess,error);
	sendAjaxRequest(successHandler,error,0,0);
	
	$("#more").click(function(){
		
		isMore(error);
		
	});
	
	/**
	 * 下拉列表处理
	 */
	selectHandler(error);
	var chart;


	/**
	 * 滚动加载
	 * */
	$(window).scroll(function(){
		
		var $body = $("body");
		if(($(window).height()+$(window).scrollTop())>=$body.height()){
			
			setTimeout(function(){
				$("#more").click();
			},500);
			
		}
		
	});
	
	function scaleSuccess (data){
		
		var colors = Highcharts.getOptions().colors,
			name = "用户下异常情况统计",
			categories =new Array();
	
		var datas = new Array();
		var series = new Array();
		
		for(var i=0;i<data.length;i++){
			
			var db = data[i];
			var name = db.name;
			var value = db.value;
			datas.push({y:parseInt(value),color:colors[i]});
			categories.push(name);
			series.push({
				
				name: name,
                data: [parseInt(value)],
                color: colors[i],
                dataLabels: {
                    enabled: true,
                    rotation: -90,
                    color: '#FFFFFF',
                    align: 'right',
                    x: -3,
                    y: 10,
                    formatter: function() {
                        return this.y;
                    },
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
				
			});
			
		}
		
		Highcharts.getOptions().colors = $.map(Highcharts.getOptions().colors, function(color) {
		    return {
		        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		        stops: [
		            [0, color],
		            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		        ]
		    };
		});
		
		var scaleChart  = new Highcharts.Chart({
			
			 chart:{
				backgroundColor: '#F5F7F6',
				renderTo : "exception_chart",
				type: 'column'
			 },
			 title: {
	                text: '用户下监控异常情况统计'
	         },
	         subtitle: {
	                text: '监控项目.'
	           },
	           xAxis: {
	                categories: ['各监控项占有率']
	            },
	         yAxis: {
                title: {
                    text: '占有率(%)'
                }
	         },
			 tooltip: {
	                formatter: function() {
	                    var point = this.point,
	                    	s = this.series.name+'：<b>'+ this.y +'</b><br/>';
	                 
	                    return s;
	                }
	            },
	            series: series,
	            exporting: {
	                enabled: false
	            }
			
		});
		
		//console.log(datas);
		//console.log(categories);
		
//		var  seriasdata = "";
//		
//		var colors = Highcharts.getOptions().colors;
//		
//		if(data.level_1 == 0 && data.level_2 == 0 && data.level_3 == 0){
//			
//			seriasdata = [['正常',100]];
//			colors = ['#74A93E'];
//			
//		}else{
//			seriasdata = [[ '严重故障', data.level_1 ], ['一般故障',data.level_2],{
//				name : '预警',
//				y : data.level_3,
//				sliced : true,
//				selected : true
//			}];
//		}
//		
//		Highcharts.getOptions().colors = $.map(Highcharts.getOptions().colors, function(color) {
//		    return {
//		        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
//		        stops: [
//		            [0, color],
//		            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
//		        ]
//		    };
//		});
//		
//		 chart = new Highcharts.Chart({
//			chart : {
//				backgroundColor: '#F5F7F6',
//				renderTo : "exception_chart",
//				plotBackgroundColor : null,
//				plotBorderWidth : null,
//				plotShadow : false
//			},
//			colors:colors,
//			title : {
//				text : '异常类型比例信息统计'
//			},
//			tooltip : {
//				pointFormat : '{series.name}: <b>{point.percentage}%</b>',
//				percentageDecimals : 1
//			},
//			plotOptions : {
//				pie : {
//					allowPointSelect : true,
//					cursor : 'pointer',
//					dataLabels : {
//						enabled : true,
//						color : '#000000',
//						connectorColor : '#000000',
//						formatter : function() {
//							return '<b>' + this.point.name + '</b>';
//						}
//					}
//				}
//			},
//			series : [ {
//				type : 'pie',
//				name : '异常统计',
//				data : seriasdata
//			} ]
//		});
	}

	
	/**查看异常详细信息相关操作*/
	$("a[excId]").live("click",function(){
		
		var excId = $(this).attr("excId");
		showExcDetail(excId,showExcDetailSuccess,showExcDetailError);
		
	});
	
	function showExcDetail(id,success,error){
		
		var url = "../rest/exception/"+id;
		
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
			height:'150px',
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
			.replace(/{identifiekey}/g,data["identifiekey"])
			.replace(/{startTime}/g,data["startTime"])
			.replace(/{message}/g,data["message"])
			.replace(/{name}/g,data["name"]);
		
		setTimeout(function(){
			
			$("#excDetailDialog").find(".loading").hide();
			$("#excDetailDialog").find(".information").empty().append(info).fadeIn(500);
			
		},1000);
		
	}
	function showExcDetailError(){
		
	}
	
	var EXCVIEW = {
			
			detail:'<div class="item"><label>发生时间:</label><span>{startTime}</span></div>'+
				'<div class="item"><label>监控项目:</label><span>{name}</span></div>'+
				'<div class="item"><label>详细信息:</label><p title="{message}">{message}</p></div>'
				
			
	}
	
	
	function error(){};
	
});


