$(document).ready(function(){
	
	function success(data) {

		if (!$.isArray(data)) {
			var temp = new Array();
			temp.push(data);
			data = temp;
		}
		//console.log( data);
	}
	
	/**下面注释部分为测试数据*/
	//var alermember = new AlertMember('7','6','192.168.0.127','excessive@126.com','1516091135');
	//alermember.save(success,error);
	//alermember.remove(success, error);
	//alermember.update();
	//AlertMember.removeById(7, success, error);
	//AlertMember.get(1, success, error);
	//AlertMember.list(success, error);
	
	function error() {
		alert("error");
	}
});