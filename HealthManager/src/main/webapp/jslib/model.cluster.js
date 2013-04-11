var VirtualServer = function(id, ip, status, clustername,createtime) {

	this.id = id;
	this.ip = ip;
	this.status = status;
	this.clustername = clustername;
	this.createtime = createtime;

}

var Cluster = function(id, name, description, userId, userName, osImage,
		asCategory, asSerial, httpPort, httpsPort, min, max, status,
		createDateTime) {

	this.id = id;
	this.name = name;
	this.description = description;
	this.userId = userId;
	this.userName = userName;
	this.osImage = osImage;
	this.asCategory = asCategory;
	this.asSerial = asSerial;
	this.httpPort = httpPort;
	this.httpsPort = httpsPort;
	this.min = min;
	this.max = max;
	this.status = status;
	this.createDateTime = createDateTime;

	this.getVirtualList = function(success, error) {

		$.ajax({

			type : "get",
			url : "../rest/thirdparty/vm/search/" + this.id,
			success : success,
			error : error,
			timeout : 5000

		});

	}

}

Cluster.prototype.vm = [];

// 持久化对象
Cluster.records = new Array();

Cluster.prototype.getVirtualListSuccess = function(data) {

	for ( var i = 0; i < data.length; i++) {

		var ip = data[i];

		var virtualServer = new VirtualServer(i + "", ip, "running", this.name,this.createDateTime);
		this.vm.push(virtualServer);

	}

	this.updateView();

};

Cluster.prototype.getVirtualListError = function() {

}

Cluster.prototype.updateView = function() {

	var records = Cluster.records;

	if(records.length>0){
	
		$("#view_cluster").empty();
	}

	for ( var i = 0; i < Cluster.records.length; i++) {

		var cluster = Cluster.records[i];

		var vms = cluster.vm;
		var count = vms.length;

		for ( var j = 0; j < count; j++) {

			var vm = vms[j];
			//console.log(vm);
			var item = View.item1;
			item = item.replace(/{cluster_name}/g, vm.clustername)
				.replace(/{vm_ip}/g,vm.ip)
				.replace(/{vm_id}/,vm.id)
				.replace(/{vm_status}/g,vm.status)
				.replace(/{cluster_begin}/g, vm.createtime)
				.replace(/{cluster_id}/g,j);
			$(item).appendTo("#view_cluster");

		}

	}
	
	//console.log($("#view_cluster").text());
	
	var option = {
			pagesize:5,//每页大小
			currentpage:1,//当前页
			target:"#table-footer"//导航条所在位置
		};

	$("#view_cluster").qucikPage(option);
	
	if($("#view_cluster").text()==""){
		
		$("#view_cluster").html("<tr align='center'><td colspan='7' align='center'><br/><sapn>没有更多的数据了！</span><a href='sdetail.html?ip=10.0.2.2'>查看示例</a><br/><br/></td></tr>");
		
	}

}

/**
 * Cluster 将全局变量代理到函数内 用于从全局找到Cluster对象
 */
Cluster.ajaxSuccess = $.proxy(function(data) {

	if (data == undefined||data.length==0) {
		
		$("#view_cluster").html("<tr align='center'><td colspan='7' align='center'><br/><sapn>没有更多的数据了！</span><a href='sdetail.html?ip=10.0.2.2'>查看示例</a><br/><br/></td></tr>");
		return;
	}

	for ( var i = 0; i < data.length; i++) {

		var d = data[i];
		var cluster = new Cluster(d.id, d.name, d.description, d.userId,
				d.userName, d.osImage, d.asCategory, d.asSerial, d.httpPort,
				d.httpsPort, d.min, d.max, d.status, d.createDateTime);

		cluster.getVirtualList($.proxy(cluster.getVirtualListSuccess, cluster),
				cluster.getVirtualListError);

	}

	this.Cluster.records.push(cluster);

}, this);

Cluster.ajaxError = function() {

}

/**
 * 获取根据id获取成员编号
 */
Cluster.getByUserName = function(username, success, error) {

	$.ajax({

		type : "get",
		url : "../rest/thirdparty/clusters/search/" + username,
		success : success,
		error : error,
		timeout : 5000

	});

};

var View = {

	item1 : '<tr>'
			+ '<td rowspan="{count}">{cluster_id}</td>'
			+ '<td>{vm_ip}</td>'
			+ '<td rowspan="{count}">{cluster_name}</td>'
			+ '<td rowspan="{count}">{cluster_begin}</td>'
			+ '<td rowspan="{count}">2013/8/12</td>'
			+ '<td>{vm_status}</td>'
			+ '<td><a href="#" class="test">配置详情</a> | <a href="serverconfig.html?ip={vm_ip}" class="test">监控设置</a> | <a href="sdetail.html?ip={vm_ip}">监控详情</a></td>'
			+ '</tr>',

	item2 : '<tr>'
			+ '<td>{vm_id}</td>'
			+ '<td>{vm_ip}</td>'
			+ '<td>{vm_status}</td>'
			+ '<td><a href="#" class="test">配置详情</a> | <a href="sdetail.html?ip={vm_ip}">监控详情</a></td></tr>'

}

/**
 * 点击查询发送请求
 */
 var checkList = function(username,check,success,error){
	
	$.ajax({

		type : "get",
		url : "../rest/thirdparty/clusters/check/" + username + "/" + check,
		success : success,
		error : error,
		timeout : 5000

	});

};
