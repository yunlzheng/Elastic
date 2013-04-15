$(function () {
    var chart;
    
    $(document).ready(function () {
    	
    	// Build the chart
        $('#container').highcharts({
            chart: {
            	backgroundColor:'#EAF8FF',
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                width:600,
                height:280
            },
            title: {
                text: null
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
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                type: 'pie',
                name: 'Browser share',
                data: [
                    ['提醒',       30],
                    ['故障',       20],
                    {
                        name: '正常',
                        y: 50,
                        sliced: true,
                        selected: true
                    }
                ]
            }]
        });
    });
    
});