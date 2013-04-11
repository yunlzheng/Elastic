
$.ajaxSettings.async = false;
$(document).bind('pageinit',function(){
	var id = $.getParam("id");
	initPageData(id);
})

//初始化异常列表
function initPageData(id){
	$.getJSON("/displayer/rest/exception/"+id+"", function(data) {
		$("input[id='name']").val(data.name);
		$("input[id='starttime']").val(data.startTime);
		$("input[id='retime']").val(data.endTime);
		$("input[id='reason']").val(data.message);
	});
}