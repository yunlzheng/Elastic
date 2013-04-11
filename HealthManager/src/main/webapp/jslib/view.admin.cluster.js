$(function() {

	$.ajaxSetup({cache:false});
	$(".searchInput:[type='text']").focus(function(){
		var $text = $(".searchInput:[type='text']");
		
		if($text.attr("value") === "集群搜索"){
			$text.css("color","gray");
			$text.attr("value","");
		}
			$text.css("color","black");
		
	});

	getClusters(getClusterSuccess, getClusterError);

	function getClusters(success, error) {

		$.ajax({

			type : "get",
			url : "../rest/thirdparty/clusters",
			success : success,
			error : error,
			timeout : 5000

		});

	}

	function getClusterSuccess(data) {

		if (data == undefined || data.length == 0) {

			$("#clusters")
					.html(
							"<tr align='center'><td colspan='7' align='center'><br/><sapn>没有更多的数据了！</span><br/></td></tr>");
			return;

		} else {

			$("#clusters").empty();

			for ( var i = 0; i < data.length; i++) {

				var id = data[i].id;

				var cluster = view.cluster
						.replace(/{name}/g, data[i].name)
						.replace(/{id}/g, data[i].id)
						.replace(/{category}/g,data[i].asCategory)
						.replace(/{version}/g,data[i].asSerial);

				$("#clusters").append(cluster);
				
			

				getVirtualList(id, getVirtualSuccess, getVirtualError)

			}

			var option = {
				pagesize : 2,
				currentpage : 1,
				target : "#table-footer"
			};

			$("#clusters").qucikPage(option);

		}

	}

	function getClusterError() {

		$("#view_cluster")
				.html(
						"<tr align='center'><td colspan='5' align='center'><br/><sapn>数据加载异常！</span></td></tr>");

	}

	function getVirtualList(id, success, error) {

		var url = "../rest/thirdparty/vm/search/"+id;
		
		$.ajax({

			type : "get",
			url : url,
			success : $.proxy(success, {
				"id" : id
			}),
			error : $.proxy(error, {
				"id" : id
			}),
			timeout : 5000

		});

	}

	function getVirtualSuccess(data) {

		var target = "#cluster-" + this.id;

		if (data == "") {

			$(target)
					.html(
							"<tr><td colspan='5' align='center'><sapn>该集群不包含虚拟机信息！</span></td></tr>")
			return;

		} else {

			$(target).empty();
			for ( var i = 0; i < data.length; i++) {

				var ip = data[i];
				
				if(ip!=0&&ip!=1){
				
					var vm;
					
					if(data[i+1]==1){
					
						vm = view.virtual
								.replace(/{vm_id}/g, i)
								.replace(/{vm_ip}/g, ip)
								.replace(/{vm_type}/g,"动态伸缩")
								.replace(/{vm_status}/g, "runing");
						
					}else if(data[i+1]==0){
						vm = view.virtual
							.replace(/{vm_id}/g, i)
							.replace(/{vm_ip}/g, ip)
							.replace(/{vm_type}/g,"固有虚拟机")
							.replace(/{vm_status}/g, "runing");
					}
					
					$(target).append(vm);
					var that = {
	
						"ip" : ip,
						"vm" : vm
					}
				}
				
			}
			
			$(".class_realtime").click(function(){
				
				var ip = $(this).attr("data");
				var url = "srealtime.html?ip=" + ip;

				var iWidth = 800; //窗口宽度
				var iHeight = 400;//窗口高度
				var iTop = (window.screen.height - iHeight) / 2;
				var iLeft = (window.screen.width - iWidth) / 2;
				window.open(url, "Detail",
						"resizable:no;status:no;scroll:no,location:no, Width="
								+ iWidth + " ,Height=" + iHeight
								+ ",top=" + iTop + ",left=" + iLeft);
				
			});

		}

	}

	function getConfigSuccess(data, textStatus, jqXHR) {

		var ip = this.ip;
		var trip = "tr-" + this.ip;


			$("tr")
					.each(
							function() {

								if ($(this).attr("class") == trip) {

									$(this)
											.find(".td_config")
											.empty();


								}

							});


			

	}

	function getConfigError() {

	}

	function getVirtualError() {

		var target = "#cluster-" + this.id;
		$(target)
				.html(
						"<tr><td colspan='5' align='center'><sapn>虚拟机信息加载异常！</span></td></tr>")

	}

	var view = {

		cluster : '<div class="section"><div class="title container">集群-{name}  [{category}-{version}]</div>'
				+ '<table cellpadding="0" cellspacing="0" class="table">'
				+ '<thead>'
				+ '<tr><th>虚拟机编号</th><th>虚拟机IP地址</th><th>虚拟机类型</th><th>虚拟机状态</th><th style="width:200px">操作</th></tr>'
				+ '</thead>'
				+ '<tbody id="cluster-{id}">'
				+ '<tr>'
				+ '<td colspan="5"><img src="../assets/images/ajax-loader.gif" alt="" /><span>数据加载中</span>'
				+ '</tr>' + '</tbody>' + '</table>' + '</div>',

		virtual : '<tr class="tr-{vm_ip}">'
				+ '<td>{vm_id}</td>'
				+ '<td>{vm_ip}</td>'
				+ '<td>{vm_type}</td>'
				+ '<td><span class="label label-info">{vm_status}</span></td>'
				+ '<td class="td_config"><a class="class_realtime" data="{vm_ip}">实时监控</a>|<a href="sdetail.html?ip={vm_ip}">监控详情</a></td></tr>'
	}
	
	
	/**
	 * 点击查询
	 */
	$(".searchButton:[type='button']").click(function(){
		var check = $(".searchInput:[type='text']").val();
		var reg = /[\/,\\,\[,\],%,&]/;
		var result = check.match(reg);
		if(!result){
			checkList(check,getClusterSuccess,getClusterError);
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
				checkList(check,getClusterSuccess,getClusterError);
			}else{
			   alert("请输入合法内容");
			}
		}
	});
	
	/**
	 * 点击查询处图标
	 */
	$(".searchimg:eq(0)").click(function(){
		/**将输入框清空*/
		$(".searchInput:[type='text']").val("集群搜索");
		$(".searchInput:[type='text']").css("color","gray");
		$(this).css("display","none");
		getClusters(getClusterSuccess, getClusterError);
	});
	

});
