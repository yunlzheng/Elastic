$(document).ready(

		function() {

			var chart;

			Highcharts.getOptions().colors = $.map(
					Highcharts.getOptions().colors, function(color) {
						return {
							radialGradient : {
								cx : 0.5,
								cy : 0.3,
								r : 0.7
							},
							stops : [
									[ 0, color ],
									[
											1,
											Highcharts.Color(color).brighten(
													-0.3).get('rgb') ] // darken
							]
						};
					});

			setTimeout(function() {
				chart = new Highcharts.Chart({
					chart : {
						renderTo : 'alert_chart',
						type : 'column',
						margin : [ 50, 50, 100, 80 ]
					},
					title : {
						text : ' 预警通知统计'
					},
					xAxis : {
						categories : [ '张文俊', '王学凯', '王孙裕' ],
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
					series : [ {
						name : '邮件通知',
						data : [ 10, 20, 15 ],
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
					}, {
						name : '短信通知',
						data : [ 5, 7, 9 ],
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
			}, 2000);

		});
