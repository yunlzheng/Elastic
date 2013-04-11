function Search(searchName,Check,content,listMemSuccess,checkError){
	
	var options = {
		searchInput:".searchInput:[type='text']",
		searchImg:".searchimg:eq(0)",
		searchButton:".searchButton:[type='button']",
		searchName:""
	}
	
	options.searchName = searchName;
	//var options = $.extend(defaults, options);
	
	$(options.searchInput).focus(function(){
		
		var $text = $(this);
		
		if($text.attr("value") === options.searchName){
			$text.css("color","gray");
			$text.attr("value","");
		}
			$text.css("color","black");
		
	});
	
	
	/**
	 * 点击查询
	 */
	$(options.searchButton).click(function(){
		var check = $(options.searchInput).val();
		var reg = /[\/,\\,\[,\],%,&]/;
		var result = check.match(reg);
		if(!result){
			Check(content, listMemSuccess, checkError);
		}else{
		   alert("请输入合法内容");
		}
	});
	
	
	/**
	 * 隐藏或显示搜索图片
	 */
	
	$(options.searchInput).blur(function(){
		if($(this).val() != ""){
			$(options.searchImg).css("display","inline");
		}
		
		if($(this).val() == ""){
			$(this).val(options.searchName);
			$(this).css("color","gray");
		}
	});
	
	$(options.searchInput).bind('keyup',function(event){
		if(event.keyCode == 13){
			
			var check = $(options.searchInput).val();
			var reg = /[\/,\\,\[,\],%,&]/;
			var result = check.match(reg);
			if(!result){
				//checkList(check,getClusterSuccess,getClusterError);
			}else{
			   alert("请输入合法内容");
			}
		}
	});
	
	/**
	 * 点击查询处图标
	 */
	$(options.searchImg).click(function(){
		/**将输入框清空*/
		$(options.searchInput).val(options.searchName);
		$(options.searchInput).css("color","gray");
		$(this).css("display","none");
		getClusters(getClusterSuccess, getClusterError);
	});
};