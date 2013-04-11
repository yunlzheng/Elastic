
$(function(){
	
	$.ajaxSetup({cache:false});
	
	$("#view-tabs").tabs("#tabs");
	
	$("#public_cluster").click(function(){
		
		mode=1;
		
	});
	
	$("#private_cluster").click(function(){
		
		mode=2;
	
	});
	
	$("#public-view-tab-result").click(function(){
		
		mode=3;
		
	});
	
	$("#private-view-tab-result").click(function(){
		
		mode=4;
		
		
	});
	
	publicCluster(successCallBack,errorCalback);
	privateCluster(privateSuccessCallBack,privateErrorCallBack);
	tabChange();
	
	focusHandler($("#clusterSearchInput"));
	
	searchByClusterName();
	
	$("#public_tab_close").bind("click",function(event){
		
		$("#public-view-tab-result").hide();
		$("#public_cluster").click();
		event.stopPropagation();
		
	});
	
	$("#private_tab_close").bind("click",function(){
		
		$("#private-view-tab-result").hide();
		$("#private_cluster").click();
		event.stopPropagation();
		
	});
	
	
});






