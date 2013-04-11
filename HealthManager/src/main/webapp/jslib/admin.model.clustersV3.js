
var mode = 1;
var footer = "";
var publicSearchTree;
var privateSearchTree;
var publicloaded = false;//共有集群表示页面是否第一次载入
var privateloaded = false;//私有集群表示页面是否第一次载入

var option = {
		pagesize : 10,
		currentpage : 1,
		target : footer
}

function tabChange(){
	$("#public_cluster").click(function(){
		
		if(publicSearchTree!=null&&publicSearchTree!=undefined){
			
			var treeName = publicSearchTree.getNodes()[0].name;
			publicCluster($.proxy(successCallBack,{"treeName":treeName}),errorCalback);
		
		}
		
	});
	
	$("#private_cluster").click(function(){
		
		if(privateSearchTree!=null&&privateSearchTree!=undefined){
			
			var treeName = privateSearchTree.getNodes()[0].children[0].name;
			privateCluster($.proxy(privateSuccessCallBack,{"treeName":treeName}),privateErrorCallBack);
		
		}
		
	});
	
}


/**
 * 获取树节点信息
 */
var publicCluster = function(successCallBack,errorCalback){
			
	$.ajax({
		url: "../rest/clusters/public/tree",
		dataType: 'json',
		success: successCallBack,
		error:errorCalback,
		timeout:5000
		})
		
}

function successCallBack(data){
	
	/**
	 * 默认设置第一个为展开状态
	 */
	
	if(data!=null&&data!=undefined&&data!=""&&data.length>0){
		
		data[0].open = true;
		var treeNodes = $.fn.zTree.init($("#publicTree"),setting,data);
		if(!publicloaded){
			
			publicSearchTree =  $.fn.zTree.init($("#publicSearchTree"),setting,data);
			
		}
		var treeName = treeNodes.getNodes()[0].name;
		/**
		 * 进行查询操作 
		 */
		virtalList($.proxy(virtalListCallBack,{"treeName":treeName}),virtalListError,data[0].id);
		
		publicloaded = true;
		
	}else{
		
		$("#publicTree").empty().text("数据为空...");
		
	}

}

/**
 * 加载失败
 */
function errorCalback(){
	$("#publicTbody").empty().append('<tr><td colspan="5">数据加载异常... </td></tr>');
	$("#publicTree").empty().text("数据加载异常...");
}

/**
 * 获取虚拟机详细信息
 */

var virtualDetail = function(successCallBack,errorCalback,id){
	$.ajax({
		url: "../rest/thirdparty/clusters/" + id,
		dataType: 'json',
		success: successCallBack,
		error:errorCalback,
		timeout:5000
		})
}

function virtualDetailCallBack(data){

	showClusterDetail(data);
	
}

function virtalDetailError(){
	//console.log("error");
}


/**
 * 虚拟机列表
 */

var  virtalList = function(virtalListCallBack,virtalListError,clusterId){
	$.ajax({
		url: "../rest/thirdparty/vm/search/" + clusterId,
		dataType: 'json',
		success: virtalListCallBack,
		error:virtalListError,
		timeout:5000
		})
}


function virtalListCallBack(data){
	/**
	 * 将获取的虚拟机列表添加到表格
	 */
	
		if(mode == 1 && !this.requestType){
			
			$("#public_virtual_detail").css("display","none");
			$("#public_virtual_list").css("display","block");
			
			footer = "#public-table-footer";
			appendDataToTable(data,"#publicTbody",footer,this.treeName);
			
		}else if(mode == 2){
			
			$("#private_virtual_detail").css("display","none");
			$("#private_virtual_list").css("display","block");
			
			footer="#private-table-footer"
			appendDataToTable(data,"#privateTbody",footer,this.treeName);
			
		}else if(mode==3){
			
			$("#result_public_virtual_detail").css("display","none");
			$("#result_public_virtual_list").css("display","block");
			footer = "#result_public-table-footer";
			appendDataToTable(data,"#result_publicTbody",footer,this.treeName);
			
		}else if(mode==4){
			
			$("#result_private_result_detail").css("display","none");
			$("#result_private_virtual_list").css("display","block");
			
			footer = "#result_private-table-footer";
			appendDataToTable(data,"#result_privateTbody",footer,this.treeName);
			
		}
		
	
	
}


