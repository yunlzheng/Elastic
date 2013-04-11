$(function(){
	
	$.ajaxSetup({cache:false});
	
	/**选项卡切换*/
	$("#view-tabs").tabs("#tab");
	
	
	var zNodes =[
		 			{ id:1, pId:0, name:"可折腾的父节点 1", t:"我很普通，随便 展开/折叠 我吧", open:true},
		 			{ id:11, pId:1, name:"叶子节点 - 1", t:"我老爸很普通，随便折腾他吧"},
		 			{ id:12, pId:1, name:"可折腾的父节点 2", t:"我和我老爸都很普通，随便折腾我和他吧"},
		 			{ id:121, pId:12, name:"叶子节点 - 21", t:"我老爸很普通，随便折腾他吧"},
		 			{ id:122, pId:12, name:"叶子节点 - 22", t:"我老爸很普通，随便折腾他吧"},
		 			{ id:123, pId:12, name:"叶子节点 - 23", t:"我老爸很普通，随便折腾他吧"},
		 			{ id:13, pId:1, name:"叶子节点 - 3", t:"我老爸很普通，随便折腾他吧"},
		 			{ id:2, pId:0, name:"无法折叠的父节点", t:"休想让我折叠起来...除非你用 expandAll 方法", open:true, collapse:false},
		 			{ id:21, pId:2, name:"叶子节点2 - 1", t:"哈哈哈，我老爸NB吧，不能折叠吧？"},
		 			{ id:22, pId:2, name:"叶子节点2 - 2", t:"哈哈哈，我老爸NB吧，不能折叠吧？"},
		 			{ id:23, pId:2, name:"叶子节点2 - 3", t:"哈哈哈，我老爸NB吧，不能折叠吧？"},
		 			{ id:3, pId:0, name:"无法展开的父节点", t:"就凭你也想展开我？难呀...嘿嘿, 除非你用 expandAll 方法", open:false, expand:false},
		 			{ id:31, pId:3, name:"叶子节点3 - 1", t:"居然让你看到了...莫非你用了全部节点展开？"},
		 			{ id:32, pId:3, name:"叶子节点3 - 2", t:"居然让你看到了...莫非你用了全部节点展开？"},
		 			{ id:33, pId:3, name:"叶子节点3 - 3", t:"居然让你看到了...莫非你用了全部节点展开？"},
		 			{ id:4, pId:0, name:"空空的父节点 1", t:"一无所有...除了我自己", isParent:true, open:false}
		 		];
		
		
		
		var setting = {
				view: {
					selectedMulti: false
				},
				data: {
					key: {
						title:"t"
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
		
		var log, className = "dark";
		function beforeClick(treeId, treeNode) {
			if (treeNode.isParent) {
				return true;
			} else {
				alert("这个 Demo 是用来测试 展开 / 折叠 的...\n\n去点击父节点吧... ");
				return false;
			}
		}
		function beforeCollapse(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeCollapse ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
			return (treeNode.collapse !== false);
		}
		function onCollapse(event, treeId, treeNode) {
			showLog("[ "+getTime()+" onCollapse ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
		}		
		function beforeExpand(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
			return (treeNode.expand !== false);
		}
		function onExpand(event, treeId, treeNode) {
			showLog("[ "+getTime()+" onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
		}
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		function expandNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			type = e.data.type,
			nodes = zTree.getSelectedNodes();
			if (type.indexOf("All")<0 && nodes.length == 0) {
				alert("请先选择一个父节点");
			}

			if (type == "expandAll") {
				zTree.expandAll(true);
			} else if (type == "collapseAll") {
				zTree.expandAll(false);
			} else {
				var callbackFlag = $("#callbackTrigger").attr("checked");
				for (var i=0, l=nodes.length; i<l; i++) {
					zTree.setting.view.fontCss = {};
					if (type == "expand") {
						zTree.expandNode(nodes[i], true, null, null, callbackFlag);
					} else if (type == "collapse") {
						zTree.expandNode(nodes[i], false, null, null, callbackFlag);
					} else if (type == "toggle") {
						zTree.expandNode(nodes[i], null, null, null, callbackFlag);
					} else if (type == "expandSon") {
						zTree.expandNode(nodes[i], true, true, null, callbackFlag);
					} else if (type == "collapseSon") {
						zTree.expandNode(nodes[i], false, true, null, callbackFlag);
					}
				}
			}
		}
	
	
		/** 初始化应用*/
		
		var config = {
				app:{
					url:"../rest/members/key1/admin@test.com",
					target:"#app-list",
					pageconfig:{
						pagesize : 10,
						currentpage : 1,
						target : "#app-table-footer"
					}
				}
		}
		
		var APP = {
				TR:'<tr><td>{appid}</td><td><a href="http://{domain}/{appname}" target="_blank">{appname}</a></td><td title="{domain}">{domain}</td><td>{clustercate}</td><td>{status}</td><td>{desc}</td></tr>'
			}
		
		var ajaxTest = function(success,error,config){
			
			var url = config.app.url;
			$.ajax({
				type:"get",
				url :url,
				success : $.proxy(success,config),
				error : error,
				timeout : 5000
			});
		}
		ajaxTest(getSuccess,error,config);
		function getSuccess(data){
			var target = this.app.target;
			//console.log(data);
			if(data != null && data.length != 0){
				$(target).empty();
				
				for(var i=0;i<data.length;i++){
					//console.log(data[i]);
					var tr;
					/**真数据*/
					/*tr = APP.TR.replace(/{appid}/g,(i+1))
					.replace(/{appname}/g,data[i].appname)
					.replace(/{domain}/g,data[i].domain)
					.replace(/{clustercate}/g,data[i].domainType)
					.replace(/{status}/g,data[i].appStatus)
					.replace(/{desc}/g,data[i].desc);*/
					
					/**just测试*/
					tr = APP.TR.replace(/{appid}/g,(i+1))
					.replace(/{appname}/g,data[i].name)
					.replace(/{domain}/g,data[i].name)
					.replace(/{clustercate}/g,data[i].name)
					.replace(/{status}/g,data[i].name)
					.replace(/{desc}/g,data[i].name);
					
					$(target).append(tr);
				}
				
				$(target).qucikPage(this.app.pageconfig);
				
			}else{
				$(target).empty().append("<tr><td colspan='7' align='center'>数据为空</td></tr>");
			}
		}
	
		function error(){
			//console.log("出错啦");
		}
	
		
	
	
	/**
	 * 初始化用户列表
	 */
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	
	
	$("#detail").bind("click",function(){
		 self.location='index.html';
	});
	
	
	
	
	
	
	
});