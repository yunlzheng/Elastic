$(function() {

	$("#btn_create").click(function() {

		redictToPush();

	});

	$('.login-info').click(function() {

		$("#menu3").toggle();

	});

});

function loadApplications(){
	
	$.ajax({
		
		url:'../service/rest/applications',
		type:'get',
		success:applicationDataHandler
		
	});
	
}

function applicationDataHandler(data){
	
	var temp = '<tr class="{tr_class}">'+
        '<td>{index}</td>'+
       ' <td><b>{name}</b></td>'+
        '<td><a href="http://{url}" target="_blank">http://{url}</a></td>'+
        '<td><b>{createDate}</b></td>'+
       ' <td><span class="label label-success">{info}</span></td>'+
       '<td>'+
		'<div class="btn-group">'+
	    '  <button class="btn btn-small" onclick="redirectDetail(\'{uuid}\')">详情</button>'+
	     ' <button class="btn btn-small dropdown-toggle" data-toggle="dropdown">'+
	    '  	<span class="caret"></span>'+
	     ' </button>'+
	     ' <ul class="dropdown-menu">';
	
   
	
	var temp3 =  ' </ul></div></td></tr>';
       
     $("#table_applications").empty();
       
     for(var i=0;i<data.length;i++){
    	 
    	 var application = data[i];
    	 
    	 var temp2 = htmlTemp2(application["operatStatus"],application["health"]);
    	 
    	 var temp2 = temp2.replace(/{uuid}/g,application["uuid"]);
    	 
    	 var row = temp.replace(/{index}/g,i)
    	 	.replace(/{name}/g,application["name"])
    	 	.replace(/{uuid}/g,application["uuid"])
    	 	.replace(/{url}/g,application["url"])
    	 	.replace(/{createDate}/g,application["createDate"])
    	 	.replace(/{health}/g,application["health"]);
    	 	
    	 
    	 if(application["operatStatus"]==0){
    		 
    		 row = row.replace(/{info}/g,getApplicationHealthDesc(application["health"]))
    		 		.replace(/{{tr_class}}/,"");
    		 
    	 }else{
    		 
    		 row = row.replace(/{info}/g,getApplicationOperatStatusDesc(application["operatStatus"]))
    		 		.replace(/{{tr_class}}/,"info");
    	 
    	 }
    	 
    	 row += temp2;
    	 row += temp3;
    	 
    	 $("#table_applications").append(row);
    	 
     }
	
}

function htmlTemp2(os,hel){
	
	var temp2 =    '<li><a  href="javascript:del(\'{uuid}\')">删除</a></li>';
	
	
	
	if(hel==100||hel==101){
		temp2 +='<li><a  href="javascript:deploy(\'{uuid}\')">部署</a></li>';	
	}
	
	
	
	if(hel==102){

		temp2 += '<li><a  href="javascript:stop(\'{uuid}\')">停止</a></li>';
		temp2 += '<li class="divider"></li><li><a  href="javascript:expand(\'{uuid}\')">扩展</a></li><li><a  href="javascript:shrink(\'{uuid}\')">收缩</a></li>';
		
	}
	
	if(hel==103){
		
		temp2 +=  '<li><a  href="javascript:start(\'{uuid}\')">启动</a></li>';		   
		
	}
	
	
	
	if(hel==102||hel==103){
		
		temp2 += '<li><a  href="javascript:undeploy(\'{uuid}\')">卸载</a></li>';
		
	}
	
	
	return temp2;
	
}

function redirectDetail(uuid){
	
	self.location.href = "detail.jsp?uuid="+uuid;
	
}

function del(uuid){

	$.ajax({
		
		url:'../service/rest/applications/'+uuid,
		type:'delete',
		success:function(data){
			
			alert(data);
		}
		
	});
}

function shrink(uuid){
	
	$.ajax({
		
		url:'../service/rest/cloudcontroller/shrink/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

function expand(uuid){
	
	$.ajax({
		
		url:'../service/rest/cloudcontroller/expand/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

function stop(uuid){
	
	$.ajax({
		
		url:'../service/rest/cloudcontroller/stop/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

function start(uuid){
	
	$.ajax({
		
		url:'../service/rest/cloudcontroller/start/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

/**卸载应用*/
function undeploy(uuid){
	
	$.ajax({
		
		url:'../service/rest/cloudcontroller/undeploy/'+uuid,
		type:'get',
		success:function(data){
			
			alert(data);
		}
		
	});
	
}

/**部署应用*/
function deploy(uuid){
	
	$.ajax({
		
		url:'../service/rest/cloudcontroller/deploy/'+uuid,
		type:'get',
		success:function (data){
			alert(data);
		}
		
	});
	
}



function redictToPush() {

	self.location.href = "create.jsp";

}