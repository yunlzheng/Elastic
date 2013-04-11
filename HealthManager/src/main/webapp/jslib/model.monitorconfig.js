var MonitorConfig = function (ip, showCpu, showMem, showIo, showDisk,deyling){
	
	
	this.ip = ip;
	this.showCpu = showCpu;
	this.showMem = showMem;
	this.showIo = showIo;
	this.showDisk = showDisk;
	this.deyling = deyling;
	
	this.save = function(success,error){
		
		$.ajax({

			type : "POST",
			url : "../rest/monitorconfig",
			data : {
				"ip" : this.ip,
				"showCpu" : this.showCpu,
				"showMem" : this.showMem,
				"showIo" : this.showIo,
				"showDisk" : this.showDisk,
				"deyling":this.deyling
			},
			success : success,
			error : error,
			timeout : 3000

		});
		
	}
	
	this.remove = function(ip,success, error){
		
		var _ip = ip?ip:this.ip;
		$.ajax({
			type : "DELETE",
			url : "../rest/monitorconfig/" + this.ip,
			success : success,
			error : error,
			timeOut : 3000
		});
	}
	
	
	this.update = function(success,error) {
		

		$.ajax({

			type : "PUT",
			url : "../rest/monitorconfig",
			data : {
				"ip" : this.ip,
				"showCpu" : this.showCpu,
				"showMem" : this.showMem,
				"showIo" : this.showIo,
				"showDisk" : this.showDisk,
				"deyling":this.deyling
			},
			success : success,
			error : error,
			timeOut : 3000

		});
	}
	
}


MonitorConfig.removeById = function(id,success,error)
{
	//console.log("alertmembers instance remove:" + id);
	$.ajax({

		type : "DELETE",
		url : "../rest/monitorconfig/" + id,
		success : success,
		error : error,
		timeOut : 3000

	});

};


/**
 * 获取根据id获取成员编号
 */
MonitorConfig.get = function(ip, success, error,that) {

	$.ajax({
		dataType:"json",
		type : "get",
		url : "../rest/monitorconfig/" + ip,
		success : $.proxy(success,that),
		error : $.proxy(error,that),
		timeOut : 5000

	});

};

/**
 * 获取所有预警成员
 */
MonitorConfig.list = function(success, error) {

	$.ajax({

		type : "get",
		url : "../rest/monitorconfig",
		success : success,
		error : error,
		timeOut : 3000

	});

};

/**
 * 查询
 */

var Check = function(check,success,error){
	$.ajax({

		type : "POST",
		url : "../rest/members/" + check,
		success : success,
		error : error,
		timeout : 5000

	});
}
