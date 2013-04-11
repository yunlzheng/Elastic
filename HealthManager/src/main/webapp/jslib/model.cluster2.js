var created = new Array();

var VirtualServer = function(id, ip, status, clustername, createtime) {

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
		var existed = false;

		for ( var j = 0; j < created.length; j++) {

			if (created[j] == ip) {
				existed = true;

			}

		}

		if (!existed) {

			var virtualServer = new VirtualServer(i + "", ip, "running",
					this.name, this.createDateTime);
			this.vm.push(virtualServer);
		}

	}

	this.updateView();

};

Cluster.prototype.getVirtualListError = function() {

}

Cluster.prototype.updateView = function() {

		var records = Cluster.records;

		$("#view_cluster").empty();
		for ( var i = 0; i < Cluster.records.length; i++) {

			var cluster = Cluster.records[i];

			var vms = cluster.vm;
			var count = vms.length;

			for ( var j = 0; j < count; j++) {

				var vm = vms[j];
				// console.log(vm);
				var item = View.item1;
				item = item.replace(/{cluster_name}/g, vm.clustername).replace(
						/{vm_ip}/g, vm.ip).replace(/{vm_id}/, vm.id).replace(
						/{vm_status}/g, vm.status).replace(/{cluster_begin}/g,
						vm.createtime).replace(/{cluster_id}/g, j);
				$(item).appendTo("#view_cluster");

			}

		}
	
		var content = $("#view_cluster").html("<tr><td colspan='7'><h2>没有可监控的项目了</h2><a href='#'>申请新设备</a>|<a href='serverconfig.html?ip=10.0.2.2'>示例设置</a></td></tr>");
		
		
}


/**
 * Cluster 将全局变量代理到函数内 用于从全局找到Cluster对象
 */
Cluster.ajaxSuccess = $.proxy(function(data) {

	if (data.length == 0) {
		$("#view_cluster").html("<tr><td colspan='7'><h2>没有可监控的项目了</h2><a href='#'>申请新设备</a>|<a href='serverconfig.html?ip=10.0.2.2'>示例设置</a></td></tr>");
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
	
	var content = $("#view_cluster").html("<tr><td colspan='7'><h2>没有可监控的项目了</h2><a href='#'>申请新设备</a>|<a href='serverconfig.html?ip=192.168.0.1'>示例设置</a></td></tr>");
	
}

/**
 * 获取根据id获取成员编号
 */
Cluster.getByUserName = function(username, success, error) {

	MonitorConfig.list(getConfigSuccess, getConfigError);

	$.ajax({

		type : "get",
		url : "../rest/thirdparty/clusters/search/" + username,
		success : success,
		error : error,
		timeout : 5000

	});

};

function getConfigSuccess(data) {

	for ( var i = 0; i < data.length; i++) {

		created.push(data[i].ip);

	}

}

function getConfigError() {

}

var View = {

	item1 : '<tr>'
			+ '<td rowspan="{count}">{cluster_id}</td>'
			+ '<td>{vm_ip}</td>'
			+ '<td rowspan="{count}">{cluster_name}</td>'
			+ '<td rowspan="{count}">{cluster_begin}</td>'
			+ '<td rowspan="{count}">2013/8/12</td>'
			+ '<td>{vm_status}</td>'
			+ '<td><a href="serverconfig.html?ip={vm_ip}" class="test">监控设置</a></td>'
			+ '</tr>',

}