function appendDataToTable(data,target,footer,treeNodeName){
	
	
	
	
	var tbody;
	$(target).empty();
	var index = 0 ;
	if(data!=null&&data!=""&&data.length > 0){
		
		for(var i = 0 ; i < data.length ; i++){
			
			if(data[i] != 0 && data[i] != 1){
				var ip = data[i];
				var type = data[i+1];
				index = index + 1;
				if(type == 1){
					tbody = virtualOption.tr.replace(/{serialNumber}/g,(index))
					 						.replace(/{virtualIp}/g,data[i])
					 						.replace(/{virtualStatus}/g,"<span class='label label-success' >运行中</span>")
					 						.replace(/{virtualType}/g,"<span class='label label-warning'>动态伸缩</span>")
					 						.replace(/{Operation}/,"<a href='sdetail.html?ip=" + ip +"'>监控详情</a>");
				}else if(type == 0){
					tbody = virtualOption.tr.replace(/{serialNumber}/g,(index))
											.replace(/{virtualIp}/g,data[i])
											.replace(/{virtualStatus}/g,"<span class='label label-success' >运行中</span>")
											.replace(/{virtualType}/g,"<span class='label label-success' >固有虚拟机</span>")
											.replace(/{Operation}/,"<a href='sdetail.html?ip=" + ip +"'>监控详情</a>");
				
				}else{
					tbody = virtualOption.tr.replace(/{serialNumber}/g,(index))
											.replace(/{virtualIp}/g,data[i])
											.replace(/{virtualStatus}/g,"<span class='label label-success'>运行中</span>")
											.replace(/{virtualType}/g,"未知")
											.replace(/{Operation}/,"<a href='sdetail.html?ip=" + ip +"'>无法进行操作</a>");
				}
				$(target).append(tbody);
				
			}
			
		}
		
		option.target = footer;
		$(target).qucikPage(option);
	}else{
		
		tbody = "<tr><td colspan='5' align='center'>数据为空...</td></tr>";
		$(target).append(tbody);
		
		if(target == "#publicTbody"){
			pageFooter("#public-table-footer");
		}else if(target == "#privateTbody"){
			pageFooter("#private-table-footer");
		}
		
	}
	
	if(mode == 1){
		$("#public_list_block_cluster_name").text(treeNodeName);
	}else if(mode == 2){
		$("#private_list_block_cluster_name").text(treeNodeName);
	}else if(mode == 3){
		$("#result_public_list_block_cluster_name").text(treeNodeName);
	}else if(mode == 4){
		$("#result_private_list_block_cluster_name").text(treeNodeName);
	}
	
}


function pageFooter(footerTarget){
	$(footerTarget).empty()
				   .append('<div class="table-data-summary">第 0 - 0条记录/ 共 0条记录</div>')
				   .append('<div class="table-pagination"><span class="pagination-info">' +
						   '第0页 / 共 0页 </span><a class="pagination-button disable" rel="first">' +
						   '<img src="../assets/images/pagination-first-arrow.png"></a> <a class="pagination-button disable" rel="brfore">' +
						   '<img src="../assets/images/pagination-prev-arrow.png"></a><a class="pagination-button disable" rel="after">' +
						   '<img src="../assets/images/pagination-next-arrow.png"></a><a class="pagination-button disable" rel="last">' +
						   '<img src="../assets/images/pagination-last-arrow.png"></a></div>');
}

function virtalListError(){
	tbody = "<tr><td colspan='5' align='center'>数据加载异常...</td></tr>";
	$("#publicTbody").empty().append(tbody);
}

/**
 * 私有集群列表
 */

var privateCluster = function(privateSuccessCallBack,privateErrorCallBack){
	$.ajax({
		url: "../rest/clusters/private/user/tree",
		dataType: 'json',
		success: privateSuccessCallBack,
		error:privateErrorCallBack,
		timeout:5000
		})
}

