function callbackMemoryChart(data){
	
	 var jvmFrees = new Array();
	 var jvmMaxs = new Array();
	 var jvmUsed = new Array();
	
	 for(var i=0;i<data.length;i++){
		 
		 var d = data[i];
		
		 jvmFrees.push([d.logDate,d.jvmFree]);
		 jvmMaxs.push([d.logDate,d.jvmMax]);
		 jvmUsed.push([d.logDate,(d.jvmTotal-d.jvmFree)]);
	 }
	
	  var jvmMemoryChart = $('#'+charts.memoryCharts).highcharts({
          chart: {
              type: 'spline',
              backgroundColor:'#EAF8FF',
              height:300
          },
          title: {
              text: null
          },
          xAxis: {
              type: 'datetime',
              dateTimeLabelFormats: { // don't display the dummy year
                  month: '%e. %b',
                  year: '%b'
              }
          },
          yAxis: {
              title: {
                  text: '内存 (KB)'
              },
              min: 0
          },
          tooltip: {
              formatter: function() {
                      return '<b>'+ this.series.name +'</b><br/>'+
                      Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' KB';
              }
          },
          
          series: [{
              name: 'JVM可使用内存',
              // Define the data points. All series have a dummy year
              // of 1970/71 in order to be compared on the same x axis. Note
              // that in JavaScript, months start at 0 for January, 1 for February etc.
              data: jvmFrees
          }, {
              name: 'JVM使用的内存',
              data: jvmUsed
          }, {
              name: 'JVM最大可使用内存',
              data: jvmMaxs
          }]
      });
	
}

function callbackThreadChart(data){
	

	 var currentThreadCount = new Array();
	 var currentThreadsBusy = new Array();
	 var maxThreadCount = new Array();
	 
	 for(var i=0;i<data.length;i++){
		 
		 var d = data[i];
		 
		 currentThreadCount.push([d.logDate,d.currentThreadCount]);
		 currentThreadsBusy.push([d.logDate,d.currentThreadsBusy]);
		 maxThreadCount.push([d.logDate,d.maxThreadCount])
		 
	 }
	
	  var flowChart = $('#'+charts.thread).highcharts({
          chart: {
              type: 'spline',
              backgroundColor:'#EAF8FF',
              height:300
          },
          title: {
              text: null
          },
          xAxis: {
              type: 'datetime',
              dateTimeLabelFormats: { // don't display the dummy year
                  month: '%e. %b',
                  year: '%b'
              }
          },
          yAxis: {
              title: {
                  text: '内存 (KB)'
              },
              min: 0
          },
          tooltip: {
              formatter: function() {
                      return '<b>'+ this.series.name +'</b><br/>'+
                      Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' KB';
              }
          },
          
          series: [{
              name: '当前线程数',
              // Define the data points. All series have a dummy year
              // of 1970/71 in order to be compared on the same x axis. Note
              // that in JavaScript, months start at 0 for January, 1 for February etc.
              data: currentThreadCount
          }, {
              name: '当前忙碌线程数',
              data: currentThreadsBusy
          },
          {
              name: '最大线程数',
              data: maxThreadCount
          }]
      });
	
	
}

function callbackFlowChart(data){
	
	
	 var bytesReceived = new Array();
	 var bytesSend = new Array();
	
	 for(var i=0;i<data.length;i++){
		 
		 var d = data[i];
		
		 bytesReceived.push([d.logDate,d.bytesReceived]);
		 bytesSend.push([d.logDate,d.bytesSend]);
		 
	 }
	
	 console.log(bytesSend);
	 
	  var flowChart = $('#'+charts.flow).highcharts({
          chart: {
              type: 'spline',
              backgroundColor:'#EAF8FF',
              height:300
          },
          title: {
              text: null
          },
          xAxis: {
              type: 'datetime'
          },
          yAxis: {
              title: {
                  text: '内存 (KB)'
              },
              min: 0
          },
          tooltip: {
              formatter: function() {
                      return '<b>'+ this.series.name +'</b><br/>'+
                      Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' KB';
              }
          },
          
          series: [{
              name: '请求流量',
              data: bytesReceived
          }, {
              name: '响应流量',
              data: bytesSend
          }]
      });
	  
}


    