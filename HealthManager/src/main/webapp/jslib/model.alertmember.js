var AlertMember = function (id, mid, ip, email, tell){
	
	this.id = id;
	this.mid = mid;
	this.ip = ip;
	this.email = email;
	this.tell = tell;
	
	this.save = function(success,error){
		
		$.ajax({

			type : "POST",
			url : "../rest/alertmembers",
			data : {
				"id" : this.id,
				"mid" : this.mid,
				"ip" : this.ip,
				"email" : this.email,
				"tell" : this.tell
			},
			success : success,
			error : error,
			timeout : 3000

		});
		
	}
	
	this.remove = function(success, error){
		$.ajax({
			type : "DELETE",
			url : "../rest/alertmembers/" + this.id,
			success : success,
			error : error,
			timeout : 3000
		});
	}
	
	
	this.update = function(success,error) {
		

		$.ajax({

			type : "PUT",
			url : "../rest/alertmembers/" + this.id,
			data : {
				"id" : this.id,
				"mid" : this.mid,
				"ip" : this.ip,
				"email" : this.email,
				"tell" : this.tell
			},
			success : success,
			error : error,
			timeout : 3000

		});
	}
	
}


AlertMember.removeById = function(id,success,error)
{
	//console.log("alertmembers instance remove:" + id);
	$.ajax({

		type : "DELETE",
		url : "../rest/alertmembers/" + id,
		success : success,
		error : error,
		timeout : 3000

	});

};


/**
 * 获取根据id获取成员编号
 */
AlertMember.get = function(id, success, error) {

	$.ajax({

		type : "get",
		url : "../rest/alertmembers/" + id,
		success : success,
		error : error,
		timeout : 3000

	});

};

/**
 * 获取所有预警成员
 */
AlertMember.list = function(success, error) {

	$.ajax({

		type : "get",
		url : "../rest/alertmembers",
		success : success,
		error : error,
		timeout : 3000

	});

};


/**
 * 批量修改监控人员信息
 * */
AlertMember.saveBatch = function(querystring,success,error){
	
	$.ajax({

		type : "post",
		url : "../rest/alertmembers/addBatch"+querystring,
		success : success,
		error : error,
		timeout : 3000

	});
	
}