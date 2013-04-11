function blockParent(){
	
	var parent = window.parent
	if(parent){
		
		try {
			parent.block();	
		} catch (e) {
			
		}
		
	}
}

function unblockParent(){
	var parent = window.parent
	if(parent){
		try {
			parent.unblock();
		}catch(e){
			
		}
	}
}
