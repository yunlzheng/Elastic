$(function() {
	
	$(".searchInput:[type='text']").focus(function(){
		
		var $text = $(".searchInput:[type='text']");
		
		if($text.attr("value") === "应用名搜索"){
			$text.css("color","gray");
			$text.attr("value","");
		}
			$text.css("color","black");
		
	});
	
	var servers = new Array();
	var r =20;
	var g = 20;
	var b  =20;
	
	var view = {
			ajaxinfo : '<div id="ajaxinfo" class="alert {status}">'
					+ '<button type="button" class="close" data-dismiss="alert">×</button>'
					+ '<strong>{message}</div>'

		};
	
	function getservers() {

		$.ajax({

			type : "get",
			url : "../rest/thirdparty/lb/status",
			success : success,
			error : error

		});
		
		var ajaxinfo = view.ajaxinfo.replace(/{status}/g, "alert-succes")
		.replace(/{message}/g, "loading...!");

		$("#ajaxinfo").replaceWith(ajaxinfo);
		$("#ajaxinfo").hide().show(500);
	
		
	}

	function success(data) {
    
		var length = data.length;
		
		var tablehead = "<table cellpadding='0' cellspacing='0' class='table'>"+
		"<thead>"+
		"<tr>"+
			"<th>编号</th>"+
			"<th>应用名称</th>"+
			"<th>服务器名称</th>"+
			"<th>当前活跃用户数</th>"+
			"<th>最大活跃用户数</th>"+
			"<th>最大用户限制</th>"+
			"<th>状态</th>"+
			"<th>上传流量<img src='../assets/images/up.png'/> </th>"+
			"<th>下载流量<img src='../assets/images/down.png'/></th>"+
			"<th>运行时间</th>"+
			"<th>操作</th>"+
		"</tr>"+
	    "</thead><tbody id='tbody'>";
		
		var servers = new Array();
		var spxname = new Array();
		
		for(var j = 0 ; j < length ; j++){
			
			var s = new server(data[j].pxname,data[j].svname,data[j].scur,data[j].smax,data[j].slim,data[j].status,data[j].bout,data[j].bin);
			
			if(data[j].pxname != 'stats' && data[j].pxname != '# pxname'&&data[j].pxname!=="http-in"){
				servers.push(s);
			}
			
		}
		
		
		spxname = servers.pxnamelist();  //获取到pxname列表
		
		//以pxname分类进行表格的创建
		for(var m = 0 ; m < spxname.length; m++){
			
			 var single = new Array();
			 
			 for(var n = 0 ; n < servers.length ; n++){
				 
				 if(spxname[m] == servers[n]["pxname"] && spxname[m] != "WOCLOUD_GATEWAY"){
					 
					 single.push(servers[n]);
					 
				 }
				 
			 }
			 var tr = "";
			 for(var i = 0 ; i < single.length ; i++){
				 
				     var svname = single[i].svname;
					 var ip = svname.substring(svname.lastIndexOf("_")+1,svname.length);
					 
					 if(single[i]["svname"] == "BACKEND"){
						 
						 tr += "<tr data_tab='" + single[i]["pxname"] + "'>" +
							"<td style='width:30px'>" + (i+1) +  "</td>" +
							"<td style='width:200px'>" +single[i]["pxname"] + "</td>" +
							"<td style='width:160px'>" + single[i]["svname"] + "</td>" +
							"<td style='width:120px'>" + single[i]["scur"] + "</td>" +
							"<td style='width:120px'>" + single[i]["smax"] + "</td>" +
							"<td style='width:90px'>" + single[i]["slim"] + "</td>" +
							"<td style='width:60px'>" + single[i]["status"] + "</td>" +
						    "<td style='width:80px'>" + single[i]["bout"] + "</td>" +
						    "<td style='width:80px'>" + single[i]["bin"] + "</td>" +
						    "<td style='width:80px'>" + single[i]["bout"] + "</td><td style='width:70px'><a href = 'privateEvnDetail.html?pxname=" + single[i]["pxname"] + ">查看详情</a></td></tr>";
					 }else{
						 
						 tr += "<tr data_tab='" + single[i]["pxname"] + "'>" +
								"<td style='width:30px'>" + (i+1) +  "</td>" +
								"<td style='width:200px'>" +single[i]["pxname"] + "</td>" +
								"<td style='width:160px'>" + single[i]["svname"] + "</td>" +
								"<td style='width:120px'>" + single[i]["scur"] + "</td>" +
								"<td style='width:120px'>" + single[i]["smax"] + "</td>" +
								"<td style='width:90px'>" + single[i]["slim"] + "</td>" +
								"<td style='width:60px'>" + single[i]["status"] + "</td>" +
							    "<td style='width:80px'>" + single[i]["bout"] + "</td>" +
							    "<td style='width:80px'>" + single[i]["bin"] + "</td>" +
							   // "<td style='width:80px'>" + single[i]["bout"] + "</td><td style='width:70px'><a href='arealtime.html?pxname="+ single[i]["pxname"]+"'>无</a></td></tr>";
							    "<td style='width:80px'>" + single[i]["bout"] + "</td><td style='width:70px'></td></tr>";
					 
					 }
						
			 }
			 
			 var table = tablehead + tr + "</tbody></table>";
			 
			 $("#tables").append(table);
			
		}
		
		 $("#tables .table:first").remove();
		
		

		hideloading();
		
		
		var option = {
				pagesize : 5,
				currentpage : 1,
				target : "#table-foot"
			};

	    $("#tables").qucikPage(option);
		

		var ajaxinfo = view.ajaxinfo.replace(/{status}/g, "alert-succes")
				.replace(/{message}/g, "load success!");

		$("#ajaxinfo").replaceWith(ajaxinfo);
		$("#ajaxinfo").hide().show(500);
		setTimeout(function() {
			$("#ajaxinfo").hide(500);
		}, 1500);
		
		$("#table-foot").css('display','block');
	}

	function error() {
		var ajaxinfo = view.ajaxinfo.replace(/{status}/g, "alert-succes")
		.replace(/{message}/g, "load error!");

		$("#ajaxinfo").replaceWith(ajaxinfo);
		$("#ajaxinfo").hide().show(500);
		setTimeout(function() {
			$("#ajaxinfo").hide(500);
		}, 1500);
	}

	function toHexColor(r, g, b)
	{
		var hex = '#';
		var hexStr = '0123456789ABCDEF';
		// R
		low = r % 16;
		high = (r - low) / 16;
		hex += hexStr.charAt(high) + hexStr.charAt(low);
		// G
		low = g % 16;
		high = (g - low) / 16;
		
		hex += hexStr.charAt(high) + hexStr.charAt(low);
		// B
		low = b % 16;
		high = (b - low) / 16;
		hex += hexStr.charAt(high) + hexStr.charAt(low);
		return hex;

	}
	getservers();
	//setInterval(getservers,5000);
	
	
	/**
	 * 点击查询
	 */
	$(".searchButton:[type='button']").click(function(){
		var check = $(".searchInput:[type='text']").val();
		var checkUper = check.toUpperCase();
		var pagecount = 0;
		var reg = /[\/,\\,\[,\],%,&]/;
		var result = check.match(reg);
		if(!result && check != "应用名搜索"){
			var data_tab = $("tr:[data_tab]");
			$("tr:[data_tab]").parent().parent().css("display","none");
			for(var i = 0 ; i < data_tab.length ; i++){
				var _data = $(data_tab[i]).attr("data_tab");
				if((_data.indexOf(check)) != -1 || _data.indexOf(checkUper) != -1){
					pagecount++;
					//console.log($("tr:[data_tab]")[i]);
					$($("tr:[data_tab]")[i]).parent().parent().css("display","table");
				}
				
			}
			
			if(pagecount != 0){
				$("#table-foot").css('display','none');
			}
			
		}else{
		   alert("请输入合法内容");
		}
		
	});
	
	/**
	 * 隐藏或显示搜索图片
	 */
	
	$(".searchInput:[type='text']").blur(function(){
		if($(this).val() != ""){
			$(".searchimg:eq(0)").css("display","inline");
		}
		
		if($(this).val() == ""){
			$(this).val("应用名搜索");
			$(this).css("color","gray");
		}
	});
	
	$(".searchInput:[type='text']").bind('keyup',function(event){
		if(event.keyCode == 13){
			$(".searchimg:eq(0)").css("display","inline");
			var check = $(".searchInput:[type='text']").val();
			var checkUper = check.toUpperCase();
			var pagecount = 0;
			var reg = /[\/,\\,\[,\],%,&]/;
			var result = check.match(reg);
			if(!result && check != ""){
				var data_tab = $("tr:[data_tab]");
				$("tr:[data_tab]").parent().parent().css("display","none");
				for(var i = 0 ; i < data_tab.length ; i++){
					var _data = $(data_tab[i]).attr("data_tab");
					if((_data.indexOf(check)) != -1 || _data.indexOf(checkUper) != -1){
						pagecount++;
						//console.log($("tr:[data_tab]")[i]);
						$($("tr:[data_tab]")[i]).parent().parent().css("display","table");
					}
					
				}
				
				if(pagecount != 0){
					$("#table-foot").css('display','none');
				}
				
			}else{
			   alert("请输入合法内容");
			}
		}
	});
	
	/**
	 * 点击查询处图表
	 */
	$(".searchimg:eq(0)").click(function(){
		/**将输入框清空*/
		$(".searchInput:[type='text']").val("应用名搜索");
		$(".searchInput:[type='text']").css("color","gray");
		$(this).css("display","none");
		$("#tables").empty();
		getservers();
		
	});
});

function hideloading(){
	$('.loaddata').css('display','none');
}

//创建服务器对象
function server(pxname,svname,scur,smax,slim,status,bout,bin){
	
	this.pxname = pxname;
	this.svname = svname;
	this.scur = scur;
	this.smax = smax;
	this.slim = slim;
	this.status = status;
	this.bout = bout;
	this.bin = bin;
	
	this.getServer = function(){
		return this;
	}
}

//获取pxname列表
Array.prototype.pxnamelist = function(){
	var n = {};
	var r = [];
	for(var i = 0 ; i < this.length; i++){
		
		if(!n[this[i].pxname]){
			n[this[i].pxname] = true;
			r.push(this[i].pxname);
		}
	}
	
	return r;
}




