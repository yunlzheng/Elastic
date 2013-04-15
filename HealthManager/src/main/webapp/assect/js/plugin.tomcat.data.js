function loadMemoryData(uuid,callback){
	
	$.ajax({
		
		url:"../service/rest/tomcat/data/memory/1/"+uuid,
		type:'GET',
		success:function(data){
			
			callback(data);
			
		},
		error:function(){
			
			
		}
		
	});
	
	
}

function loadThreadData(uuid,callback){
	
	$.ajax({
		
		url:"../service/rest/tomcat/data/thread/1/"+uuid,
		type:'GET',
		success:function(data){
			
			callback(data);
			
		},
		error:function(){
			
			
		}
		
	});
	
}

function loadFlowData(uuid,callback){
	
	$.ajax({
		
		url:"../service/rest/tomcat/data/flow/1/"+uuid,
		type:'GET',
		success:function(data){
			
			callback(data);
			
		},
		error:function(){
			
			
		}
		
	});
	
}