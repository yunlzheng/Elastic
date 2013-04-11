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

	$("#view_ip").text("服务器-"+$.getParam("ip"));
	
	var char1;

	/**
	 * 显示图标内容
	 */
	getSysConfig();
	
	function sucess(data) {

		var char = window.chart1;

		data = eval('(' + data + ')');
		var series1 = char.series[0];
		var series2 = char.series[1];
		var series3 = char.series[2];
		var series4 = char.series[3];
		
		var finded = false;
		var x = (new Date()).getTime();
		
		for(var i=0;i<data.length;i++){
			
			var ip = $.getParam("ip");
			
			if(data[i].ip==ip){
			
				finded=true;
				
				var y1 = parseInt(data[i].cpu);
				var y2 = parseInt(data[i].mem);
				var y3 = parseInt(data[i].io);
				var y4 = parseInt(data[i].disk);
	
				series1.addPoint([ x, y1 ], true, true);
				series2.addPoint([ x, y2 ], true, true);
				series3.addPoint([ x, y3 ], true, true);
				series4.addPoint([ x, y4 ], true, true);
				
			}
		
		}
		
		if(!finded){
			
			series1.addPoint([ x, 40 ], true, true);
			series2.addPoint([ x, 5 ], true, true);
			series3.addPoint([ x, 25 ], true, true);
			series4.addPoint([ x, 20 ], true, true);
		}
		

	}

	function error() {

	}
	
	function getSysConfig(){
		
		
		$.ajax({

			type : "get",
			url : "../rest/configuration",
			success : ajaxSuccess,
			error : ajaxError,
			timeout : 5000

		});
		
	}
	
	function ajaxSuccess(data){
		
		var url = data.iaasUrl+"/veapi?cmd=GtVmStat";
		loadChart(url);
		
	}
	
	function ajaxError(){
		
		

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

							getRealTime(url,sucess, error);

						}, 2000);
					}
				}
			},
			title : {
				text : '｛服务器实时监控｝'
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
				max : 100,
				min : 0
			},

			legend : {
				enabled : false
			},
			exporting : {
				enabled : false
			},
			series : [ {
				name : "CPU占有率",
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
				name : "内存占有率",
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
				name : "IO占有率",
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
				name : "磁盘占有率",
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

	/**
	 * 实时获取数据的方法
	 */
	function getRealTime(url,success, error) {

		$.ajax({

			type : "get",
			url : "../proxy?URL="+url,
			success : success,
			error : error,
			timeout : 5000

		});

	}

});