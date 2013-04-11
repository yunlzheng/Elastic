$(function() {

	$.ajaxSetup({cache:false});
	
	var totalClusterCounter;
	var loadDataOver = false;
	var flag = false;
	var url = window.location.search;
	
	$("#clusterSearchInput").focus(function(){
		
		var $text = $(".searchInput:[type='text']");
		if($text.attr("value") === "集群搜索"){
			$text.css("color","gray");
			$text.attr("value","");
		}
			$text.css("color","black");
		
	});
	
	$("#monitorSearchInput").focus(function(){
		
		$(this).attr("value","");
		$(this).css("color","black");
		
	});
	
	$("#monitorSearchButton").click(function(){
		
		var key =$("#monitorSearchInput").val();
		var model = $(this).attr("mode");
		
		if(model=="1"){
			
			$("#monitors").children().each(function(){
				
				if($(this).text().indexOf(key)!=-1){
					
					$(this).show();
					
				}else{
					
					$(this).hide();
					
				}
				
			});
			
			
			$(this).attr("mode","2");
			
		}else{
			
			$("#monitors").children().each(function(){
				
				$(this).show();
				
			});
			
			$("#monitorSearchButton").css("background","url(../assets/images/but_search.png) no-repeat");
			$(this).attr("mode","1");
			$("#monitorSearchInput").attr("value","");
			
		}
		
		
		
	});
	
	
	var username = $.cookie('account');
	getByClusterName(username, getClusterSuccess, getClusterError,"load");
	
	$(".nav-tabs").tabs("#tabs");
	
	function getByClusterName(username, success, error,type) {

		$.ajax({

			type : "get",
			url : "../rest/thirdparty/clusters/search/" + username,
			success : $.proxy(success,{"type":type}),
			error : error,
			timeout : 5000

		});

	}
	function getClusterSuccess(data) {

		var type=this.type;
		
		if (data == undefined || data.length == 0) {

			$("#clusters")
					.html(
							"<tr align='center'><td colspan='7' align='center'><br/><sapn>没有更多的数据了！</span><br/><br/></td></tr>");
			return;

		} else {

			$("#clusters").empty();
			
			totalClusterCounter = data.length;
			
			for ( var i = 0; i < data.length; i++) {

				var id = data[i].id;
				var cluster = view.cluster.replace(/{name}/g, data[i].name)
						.replace(/{id}/g, data[i].id).replace(/{category}/g,
								data[i].asCategory).replace(/{version}/g,
								data[i].asSerial);

				$("#clusters").append(cluster);
				getVirtualList(id, getVirtualSuccess, getVirtualError,type)

				if(i+1==totalClusterCounter){
					loadDatOver = true;
				}
				
			}

			var option = {
				pagesize : 4,
				currentpage : 1,
				target : "#table-footer"
			};

			$("#clusters").qucikPage(option);

		}
		
	

	}

	function getClusterError() {

		$("#view_cluster")
				.html(
						"<tr align='center'><td colspan='4' align='center'><br/><sapn>数据加载异常！</span><br/><br/></td></tr>");

	}

	function getVirtualList(id, success, error,type) {

		var url = "../rest/thirdparty/vm/search/"+id;
		$.ajax({

			type : "get",
			url : url,
			success : $.proxy(success, {
				"id" : id,
				"type":type
			}),
			error : $.proxy(error, {
				"id" : id
			}),
			timeout : 5000

		});

	}

	function getVirtualSuccess(data) {

		var type = this.type;
		var target = "#cluster-" + this.id;

		if (data == "") {

			$(target)
					.html(
							"<tr><td colspan='4' align='center'><sapn>该集群不包含虚拟机信息！</span></td></tr>")
			return;

		} else {

			$(target).empty();
			for ( var i = 0; i < data.length; i++) {

				var ip = data[i];
				if(ip!="1"&&ip!="0"){
					
					var vm = view.virtual.replace(/{vm_id}/g, i).replace(
							/{vm_ip}/g, ip).replace(/{vm_status}/g, "runing");
					
					$(target).append(vm);
	
					var that = {
	
						"ip" : ip,
						"vm" : vm,
						"type":type
					}
	
					MonitorConfig.get(ip, getConfigSuccess, getConfigError, that);
				
				}
				
			}

		}

	}
	
	var count = 0;

	function getConfigSuccess(data, textStatus, jqXHR) {

		var ip = this.ip;
		var trip = "tr-" + this.ip;	
		var type=this.type;
		
			if (data == null) {
	
				$("tr")
						.each(
								function() {
	
									if ($(this).attr("class") == trip) {
	
										$(this).addClass("noconfig");
										$(this)
												.find(".td_config")
												.empty()
												.append(
														'<a  href="serverconfig.html?ip='+ip+'">开通服务器监控服务</a>');
	
									}
	
								});
	
			} else {
	
				$("tr").each(function() {
	
					if ($(this).attr("class") == trip) {
	
						if(type=="load"){
							
							$("#monitors").append(view.mvirtual
									.replace(/{vm_ip}/g, ip)
									.replace(/{vm_id}/g,++count)
									.replace(/{vm_status}/g,"runing"));
							
						}
						
						$(this).find(".td_config").empty().append('<a href="sdetail.html?ip='+ip+'">监控详情</a>').append("&nbsp;&nbsp;|&nbsp;&nbsp;").append('<a href="serverconfig.html?ip='+ip+'">修改监控设置</a>');
						
						var option = {
								pagesize : 4,
								currentpage : 1,
								target : "#monitors-table-footer"
							};
	
						$("#monitors").qucikPage(option);
						
					}
				
					
	
				});
	
			}
		
	}

	function getConfigError() {

	}

	function getVirtualError() {

		var target = "#cluster-" + this.id;
		$(target)
				.html(
						"<tr><td colspan='4' align='center'><sapn>虚拟机信息加载异常！</span></td></tr>")

	}

	var view = {

		cluster : '<div class="section"><div class="title container"><span clusterid="{id}">{name}</span></div>'
				+ '<table cellpadding="0" cellspacing="0" class="table">'
				+ '<thead>'
				+ '<tr><th>虚拟机编号</th><th>虚拟机IP地址</th><th>虚拟机状态</th><th style="width:200px">操作</th></tr>'
				+ '</thead>'
				+ '<tbody id="cluster-{id}">'
				+ '<tr>'
				+ '<td colspan="4"><img src="../assets/images/ajax-loader.gif" alt="" /><span>数据加载中</span>'
				+ '</tr>' + '</tbody>' + '</table>' + '</div>',

		virtual : '<tr class="tr-{vm_ip}">'
				+ '<td>{vm_id}</td>'
				+ '<td>{vm_ip}</td>'
				+ '<td><span class="label label-info">{vm_status}</span></td>'
				+ '<td class="td_config"></td></tr>',
				
		mvirtual : '<tr class="mtr-{vm_ip}">'
					+ '<td>{vm_id}</td>'
					+ '<td>{vm_ip}</td>'
					+ '<td><span class="label label-info">{vm_status}</span></td>'
					+ '<td class="td_config"><a href="sdetail.html?ip={vm_ip}">监控详情</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="serverconfig.html?ip={vm_ip}">修改监控设置</a></td></tr>'
	}
	

	
	/**
	 * 点击查询
	 */
	$("#clusterSearchButton").click(function(){
		
		var check = $(".searchInput:[type='text']").val();
		var reg = /[\/,\\,\[,\],%,&]/;
		var result = check.match(reg);
		if(!result){
			checkList(username,check,getClusterSuccess,getClusterError);
		}else{
		   alert("请输入合法内容");
		}
		
	});
	
	/**
	 * 隐藏或显示搜索图片
	 */
	
	$(".searchInput:[type='text']").blur(function(){
		if($(this).val() != ""){
			$(".searchimg:eq(0)").css("display","inline");
		}
		
		if($(this).val() == ""){
			$(this).val("集群搜索");
			$(this).css("color","gray");
		}
		
	});
	
	$(".searchInput:[type='text']").bind('keyup',function(event){
		if(event.keyCode == 13){
			
			var check = $(".searchInput:[type='text']").val();
			var reg = /[\/,\\,\[,\],%,&]/;
			var result = check.match(reg);
			if(!result){
				checkList(username,check,getClusterSuccess,getClusterError);
			}else{
			   alert("请输入合法内容");
			}
		}
	});
	
	/**
	 * 点击查询处图表
	 */
	$(".searchimg:eq(0)").click(function(){
		
		/**将输入框清空*/
		$(".searchInput:[type='text']").val("集群搜索");
		$(".searchInput:[type='text']").css("color","gray");
		$(this).css("display","none");
		getByClusterName(username, getClusterSuccess, getClusterError,"update");
	
	});
	
	
	/**
	 * 添加页面跳转的参数
	 */
	$(".td_config:eq(0)").find("a:eq(1)").live("click",function(e){
		e.preventDefault();
		var thisurl = this+"&server";
		self.location = thisurl;
	});
	
	$(".td_config:eq(1)").find("a:eq(1)").live("click",function(e){
		e.preventDefault();
		var thisurl = this+"&moniter";
		self.location = thisurl;
	});
	
	/**
	 * 返回正确的页面
	 */
	(function checkurl(){

		if(url.indexOf("moniter") != -1){
			flag = true;
			
		}
		if(url.indexOf("SERVER") != -1){
			flag = false;
		}
		
		if(flag){
			$("li[tab-data='monitoring']").attr("class","tab active ").click();
			$("li[tab-data='servers']").attr("class","tab");
		}else{
			$("li[tab-data='monitoring']").attr("class","tab");
			$("li[tab-data='servers']").attr("class","tab active").click();
		}
	}());
	
	/**
	 * 齐群详细信息展示
	 * */
	$("[clusterid]").live("mouseover",function(event){
		
		$("#clusterDetailDialog").find(".loading").show();
		$("#clusterDetailDialog").find(".information").empty().hide();
		
		var clusterid= $(this).attr("clusterid");
		var x = event.clientX+5;
		var y = event.clientY-50;
		$("#clusterDetailDialog").animate({"top":y,"left":x},0);
		$("#clusterDetailDialog").fadeIn(600);
		getClusterInfo(clusterid,getClusterInfoSuccess,getClusterInfoError);
		
	}).live("mouseout",function(){
		
		$("#clusterDetailDialog").hide();
		
	});
	
	$("#clusterDetailDialog").find(".close").click(function(){
		
		$("#clusterDetailDialog").hide();
		$("#clusterDetailDialog").find(".information").empty();
		
	});
	
	function getClusterInfo(id,success,error){
	
		var url = "../rest/thirdparty/clusters/"+id;
		$.ajax({

			type : "GET",
			url : url,
			success : success,
			error : error,
			timeout : 3000

		});
			
	}
	
	function getClusterInfoSuccess(data){
		
		//console.log(data);
		var info = CLUSTERVIEW.clusterDetail
			.replace(/{name}/g,data["name"])
			.replace(/{id}/g,data["id"])
			.replace(/{create_datetime}/g,data["createDateTime"])
			.replace(/{min}/g,data["min"])
			.replace(/{max}/g,data["max"])
			.replace(/{http_port}/g,data["httpPort"])
			.replace(/{https_port}/g,data["httpsPort"])
			.replace(/{cpu}/g,data["cpu"])
			.replace(/{disk}/g,data["disk"])
			.replace(/{mem}/g,data["mem"])
			.replace(/{os_image}/g,data["osImage"])
			.replace(/{description}/g,data["description"])
			.replace(/{as_category}/g,data["asCategory"])
			.replace(/{as_serial}/g,data["asSerial"])
			.replace(/{pxname}/g,data["pxname"]);
		setTimeout(function(){
			
			$("#clusterDetailDialog").find(".loading").hide();
			$("#clusterDetailDialog").find(".information").empty().append(info).fadeIn(500);
			
		},1000);
		
		
	}
	
	function getClusterInfoError(){
		
	}
	
	var CLUSTERVIEW = {
			
			clusterDetail:'<div class="logo"><img src="../assets/images/ubuntu_48.png"/></div>'+
				'<div class="item"><h2>{name} 详情</h2></div>'+
				'<div class="item"><label>域名:</label><span>{pxname}</span></div>'+
				'<div class="item"><label>创建时间:</label><span>{create_datetime}</span></div>'+
				'<div class="item"><label>集群范围:</label><span>{min}~{max}</span></div>'+
				'<div class="item"><label>集群类型:</label><span>{as_category}-{as_serial}</span></div>'+
				'<div class="item"><label>硬件信息:</label><span>处理器:{cpu}HZ;磁盘:{disk}G;内存：{mem}M</span></div>'+
				'<div class="item"><label>端口信息:</label><span>http端口:{http_port};https端口:{https_port}</span></div>'+
				'<div class="item"><label>系统镜像:</label><span>{os_image}</span></div>'+
				'<div class="item"><label>描述信息:</label><span>{description}</span></div>'
			
	}
	
	
	
	
	
});
