var MonitorThreshold = function (ip, cpuThreshlod, memThreshlod, ioThreshlod, diskThreshlod){
	
	this.ip = ip;
	this.cpuThreshlod = cpuThreshlod;
	this.memThreshlod = memThreshlod;
	this.ioThreshlod = ioThreshlod;
	this.diskThreshlod = diskThreshlod;
	
	this.save = function(success,error){
		
		$.ajax({

			type : "POST",
			url : "../rest/monitorthreshold",
			data : {
				"ip" : this.ip,
				"cpuThreshlod" : this.cpuThreshlod,
				"memThreshlod" : this.memThreshlod,
				"ioThreshlod" : this.ioThreshlod,
				"diskThreshlod" : this.diskThreshlod
			},
			success : success,
			error : error,
			timeout : 3000

		});
		
	}
	
	this.remove = function(success, error){
		$.ajax({
			type : "DELETE",
			url : "../rest/monitorthreshold/" + this.ip,
			success : success,
			error : error,
			timeout : 3000
		});
	}
	
	
	this.update = function(success,error) {
		

		$.ajax({

			type : "PUT",
			url : "../rest/monitorthreshold/" + this.ip,
			data : {
				"ip" : this.ip,
				"cpuThreshlod" : this.cpuThreshlod,
				"memThreshlod" : this.memThreshlod,
				"ioThreshlod" : this.ioThreshlod,
				"diskThreshlod" : this.diskThreshlod
			},
			success : success,
			error : error,
			timeout : 3000

		});
	}
	
}

MonitorThreshold.saveOrUpdate = function(data,success,error){
	
	$.ajax({

		type : "POST",
		url : "../rest/monitorthreshold",
		data : data,
		success : success,
		error : error,
		timeout : 3000

	});
	
}

MonitorThreshold.removeById = function(ip,success,error)
{
	//console.log("alertmembers instance remove:" + ip);
	$.ajax({

		type : "DELETE",
		url : "../rest/monitorthreshold/" + ip,
		success : success,
		error : error,
		timeout : 3000

	});

};


/**
 * 获取根据id获取成员编号
 */
MonitorThreshold.get = function(ip, success, error) {

	$.ajax({

		type : "get",
		url : "../rest/monitorthreshold/" + ip,
		success : success,
		error : error,
		timeout : 3000

	});

};

/**
 * 获取所有预警成员
 */
MonitorThreshold.list = function(success, error) {

	$.ajax({

		type : "get",
		url : "../rest/monitorthreshold",
		success : success,
		error : error,
		timeout : 3000

	});

};
