jQuery.fn.time = function() {

	var element = $(this);

	function timer() {

		var year, month, day, hours, minutes, seconds, ap;
		var intYear, intMonth, intDay, intHours, intMinutes, intSeconds;
		var today;
		
		today = new Date();
		intYear = today.getFullYear();
		intMonth = today.getMonth() + 1;
		intDay = today.getDate();
		intHours = today.getHours();
		intMinutes = today.getMinutes();
		intSeconds = today.getSeconds();
		
		// 获取系统时间的小时数
		if (intHours == 0) {
			hours = intHours + ":";
			ap = "凌晨";
		} else if (intHours < 12) {
			hours = intHours + ":";
			ap = "早晨";
		} else if (intHours == 12) {
			hours = intHours + ":";
			ap = "中午";
		} else {
			intHours = intHours - 12;
			hours = intHours + ":";
			ap = "下午";
		}
		// 获取系统时间的分数
		if (intMinutes < 10) {
			minutes = "0" + intMinutes + ":";
		} else
			minutes = intMinutes + ":";
		// 获取系统时间的秒数
		if (intSeconds < 10)
			seconds = "0" + intSeconds + " ";
		else
			seconds = intSeconds + " ";
		
		timeString = hours + minutes + seconds + ap;

		timeStr = intYear+"-"+intMonth+"-"+intDay
		
		element.text(timeStr);

	}

	window.setInterval(function(){
		timer();
	}, 100);

	return this;

}