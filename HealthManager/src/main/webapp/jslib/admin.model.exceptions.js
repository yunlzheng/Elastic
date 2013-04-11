
var options = {
		offset:0,
		length:10
}
/**
 * 页面载入时 获取异常比例
 */
var  scaleRequest = function(success,error){
	
	$.ajax({

		type : "get",
		url : "../rest/combin/exception/globalscale",
		success : success,
		error : error,
		timeout : 3000

	});
	
	
}



var sendAjaxRequest = function(success, error, type, level) {
	/**
	 * 如果size为空则获取所有的记录
	 */
	var url = "../rest/exception/"+options.offset+"/"+options.length;
	
	if(type==0&&level!=0){
		
		url = url + "?eqs=level:" + level;
		
	}else if(type!=0&&level==0){
		
		url = url + "?eqs=type:" + type;
		
	}else if(type!=0&&level!=0){
		url = url + "?eqs=type:" + type + "&eqs=level:" + level;
	}else{
		
		
	}


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

/**
 * 下拉列表选择
 */
function selectHandler(error){
	
	$("#select_type").bind("change",function(){
		
		clickInit();
		
		var type = $.trim($("#select_type").find("option:selected").val());
		var level = $.trim($("#select_level").find("option:selected").val());
		sendAjaxRequest(successHandler,error,type,level);
		
	});
	
	
	$("#select_level").bind("change",function(){
		
		var type = $.trim($("#select_type").find("option:selected").val());
		var level = $.trim($("#select_level").find("option:selected").val());
		
		//console.log("type:"+type+"  level:"+level);
		clickInit();
		sendAjaxRequest(successHandler,error,type,level);
		
	});
	
}


/**
 * 当点击下拉以后 对相关参数进行重置
 */
function clickInit(){
	$("#tbody").empty();
	options.offset = 0;
	$("#more").text("点击查看更多数据");
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
				"<td><a excId='"+data[i]["id"]+"' style='cursor: pointer;'>详情</a></td></tr>";
		
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
