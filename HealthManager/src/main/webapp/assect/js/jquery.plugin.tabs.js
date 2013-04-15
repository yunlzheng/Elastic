jQuery.fn.tabs = function(target){
	
	var $element = $(this);
	var $target = $(target);
	
	$element.children().each(function(){
		
		$(this).removeClass("active");
		
	});;
	var tabData = $element.find(":first").addClass("active").attr("tab-data");
	
	$target.find(">[tab-data]").hide();
	$target.find(">[tab-data='"+tabData+"']").show();
	
	$element.delegate(".tab","click",function(){
		
		$(".tab").removeClass("active");
		var data = $(this).addClass("active").attr("tab-data");
		$element.trigger("tabs.change",data);

		
	});
	
	$element.bind("tabs.change",function(e,data){
		
		$target.find(">[tab-data]").hide();
		$target.find(">[tab-data='"+data+"']").show();
		
	});
	
}