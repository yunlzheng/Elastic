$(function(){
	
	var timer = 1000;
	var c1 = $(".row li:nth-child(1)");
	c1.animate({opacity:"0"},0);
	
	var c2 = $(".row li:nth-child(2)");
	c2.animate({opacity:"0"},0);
	
	var c3 = $(".row li:nth-child(3)");
	c3.animate({opacity:"0"},0);
	
	var c4 = $(".row li:nth-child(4)");
	c4.animate({opacity:"0"},0);
	
	setTimeout(function(){
		
		c1.animate({
			opacity:"1",
			marginTop:"-=50"
		},1000);
		
	},timer);
	
	timer += 700;
	
	setTimeout(function(){
		
		c2.animate({
			opacity:"1",
			marginTop:"-=100"
		},1000);
		
	},timer);
		
	timer += 700;
	
	setTimeout(function(){
		
		c3.animate({
			opacity:"1",
			marginTop:"-=50"
		},1000);
		
		
	},timer);
		
	timer += 700;
	
	setTimeout(function(){
		
		c4.animate({
			opacity:"1",
			marginTop:"-=100"
		},1000);
		
		
	},timer);
		
	
});


function toTop(){
	$(".to-top").bind("click",function(){
		$( 'html, body' ).animate( { scrollTop: 0 }, 500 );
	});
	
	/**
	 * 控制滚动条的出现 
	 */
	
	$(window).scroll(function(){
		var scroll = $(window).scrollTop();
		if(scroll==0){
			$(".to-top").css("display","none");
		}else{
			$(".to-top").fadeIn({"display":"block"},1000);
		}
		
	});
}
