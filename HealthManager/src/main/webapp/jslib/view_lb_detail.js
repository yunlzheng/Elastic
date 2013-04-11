$(function() {

	$.ajaxSetup({cache:false});
	
	var view = {
		ajaxinfo : '<div id="ajaxinfo" class="alert {status}">'
				+ '<strong>{message}</div>'

	};

	/**
	 * 初始化参数
	 * */
	var pxname = $.getParam("pxname");
	var viewmode = $.getParam("type");
	var appname = $.getParam("appname");
	
	if(viewmode==1){
		
		$("#view_mode").text("应用");
		$("#view_mode_title").text("应用");
		$("#view_app_name").text(appname);
		
	}else if(viewmode==2){
		
		$("#view_mode").text("私有环境");
		$("#view_mode_title").text("私有环境");
		$("#view_app_name").text(pxname);
		
	}
	
	var timestamp = new Array();

	var data = {};
	var cur = new Array();
	var max = new Array();
	var total = new Array();

	$.ajax({

		type : "get",
		url : "../rest/lbstatusbackend/key1/" + pxname,
		success : success,
		error : error,
		timeOut : 3000

	});

	function success(data) {

		//console.log(data);
		if (data == undefined || data.length == 0) {
			
			return;
		}

		var length = data.length;

		var _lbstatus = data[0];

		$("#econ").text(_lbstatus["econ"] ? _lbstatus["econ"] : 0);
		$("#ereq").text(_lbstatus["ereq"] ? _lbstatus["ereq"] : 0);
		$("#eresp").text(_lbstatus["eresp"] ? _lbstatus["eresp"] : 0);

		$("#wretr").text(_lbstatus["wretr"] ? _lbstatus["wretr"] : 0);
		$("#wredis").text(_lbstatus["wredis"] ? _lbstatus["wredis"] : 0);

		var bin = _lbstatus["bin"];
		var bout = _lbstatus["bout"];

		var networkData = [ [ '下载', 45.0 ], [ '上传', 26.8 ] ];

		loadPieChart("集群【" + pxname + "】网络流量统计", "chart_network", networkData);

		for ( var i = 0; i < length; i++) {

			var lbstatus = data[i];

			// base
			lbstatus["pxname"];
			lbstatus["svname"];

			// Queue
			lbstatus["qcur"];
			lbstatus["qmax"];
			lbstatus["qlimit"];

			// session rate
			lbstatus["rate"];
			lbstatus["ratelim"];
			lbstatus["ratemax"];

			// Sessions
			var scur = lbstatus["scur"];
			var smax = lbstatus["smax"];
			var slim = lbstatus["slim"];
			var stot = lbstatus["stot"];

			// bytes
			lbstatus["bin"];
			lbstatus["bout"];

			// Denied
			lbstatus["dreq"];
			lbstatus["dresp"];

			// error
			lbstatus["econ"];
			lbstatus["ereq"];
			lbstatus["eresp"];

			// warnings
			lbstatus["wredis"];
			lbstatus["wretr"];

			// server
			lbstatus["status"];
			lbstatus["LastChk"]
			lbstatus["weight"];
			lbstatus["act"];
			lbstatus["bck"];
			lbstatus["downtime"];
			lbstatus["throttle"];

			var t = lbstatus["detailTime"];

			var year = parseInt(t.substring(0, 4));
			var month = parseInt(t.substring(5, 7));
			var day = parseInt(t.substring(8, 10));

			var hours = parseInt(t.substring(11, 13));
			var minutes = parseInt(t.substring(14, 16));
			var seconds = parseInt(t.substring(17, 19));

			var date = Date.UTC(year, month, day, hours, minutes, seconds);

			cur.push(parseInt(scur));
			max.push(parseInt(smax));
			total.push(parseInt(stot));
			timestamp.push(t);

		}
		
		

		timestamp.reverse();
		//console.log(cur);
		//console.log(max);
		//console.log(total);
		loadClumnChart("集群【" + pxname + "】Session统计", timestamp);

		var ajaxinfo = view.ajaxinfo.replace(/{status}/g, "alert-succes")
				.replace(/{message}/g, "load success!");

		$("#ajaxinfo").replaceWith(ajaxinfo);
		$("#ajaxinfo").hide().show(500);
		setTimeout(function() {
			$("#ajaxinfo").hide(500);
		}, 1500);

	}

	function error() {

	}

	function loadPieChart(title, container, data) {

		var chart;
		chart = new Highcharts.Chart({
			chart : {
				backgroundColor: '#F5F7F6',
				renderTo : container,
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			title : {
				text : title
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
				data : data
			} ]
		});

	}

	function loadClumnChart(title, xaxis) {
		/** 图表处理 */
		var chart;
		chart = new Highcharts.Chart({
			chart : {
				backgroundColor: '#F5F7F6',
				renderTo : 'chart_session',
				type : 'column'
			},
			title : {
				text : title
			},
			subtitle : {
				text : 'source icp.lb'
			},
			xAxis : {

				categories : xaxis
			},
			yAxis : {
				min : 0,
				title : {
					text : 'Count'
				}
			},
			legend : {
				layout : 'vertical',
				backgroundColor : '#FFFFFF',
				align : 'left',
				verticalAlign : 'top',
				x : 100,
				y : 70,
				floating : true,
				shadow : true
			},
			tooltip : {
				formatter : function() {
					return '' + this.x + ': ' + this.y + ' 次';
				}
			},
			plotOptions : {
				column : {
					pointPadding : 0.2,
					borderWidth : 0
				}
			},
			series : [ {
				name : '当前Session数',
				data : cur

			}, {
				name : '最大Session数',
				data : max

			}, {
				name : 'Session总数',
				data : total

			} ]
		});

	}

});
