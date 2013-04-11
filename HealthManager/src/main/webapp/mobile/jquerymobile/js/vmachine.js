
$(document).bind('pageinit',function(){
	var id = $.getParam("id");
	initVMdata(id);
})

function initVMdata(id){
	
	$.getJSON("../../rest/thirdparty/vm/search/"+id, function(data) {
		if(data.length > 0){
			var title = "<li data-role='listdivider' data-theme='f'>异常列表</li>";
			$("#vmlist").empty().append(title).trigger('create');
			$.each(data,function(i,item){
				var row = "<li data-filtertext='NASDAQ:GOOG Google Inc.' data-theme='c'><a href='serverdetail.html?ip="+item+"' data-ajax='false'>"+i+"号虚拟机&nbsp&nbsp&nbsp&nbspip:"+item+"</a></li>";
				$("#vmlist").append(row).trigger("create");
			});
		}else{
			$("#vmlist").empty().append("<a href='#' data-role='button' class='ui-disabled'>改集群没有任何虚拟机信息</a>").trigger("create");
		}
		$("#vmlist").listview("refresh");
	});
}


