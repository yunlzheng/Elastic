
$(document).bind('pageinit',function(){
	initClusterData();
	$("#cluster").bind("click",initClusterData);
	$("#monitor").bind("click",function(){
		$("ul#clusterlist").empty();
	});
});

//初始化集群列表
function initClusterData(){
	$.getJSON("../../rest/thirdparty/clusters/search/"+$.cookie('account'), function(data) {
		$("ul#clusterlist").empty();
		if(data != null){
			$.each(data, function(i, item) {
				var row ="<li data-filtertext='NASDAQ:GOOG Google Inc.' data-theme='d'><a href='vmachine.html?id="+item.id+"' data-ajax='false'>集群-"
					+item.name+"&nbsp&nbsp["+item.asCategory+"-"+item.asSerial+"]</a></li>";
				$("ul#clusterlist").append(row).trigger('create');
				$("ul#clusterlist").listview('refresh');
			});
		}else {
			$("div#server").append("没有集群信息").trigger('create');
		}
	});
}