function privateSuccessCallBack(data){

	if(data!=null&&data!=undefined&&data.length>0){
		
		
		data[0].open = true;
		$.fn.zTree.init($("#privateTree"),setting,data);
		
		if(!privateloaded){
			privateSearchTree =  $.fn.zTree.init($("#privateSearchTree"),setting,data);
		}
		
		privateloaded = true;
		
		if(data[0]["children"]!=null&&data[0]["children"]!=undefined&&data[0]["children"].length>0){
			
			var id = data[0]["children"][0]["id"];
			virtalList($.proxy(virtalListCallBack,{"treeName":this.treeName,"requestType":"private"}),privateVirtalListError,id);
		
		}else{
			
			$("#privateTbody").empty().append("<tr><td colspan='5'>数据为空</td></tr>");
			
		}
		
	}else{
		
		$("#privateTree").empty().append("数据为空...");
		
	}
	
}

function privateErrorCallBack(){
	
	$("#privateTree").empty().append("数据加载异常...");
	$("#privateTbody").empty().append("<tr><td colspan='5'>数据加载异常...</td></tr>");
}

function privateVirtalListError(){
	tbody = "<tr><td colspan='5' align='center'>数据加载异常..</td></tr>";
	$("#privateTbody").empty().append(tbody);
}

function privateVirtalListResultError(){
	tbody = "<tr><td colspan='5' align='center'>数据加载异常...</td></tr>";
	$("#result_privateTbody").empty().append(tbody);
}

function publicVirtalListResultError(){
	tbody = "<tr><td colspan='5' align='center'>数据加载异常...</td></tr>";
	$("#result_publicTbody").empty().append(tbody);
}
/**
 * 根据不同的类型选择发送的地址
 * @param treeNode
 */

function RequestTypeById(treeNode,parentId){
	
	switch (treeNode.name) {
		case "详细信息":
		
			virtualDetail(virtualDetailCallBack,virtalDetailError,treeNode.pId);
			break;
	
		case "虚拟机列表":
			
			if(parentId == "publicTree"){
				virtalList($.proxy(virtalListCallBack,{"treeName":treeNode.getParentNode().name}),virtalListError,treeNode.pId);
			}else if(parentId == "privateTree"){
				virtalList($.proxy(virtalListCallBack,{"treeName":treeNode.getParentNode().name}),privateVirtalListError,treeNode.pId);
			}else if(parentId == "privateSearchTree"){
				virtalList($.proxy(virtalListCallBack,{"treeName":treeNode.getParentNode().name}),privateVirtalListResultError,treeNode.pId);
			}else if(parentId == "publicSearchTree"){
				virtalList($.proxy(virtalListCallBack,{"treeName":treeNode.getParentNode().name}),publicVirtalListResultError,treeNode.pId);
			}
			
			break;
		default:
			break;
	}
	
}


/**
 * zTree构造
 */

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
		}
};

function beforeClick(treeId, treeNode) {
	
	if (treeNode.isParent) {
		return true;
	} else {
		var parentId = treeNode.parentTId.substring(0,(treeNode.parentTId).indexOf("_"));
		loading(parentId,treeNode.name);
		RequestTypeById(treeNode,parentId);
		return false;
	}

}





/**
 *锁屏
 */
function showClusterDetail(data){
	
	if(mode == 1){
		$("#public_virtual_detail").css("display","block");
		$("#public_virtual_list").css("display","none");
		appendDetail(data,"#public_");
	}else if(mode == 2){
		$("#private_virtual_detail").css("display","block");
		$("#private_virtual_list").css("display","none");
		appendDetail(data,"#private_");
	}else if(mode == 3){
		$("#result_public_virtual_detail").css("display","block");
		$("#result_public_virtual_list").css("display","none");
		appendDetail(data,"#result_public_");
	}else if(mode == 4){
		$("#result_private_result_detail").css("display","block");
		$("#result_private_virtual_list").css("display","none");
		appendDetail(data,"#result_private_");
	}
	
	
}


