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
             '   <li><a href="#">启动</a></li>'+
             '   <li><a href="#">停止</a></li>'+
             '   <li><a href="#">卸载</a></li>'+
             '   <li class="divider"></li>'+
             '   <li><a href="#">扩展</a></li>'+
             '   <li><a href="#">收缩</a></li>'+
             ' </ul>'+
         ' </div>'+
       ' </td>'+
     ' </tr>'
       
     $("#table_applications").empty();
       
     for(var i=0;i<data.length;i++){
    	 
    	 var application = data[i];
    	 var row = temp.replace(/{index}/g,i)
    	 	.replace(/{name}/g,application["name"])
    	 	.replace(/{url}/g,application["url"])
    	 	.replace(/{health}/g,application["health"]);
    	 
    	 $("#table_applications").append(row);
    	 
     }
	
	console.log(data);
	
}

function redictToPush() {

	self.location.href = "push.jsp";

}