/**
 * 
 * 开发说明 该页面是作为实时监控数据的展示页面，可作为cpu占有率，disk.mem,io等占有率信息的展示界面
 * 
 * 实现步骤 1，获取参数 ip:需要监控的服务器IP地址 type:监控的项目 如cpu,mem,disk,io 2, 获取特定ip服务器的实时数据 3,
 * 更具type参数确定需要展示的图标类型 4, 加载hightchart图标，并定时加载数据
 * 
 * 需要使用的js库
 * 
 * jquery.js
 * 
 * highcharts.js 用于显示图标的js库
 * 
 * jquery.getparam.js 用于获取 url中包含的参数方法 如 www.aaa.com?a=1 调用方法 var a =
 * $.getParam("a");
 * 
 * 
 * 参考资料 http://api.highcharts.com/highcharts 参考示例
 * http://www.highcharts.com/demo/dynamic-update
 * 
 */
$(function() {

	$("#view_ip").text("应用-"+$.getParam("pxname"));
	
	var type=$.getParam("type");
	
	var char1;
	var char2;

	/**
	 * 显示图标内容
	 */
	if(type=="session"){
		
		$("#container").hide();
		$("#charts").show();
	}else if(type=="byte"){
		
		$("#container").show();
		$("#charts").hide();
	}else{
		
		$("#container").show();
		$("#charts").show();
		
	}
	
	loadChart();
	loadByteChart();
	
	function sucess(data) {

		var char = window.chart1;
		
		
		//console.log("调用");
		
		var series1 = char.series[0];
		var series2 = char.series[1];
		var series3 = char.series[2];
		
		
		for(var i=0;i<data.length;i++){
			
			var pxname = $.getParam("pxname");
			if(data[i].pxname==pxname){
				
				if(data[i].svname=="BACKEND"){
					
				    var x = (new Date()).getTime()
					var y1 = parseInt(data[i].stot);
					var y2 = parseInt(data[i].scur);
					var y3 = parseInt(data[i].smax);
					series1.addPoint([ x, y1 ], true, true);
					series2.addPoint([ x, y2 ], true, true);
					series3.addPoint([ x, y3 ], true, true);
					
					var y4 = parseInt(data[i].bin);
					var y5 = parseInt(data[i].bout);
					
					
					loadByteChart( [{
		                name: '输入流量',
		                data: [y4],
		                dataLabels: {
		                    enabled: true,
		                    rotation: -90,
		                    color: '#FFFFFF',
		                    align: 'center',
		                    x: 4,
		                    y: 10,
		                    style: {
		                        fontSize: '13px',
		                        fontFamily: 'Verdana, sans-serif'
		                    }
		                }
		    
		            },{
		                name: '输出流量',
		                data: [y5],
		                dataLabels: {
		                    enabled: true,
		                    rotation: -90,
		                    color: '#FFFFFF',
		                    align: 'center',
		                    x: 4,
		                    y: 10,
		                    style: {
		                        fontSize: '13px',
		                        fontFamily: 'Verdana, sans-serif'
		                    }
		                }
		    
		            }]);
					
					
				}
				
			}
				
			}
	}

	

	function error() {

	}


	function loadChart(url) {

		chart1 = new Highcharts.Chart({

			chart : {
				backgroundColor: '#F5F7F6',
				renderTo : 'charts',
				type : 'spline',
				marginRight : 10,
				events : {
					load : function() {

						// set up the updating of the chart each second
						var series = this.series[0];
						setInterval(function() {

							getRealTime(sucess, error);

						}, 4000);
					}
				}
			},
			title : {
				text : '应用Session实时监控'
			},
			xAxis : {
				type : 'datetime',
				tickPixelInterval : 150
			},
			yAxis : {
				title : {
					text : '占用率'
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ],
				min : 0
			},

			legend : {
				enabled : false
			},
			exporting : {
				enabled : false
			},
			series : [ {
				name : "最大在线用户数",
				data : (function() {
					// generate an array of random data
					var data = [], time = (new Date()).getTime(), i;

					for (i = -19; i <= 0; i++) {
						data.push({
							x : time + i * 1000,
							y : -20
						});
					}
					return data;
				})()
			},{
				name : "当前在线用户数",
				data : (function() {
					// generate an array of random data
					var data = [], time = (new Date()).getTime(), i;

					for (i = -19; i <= 0; i++) {
						data.push({
							x : time + i * 1000,
							y : -20
						});
					}
					return data;
				})()
			},{
				name : "最大同时在线用户数",
				data : (function() {
					// generate an array of random data
					var data = [], time = (new Date()).getTime(), i;

					for (i = -19; i <= 0; i++) {
						data.push({
							x : time + i * 1000,
							y : -20
						});
					}
					return data;
				})()
			} ]
		});

	}

	function loadByteChart(data){
		
		char2 = new Highcharts.Chart({
	            chart: {
	                renderTo: 'container',
	                type: 'column'
	            },
	            title: {
	                text: '实时流量统计'
	            },
	            subtitle: {
	                text: 'Source: '+$.getParam("pxname")
	            },
	            xAxis: {
	                categories: [
	                    '应用流量统计',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Rainfall (mm)'
	                }
	            },
	            legend: {
	                layout: 'vertical',
	                backgroundColor: '#FFFFFF',
	                align: 'left',
	                verticalAlign: 'top',
	                
	                floating: true,
	                shadow: true
	            },
	            tooltip: {
	                formatter: function() {
	                    return ''+
	                        this.x +': '+ this.y +' mm';
	                }
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series:data
	        });
		
	}
	
	/**
	 * 实时获取数据的方法
	 */
	function getRealTime(success, error) {

		$.ajax({

			type : "get",
			url : "../rest/thirdparty/lb/status",
			success : success,
			error : error,
			timeout : 5000

		});

	}

});