var appendDetailOption = {
		
		"head":"<div class='unitclose'></div>" +
				"<div class='logo ubuntu' id='logo_close'></div>" +
				"<div class='util_title'><h2>集群  <span id='block_cluster_name'>{clusterName}</span></h2></div><br/>",
		"realmName":"<div class='one big'><label>域名：</label><span> {realmName} </span></div>",
		"serviceType":"<div class='one big'><label>服务类型：</label><span> {serviceType} </span></div>",
		"elasticRange":"<div class='one big'><label>伸缩范围：</label><span> {elasticRange} </span></div>",
		"hardwareInfo":"<div class='one big'><label>硬件信息：</label><span> {hardwareInfo} </span></div>",
		"portInfo":"<div class='one big'><label>端口信息：</label><span> {portInfo} </span></div>",
		"description":"<div class='one big'><label>描述：</label><span class='bigtext'> {description} </span></div>"
		
}

var virtualOption = {
	
	"tr":"<tr>" +
			"<td>{serialNumber}</td>" +
			"<td>{virtualIp}</td>" +
			"<td>{virtualStatus}</td>" +
			"<td>{virtualType}</td>" +
			"<td>{Operation}</td>" +
		 "</tr>"
	
}

function appendDetail(data,targetMode){
	
	var servicetype = data.asCategory + "-" + data.asSerial;
	var range = data.min + "-" + data.max;
	var hardware = "处理器:" + data.cpu + "HZ;内存空间:" + data.mem + "M;存储空间:" + data.disk + "G";
	var ports = "Http端口:" + data.httpPort + ";&nbsp;&nbsp;&nbsp;&nbsp;Https端口:" + data.httpsPort;
	
	
	var $block_cluster_name = $(targetMode+"block_cluster_name");
	var $realmName = $(targetMode+"realmName");
	var $serviceType = $(targetMode+"serviceType");
	var $elasticRange = $(targetMode+"elasticRange");
	var $hardwareInfo = $(targetMode+"hardwareInfo");
	var $portInfo = $(targetMode+"portInfo");
	var $description = $(targetMode+"description");
	
	/**
	 * 清空内容
	 */
	$block_cluster_name.empty();
	$realmName.empty();
	$serviceType.empty();
	$elasticRange.empty();
	$hardwareInfo.empty();
	$portInfo.empty();
	$description.empty();
	
	/**
	 * 添加内容
	 */
	$block_cluster_name.text(data.name);
	$realmName.text(data.pxname);
	$(targetMode + "realmName_title").attr("title",data.pxname);
	$serviceType.text(servicetype);
	$elasticRange.text(range);
	$hardwareInfo.text(hardware);
	$portInfo.html(ports);
	$description.text(data.description);
	$(targetMode + "description_title").attr("title",data.description);

}

/**
 * 查询
 */
function searchByClusterName(){
	
	var $searchInput = $("#clusterSearchInput");
	var $searchButton = $("#clusterSearchButton");
	searchAction($searchInput,$searchButton);
	/**
	 * 添加enter事件
	 */
	$searchInput.bind("keyup",function(event){
		if(event.keyCode == 13){
		
		searchByEnterOrClick($searchInput,$searchButton);
		}
	});
}

