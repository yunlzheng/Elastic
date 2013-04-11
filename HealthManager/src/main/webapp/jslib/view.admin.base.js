$(function(){
	
	var id = "base_charts";
	
	setTimeout(function(){
		
		showCharts(id);
		
	},2000);
	
	function showCharts(id){
		
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
				renderTo : id,
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			title : {
				text : '云监控概况'
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
				data : [ [ '正常',30 ],[ '提醒', 20 ], [ '故障', 50 ] ]
			} ]
		});
		
	}
});