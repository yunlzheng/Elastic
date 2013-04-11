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
	
	var monitor = new MonitorThreshold('192.168.0.49','1','1','1','1');
	
	//monitor.save(success,error);
	monitor.update(success, error);
	//MonitorThreshold.list(success, error);
	//MonitorThreshold.get('192.168.0.49', success, error);
	//MonitorThreshold.removeById('192.168.0.49', success, error);
	//monitor.remove(success, error);
	
	function error() {
		alert("error");
	}
});