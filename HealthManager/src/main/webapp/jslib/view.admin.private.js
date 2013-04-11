$(function(){
	
	$.ajaxSetup({cache:false});
	var pxname = $.getParam("pxname");
	
	$("#view_private_name").text(pxname);
	
	$("#nav-tabs").tabs("#tabs");

	function getClusterDetail(id,success,error){
		
		var url = "../rest/thirdparty/clusters/"+id;
		
		$.ajax({

			type : "get",
			url : url,
			success : success,
			error : error,
			timeout : 5000

		});
		
	}
	
	function getClusterDetailSuccess(data){
		
		if(data!=null){
			
			var clusterinfo = '<div id="clusterinfo">'+
				'<div class="citem"><label>名称&nbsp;:&nbsp;</label><span class="label">{name}</span></div>'+
				'<div class="citem"><label>创建者&nbsp;:&nbsp;</label><span class="label">{creater}</span></div>'+
				'<div class="citem"><label>域名&nbsp;:&nbsp;</label><span class="label">{domain}</span></div>'+
				'<div class="citem"><label>创建时间&nbsp;:&nbsp;</label><span class="label">{createtime}</span></div>'+
				'<div style="clear:both;"/>'+
				'<div><label>LB监控key&nbsp;:&nbsp;</label><span >{lbkey}</span></div>'+
				'<div><label>描述&nbsp;:&nbsp;</label><span >{desc}</span></div>'+
				'</div>';
			
			clusterinfo = clusterinfo.replace(/{name}/g,data["name"])
				.replace(/{desc}/g,data["description"])
				.replace(/{domain}/g,data["pxname"])
				.replace(/{createtime}/g,data["createDateTime"])
				.replace(/{lbkey}/g,pxname)
				.replace(/{creater}/g,data["userName"])
				.replace(/{name}/g,data["name"]);
			
			$("#section_title").append(clusterinfo);
		
		}
	}
	
	function getClusterDetailError(){}
	
	loadChartsInfo(pxname,loadChartsInfoSuccess,loadChartsInfoError)
	
	/**
	 * load chart info
	 * */
	function loadChartsInfo(pxname,success,error){
		
		$.ajax({

			type : "get",
			url : "../rest/lbstatusbackend/key1/" + pxname,
			success : success,
			error : error,
			timeOut : 3000

		});
		
		
	}
	
	function loadChartsInfoSuccess(data){
		
		if(data!=null&&data.length!=0){
			
			var _lbstatus = data[0];
			
			var bin = parseInt(_lbstatus["bin"]);
			var bout = parseInt(_lbstatus["bout"]);
			
			var scur = parseInt(_lbstatus["scur"]);
			var smax = parseInt(_lbstatus["smax"]);
			var slim = parseInt(_lbstatus["slim"]);
			var stot = parseInt(_lbstatus["stot"]);
			
			// error
			var econ = parseInt(_lbstatus["econ"]?_lbstatus["econ"]:0);
			var eeqr = parseInt(_lbstatus["ereq"]?_lbstatus["ereq"]:0);
			var eresp = parseInt(_lbstatus["eresp"]?_lbstatus["eresp"]:0);
			
			var qcur = parseInt(_lbstatus["qcur"]?_lbstatus["qcur"]:0);
			var qmax = parseInt(_lbstatus["qmax"]?_lbstatus["qmax"]:0);
			
			var clusterid = _lbstatus["clutsterId"];
			
			//获取集群相关信息
			getClusterDetail(clusterid,getClusterDetailSuccess,getClusterDetailError);
			
			
			loadBytesChart(bin,bout);
			loadSessionChart(scur,smax,stot);
			loadErrorChart(econ,eeqr,eresp);
			loadQueueChart(qcur,qmax);
			
			
			
		}else{
			
			setTimeout(function(){
				
				$(".ajaxinfo").hide();
				loadBytesChart(0,0);
				loadSessionChart(0,0,0);
				loadErrorChart(0,0,0);
				loadQueueChart(0,0);
				
			},1000);
			
			
		}
		
	}
	
	function loadChartsInfoError(){
		
		loadBytesChart(0,0);
		loadSessionChart(0,0,0);
		loadErrorChart(0,0,0);
		loadQueueChart(0,0);
		
	}
	
	/**charts*/
	function loadBytesChart(bin,bout){
		
		var byteschart = new Highcharts.Chart({
            chart: {
                renderTo: 'charts-bytes',
                type: 'bar'
            },
            title: {
                text: '流量统计'
            },
            subtitle: {
                text: 'Source: icp.haproxy'
            },
            xAxis: {
                categories: ['用户请求流量','服务器响应流量'],
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'bytes',
                    align: 'high'
                },
                labels: {
                    overflow: 'justify'
                }
            },
            tooltip: {
                formatter: function() {
                    return ''+
                        this.series.name +': '+ this.y +' kb';
                }
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -100,
                y: 100,
                floating: true,
                borderWidth: 1,
                backgroundColor: '#FFFFFF',
                shadow: true
            },
            credits: {
                enabled: false
            },
            series: [{
                name: '流量统计',
                data: [bin, bout]
            }]
        });
		
	}
	
	/**charts*/
	function loadSessionChart(cur,max,total){
		
		var sessionchart = new Highcharts.Chart({
            chart: {
                renderTo: 'charts-sessions',
                type: 'bar'
            },
            title: {
                text: 'Session统计'
            },
            subtitle: {
                text: 'Source: icp.haproxy'
            },
            xAxis: {
                categories: ['当前','最大','总量'],
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: '访问量(次)',
                    align: 'high'
                },
                labels: {
                    overflow: 'justify'
                }
            },
            tooltip: {
                formatter: function() {
                    return ''+
                        this.series.name +': '+ this.y +' 次';
                }
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -100,
                y: 100,
                floating: true,
                borderWidth: 1,
                backgroundColor: '#FFFFFF',
                shadow: true
            },
            credits: {
                enabled: false
            },
            series: [{
                name: 'Session统计',
                data: [cur,max,total]
            }]
        });
		
	}
	
	function loadErrorChart(econ,eeqr,eresp){
		
		 var errorChart = new Highcharts.Chart({
	            chart: {
	                renderTo: 'charts_error',
	                type: 'column'
	            },
	            title: {
	                text: '异常情况统计'
	            },
	            subtitle: {
	                text: 'source icp.haproxy'
	            },
	            xAxis: {
	                categories: ['错误连接数','错误请求数','错误响应数']
	            },
	            yAxis: {
	                title: {
	                    text: '次数(/次)'
	                }
	            },
	            plotOptions: {
	                column: {
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: true,
	                        style: {
	                            fontWeight: 'bold'
	                        },
	                        formatter: function() {
	                            return this.y +'次';
	                        }
	                    }
	                }
	            },
	            
	            series: [{
	                name: "异常统计信息",
	                data: [econ,eeqr,eresp],
	            }],
	            exporting: {
	                enabled: false
	            }
	        });
		
	}
	
	function loadQueueChart(cur,max){
		
		var byteschart = new Highcharts.Chart({
            chart: {
                renderTo: 'charts_queue',
                type: 'bar'
            },
            title: {
                text: '服务器待处理请求队列'
            },
            subtitle: {
                text: 'Source: icp.haproxy'
            },
            xAxis: {
                categories: ['当前队列数','队列最大数'],
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'bytes',
                    align: 'high'
                },
                labels: {
                    overflow: 'justify'
                }
            },
            tooltip: {
                formatter: function() {
                    return ''+
                        this.series.name +': '+ this.y +' ';
                }
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -100,
                y: 100,
                floating: true,
                borderWidth: 1,
                backgroundColor: '#FFFFFF',
                shadow: true
            },
            credits: {
                enabled: false
            },
            series: [{
                name: '服务器待处理请求队列',
                data: [cur,max]
            }]
        });
		
	}
	
});