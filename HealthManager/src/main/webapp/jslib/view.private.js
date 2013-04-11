$(function(){
	
	//是有环境信息
	//?pxname=PRI_12_247de5b4-332f-4bc8-8495-c7f993e9d54fapp.wocloud.com&clusterid=12
	//应用信息
	//?pxname=PUB_12_12_247de5b4-332f-4bc8-8495-c7f993e9d54fapp.wocloud.com&appname=12&type=1
	
	var pxname = $.getParam("pxname");
	var clusterid = $.getParam("clusterid");
	var type=$.getParam("type");
	
	var domain = pxname.split("_")[2];
	
	if(type!=null||type!=undefined){
		
		var appname = $.getParam("appname");
		$("#view-nav-top").text("应用详情");
		$("#view-page-title").text("应用基本信息");
		$(".tab[tab-data='tab-list']").remove();
		$("#view_private_name").text(appname);
		
	
	}else{
		
		$("#view_private_name").text(pxname);
		
		
	}
	
	if(clusterid!=null||clusterid!=undefined){
		
		if(clusterid.indexOf("#")!=-1){
			clusterid = clusterid.substring(0,clusterid.indexOf("#"));
		}
		getClusterDetail(clusterid,getClusterDetailSuccess,getClusterDetailError);
		
	}else{
		
		$("#cluster-infos").remove();
		$(".tab[tab-data='tab-list']").remove();
		
	}
	
	
	$("#nav-tabs").tabs("#tabs");
	
	/**事件监听*/
	$(".tab").bind("click",function(){
		
		var tab = $(this).attr("tab-data");
		if(tab=="tab-list"){
			
			getDeployArtifactsByCluster(clusterid,getDeployArtifactsByClusterSuccess,getDeployArtifactsByClusterError);
			
		}
		
	});
	
	
	
	
	
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
			
			////console.log(data);
			$("#view_server_jointime").text(data.createDateTime);
			$("#view_asCatrgory").text(data.asCategory);
			$("#view_asVersion").text(data.asSerial);
			$("#view_domain_name").text(data.pxname);
			
			getDeployArtifactsByCluster(clusterid,getDeployArtifactsByClusterSuccess,getDeployArtifactsByClusterError);
		
		}
	}
	
	function getClusterDetailError(){}
	
	function getDeployArtifactsByCluster(id,success,error){
		
		var url = "../rest/artifacts/clusters/deploy/"+id;
		
		$("#view-apps-table").hide();
		$("#view-apps-loadinfo").show();
		
		$.ajax({

			type : "get",
			url : url,
			success : success,
			error : error,
			timeout : 5000

		});
		
	}
	
	function getDeployArtifactsByClusterSuccess(data){
		
		if(data!=null&&data.length!=0){
			
			$("#view-apps-tbody").empty();
			for(var i=0;i<data.length;i++){
				
				var artifact = data[i];
				var artifact_tr = view.artifact
					.replace(/{id}/g,(i+1))
					.replace(/{name}/,artifact.name)
					.replace(/{time}/,artifact.createDate)
					.replace(/{status}/,'<span class="label label-success">运行中</span>')
					.replace(/{version}/,artifact.serial)
					.replace(/{url}/,"http://"+domain+"/"+artifact.name);
				$("#view-apps-tbody").append(artifact_tr);
			}
			
			$("#view-apps-table").show();
			$("#view-apps-loadinfo").hide();
			
			
		}else{
			
			$("#view-apps-loadinfo").hide();
			$("#view-apps-table").show();
			
			
		}
	}
	
	function getDeployArtifactsByClusterError(){
		
	}
	
	var view={
			
		artifact:"<tr><td>{id}</td><td>{name}</td><td>{time}</td><td>{version}</td><td>{status}</td><td><a href='{url}' target='_block' title='点击访问应用程序'><i class='icon-search'></i>打开连接</a></td></tr>"
			
	}
	
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
		
		//console.log(data);
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
			
			loadBytesChart(bin,bout);
			loadSessionChart(scur,smax,stot);
			loadErrorChart(econ,eeqr,eresp);
			loadQueueChart(qcur,qmax);
		}else{
			
			loadBytesChart(0,0);
			loadSessionChart(0,0,0);
			loadErrorChart(0,0,0);
			loadQueueChart(0,0);
			
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
	                            return this.y;
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