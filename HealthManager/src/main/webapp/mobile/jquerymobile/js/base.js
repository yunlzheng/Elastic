$(document).bind("pageinit",function(){
	
	show();
	
	function show(){
		var chart = new Highcharts.Chart({
	        chart: {
	            renderTo: 'container',
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
	            backgroundColor:'#F5F5F5'
	        },
	        title: {
	            text: '云监控概况'
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage}%</b>',
	        	percentageDecimals: 1
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    formatter: function() {
	                        return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '统计情况',
	            data: [
	                ['故障',   50],
	                ['正常',     30],
	                {
	                    name: '提醒',
	                    y: 20,
	                    sliced: true,
	                    selected: true
	                }
	               
	                
	            ]
	        }]
	    });
	}
	
	
});