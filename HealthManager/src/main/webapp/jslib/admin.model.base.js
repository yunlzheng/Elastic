var options = {
		offset:0,
		length:10
}

/**
 * 请求比例
 */
function BaseScale(success,error){
	
	$.ajax({
		  url: "../rest/combin/base/scale",
		  type:"get",
		  success: success,
		  error:error,
		  timeout : 3000
		});
	
}



var sendAjaxRequest = function(success, error) {
	
	var url = "../rest/exception/"+options.offset+"/"+options.length;

	$.ajax({

		type : "get",
		url : url,
		success : success,
		error : error,
		timeout : 3000

	});

}


/**
 * 对接受到的数据进行处理
 */
function successHandler(data){
	
	var index = options.offset;
	
	if(data.length == 0 || data.length < options.length){
		$("#more").text("没有更多数据了");
	}
	
	addDataToBody(data,index);
	options.offset = options.offset + data.length;
}


/**
 * 控制点击更多
 * @param data
 */
function isMore(error){
	
	var type = $.trim($("#select_type").find("option:selected").val());
	var level = $.trim($("#select_level").find("option:selected").val());
	sendAjaxRequest(successHandler,error,type,level);

}



function addDataToBody(data,index){
	
	for ( var i = 0; i < data.length; i++) {
		index++;
		/**
		 * 添加表格
		 */
		var keepTime = (Date.parse(data[i]["endTime"]) - Date
				.parse(data[i]["startTime"]))
				/ (3600 * 1000 * 24);

		var tr = "<tr><td>"+(index)+"</td><td>" + data[i]["name"] + "</td><td>"
				+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
				+ "</td><td>" + keepTime + "*24h</td><td>" + data[i]["message"]+"</td><td>"+transformExceptionLevel(data[i]["level"])+"</td>" +
				"<td><a excId='"+data[i]["id"]+"' style='cursor: pointer;'>查看详情</a></td></tr>";
		
		$("#tbody").append(tr);

	}
}

function transformExceptionLevel(level){
	
	level = parseInt(level);
	switch (level) {
	case 1:
		return "严重故障";
		break;
	case 2:
		return "一般故障";
		break;
	case 3:
		return "预警";
		break;
	default:
		return "待定义";
		break;
	}
	
}