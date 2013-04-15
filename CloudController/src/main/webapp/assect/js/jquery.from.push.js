// prepare the form when the DOM is ready 
$(document).ready(function() { 
	
    var options = { 
       
        beforeSerialize :showSerialize,
        beforeSubmit:  showRequest,  // pre-submit callback 
        success:       showResponse,  // post-submit callback 
        error:			showError,
        url:"../service/rest/applications/form",
        // other available options: 
        //url:       url         // override for form's 'action' attribute 
        //type:      type        // 'get' or 'post', override for form's 'method' attribute 
        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
        //clearForm: true        // clear all form fields after successful submit 
        //resetForm: true        // reset the form after successful submit 
        // $.ajax options can be used here too, for example: 
        //timeout:   3000 
    }; 
 
    var options2={
    		
    	beforeSubmit:beforeUploadHandler,
    	success:uploadResultHandler
    		
    }
    
    // bind form using 'ajaxForm' 
    $('#formCreateApplication').ajaxForm(options); 
    
    $("#formUploadApplication").ajaxForm(options2);
    
    
    
}); 

function beforeUploadHandler(){
	
	$("#btnUpload").attr("disabled","disabled");
	$("#btnUpload").text("上传中...");
	
}

function uploadResultHandler(responseText, statusText, xhr, $form){
	
	if("success"==responseText){
		
		$.dialog({
	    	content:'上传成功',
	    	icon: 'succeed',
	    	time:2
		});
		$("#btnUpload").removeAttr("disabled");
		$("#btnUpload").text("重新上传");
		
	}else{
		
		$.dialog({
	    	content:'文件上传失败',
	    	icon: 'error',
	    	time:2
		});
		$("#btnUpload").removeAttr("disabled");
		$("#btnUpload").text("上传");
		
	}
	
}

function showSerialize($form, options){

	return true;

}
 
// pre-submit callback 
function showRequest(formData, jqForm, options) { 
    
    var queryString = $.param(formData); 
    infoDialog = $.dialog({
    	content:'应用创建中...',
    	lock:true,
    });
    return true; 
    
} 
 
// post-submit callback 
function showResponse(responseText, statusText, xhr, $form)  { 
   
	if(responseText.uuid){
		
		successDialog = $.dialog({
		    	content:'应用创建成功',
		    	icon: 'succeed',
		    	lock:true,
		});
		
		infoDialog.close();
		self.location.href="home.jsp";
		
	}else{
		
		successDialog = $.dialog({
	    	content:responseText,
	    	icon:'error',
	    	lock:true,
		});
		
		infoDialog.close();
		
	}
   
}

function showError(data){
	
	console.log(data);
	infoDialog.close();
	errorDialog = $.dialog({
    	content:'应用创建失败，仓库服务异常',
    	lock:true,
	});
	
}