$(function() {

	$.ajaxSetup({cache:false});
	scaleRequest(scaleSuccess,error);
	sendAjaxRequest(successHandler,error,0,0);
	
	$("#more").click(function(){
		
		isMore(error);
		
	});
	
	$(window).scroll(function(){
		
		var $body = $("body");
		if(($(window).height()+$(window).scrollTop())>=$body.height()){
			
			setTimeout(function(){
				
				$("#mode").empty().html("<img src='../assets/images/ajax-loader.gif'/><span>数据加载中...</span>");
				$("#more").click();
	
			},500);
			
		}
		
	});
	
	/**
	 * 下拉列表处理
	 */
	selectHandler(error);
	var chart;

	function scaleSuccess (data){
		
		
		var colors =  ['#BD1919', '#E45410','#E4DC10' ]
		
		var seriasdata = [[ '严重故障', data.level_1 ], ['一般故障',data.level_2],{
			name : '预警',
			y : data.level_3,
			sliced : true,
			selected : true
		}];
		
		if(data.level_1 == 0 && data.level_2 == 0 && data.level_3 == 0){
			
			seriasdata = [['正常',100]];
			colors = ['#74A93E'];
			

			
		
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
		
		 chart = new Highcharts.Chart({
			chart : {
				backgroundColor: '#F5F7F6',
				renderTo : "exception_chart",
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			colors:colors,
			title : {
				text : '异常信息统计'
			},
			tooltip : {
				pointFormat : '{series.name}: <b>{point.percentage}%</b>',
				percentageDecimals : 1
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						color : '#000000',
						connectorColor : '#000000',
						formatter : function() {
							return '<b>' + this.point.name + '</b>';
						}
					}
				}
			},
			series : [ {
				type : 'pie',
				name : '监控数据',
				data : seriasdata
			} ]
		});
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
			height:'140px',
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
				'<div class="item"><label>详细信息:</label><p>{message}</p></div>'
			
	}
	
	
	function error(){};
	
});


