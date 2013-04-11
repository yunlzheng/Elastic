
$(document).bind('pageinit',function(){
	var ip = $.getParam("ip");
	initserverdata(ip);
});


function initserverdata(ip){
	
	$.ajax({
		type : "get",
		url : "../../rest/monitorconfig/"+ip,
		success : function (data){
			$("h3#title span").append(data.ip);
			$("p#content").append("创建时间:"+data.joinTime+"  <a href='serverconfig.html?ip="+ip+"'>修改监控设置</a>  <a href='#'>查看实时数据</a><br><br>CPU：2G 内存：256M 磁盘:20G 网络:1M带宽")
		}
	});
}



