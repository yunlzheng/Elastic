$(function(){
	
	$.ajaxSetup({cache:false});
	
	var config = {
			
			pub:{
				url:"../rest/artifacts/pripub/1",
				target:"#public-app-list",
				pageconfig:{
					pagesize : 10,
					currentpage : 1,
					target : "#pub-table-footer"
				}
			},
			pri:{
				url:"../rest/artifacts/pripub/2",
				target:"#private-app-list",
				pageconfig:{
					pagesize : 10,
					currentpage : 1,
					target : "#pri-table-footer"
				},
				loaded:function(){
					
				}
			},
			run:{
				url:"../rest/artifacts/runing",
				target:"#run-app-list",
				pageconfig:{
					pagesize : 10,
					currentpage : 1,
					target : "#run-table-footer"
				}
			},
			test:{
				url:"../rest/artifacts/testing",
				target:"#test-app-list",
				pageconfig:{
					pagesize : 10,
					currentpage : 1,
					target : "#test-table-footer"
				}
			},
			search:{
				url:"../rest/artifacts/search?appname=",
				target:"#result-app-list",
				pageconfig:{
					pagesize : 10,
					currentpage : 1,
					target : "#result-table-footer"
				}
			}
			
	}
	
	$("#nav-tabs").tabs("#tabs");
	
	/**关闭搜索结果*/
	$(".icon-remove").click(function(){
		
		$("#view-result-tab").hide();
		$("#view-public-tab").click();
		event.stopPropagation();
		
	});
	
	$(".tab[tab-data]").click(function(){
		
		var key = $(this).attr("tab-data");
		if(key=="run-tab"){
			getRunApps(getAppsSuccess,getAppsError,config.run);
		}else if(key=="test-tab"){
			getRunApps(getAppsSuccess,getAppsError,config.test);
		}else if(key=="public-tab"){
			getRunApps(getAppsSuccess,getAppsError,config.pub);
		}else if(key=="private-tab"){
			
			getRunApps(getAppsSuccess,getAppsError,config.pri);
		}
		
	});
	
	$("#clusterSearchButton").bind("click",function(){
		if(!isLegal()){
			$(this).blur();
		}else{
			searchArtifact();
		}
	});
	
	$("#clusterSearchInput").bind("keyup",function(){
		if(event.keyCode == 13){
			if(!isLegal()){
				$(this).blur();
			}else{
				searchArtifact();
			}
		}
	});

	
	function searchArtifact(){
		
		$("#view-result-tab").show();
		var searchKey = $("#clusterSearchInput").attr("value");
		if(searchKey == "应用名称"){
			searchKey = "";
		}
		$("#view_searchkey").text('"'+searchKey+'"的查询');
		var hasresult = false;
		
		var conf = config.search;
		conf.url = conf.url+searchKey
		getRunApps(getAppsSuccess,getAppsError,config.search);
		$("#view-result-tab").click();
		
	}
	
	getRunApps(getAppsSuccess,getAppsError,config.pub);
	
	function getRunApps(success,error,config){
		
		var url = config.url;
		
		$.ajax({

			type : "get",
			url : url,
			success : $.proxy(success,config),
			error : error,
			timeout : 5000

		});
		
	}
	
	function getAppsSuccess(data){
		
		var target = this.target;
		
		//console.log(data);
		if(data!=null&&data.length!=0){
			
			$(target).empty();
			for(var i=0;i<data.length;i++){
				
				var app = data[i];
				
				var tr;
					
				if(app.appStatus=="测试中"){
					
					
					tr = APP.TR
					.replace(/{appid}/g,(i+1))
					.replace(/{creater}/g,app.createrName)
					.replace(/{appname}/g,app.appname)
					.replace(/{clusterName}/g,app.clusterName)
					.replace(/{domain}/g,app.domain)
					.replace(/{clustercate}/g,app.domainType)
					.replace(/{status}/g,'<span class="label label-warning">'+app.appStatus +'</span>')
					.replace(/{desc}/g,app.desc)
					.replace(/{opt}/g,"<a href='http://"+app.domain+"/"+app.appname+"' target='_blank' title='点击打开连接地址'>打开连接</a>");
				
				} else if(app.domainType=="公有环境"){
					
					tr = APP.TR
					.replace(/{appid}/g,(i+1))
					.replace(/{creater}/g,app.createrName)
					.replace(/{appname}/g,app.appname)
					.replace(/{clusterName}/g,app.clusterName)
					.replace(/{domain}/g,app.domain)
					.replace(/{clustercate}/g,app.domainType)
					.replace(/{status}/g,'<span class="label label-success">'+app.appStatus +'</span>')
					.replace(/{desc}/g,app.desc)
					.replace(/{opt}/g,"<a href='privateEvnDetail.html?pxname="+app.url+"_"+app.appname+"&type=1&appname="+app.appname+"' title='点击查看应用统计信息'><i class='icon-list-alt'></i>应用详情</a>");
				
				}else if(app.domainType=="私有环境"){
					
					
					tr = APP.TR
					.replace(/{appid}/g,(i+1))
					.replace(/{creater}/g,app.createrName)
					.replace(/{appname}/g,app.appname)
					.replace(/{clusterName}/g,app.clusterName)
					.replace(/{domain}/g,app.domain)
					.replace(/{clustercate}/g,app.domainType)
					.replace(/{status}/g,'<span class="label label-success">'+app.appStatus +'</span>')
					.replace(/{desc}/g,app.desc)
					.replace(/{opt}/g,"<a href='privateEvnDetail.html?pxname="+app.url+"&clusterid="+app.clusterId+"' title='点击查看私有环境统计信息'><i class='icon-list-alt'></i>私有环境详情</a>");
					
				}
				
				$(target).append(tr);
				
				if(this.loaded){
					
					$(target).hide();
					this.loaded();
					$(target).show();
					
				}
				
				
			}
			$(target).qucikPage(this.pageconfig);
			
			
			
		}else{
			
			$(target).empty().append("<tr><td colspan='8' align='center'>数据为空</td></tr>");
			
		}
		
	}
	
	function getAppsError(){
		
	}
	
	
	
	var APP = {
			
		TR:'<tr><td>{appid}</td><td><a href="http://{domain}/{appname}" title="点击打开连接" target="_blank">{appname}</a></td><td>{creater}</td><td>{clusterName}</td><td>{clustercate}</td><td>{status}</td><td>{desc}</td><td>{opt}</td></tr>'
			
	}
	
	/**
	 * 聚焦与失去焦点的处理
	 */
	focusAndBlur();
	
	function focusAndBlur(){
		
		var searchCondition = "应用名称";
		var inputVal;
		var legalCue = "请输入合法内容";
		
		var $searchInput = $("#clusterSearchInput");
		var $searchButton = $("#clusterSearchButton");
		$searchInput.focus(function(){
			inputVal = $searchInput.val();
			
			if(inputVal == searchCondition || inputVal == legalCue){
				$searchInput.val("");
				$searchInput.css("color","black");
			}
		});
		
		$searchInput.blur(function(){
			inputVal = $searchInput.val();
			if(inputVal == ""){
				$searchInput.val(searchCondition);
				$searchInput.css("color","gray");
			}
		});
	}
	/**
	 * 查询前检查输入内容是否合法
	 */
	function isLegal(){
		
		var islegal = true;
		var legalCue = "请输入合法内容";
		var $searchInput = $("#clusterSearchInput");
		var reg = /[\/,\\,\[,\],%,&]/;
		
		if(($searchInput.val()).match(reg)){
			$searchInput.val(legalCue);
			$searchInput.css("color","red");
			islegal = false;
		}else{
			islegal = true;
		};
		
		return islegal;
	}
	
});