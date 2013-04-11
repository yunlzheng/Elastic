jQuery.fn.viewCpuChar = function() {

	var element = $(this);
	var id = element.attr("id");

	var chart;

	chart = new Highcharts.Chart({
		chart : {
			renderTo : id,
			type : 'line',
			marginRight : 130,
			marginBottom : 25
		},
		title : {
			text : 'Monthly Average Temperature',
			x : -20
		// center
		},
		subtitle : {
			text : 'Source: WorldClimate.com',
			x : -20
		},
		xAxis : {
			categories : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul',
					'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ]
		},
		yAxis : {
			title : {
				text : 'Temperature (°C)'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '</b><br/>' + this.x + ': '
						+ this.y + '°C';
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'top',
			x : -10,
			y : 100,
			borderWidth : 0
		},
		series : [
				{
					name : 'Tokyo',
					data : [ 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3,
							18.3, 13.9, 9.6 ]
				},
				{
					name : 'New York',
					data : [ -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1,
							20.1, 14.1, 8.6, 2.5 ]
				},
				{
					name : 'Berlin',
					data : [ -0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3,
							9.0, 3.9, 1.0 ]
				},
				{
					name : 'London',
					data : [ 3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2,
							10.3, 6.6, 4.8 ]
				} ]
	});
}

jQuery.fn.viewDiskChar = function() {

	var element = $(this);
	var id = element.attr("id");

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
			text : 'Browser market shares at a specific website, 2010'
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
			data : [ [ 'Firefox', 45.0 ], [ 'IE', 26.8 ], {
				name : 'Chrome',
				y : 12.8,
				sliced : true,
				selected : true
			}, [ 'Safari', 8.5 ], [ 'Opera', 6.2 ], [ 'Others', 0.7 ] ]
		} ]
	});

}

jQuery.fn.viewNetworkChar = function() {

	var element = $(this);
	var id = element.attr("id");

	var chart;

	chart = new Highcharts.Chart({
		chart : {
			renderTo : id,
			type : 'line',
			marginRight : 130,
			marginBottom : 25
		},
		title : {
			text : 'Monthly Average Temperature',
			x : -20
		// center
		},
		subtitle : {
			text : 'Source: WorldClimate.com',
			x : -20
		},
		xAxis : {
			categories : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul',
					'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ]
		},
		yAxis : {
			title : {
				text : 'Temperature (°C)'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '</b><br/>' + this.x + ': '
						+ this.y + '°C';
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'top',
			x : -10,
			y : 100,
			borderWidth : 0
		},
		series : [
				{
					name : 'Tokyo',
					data : [ 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3,
							18.3, 13.9, 9.6 ]
				},
				{
					name : 'New York',
					data : [ -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1,
							20.1, 14.1, 8.6, 2.5 ]
				},
				{
					name : 'Berlin',
					data : [ -0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3,
							9.0, 3.9, 1.0 ]
				},
				{
					name : 'London',
					data : [ 3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2,
							10.3, 6.6, 4.8 ]
				} ]
	});

}

jQuery.fn.viewMemoryChar = function() {

	var element = $(this);
	var id = element.attr("id");

	var chart;

	chart = new Highcharts.Chart({
		chart : {
			renderTo : id,
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		title : {
			text : 'Browser market shares at a specific website, 2010'
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
			data : [ [ 'Firefox', 45.0 ], [ 'IE', 26.8 ], {
				name : 'Chrome',
				y : 12.8,
				sliced : true,
				selected : true
			}, [ 'Safari', 8.5 ], [ 'Opera', 6.2 ], [ 'Others', 0.7 ] ]
		} ]
	});

}