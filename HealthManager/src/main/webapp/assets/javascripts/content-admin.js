var router = {
		
		"#1":"base.html",
		"#2":"exception.html",
		"#3":"alert.html",
		"#4":"lbmonitor.html",
		"#5":"clusters.html",
		"#6":"mailSmsConfig.html",
		
}

$(function(){
	
	var hash = location.hash;
	
	hashchange();
	
	if( ('onhashchange' in window) && ((typeof document.documentMode==='undefined') || document.documentMode==8)) {
	  
	    window.onhashchange = hashchange;  
	    
	} else {
		
		setInterval(function(){
			
			if(isHashChanged()){
				
				hashchange();
				hash =location.hash;
				
			}
			
		},100);
		
	}
	
	$(".icpmenu a").bind("click",function(){
		
		var url = $(this).attr("data");
		$(this).attr("href",url+"_"+guid()+"?timestamp="+new Date().getTime());
		return true;
		
	});
	
	function isHashChanged(){
		
		if(hash==location.hash){
			return false;
		}else{
			return true;
		}
		
	}
	
	function hashchange(){

		var _hash = location.hash;
		var index = _hash.substring(0,_hash.indexOf("_"));
		
		var url = router[index];
		var target = "index.html"+location.hash;
		if(url==undefined){
			url=router["#1"];
			target = "index.html#1"
		}
		
		$(".container a").removeClass("chilOver");
		var temp = target.substring(0,target.indexOf("_"));
		$("a[href^='"+temp+"']").addClass("chilOver");
		$("#contentLayerFrame").attr("src",url);
		
	}
	
	function guid(){
		
		return 'xxxxxyyy4xyyyxyyxyx'.replace(/[xy]/g,function(c){
			
			var r = Math.random()*16|0,v=c=='x'?r:(r&0x3|0x8);
			return v.toString(16);
			
		}).toUpperCase();
	}
	
});