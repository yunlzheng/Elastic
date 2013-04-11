
//定义初始化记录条数
var count = 10;

$(document).bind('pageinit',function(){
	
	initPageData();
	
	$("#more").bind("click",getMoreInformation)
})

//初始化异常列表
function initPageData(){
	
	$.getJSON("../../rest/exception/0/10", function(data) {
		var title = "<li data-role='listdivider' data-theme='f'>异常列表</li>";
		$("ul#exception").empty().append(title).trigger('create');
		$.each(data, function(i, item) {
			var row ="<li data-filtertext='NASDAQ:GOOG Google Inc.' data-theme='"+getTheme(item.level)+"'><a href='exceptiondetail.html?id="+item.id+"' data-ajax='false'>"+item.name+"</a></li>";
			$("ul#exception").append(row);
		});
		$("ul#exception").listview('refresh');
	});
}

//根据异常等级获取button颜色符号
function getTheme(level) {
	switch(level){
		case 1:return "e";
		case 2:return "d";
		case 3:return "c";
		case 4:return "b";
		default:return "a";
	}
}

//点击查看更多异常信息处理函数
function getMoreInformation() {
	
	$.getJSON("../../rest/exception/"+count+"/"+(count+=10), function(data) {
		$.each(data, function(i, item) {
			var row ="<li data-filtertext='NASDAQ:GOOG Google Inc.' data-theme='"+getTheme(item.level)+"'><a href='exceptiondetail.html?id="+item.id+"' data-ajax='false'>"+item.name+"</a></li>";
			$("ul#exception").append(row);
		});
		$("ul#exception").listview('refresh');
	});
}










