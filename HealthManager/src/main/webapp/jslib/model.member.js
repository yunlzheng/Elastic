var Member = function(id, name, email, tell, creater) {

	this.id = id;
	this.name = name;
	this.tell = tell;
	this.creater = creater;
	this.email = email;
	
	this.save=function(success,error){
		
		$.ajax({

			type : "POST",
			url : "../rest/members",
			data : {
				"name":this.name,
				"email":this.email,
				"tell":this.tell,
				"creater":this.creater
			},
			success : success,
			error : error,
			timeout : 5000

		});
		
	}
	
	this.remove = function(success, error) {

		$.ajax({

			type : "DELETE",
			url : "../rest/members/" + this.id,
			success : success,
			error : error,
			timeout : 5000

		});

	}

	this.update = function(success, error) {

		$.ajax({

			type : "PUT",
			url : "../rest/members/" + id,
			data : {
				"id" : this.id,
				"name" : this.name,
				"email" : this.email,
				"tell" : this.tell,
				"creater" : this.creater
			},
			success : success,
			error : error,
			timeout : 5000

		});

	}
	
	

};

Member.removeById = function(id,success,error)
{
	//console.log("remove instance "+id);
	$.ajax({

		type : "DELETE",
		url : "../rest/members/" + id,
		success : success,
		error : error,
		timeout : 5000

	});

};

/**
 * 获取根据id获取成员编号
 */
Member.get = function(id, success, error) {

	$.ajax({

		type : "get",
		url : "../rest/members/" + id,
		success : success,
		error : error,
		timeout : 5000

	});

};

Member.listByTenant = function(success,error){
	
	
	
}

/**
 * 获取所有预警成员
 */
Member.list = function(success, error) {

	$.ajax({

		type : "get",
		url : "../rest/members",
		success : success,
		error : error,
		timeout : 5000

	});

};

/**
 * 获取所有预警成员
 */
Member.listbyname = function(useraccount,success, error) {

	$.ajax({

		type : "get",
		url : "../rest/members/key1/"+useraccount,
		success : success,
		error : error,
		timeout : 5000

	});

};

/**
 * 获取所有为对该服务器未预警的用户列表
 * */
Member.listByIp = function(ip,success,error){
	
	$.ajax({

		type : "get",
		url : "../rest/members/key2/"+ip,
		success : success,
		error : error,
		timeout : 5000

	});
	
}

/**
 * 获取所有为对该服务器进行预警的用户列表
 * */
Member.listByIpConfig = function(ip,success,error){
	
	$.ajax({

		type : "get",
		url : "../rest/members/key3/"+ip,
		success : success,
		error : error,
		timeout : 5000

	});
	
}

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

function addfooter(flag, option) {

	if (flag) {
		$("#table-footer").append(
				'<div class="table-data-summary">第 '
						+ (0) + ' - ' + (0)
						+ '条纪录 / 共 ' + 0 + '条纪录</div>');
		$("#table-footer")
				.append(
						'<div class="table-pagination">'
								+ '<span class="pagination-info">第'
								+ option.currentpage
								+ '页 / 共 '
								+ 1
								+ '页 </span>'
								+ '<a  class="pagination-button" rel="first"><img src="../assets/images/pagination-first-arrow.png" /></a> '
								+ '<a  class="pagination-button" rel="brfore"><img src="../assets/images/pagination-prev-arrow.png" /></a>'
								+ '<a  class="pagination-button" rel="after"><img src="../assets/images/pagination-next-arrow.png" /></a>'
								+ '<a  class="pagination-button" rel="last"><img src="../assets/images/pagination-last-arrow.png" /></a>'
								+ '</div>');

		$("#table-footer").find(".pagination-button")
				.each(function() {

					$(this).addClass("disable");

				});
	} else {
		$("#view_memberlist").qucikPage(option);
	}

}
