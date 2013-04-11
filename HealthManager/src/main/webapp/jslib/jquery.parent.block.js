function block(){
	
	$("#topLayer").block(
		{ 	
			message: null,
			overlayCSS:  { 
		        backgroundColor: '#000', 
		        opacity:         0.6, 
		        cursor:          'default' 
	   		},
	   		fadeIn:  300, 
	   	    fadeOut:  300, 
    });
	$("#menuLayer").block({ 	
			message: null,
			overlayCSS:  { 
		        backgroundColor: '#000', 
		        opacity:         0.6, 
		        cursor:          'default' 
	        },
	        fadeIn:  300, 
	   	    fadeOut:  300, 
	});
	
}

function unblock(){
	
	$("#topLayer").unblock({ 
		fadeIn:  300, 
   	    fadeOut:  300 });
	$("#menuLayer").unblock({
		
		 fadeIn:  300, 
	   	 fadeOut:  300 
		
	});
	
}


