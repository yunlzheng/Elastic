/**
 * 
 * 获取ajax请求信息，提示页面
 * @author zheng-pc
 *
 * */

jQuery.fn.ajaxListener = function(){
	
	$element = $(this);
	
	$element.ajaxComplete(function(){
		
		console.log("complete");
		
	});
	
	$element.ajaxSend(function(){
		
		console.log("send");
	});
	
	$element.ajaxSuccess(function(){
		console.log("success");
		
	});
	
}