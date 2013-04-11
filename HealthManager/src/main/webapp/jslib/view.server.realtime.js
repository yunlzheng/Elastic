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
		
		var data = new Date();
		
		var x = data.getTime();
		
		var finded = false;
		
		for(var i=0;i<data.length;i++){
			
			var ip = $.getParam("ip");
			
			if(data[i].ip==ip){
			
				finded = true;
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
			
			series1.addPoint([ x, Math.random()*10 ], true, true);
			series2.addPoint([ x, Math.random()*20 ], true, true);
			series3.addPoint([ x, Math.random()*30 ], true, true);
			series4.addPoint([ x, Math.random()*40 ], true, true);
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

				renderTo : 'charts',
				type : 'spline',
				marginRight : 10,
				events : {
					load : function() {

			
						setInterval(function() {

							getRealTime(url,sucess, error);
							

						}, 1000);
					}
				}
			},
			title : {
				text : '｛服务器实时监控｝'
			},
			xAxis : {
				type: 'datetime',
                tickPixelInterval: 150
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
			tooltip: {
                formatter: function() {
                	
                		var today = new Date(this.x);
                		intYear = today.getYear();
                		intMonth = today.getMonth() + 1;
                		intDay = today.getDate();
                		intHours = today.getHours();
                		intMinutes = today.getMinutes();
                		intSeconds = today.getSeconds();
                		
                		// 获取系统时间的小时数
                		if (intHours == 0) {
                			hours = intHours + ":";
                			ap = "凌晨";
                		} else if (intHours < 12) {
                			hours = intHours + ":";
                			ap = "早晨";
                		} else if (intHours == 12) {
                			hours = intHours + ":";
                			ap = "中午";
                		} else {
                			intHours = intHours - 12;
                			hours = intHours + ":";
                			ap = "下午";
                		}
                		// 获取系统时间的分数
                		if (intMinutes < 10) {
                			minutes = "0" + intMinutes + ":";
                		} else
                			minutes = intMinutes + ":";
                		// 获取系统时间的秒数
                		if (intSeconds < 10)
                			seconds = "0" + intSeconds + " ";
                		else
                			seconds = intSeconds + " ";
                		
                		timeString = hours + minutes + seconds + ap;
                		
                        return '<b>'+ this.series.name +'</b><br/>'+timeString
                         +'<br/>'+Highcharts.numberFormat(this.y, 2);
                        
                }
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
                    var data = [],
                        time = (new Date()).getTime(),
                        i;
    
                    //console.log(new Date());
                    //console.log(new Date(time));
                    
                    for (i = -10; i <= 0; i++) {
                        data.push({
                            x: time + i * 1000,
                            y: 0
                        });
                    }
                    return data;
					
				})()
			},{
				name : "内存占有率",
				data : (function() {
					// generate an array of random data
					var data = [], time = (new Date()).getTime(), i;

					for (i = -10; i <= 0; i++) {
						data.push({
							 x: time + i * 1000,
							y : 0
						});
					}
					return data;
				})()
			},{
				name : "IO占有率",
				data : (function() {
					// generate an array of random data
					var data = [], time = (new Date()).getTime(), i;

					for (i = -10; i <= 0; i++) {
						data.push({
							x: time + i * 1000,
							y : 0
						});
					}
					return data;
				})()
			},{
				name : "磁盘占有率",
				data : (function() {
					// generate an array of random data
					var data = [], time = (new Date()).getTime(), i;

					for (i = -10; i <= 0; i++) {
						data.push({
							 x: time + i * 1000,
							 y : 0
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