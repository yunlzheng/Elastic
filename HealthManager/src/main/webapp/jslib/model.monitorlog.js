var MonitorLog = function(id,ip,cpu,mem,io,disk,joinTime,detailTime) {

	this.id = id;
	this.ip = ip;
	this.cpu = cpu;
	this.mem = mem;
	this.io = io;
	this.disk = disk;
	this.joinTime = joinTime;
	this.detailTime = detailTime;

	this.remove = function() {

		$.ajax({

			type : "DELETE",
			url : "../rest/monitorlog/" + this.id,
			success : success,
			error : error,
			timeOut : 3000

		});

	}

	this.update = function() {

		$.ajax({

			type : "PUT",
			url : "../rest/monitorlog",
			data : {
				"id" : this.id,
				"ip" : this.name,
				"cpu" : this.email,
				"mem" : this.tell,
				"io" : this.creater,
				"disk" : this.disk,
				"jointime" : this.joinTime,
				"detailTime":this.detailTime
			},
			success : success,
			error : error,
			timeOut : 3000

		});

	}

	this.save = function() {
		$.ajax({

			type : "POST",
			url : "../rest/monitorlog",
			data : {
				"ip" : this.name,
				"cpu" : this.email,
				"mem" : this.tell,
				"io" : this.creater,
				"disk" : this.disk,
				"jointime" : this.joinTime,
				"detailTime":this.detailTime
			},
			success : success,
			error : error,
			timeOut : 3000

		});
	}

};

MonitorLog.get = function(id) {

	$.ajax({

		type : "get",
		url : "../rest/monitorlog/" + id,
		success : success,
		error : error,
		timeOut : 3000

	});

}

MonitorLog.getByIp = function(ip,success,error){
	
	$.ajax({

		type : "get",
		url : "../rest/monitorlog/search/"+ip,
		success : success,
		error : error,
		timeOut : 3000

	});
	
}

MonitorLog.list = function(success, error) {

	$.ajax({

		type : "get",
		url : "../rest/monitorlog",
		success : success,
		error : error,
		timeOut : 3000

	});

}

MonitorLog.getByMode = function(ip,mode,success,error){
	
	$.ajax({

		type : "get",
		url : "../rest/monitorlog/searchdate/"+ip+"/"+mode,
		success : success,
		error : error,
		timeOut : 3000

	});
	
}
