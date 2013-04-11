var setting = {
		
		view: {
			selectedMulti: false
		},
		data: {
			key: {
				showTitle: false
			},
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			beforeCollapse: beforeCollapse,
			beforeExpand: beforeExpand,
			onCollapse: onCollapse,
			onExpand: onExpand
		}
	};

	function beforeClick(treeId, treeNode) {
		
		if (treeNode.isParent) {
			return true;
		} else {
			if(treeNode.name=="详细信息"){
				
				if(mode==1){
					$(".dom").hide();
					$("#cluster-detail-dom-"+treeNode.pId).show();
				}else if(mode==2){
					
					var temp = $("#cluster-detail-dom-"+treeNode.pId).html();
					var node = $("<div class='clusterview'/>").append(temp).show();
					$("#result-cluster-containter").empty().append(node);
					
				}
				
			}else if(treeNode.name=="虚拟机列表"){
				
				if(mode==1){
					
					$(".dom").hide();
					$("#vm-list-dom-"+treeNode.pId).show();
				
				}else if(mode==2){
					
					var temp = $("#vm-list-dom-"+treeNode.pId).html();
						temp = temp.replace(/{result-id}/g,treeNode.pId);
						
						
					var node = $("<div class='clusterview'/>").append(temp).show();
					$("#result-cluster-containter").empty().append(node);
					
					//分页处理
					var target = "#result-vm-list-"+treeNode.pId;
					var footer = "#result-vm-list-foot-"+treeNode.pId;
					
					var result_option = {
							pagesize : 10,
							currentpage : 1,
							target : footer
					};

					$(target).qucikPage(result_option);
					
				}
				
			}
			return false;
		}
		
	}
	function beforeCollapse(treeId, treeNode) {
		return (treeNode.collapse !== false);
	}
	function onCollapse(event, treeId, treeNode) {
		
	}		
	function beforeExpand(treeId, treeNode) {
		
		return (treeNode.expand !== false);
	}
	function onExpand(event, treeId, treeNode) {
	
	}

	function getVirtualList(id,success, error) {
		
		var url = "../rest/thirdparty/vm/search/"+id;
		$.ajax({

			type : "get",
			url : url,
			success : $.proxy(success,{id:id}),
			error : error,
			timeout : 5000

		});
		$("#view_vm").empty().append('<tr><td colspan="5">数据加载中...</td></tr>');
		
	}
	
	function getVirtualListSuccess(data){
		
		var clusterid = this.id;
		var target = "[vmlist='cluster-vm-dom-"+clusterid+"']";
		var footer = "[vmlistfoot='view-vm-dom-fotter-"+clusterid+"']";

		if(data==null||data==""||data.length==0){
			
			$(target).empty().append('<tr><td colspan="5">未找到相应记录..</td></tr>');
			
		}else{
			
			$(target).empty();
			for ( var i = 0; i < data.length; i++) {
				
				if(data[i]!=0&&data[i]!=1){
					
					var ip = data[i];
					var type=data[i+1];
					
					/**
					 * 更新监控列表的虚拟机状态
					 * */
					$("#result_view_vm_monitor").children().each(function(){
						
						var temp = $(this).html();
						
						if(temp.indexOf(ip)!=-1){
							
							
							temp = temp.replace(/已失效/g,"运行中")
								.replace(/label-important/g,"label-success");
							
						}
						
						$(this).empty().append(temp);
						
					});
					
					var vm;
					
					if(type==1){
					
						vm = view.vm
							.replace(/{index}/,(i+1))
							.replace(/{ip}/g,ip)
							.replace(/{type}/,'<span class="label label-warning">动态伸缩</span>');
						
					}else if(type==0){
						
						vm = view.vm
							.replace(/{index}/,(i+1))
							.replace(/{ip}/g,ip)
							.replace(/{type}/,'<span class="label label-success">固有虚拟机</span>');
						
					}else{
					    vm = view.vm
					    	.replace(/{index}/,(i+1))
					    	.replace(/{ip}/g,ip)
					    	.replace(/{type}/,'<span class="label label-warning">未知</span>');
						
					}
					
					$(target).append(vm);
					getVMConfig(ip,getVMConfigSuccess,getVMConfigError);
				}
				
			}
			
			var option = {
					pagesize : 10,
					currentpage : 1,
					target : footer
			};

			$(target).qucikPage(option);
			
		}
		
	}
	
	function getVMConfig(ip,success,error){
		
		$.ajax({
			dataType:"json",
			type : "get",
			url : "../rest/monitorconfig/" + ip,
			success : $.proxy(success,{ip:ip}),
			error : error,
			timeOut : 5000

		});
		
	}
	
	function getVMConfigSuccess(data){
		
		
		if(data==null||data==""){
			
		}else{
			var ip = data.ip;
			$("[configdata='"+ip+"']").empty().append('<a href="sdetail.html?ip='+ip+'"><i class="icon-list-alt"></i>监控详情</a>').append("&nbsp;&nbsp;").append('<a href="serverconfig.html?ip='+ip+'"><i class="icon-cog"></i>修改监控设置</a>');
		}
		
	}
	
	function getVMConfigError(){
		
	}
	
	function showClusterDetail(name){
		
		$("#block_cluster_name").text(name);
		
		if(blockParent()){}
		$.blockUI({
			message:$('#view-clusters'),
			fadeIn:  0, 
			fadeOut:  0,
			css: {
				padding:	'20px',
				margin:		0,
				width:		'550px',
				height:		'300px',
				top:		'20%',
				left:		'20%',
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
		
		
		
		$(".unitclose").click(function(){
			
			$.unblockUI({
				fadeIn:  0, 
				fadeOut:  0});
			unblockParent();
			
		});
		
	}
	
	function getClusterDetail(id,success,error){
		
		var url = "../rest/thirdparty/clusters/"+id;
		$.ajax({

			type : "get",
			url : url,
			success : $.proxy(success,{id:id}),
			error : error,
			timeout : 5000

		});
		
	}
	
	function getClusterDetailSuccess(data){
		
		var clusterid = this.id;
		var target = "#cluster-detail-dom-"+clusterid;
		var temp = $(target).html();
		
		if(data!=null){
			
			var detail = temp
				.replace(/{tenantName}/g,data.tenantName)
				.replace(/{userName}/g,data.userName)
				.replace(/{domain}/g,data.pxname)
				.replace(/{asCategory}/g,data.asCategory)
				.replace(/{asVersion}/g,data.asSerial)
				.replace(/{min}/g,data.min)
				.replace(/{max}/g,data.max)
				.replace(/{cpu}/g,data.cpu)
				.replace(/{mem}/g,data.mem)
				.replace(/{disk}/g,data.disk)
				.replace(/{httpport}/g,data.httpPort)
				.replace(/{httpsport}/g,data.httpsPort)
				.replace(/{description}/g,data.description);
			$(target).empty().append(detail);
		}
		
	}
	function getClusterDetailError(){
		
		
		
	}
	
	function getVirtualListError(){
		
		$("#view_vm").empty().append('<tr><td colspan="5">数据加载异常...</td></tr>');
		
	}
	
	function searchCluster(){
		
		$("#view-tab-result").show();
		$("#result_section").hide();
		var searchKey = $("#clusterSearchInput").attr("value");
		
		var hasresult = false;
		if(searchKey == "集群名称"){
			searchKey = "";
		}
		$("#view_searchkey").text('"'+searchKey+'"的查询');
		
		$("#view-result").empty();
		
		if(searchTree){
			
			var nodes = searchTree.getNodes();
			for(var i=0;i<nodes.length;i++){
				
				var node = nodes[i];
				
				
				if(node.name.indexOf(searchKey)!=-1){
					//searchTree.expandNode(node);
					searchTree.showNode(node);
					hasresult=true;
				
				}else{
				
					searchTree.hideNode(node);
				}
				
			}
		
		
		}
		
		
		if(hasresult){
			
			$("#zTreeSearch").show();
			$("#view-tab-result").click();
			
			
		}else{
			
			$("#result_section").hide();
			$("#view-tab-result").click();
			
		}
		

		
		return false;
	}
	
	function searchVM(){
		
		$("#view-vm-tab-result").show();
		$("#view-vm-tab-result").click();
		var searchKey = $("#vmSearchInput").val();
		
		if(searchKey == "虚拟机IP"){
			searchKey = "";
		}
		$("#view_vm_searchKey").text('"'+searchKey+'"');
		
		var finded = false;
		
		$("#search_vm_result").empty();
		/**搜索关键字*/
		$("#result_view_vm_monitor").children().each(function(){
			
			var text = $(this).text();
			if(text.indexOf(searchKey)!=-1){
				var temp = $(this).clone();
				finded = true;
				temp.appendTo("#search_vm_result");
			}
			
		});
		
		if(!finded){
			
			$("#search_vm_result").append('<tr><td colspan="8">查询结果为空</td></tr>');
			
		}
		
		
		return false;
	}
	
	/**获取正在监控中的虚拟机列表*/
	function getMonitorningList(success,error){
		
		var url = "../rest/monitorconfig";
		$.ajax({

			type : "get",
			url : url,
			success : success,
			error : error,
			timeout : 5000

		});
	}
	
	function getMonitoringListSuccess(data){
		
		$("#result_view_vm_monitor").empty();
		if(data!=null&&data.length!=0){
			
			for(var i=0;i<data.length;i++){
				
				var vm = data[i];
				var _view = view.monitorvm;
				_view = _view.replace(/{ip}/g,vm.ip);
				_view = _view.replace(/{status}/g,'<span class="label label-important">已失效</span>');
				_view = _view.replace(/{id}/g,(i+1));
				_view = _view.replace(/{cpu}/g,tracsMonitorStatus(vm.showCpu));
				_view = _view.replace(/{mem}/g,tracsMonitorStatus(vm.showMem));
				_view = _view.replace(/{disk}/g,tracsMonitorStatus(vm.showDisk));
				_view = _view.replace(/{network}/g,tracsMonitorStatus(vm.showIo));
			
				$("#result_view_vm_monitor").append(_view);
				
			}
			
			var pageconfig={
				
				pagesize : 6,
				currentpage : 1,
				target : "#monitor-table-footer"
			}
			
			$("#result_view_vm_monitor").qucikPage(pageconfig);
			
		}else{
			
			$("#result_view_vm_monitor").append('<tr><td colspan="8">数据为空...</td></tr>');
			
		}
		
		getClusterTree();
		
	}
	
	function getMonitoringListError(){
		
		$("#result_view_vm_monitor").append('<tr><td colspan="8">数据加载异常...</td></tr>');
		
	}
	
	function tracsMonitorStatus(bool){
		if(bool){
			return '<i class="icon-ok"></i>';
		}else{
			return '<i class="icon-remove"></i>';
		}
	}
	
	var searchTree;
	
	var mode = 1;//2表示搜索

	function getClusterTree(){
		
		$.ajax({
			  url: "../rest/clusters/private/tree",
			  type:"get",
			  success: getClusterTreeSuccess,
			  error:getClusterTreeError,
			  timeout : 3000
		});
		
	}
	
	function getClusterTreeSuccess(data){
		
		if(data!=null&&data.length!=0){
			
			$.fn.zTree.init($("#zTree"), setting, data);
			searchTree = $.fn.zTree.init($("#zTreeSearch"), setting, data);
			
			$("#view_clusters_doms").empty();
			
			/**加载集群虚拟机dom信息*/
			for(var i=0;i<data.length;i++){
				
				var _view =  view.clustervm
					.replace(/{clustername}/g,data[i].name)
					.replace(/{clusterid}/g,data[i].id);
				
				$(_view).appendTo("#view_clusters_doms");
				
				
				var _detail = view.clusterdetail
					.replace(/{clusterid}/g,data[i].id)
					.replace(/{clustername}/g,data[i].name);
				$(_detail).appendTo("#view_clusters_doms");
				
				getVirtualList(data[i].id,getVirtualListSuccess,getVirtualListError)
				getClusterDetail(data[i].id, getClusterDetailSuccess, getClusterDetailError);
			
			}
			
			setTimeout(function(){
				
				$("#pagelodinfo").hide();
				$("#cluster-detail-dom-"+data[0].id).show();
				
			},500);
			
		}else{
			$("#pagelodinfo").text("数据为空...");
			$("#zTree").text("数据为空...");
		}
		
	}
	
	function getClusterTreeError(data){
		
		$("#pagelodinfo").text("数据加载异常...");
		$("#zTree").text("数据加载异常...");
		
	}
	
	$(document).ready(function(){
		
		$.ajaxSetup({cache:false});
		
		getMonitorningList(getMonitoringListSuccess,getMonitoringListError);
		
		$("#view-tabs").tabs("#tabs");
		
		$("#view-tab-servers").click(function(){
			mode=1;
		});
		
		$("#view-tab-result").click(function(){
			mode=2;
		});
		
		$("#view-tab-monitor").click(function(){
			
			//getMonitorningList(getMonitoringListSuccess,getMonitoringListError);
			$("#vmSearch").show();
			$("#clusterSearch").hide();
			
		});
		
		$("#view-tab-servers").click(function(){
			
			//getClusterTree();
			$("#vmSearch").hide();
			$("#clusterSearch").show();
			
		});
		
		/**关闭搜索结果*/
		$("#tab-close-cluster").click(function(){
			
			$("#view-tab-result").hide();
			$("#view-tab-servers").click();
			event.stopPropagation();
			
		});
		
		$("#tab-close-vm").click(function(){
			
			$("#view-vm-tab-result").hide();
			$("#view-tab-monitor").click();
			event.stopPropagation();
			
		});
		

		//我的集群查询处理
		focusAndBlur($("#clusterSearchInput"),$("#clusterSearchButton"),true);
		//虚拟机列表查询处理
		focusAndBlur($("#vmSearchInput"),$("#vmSearchButton"),false);
		
		$("#clusterSearchButton").bind("click",function(){
			
			if(!isLegal($("#clusterSearchInput"))){
				$(this).blur();
			}else{
				searchCluster();
			}
		});
		
		$("#clusterSearchInput").bind("keyup",function(){
		
			if(event.keyCode == 13){
				
				if(!isLegal($("#clusterSearchInput"))){
					$(this).blur();
				}else{
					searchCluster();
				}
			}
		});
		
		//$("#searchClusterForm").bind("submit",searchCluster)
		
		$("#vmSearchButton").bind("click",function(){
			if(isLegal($("#vmSearchInput"))){
				searchVM();
			}
		});
		$(" #vmSearchInput").bind("keyup",function(){
			if(event.keyCode == 13){
				if(!isLegal($("#vmSearchInput"))){
					$(this).blur();
				}else{
					searchVM();
				}
			}
		});
		//$("#searchVMForm").bind("submit",searchVM);
		
		
	
		
	});
	
	var clustersView = {
			
			detail:'<div class="one big"><label>域名：</label><span>{domain}</span></div>'+
				'<div class="one big"><label>服务类型：</label><span>{ascategory}-{asversion}</span></div>'+
				'<div class="one big"><label>伸缩范围：</label><span>{min}~{max}</span></div>'+
				'<div class="one big"><label>硬件信息：</label><span>处理器：{cpu}HZ；内存空间:{mem}M；存储空间：{disk}G</span></div>'+
				'<div class="one big"><label>端口信息：</label><span>Http端口:{httpport};Https端口:{httpsport};</span></div>'+
				'<div class="one big"><label>描述：</label><span class="bigtext">{desc}</span></div>'
			
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
			
			vm:'<tr><td>{index}</td><td>{ip}</td><td><span class="label label-success">运行中</span></td><td>{type}</td><td style="width:200px" class="confifopt" configdata={ip}><a href="serverconfig.html?ip={ip}"><i class="icon-eye-open"></i>创建监控设置</a></td></tr>',
			
			monitorvm:'<tr><td>{id}</td><td>{ip}</td><td>{status}</td><td>{cpu}</td><td>{mem}</td><td>{disk}</i></td><td>{network}</td><td><a href="sdetail.html?ip={ip}"><i class="icon-list-alt"></i>监控详情</a>&nbsp;&nbsp;<a href="serverconfig.html?ip={ip}"><i class="icon-cog"></i>修改监控设置</a></td></tr>',
			
			clustervm:'<div class="clusterview dom" id="vm-list-dom-{clusterid}" style="display:none">'+
						'<div class="logo ubuntu"></div>'+
						'<div class="util_title"><h2>集群{clustername}</h2></div>'+
						'<div>'+
							'<table class="table">'+
								'<thead><tr><th>虚拟机编号</th><th>虚拟机IP地址</th><th>虚拟机状态</th><th>虚拟机类型</th><th style="width:200px">操作</th></tr></thead>'+
								'<tbody vmlist="cluster-vm-dom-{clusterid}" id="result-vm-list-{result-id}">'+
									'<tr><td colspan="5">数据加载中..</td></tr>'+
								'</tbody>'+
							'</table>'+
							'<div vmlistfoot="view-vm-dom-fotter-{clusterid}" id="result-vm-list-foot-{result-id}"class="table-footer">'+
								'<div class="table-data-summary">第 0 - 0条记录/ 共 0条记录</div>'+
								'<div class="table-pagination">'+
								'<span class="pagination-info">第0页 / 共 0页 </span>'+
								'<a class="pagination-button disable" rel="first"><img src="../assets/images/pagination-first-arrow.png"/></a>'+
								'<a class="pagination-button disable" rel="brfore"><img src="../assets/images/pagination-prev-arrow.png"/></a>'+
								'<a class="pagination-button disable" rel="after"><img src="../assets/images/pagination-next-arrow.png"/></a>'+
								'<a class="pagination-button disable" rel="last"><img src="../assets/images/pagination-last-arrow.png"/></a>'+
							'</div>'+
							'</div>'+
						'</div>'+
					'</div>',
					
		    clusterdetail:'<div class="clusterview dom" id="cluster-detail-dom-{clusterid}" style="display:none">'+
				'<div class="logo ubuntu"></div>'+
				'<div class="util_title"><h2>集群  <span>{clustername}</span></h2></div><br>'+
				'<table class="table">'+
				  	'<tbody>'+
				  	'<tr>'+
				  		'<td class="big one left-position"><label>租&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户：</label></td>'+
				  		'<td class="big one right-position"><span>{tenantName}</span></td>'+
				  		'<td class="big one left-position"><label>创&nbsp;&nbsp;&nbsp;建&nbsp;&nbsp;&nbsp;者：</label></td>'+
				  		'<td class="big one right-position"><span >{userName}</span></td>'+
				  	'</tr>'+
					'<tr>'+
				  		'<td class="big one left-position"><label>域&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label></td>'+
				  		'<td class="big one right-position"><span>{domain}</span></td>'+
				  		'<td class="big one left-position"><label>服务器类型：</label></td>'+
				  		'<td class="big one right-position"><span >{asCategory}-{asVersion}</span></td>'+
				  	'</tr>'+
				  	'<tr>'+
				  		'<td class="big one left-position"><label>伸缩范围：</label></td>'+
				  		'<td class="big one right-position"><span>{min}-{max}</span></td>'+
				  		'<td class="big one left-position"><label>硬&nbsp;件&nbsp;信&nbsp;息：</label></td>'+
				  		'<td class="big one right-position"><span>处理器:{cpu}HZ;内存空间:{mem}M;存储空间:{disk}G</span></td>'+
				  	'</tr>'+
				  	'<tr>'+
				  		'<td class="big one left-position"><label>端口信息：</label></td>'+
				  		'<td class="big one right-position"><span>Http端口:{httpport};&nbsp;&nbsp;&nbsp;&nbsp;Https端口:{httpsport}</span></td>'+
				  		'<td class="big one left-position"><label>描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</label></td>'+
				  		'<td class="big one right-position"><span class="bigtext">{description}</span></td>'+
				  	'</tr>'+
				'</tbody></table>'+
			'</div>'
			
					
	}
	
	/**
	 * 聚焦与失去焦点的处理
	 */
	
	function focusAndBlur(searchInput,searchButton,postion){

		var searchCondition;
		if(postion){
			searchCondition = "集群名称";
		}else{
			searchCondition = "虚拟机IP";
		}
		var inputVal;
		var legalCue = "请输入合法内容";

		searchInput.focus(function(){
			inputVal = searchInput.val();
			
			if(inputVal == searchCondition || inputVal == legalCue){
				searchInput.val("");
				searchInput.css("color","black");
			}
		});
		
		searchInput.blur(function(){
			inputVal = searchInput.val();
			if(inputVal == ""){
				searchInput.val(searchCondition);
				searchInput.css("color","gray");
			}
		});
	}
	/**
	 * 查询前检查输入内容是否合法
	 */
	function isLegal(searchInput){
		
		var islegal = true;
		var legalCue = "请输入合法内容";
		//var $searchInput = $("#clusterSearchInput");
		var reg = /[\/,\\,\[,\],%,&]/;
		
		if((searchInput.val()).match(reg)){
			searchInput.val(legalCue);
			searchInput.css("color","red");
			islegal = false;
		}else{
			islegal = true;
		};
		
		return islegal;
	}