$(function() {

	
	sendAjaxRequest(successRequest,error);
	
	$("#more").click(function(){
		
		sendAjaxRequest(successRequest,error);
		
	});
	
	var chart;

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
			renderTo : "exception_chart",
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
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
						return '<b>' + this.point.name + '</b>: '
								+ this.percentage + ' %';
					}
				}
			}
		},
		series : [ {
			type : 'pie',
			name : 'Browser share',
			data : [ [ '一般异常',  getGeneralCount()], [ '严重异常', getSeriousCount() ], ['严重错误',getSeriousFaultCount()],{
				name : '严重错误',
				y : getGeneralFaultCount(),
				sliced : true,
				selected : true
			}]
		} ]
	});

});


