// prepare the form when the DOM is ready 
$(document).ready(function() { 
	
    var options = { 
       
        beforeSubmit:  showRequest,  // pre-submit callback 
        success:       showResponse,  // post-submit callback 
        error:			showError,
       
    }; 
 
    $('#createTomcatForm').ajaxForm(options); 
    
}); 

function showRequest(formData, jqForm, options){
	
}

function showResponse(responseText, statusText, xhr, $form){
	
	if(responseText.uuid){
		alert("创建成功");
		self.location.href="services.jsp";
	}
	
}

function showError(){
	
}