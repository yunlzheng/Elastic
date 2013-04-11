$(function(){
	
	/**初始化数据*/
	var username = $.cookie('account');
	
	/**其它事件*/
	$("#view-tabs").tabs("#tabs");
	
	$(".confifopt a").live("click",function(){
		
		unblockParent();
		return true;
		
	});
	
	/**
	 * 搜索相关事件监听
	 * */
	$("#clusterSearchInput").bind("focus",function(){
		
		$("#clusterSearchInput").attr("value","");
		
	});
	$("#clusterSearchButton").bind("click",searchCluster);
	
	getClusters(username,getClustersSuccess,getClusterError);
	
	function searchCluster(){
		
		var searchKey = $("#clusterSearchInput").attr("value");
		var hasresult = false;
		
		$("#view_searchkey").text('"'+searchKey+'"的查询');
		
		$("#view-result").empty();
		
		$(".unit").each(function(){
			
			var temp = $(this).clone();
			var ss = $(this).find("h2").text();
			
			var result = ss.indexOf(searchKey);
			if(result!=-1){
				
				$(temp).appendTo("#view-result").bind("click",showVmList);
				hasresult=true;
			}
			
		});
		
		if(hasresult){
			
			$("#view-tab-result").show();
			var option = {
					
					pagesize : 8,
					currentpage : 1,
					target : "#result-table-footer"
				
				};

			$("#view-result").qucikPage(option);
			$("#view-tab-result").click();
			
			
		}else{
			
			$("#view-result").empty().append("<h2>未查询到相关数据</h2>");
			$("#view-tab-result").click();
		}
		
	}
	
	function getClusters(username,success,error){
		
		$.ajax({

			type : "get",
			url : "../rest/thirdparty/clusters/search/" + username,
			success : success,
			error : error,
			timeout : 5000

		});
		
	}
	
	function getClustersSuccess(data){
		
		if(data!=null){
			
			$("#view-cluster").empty();
			
			for(var i=0;i<data.length;i++){
				
				var cluster = data[i];
				
				var description = cluster.description.substring(0,30)+"...";
			
				
				var unit = view.unit
					.replace(/{id}/g,cluster.id)
					.replace(/{clustername}/g,cluster.name)
					.replace(/{system}/g,"suse")
					.replace(/{domain}/g,cluster.pxname)
					.replace(/{min}/g,cluster.min)
					.replace(/{max}/g,cluster.max)
					.replace(/{desc}/g,description);
				
				$(unit).appendTo("#view-cluster").bind("click",showVmList);
				
				var option = {
						
					pagesize : 8,
					currentpage : 1,
					target : "#cluster-table-footer"
				
				};

				$("#view-cluster").qucikPage(option);
				
				
			}
			
		}else{
			
			$("#view-cluster").empty();
			var unit = view.unit;
			$("#view-cluster").append(unit);
			
		}
	}
	
	function getClusterError(){
		
		$("#view-cluster").empty();
		var unit = view.unit
			.replace(/{clustername}/g,"未申请集群")
			.replace(/{system}/g,"windows")
			.replace(/{domain}/g,"未分配域名")
			.replace(/{min}/g,0)
			.replace(/{max}/g,0)
			.replace(/{desc}/g,"还未申请集群，赶快行动!!!!!");
		$("#view-cluster").append(unit);
		
	}
	
	
	function getVirtualList(id, success, error) {

		var url = "../rest/thirdparty/vm/search/"+id;
		$.ajax({

			type : "get",
			url : url,
			success : success,
			error : error,
			timeout : 5000

		});
		$("#view_vm").empty().append('<tr><td colspan="5">数据加载中...</td></tr>');

	}
	
	function getVirtualListSuccess(data){
		
		if(data==null||data==""||data.length==0){
			
			$("#view_vm").empty().append('<tr><td colspan="5">未找到相应记录..</td></tr>');
			
		}else{
			
			$("#view_vm").empty();
			for ( var i = 0; i < data.length; i++) {
				
				if(data[i]!=0&&data[i]!=1){
					var ip = data[i];
					var type=data[i+1];
					
					var vm;
					
					if(type==1){
					
						vm = view.vm
							.replace(/{index}/,(i+1))
							.replace(/{ip}/g,ip)
							.replace(/{type}/,"动态伸缩");
						
					}else if(type==0){
						
						vm = view.vm
							.replace(/{index}/,(i+1))
							.replace(/{ip}/g,ip)
							.replace(/{type}/,"固有虚拟机");
						
					}else{
					    vm = view.vm
					    	.replace(/{index}/,(i+1))
					    	.replace(/{ip}/g,ip)
					    	.replace(/{type}/,"未知");
						
					}
					
					$("#view_vm").append(vm);
					getVMConfig(ip,getVMConfigSuccess,getVMConfigError);
				}
				
			}
			
			var option = {
					pagesize : 10,
					currentpage : 1,
					target : "#table-footer"
			};

			$("#view_vm").qucikPage(option);
			
		}
		
	}
	
	function getVirtualListError(){
		
		$("#view_vm").empty().append('<tr><td colspan="5">数据加载异常...</td></tr>');
		
	}

	function getVMConfig(ip,success,error){
		
		$.ajax({
			dataType:"json",
			type : "get",
			url : "../rest/monitorconfig/" + ip,
			success : success,
			error : error,
			timeOut : 5000

		});
		
	}
	
	function getVMConfigSuccess(data){
		
		if(data==null||data==""){
			
		}else{
			
			var ip = data.ip;
			$("[configdata='"+ip+"']").empty().append('<a href="sdetail.html?ip='+ip+'">监控详情</a>').append("&nbsp;&nbsp;|&nbsp;&nbsp;").append('<a href="serverconfig.html?ip='+ip+'">修改监控设置</a>');
		}
		
	}
	
	function getVMConfigError(){
		
	}
	
	
	
	/**
	 * 弹出层显示虚拟机列表详情
	 * */
	function showVmList(){
		
		$("#view_clustername").text($(this).attr("clustername"));
		var clusterId = $(this).attr("data");
		$.blockUI({
			message:$('#view-clusters'),
			fadeIn:  0, 
			fadeOut:  0,
			css: {
				padding:	'20px',
				margin:		0,
				width:		'900px',
				height:		'500px',
				top:		'2%',
				left:		'2%',
				textAlign:	'left',
				color:		'#000',
				border:		'0px solid #ccc',
				borderRadius: '10px',
				backgroundColor:'#fff',
				cursor:		'default'
			},
			overlayCSS:  {
				backgroundColor:	'#000',
				opacity:			 0.6,
				cursor:				'default'
			}
		});
		blockParent();
		
		$(".unitclose").click(function(){
			
			$.unblockUI({
				fadeIn:  0, 
				fadeOut:  0});
			unblockParent();
			
		});
		
		getVirtualList(clusterId,getVirtualListSuccess,getVirtualListError);
		
	}
	
	var view = {
			
			unit:'<div class="unit" title="点击查看集群详情" data="{id}" clustername={clustername}>'+
				'<div class="logo {system}"></div>'+
				'<div class="util_title"><h2>{clustername}</h2></div>'+
				'<div class="one first">'+
					'<label>域名：</label><span>{domain}</span>'+
				'</div>'+
				'<div class="one">'+
					'<label>伸缩范围：</label><span>{min}~{max}</span>'+
				'</div>'+
				'<div class="one">'+
					'<label>描述：</label><span class="bigtext">{desc}</span>'+
				'</div>'+
			'</div>',
			
			vm:'<tr><td>{index}</td><td>{ip}</td><td>运行中</td><td>{type}</td><td style="width:200px" class="confifopt" configdata={ip}><a href="serverconfig.html?ip={ip}">开通服务器监控服务</a></td></tr>'
			
	}
	
});