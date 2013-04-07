$(function() {

	loadApplications();
	
	$("#btn_create").click(function() {

		redictToPush();

	});

	$('.login-info').click(function() {

		$("#menu3").toggle();

	});
	
});

function loadApplications(){
	
	$.ajax({
		
		url:'service/rest/applications',
		type:'get',
		success:applicationDataHandler
		
	});
	
}

function applicationDataHandler(data){
	
	var temp = '<tr>'+
        '<td>{index}</td>'+
       ' <td><b>{name}</b></td>'+
        '<td><a href="http://hello.app.cloud.com" target="_blank">http://{url}</a></td>'+
        '<td><b>2009-12-31</b></td>'+
       ' <td><span class="badge badge-info">8</span></td>'+
       ' <td><span class="label label-success">{health}</span></td>'+
        '<td>'+
        	'<div class="btn-group">'+
            '  <button class="btn btn-small">详情</button>'+
             ' <button class="btn btn-small dropdown-toggle" data-toggle="dropdown">'+
            '  	<span class="caret"></span>'+
             ' </button>'+
             ' <ul class="dropdown-menu">'+
             '   <li><a href="javascript:deploy(\'{uuid}\')">部署</a></li>'+
             '   <li><a href="javascript:start(\'{uuid}\')">启动</a></li>'+
             '   <li><a href="javascript:stop(\'{uuid}\')">停止</a></li>'+
             '   <li><a href="javascript:undeploy(\'{uuid}\')">卸载</a></li>'+
             '   <li><a href="javascript:del(\'{uuid}\')">删除</a></li>'+
             '   <li class="divider"></li>'+
             '   <li><a href="javascript:expand(\'{uuid}\')">扩展</a></li>'+
             '   <li><a href="javascript:shrink(\'{uuid}\')">收缩</a></li>'+
             ' </ul>'+
         ' </div>'+
       ' </td>'+
     ' </tr>'
       
     $("#table_applications").empty();
       
     for(var i=0;i<data.length;i++){
    	 
    	 var application = data[i];
    	 var row = temp.replace(/{index}/g,i)
    	 	.replace(/{name}/g,application["name"])
    	 	.replace(/{uuid}/g,application["uuid"])
    	 	.replace(/{url}/g,application["url"])
    	 	.replace(/{health}/g,application["health"]);
    	 
    	 $("#table_applications").append(row);
    	 
     }
	
}

function del(uuid){
	alert("delete "+uuid);
}

function shrink(uuid){
	
	$.ajax({
		
		url:'service/rest/cloudcontroller/shrink/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

function expand(uuid){
	
	$.ajax({
		
		url:'service/rest/cloudcontroller/expand/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

function stop(uuid){
	
	$.ajax({
		
		url:'service/rest/cloudcontroller/stop/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

function start(uuid){
	
	$.ajax({
		
		url:'service/rest/cloudcontroller/start/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

/**卸载应用*/
function undeploy(uuid){
	
	$.ajax({
		
		url:'service/rest/cloudcontroller/undeploy/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

/**部署应用*/
function deploy(uuid){
	
	$.ajax({
		
		url:'service/rest/cloudcontroller/deploy/'+uuid,
		type:'get',
		success:function (data){
			alert(data);
		}
		
	});
	
}



function redictToPush() {

	self.location.href = "push.jsp";

}