function focusHandler(searchInput){
	
	var searchCondition = "集群名称";
	var legal = "请输入合法内容";
	var inputVal = searchInput.val();
	
	if(inputVal == legal){
		searchInput.css("color","red");
	}
	
	searchInput.focus(function(){
		if(inputVal == searchCondition || inputVal == ""  || inputVal == legal){
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

function searchAction(searchInput,searchButton){
	
	
	searchButton.click(function(){
		searchByEnterOrClick(searchInput,searchButton);
	});
}

function searchByEnterOrClick(searchInput,searchButton){
	var hasresult = false;
	var inputVal = searchInput.val();
	
	/**
	 * 控制tab选项
	 */
	
	if(validateSearchContent(inputVal)){
		if(mode == 1||mode==3){
			
			$("#public-view-tab-result").show();
			$("#public-view-tab-result").click();
			
			if(inputVal == "集群名称"){
				inputVal = "";
			}
			$("#public_view_searchkey").text('"'+inputVal+'"的查询');
			
			searchAppend(publicSearchTree,inputVal,false);
			
		}else if(mode == 2||mode==4){
			
			$("#private-view-tab-result").show();
			$("#private-view-tab-result").click();
			if(inputVal == "集群名称"){
				inputVal = "";
			}
			$("#private_view_searchkey").text('"'+inputVal+'"的查询');
			
			searchAppend(privateSearchTree,inputVal,true);
		}
	}else{
		//请输入合法的内容
		searchInput.val("请输入合法内容");
		searchInput.blur();
		focusHandler(searchInput);
	}
	

}

function searchAppend(searchTree,inputVal,isPrivate){
	
	var nodes = searchTree.getNodes();
	var nodeArr = new Array();
	if(isPrivate){ //私有集群显示
		
		if(inputVal == "" || inputVal == "集群名称"){
			searchTree.showNode(node);
		}else{
			for(var i=0;i<nodes.length;i++){
				
				var node = nodes[i];
				var children = node.children;
				
				for(var j = 0 ; j < children.length ; j++){
					
					if(children[j].name.indexOf(inputVal) != -1){
						nodeArr.push(children[j]);
						isHidden = false;
					}
					searchTree.hideNode(node);
				}
				
			}
			
			for(var j = 0 ; j < nodeArr.length ; j++){
				
				var parent = nodeArr[j].getParentNode();
				var children = parent.children;
				searchTree.showNode(parent);
				
				for(var i = 0 ; i < children.length; i++){
					if(children[i].name == nodeArr[j].name){
						searchTree.showNode(nodeArr[j]);
					}else{
						searchTree.hideNode(children[i]);
					}
				}
			}
		}
		
	}else{ //公有集群显示
		for(var i=0;i<nodes.length;i++){
			
			var node = nodes[i];
			
			if(inputVal == "" || inputVal == "集群名称"){
				searchTree.showNode(node);
			}else{
				if(node.name.indexOf(inputVal)!=-1){
					
					searchTree.showNode(node);
				
				}else{
					
					searchTree.hideNode(node);
				
				}
			}
			
		}
	}

}

/**
 * 验证查询是否合格
 */
function validateSearchContent(value){
	
	var isLegal = false;
	var reg = /[\/,\\,\[,\],%,&]/;
	if(value.match(reg)){
		isLegal = false;
	}else{
		isLegal = true;
	}
	
	return isLegal;
}

/**
 * 点击加载样式控制
 */
function loading(parentId,name){
	
	if(parentId == "publicTree" && name == "虚拟机列表"){
		
		$("#public_virtual_list").css("display","block");
		$("#public_virtual_detail").css("display","none");
		$("#publicTbody").empty().append("<tr><td colspan='5' align='center'><img src='../assets/images/ajax-loader.gif'>数据加载中</td></tr>");
		
	}else if(parentId == "privateTree" && name == "虚拟机列表"){
		
		$("#private_virtual_list").css("display","block");
		$("#private_virtual_detail").css("display","none");
		$("#privateTbody").empty().append("<tr><td colspan='5' align='center'><img src='../assets/images/ajax-loader.gif'>数据加载中</td></tr>");
		
	}else if(parentId == "publicSearchTree" && name == "虚拟机列表"){
		
		$("#result_public_virtual_list").css("display","block");
		$("#result_public_virtual_detail").css("display","none");
		$("#result_publicTbody").empty().append("<tr><td colspan='5' align='center'><img src='../assets/images/ajax-loader.gif'>数据加载中</td></tr>");
		
	}else if(parentId == "privateSearchTree" && name == "虚拟机列表"){
		
		$("#result_private_virtual_list").css("display","block");
		$("#result_private_result_detail").css("display","none");
		$("#result_privateTbody").empty().append("<tr><td colspan='5' align='center'><img src='../assets/images/ajax-loader.gif'>数据加载中</td></tr>");
		
	}
}
