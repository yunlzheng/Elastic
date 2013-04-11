$(function() {

	var config = {

		showcpu : true,
		showdisk : true,
		showio : true,
		showmem : true

	}

	var cpulog = new Array();
	var memlog = new Array();
	var iolog = new Array();
	var disklog = new Array();
	var datalog = new Array();

	var ip = $.getParam("ip");
	$("#view_server_ip").text(ip);

	var page = {
			offset:0,
			len:10
	}
	
	MonitorLog.getByMode(ip, 3, success, error);

	$("#nav-tabs").append('<li class="active tab" tab-data="cpu-tab"><a>CPU占有率</a></li>');
	$("#nav-tabs").append('<li class="tab" tab-data="disk-tab"><a>磁盘占有率</a></li>');
	$("#nav-tabs").append('<li class="tab" tab-data="io-tab"><a>网络IO占有率</a></li>');
	$("#nav-tabs").append('<li class="tab" tab-data="mem-tab"><a>内存占有率</a></li>');
	
	$(".nav-tabs").tabs("#tabs");
	
	
	initScale(initScaleSuccess,initScaleError);
	
	function initScale(success,error){
		
		var url = "../rest/combin/exception/scale?ip="+ip;
		$.ajax({
			
			type:"get",
			url:url,
			success:success,
			error:error,
			timeout:3000
			
			
		});
		
	}
	
	function initScaleSuccess(data){
		
		//console.log(data);
		var scaleChart = new Highcharts.Chart({
            chart: {
            	backgroundColor: '#F5F7F6',
                renderTo: 'scale-chart',
                type: 'column'
            },
            title: {
                text: '虚拟机['+data["ip"]+']异常状态比例'
            },
            subtitle: {
                text: 'Source: monitor.wocloud.com'
            },
            xAxis: {
                categories: [
                    '总数',
                    '异常']
            },
            yAxis: {
                min: 0,
                title: {
                    text: '次数/次'
                }
            },
            legend: {
                layout: 'vertical',
                backgroundColor: '#FFFFFF',
                align: 'left',
                verticalAlign: 'top',
                x: 100,
                y: 70,
                floating: true,
                shadow: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: [{
	                name: '统计信息',
	                data: [
	                       {y:data["total"],color:'#3D668F'},
	                       {y:data["exception"],color:'#BF0B23'}
	                      ]
            		
            	}]
            
        });
    
		
	}
	
	function initScaleError(){
		
	}
	

	
	
	
	$(".cpu_mode").bind("click", function() {

		var element = $(this);
		var mode = element.attr("data");
		MonitorLog.getByMode(ip, mode, successUpdateCpu, error);

	});

	$(".mem_mode").bind("click", function() {

		var element = $(this);
		var mode = element.attr("data");
		MonitorLog.getByMode(ip, mode, successUpdateMem, error);

	});
	$(".io_mode").bind("click", function() {
		var element = $(this);
		var mode = element.attr("data");
		MonitorLog.getByMode(ip, mode, successUpdateIo, error);

	});
	$(".disk_mode").bind("click", function() {

		var element = $(this);
		var mode = element.attr("data");
		MonitorLog.getByMode(ip, mode, successUpdateDisk, error);

	});

	function success(data) {

		if (data != undefined) {

			if (!$.isArray(data)) {

				var temp = new Array();
				temp.push(data);
				data = temp;

			}

			cpulog.length = 0;
			memlog.length = 0;
			iolog.length = 0;
			disklog.length = 0;
			datalog.length = 0;

			for ( var i = 0; i < data.length; i++) {

				var obj = data[i];
				var log = new MonitorLog(obj.id, obj.ip, obj.cpu, obj.mem,
						obj.io, obj.disk, obj.joinTime, obj.detailTime);
				if (config.showcpu) {
					cpulog.push(parseInt(log.cpu));
				}
				if (config.showmem) {
					memlog.push(parseInt(log.mem));
				}
				if (config.showio) {
					iolog.push(parseInt(log.io));
				}
				if (config.showdisk) {
					disklog.push(parseInt(log.disk));
				}

				var t = log.detailTime;
				var year = parseInt(t.substring(0,4));
				var month = parseInt(t.substring(5,7));
				var day = parseInt(t.substring(8,10));
				
				var hours = parseInt(t.substring(11,13));
				var minutes = parseInt(t.substring(14,16));
				var seconds = parseInt(t.substring(17,19));
				
				
				var date = Date.UTC(year,month,day,hours,minutes,seconds);
				
				datalog.push(date);

			}

		}

		if (config.showcpu) {
			LineCharts("charts_cpu", cpulog, datalog, 'CPU占有率', 'CPU占有率');
		}
		if (config.showmem) {
			LineCharts("charts_memory", memlog, datalog, '内存占有率', '内存占有率');
		}
		if (config.showio) {
			LineCharts("charts_io", iolog, datalog, 'IO占有率', 'IO占有率');
		}
		if (config.showdisk) {
			LineCharts("charts_disk", disklog, datalog, '磁盘占有率', '磁盘占有率');
		}

	}

	function successUpdateDisk(data) {

		if (data != undefined) {

			if (!$.isArray(data)) {

				var temp = new Array();
				temp.push(data);
				data = temp;

			}

			disklog.length = 0;

			for ( var i = 0; i < data.length; i++) {

				var obj = data[i];
				var log = new MonitorLog(obj.id, obj.ip, obj.cpu, obj.mem,
						obj.io, obj.disk, obj.joinTime, obj.detailTime);
				disklog.push(parseInt(log.disk));
				
				var t = log.detailTime;
				var year = parseInt(t.substring(0,4));
				var month = parseInt(t.substring(5,7));
				var day = parseInt(t.substring(8,10));
				
				var hours = parseInt(t.substring(11,13));
				var minutes = parseInt(t.substring(14,16));
				var seconds = parseInt(t.substring(17,19));
				
				
				var date = Date.UTC(year,month,day,hours,minutes,seconds);
				
				datalog.push(date);

			}

		}

		LineCharts("charts_disk", disklog, datalog, '磁盘占有率', '磁盘占有率');

	}

	function successUpdateCpu(data) {

		if (data != undefined) {

			if (!$.isArray(data)) {

				var temp = new Array();
				temp.push(data);
				data = temp;

			}

			cpulog.length = 0;

			for ( var i = 0; i < data.length; i++) {

				var obj = data[i];
				var log = new MonitorLog(obj.id, obj.ip, obj.cpu, obj.mem,
						obj.io, obj.disk, obj.joinTime, obj.detailTime);
				
				if(i==0){
					
					
				}
				
				cpulog.push(parseInt(log.cpu));
				
				//console.log(cpulog[(i-1)]);
				if(cpulog[(i-1)]!=parseInt(log.cpu)){
					
				}
				
				var t = log.detailTime;
				var year = parseInt(t.substring(0,4));
				var month = parseInt(t.substring(5,7));
				var day = parseInt(t.substring(8,10));
				
				var hours = parseInt(t.substring(11,13));
				var minutes = parseInt(t.substring(14,16));
				var seconds = parseInt(t.substring(17,19));
				
				
				var date = Date.UTC(year,month,day,hours,minutes,seconds);
				
				datalog.push(date);
				
				

			}
			
			
			

		}

		LineCharts("charts_cpu", cpulog, datalog, "CPU占有率", "CPU占有率");

	}

	function successUpdateMem(data) {

		if (data != undefined) {

			if (!$.isArray(data)) {

				var temp = new Array();
				temp.push(data);
				data = temp;

			}

			memlog.length = 0;

			for ( var i = 0; i < data.length; i++) {

				var obj = data[i];
				var log = new MonitorLog(obj.id, obj.ip, obj.cpu, obj.mem,
						obj.io, obj.disk, obj.joinTime, obj.detailTime);
				memlog.push(parseInt(log.mem));
			
				var t = log.detailTime;
				var year = parseInt(t.substring(0,4));
				var month = parseInt(t.substring(5,7));
				var day = parseInt(t.substring(8,10));
				
				var hours = parseInt(t.substring(11,13));
				var minutes = parseInt(t.substring(14,16));
				var seconds = parseInt(t.substring(17,19));
				
				
				var date = Date.UTC(year,month,day,hours,minutes,seconds);
				
				datalog.push(date);

			}

		}

		LineCharts("charts_memory", memlog, datalog, "内存占有率", "内存占有率");

	}

	function successUpdateIo(data) {

		if (data != undefined) {

			if (!$.isArray(data)) {

				var temp = new Array();
				temp.push(data);
				data = temp;

			}

			iolog.length = 0;

			for ( var i = 0; i < data.length; i++) {

				var obj = data[i];
				var log = new MonitorLog(obj.id, obj.ip, obj.cpu, obj.mem,
						obj.io, obj.disk, obj.joinTime, obj.detailTime);
				
				iolog.push(parseInt(log.io));
				
				var t = log.detailTime;
				var year = parseInt(t.substring(0,4));
				var month = parseInt(t.substring(5,7));
				var day = parseInt(t.substring(8,10));
				
				var hours = parseInt(t.substring(11,13));
				var minutes = parseInt(t.substring(14,16));
				var seconds = parseInt(t.substring(17,19));
				
				var date = Date.UTC(year,month,day,hours,minutes,seconds);
				
				datalog.push(date);

			}

		}

		LineCharts("charts_io", iolog, datalog, "IO占有率", "IO占有率");

	}

	function error() {

	}
	
	function LineCharts(id, data, xaxis, title, sname) {

		$("#"+id).empty().append('<div align="center" style="margin-top: 150px;"><img src="../assets/images/ajax-loader-bigger.gif" /></div>');
				
		var chart;

		chart = new Highcharts.Chart({
			chart : {
				
				renderTo : id,
				//zoomType: 'x',
				 spacingBottom: 30
	           
			},
			title : {
				text : title,
			},
			subtitle : {
				text : 'ICP.Monitor',
				x : -20
			},
			xAxis : {
				type : 'datetime',
			},
			yAxis : {
				title: {
                    text: 'Exchange rate'
                },
				max : 100,
				min : 0
			},
			tooltip: {
                shared: true
            },
            legend: {
               
            },
            plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, 'rgba(2,0,0,0)']
                        ]
                    },
                    lineWidth: 1,
                    marker: {
                       enabled: false,
                        states: {
                            hover: {
                                enabled: true,
                                radius: 5
                            }
                        }
                    },
                    shadow: false,
                    states: {
                        hover: {
                            lineWidth: 1
                        }
                    }
                }
            },
			series : [ {
				name : sname,
				type: 'area',
				data : (function() {

					var length = data.length;
					var temp = new Array();
					for(var i=0;i<length;i++){
					
						temp.push([xaxis[i],data[i]]);
						
					}
					return temp;

				})()
			} ]
		});

	}
	
	function exceptionleveltrac(level){
		
		switch (level) {
		case 1:
			return "严重异常";
			break;
		case 2:
			return "一般异常";
			break;
		case 3:
			return "严重错误";
			break;
		case 4:
			return "一般错误";
			break;
		default:
			return "unkown";
			break;
		}
		
	}
	
	var view = {
			
			exceptionRow:'<tr>'+
				'<td>{id}</td>'+
				'<td>{ip}</td>'+
				'<td>{item}</td>'+
				'<td>{level}</td>'+
				'<td>{message}</td>'+
				'<td><a>详情</a></td>'+
			'</tr>'
			
	}
	
});
