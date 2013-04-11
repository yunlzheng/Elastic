$(function() {

	var optionTest = {
		pagesize : 10,
		currentpage : 1,
		target : "#test-table-footer"
	};
	
	var optionDeploy ={
			pagesize : 10,
			currentpage : 1,
			target : "#test-table-footer"
		};
	
	var username = $.cookie('account');
	
	getByClusterName(username, getClusterSuccess, getClusterError);
	
	$(".searchInput:[type='text']").focus(function(){
		var $text = $(".searchInput:[type='text']");
		
		if($text.attr("value") === "应用名称搜索"){
			$text.css("color","gray");
			$text.attr("value","");
		}
			$text.css("color","black");
		
	});

	function getByClusterName(username, success, error) {

		$.ajax({

			type : "get",
			url : "../rest/thirdparty/clusters/search/" + username,
			success : success,
			error : error,
			timeout : 5000

		});

	}
	
	function getClusterSuccess(data) {
		
		if (data == undefined || data.length == 0) {

			$("#testapplist").html("<tr align='center'><td colspan='9' align='center'><sapn>没有更多的数据了！</span></td></tr>");
			$("#deployapplist").html("<tr align='center'><td colspan='9' align='center'><sapn>没有更多的数据了！</span></td></tr>");
			
			return;

		} else {
			
			
			$("#testapplist").empty();
			$("#deployapplist").empty();
			for ( var i = 0; i < data.length; i++) {

				var id = data[i].id;
				//getTestAppsByClusterId(id,data[i].pxname,getTestAppsByClusterSuccess,getTestAppsByClusterError);
				getDeployAppsByClusterId(id,data[i].pxname,getDeployAppsByClusterSuccess,getDeployAppsByClusterError);
			}

		}
		
		
	}
	
	
	
	function getDeployAppsByClusterId(clusterid,pxname,success,error){
		
		$.ajax({
			
			type : "get",
			url : "../rest/artifacts/clusters/deploy/"+clusterid,
			success : $.proxy(success,{"pxname":pxname}),
			error : error,
			timeout : 5000

		});
		
	}
	
	function getDeployAppsByClusterSuccess(data){
		
		if(data==""||data==null){}else{
			for(var i=0;i<data.length;i++){
		
				var artifact = data[i];
				var pxname=this.pxname+"/"+artifact.name;
				var app = view.app
				.replace(/{name}/g, artifact.name)
				.replace(/{pxname}/g, pxname)
				.replace(/{id}/g,artifact.id)
				.replace(/{createDateTime}/g,artifact.createDate);
			    $("#deployapplist").append(app);
			    
				getAppStatusByPxName(pxname,getAppStatusSuccess,getAppStatusError);
			}
		}
		
		$("#deployapplist").qucikPage(optionDeploy);
		
	}
	
	function getDeployAppsByClusterError(){
		
	}
	

	
	function getAppStatusByPxName(pxname,success,error){
		
		$.ajax({

			type : "get",
			url : "../rest/lbstatusbackend/key2/" + pxname,
			success : success,
			error : $.proxy(error,{"pxname":pxname}),
			timeout : 5000,
			statusCode: {
			    404: $.proxy(function() {
			    
			    
			     
			    },{"pxname":pxname})
			 }

		});
		
	}
	
	function getAppStatusSuccess(data){
	
		if(data!=""){
			
			var pxname = data[0].pxname;
			$("#"+pxname).children(".sessioncount").text(data[0].stot);
			$("#"+pxname).children(".bin").text(data[0].bin);
			$("#"+pxname).children(".bout").text(data[0].bout);
			$("#"+pxname).children(".status").text(data[0].status);
		}
			
	}
	
	function getAppStatusError(){
		
		////console.log(this.pxname);
		
	}


	function getClusterError() {

		$("#applist").html("<tr align='center'><td colspan='9' align='center'><br/><sapn>数据加载异常！<br/><br/></td></tr>");

	}

	var view = {

		cluster : '<div class="section"><h4>{name}</h4>'+
					'<table cellpadding="0" cellspacing="0" class="table">'+
						'<thead>'+
							'<tr><th>虚拟机编号</th><th>虚拟机IP地址</th><th>虚拟机状态</th><th>操作</th></tr>'+
						'</thead>'+
						'<tbody id="cluster-{id}">'+
						'<tr>'+
							'<td colspan="4" align="left"><br/><img src="../assets/images/ajax-loader.gif" alt="" /><span>数据加载中</span></td>'+
					    '</tr>'+
						'</tbody>'+
					'</table>'+
					'</div>',
					
		virtual:'<tr>'
			+ '<td>{vm_id}</td>'
			+ '<td>{vm_ip}</td>'
			+ '<td>{vm_status}</td>'
			+ '<td><a href="#" class="test">监控设置</a> | <a href="sdetail.html?ip={vm_ip}">监控详情</a></td></tr>',
		
		app:'<tr id="{pxname}">'+
	    	'<td>{id}</td>'+
	    	'<td class="toolltip">{name}</td>'+
	    	'<td><a href="http://{pxname}" title="{pxname}" target="_block">点击访问</a></td>'+
	    	'<td>{createDateTime}</td>'+
	    	'<td class="sessioncount">无数据</td>'+
	    	'<td class="bin">无数据</td>'+
	    	'<td class="bout">无数据</td>'+
	    	'<td class="status">无数据</td>'+
	    	'<td><a href="lb_appdetail.html?pxname={pxname}" title="点击查看详情">监控详情</a></td>'+
	    '</tr>'
	}
	
	
	function checkList(name,check,success,error){
		
		$.ajax({

			type : "get",
			url : "../rest/thirdparty/clusters/app/" + name + "/" + check,
			success : success,
			error : error,
			timeout : 5000

		});
		
	}
	
	/**
	 * 点击查询
	 */
	$(".searchButton:[type='button']").click(function(){
		
		var check = $(".searchInput:[type='text']").val();
		if(check == ""){
			alert("确保查询的内容不为空");
		}else{
			var reg = /[\/,\\,\[,\],%,&]/;
			var result = check.match(reg);
			if(!result){
				checkList(username,check,getClusterSuccess,getClusterError);
			}else{
				alert("请输入合法的内容");
			}
			
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
			$(this).val("应用名称搜索");
			$(this).css("color","gray");
		}
	});
	
	$(".searchInput:[type='text']").bind('keyup',function(event){
		if(event.keyCode == 13){
			
			var check = $(".searchInput:[type='text']").val();
			if(check == ""){
				alert("确保查询的内容不为空");
			}else{
				var reg = /[\/,\\,\[,\],%,&]/;
				var result = check.match(reg);
				
				if(!result){
					$(".searchimg:eq(0)").css("display","inline");
					checkList(username,check,getClusterSuccess,getClusterError);
				}else{
					alert("请输入合法的内容");
				}
				
			}
			
		}
	});
	
	/**
	 * 点击查询处图表
	 */
	$(".searchimg:eq(0)").click(function(){
		/**将输入框清空*/
		$(".searchInput:[type='text']").val("应用名称搜索");
		$(".searchInput:[type='text']").css("color","gray");
		$(this).css("display","none");
		getByClusterName(username, getClusterSuccess, getClusterError);
	});
	
	
	/**测试中的应用*/
	function getTestAppsByClusterId(clusterid,pxname,success,error){
		
		$.ajax({
			
			type : "get",
			url : "../rest/artifacts/clusters/test/"+clusterid,
			success : $.proxy(success,{"pxname":pxname}),
			error : error,
			timeout : 5000

		});
		
	}
	
	function getTestAppsByClusterSuccess(data){
		
		if(data==""||data==null){}else{
			for(var i=0;i<data.length;i++){
		
				var artifact = data[i];
				var pxname=this.pxname+"/"+artifact.name;
				var app = view.app
				.replace(/{name}/g, artifact.name)
				.replace(/{pxname}/g, pxname)
				.replace(/{id}/g,artifact.id)
				.replace(/{createDateTime}/g,artifact.createDate);
			    $("#testapplist").append(app);
			    
				getAppStatusByPxName(pxname,getAppStatusSuccess,getAppStatusError);
			}
		}
		
		$("#testapplist").qucikPage(optionTest);
		
	}
	
	function getTestAppsByClusterError(){
		
	}

});
