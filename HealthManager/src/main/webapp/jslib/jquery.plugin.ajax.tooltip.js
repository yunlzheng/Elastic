jQuery.fn.tooltip = function(options){
	
	var defaults = {
			
			event:{
				onload:function(){
				
				},
				onclose:function(){	
					return true;
				}
			},
			css:{
				"position":"absolute",
				"zIndex":1000,
				"backgroundColor":"#fff",
				"width":"auto",
				"minWidth":"150px",
				"height":"auto",
				"minHeight":"30px",
				"padding":"5px 0",
				"margin":"2px 0 0",
				"fontSize":"14px",
				"font-family":"'Helvetica Neue', Helvetica, Arial, sans-serif",
				"borderWidth":"1px",
				"borderStyle":"solid",
				"borderColor":'#CCC',
				"borderColor":'rgba(0, 0, 0, 0.2)',
				"borderRadius": "6px",
				"list-style": "none"
				
			},
			content:null
			
			
	}
	
	var options = $.extend(defaults, options);
	var $element = $(this);
	var tooltip =$("<div></div>");
	
	
	$element.live("mouseenter ",function(){
		
		event.stopPropagation;
		event.preventDefault;
		var x = event.pageX;
		var y = event.pageY;
		options.css["top"]=y-10;
		options.css["left"]=x;
		options.event.onload();
		tooltip.empty();
		tooltip.append(options.content);
		tooltip.appendTo("body").css(options.css);
	
	});
	
	$element.live("mouseout ",function(){
		
		options.event.onclose();
		tooltip.remove();
		
	})
	

}