$(function() {
	
	$.ajaxSetup({cache:false});
	
	$("#nav-tabs").tabs("#tabs");
	var servers = new Array();
	getservers();
	
	window.setInterval(function(){
		
		getservers();
		
	},20000);
	
	$("#searchButton").bind("click",function(){
		if(!isLegal()){
			$(this).blur();
		}else{
			search();
		}
	});
	$("#searchInput").bind("keyup",function(event){
		if(event.keyCode == 13){
			if(!isLegal()){
				$(this).blur();
			}else{
				search();
			};
		}
	});
	
	$("#result_tab_close").bind("click",function(){
		
		$("#tab-view").click();
		$("[tab-data='tab-result']").hide();
		return false;
		
	});
	

	/**
	 * 聚焦与失去焦点的处理
	 */
	focusAndBlur();
	
	function focusAndBlur(){
		
		var searchCondition = "应用名搜索";
		var inputVal;
		var legalCue = "请输入合法内容";
		
		var $searchInput = $("#searchInput");
		var $searchButton = $("#searchButton");
		$searchInput.focus(function(){
			inputVal = $searchInput.val();
			
			if(inputVal == searchCondition || inputVal == legalCue){
				$searchInput.val("");
				$searchInput.css("color","black");
			}
		});
		
		$searchInput.blur(function(){
			inputVal = $searchInput.val();
			if(inputVal == ""){
				$searchInput.val(searchCondition);
				$searchInput.css("color","gray");
			}
		});
	}
	/**
	 * 查询前检查输入内容是否合法
	 */
	function isLegal(){
		
		var islegal = true;
		var legalCue = "请输入合法内容";
		var $searchInput = $("#searchInput");
		var reg = /[\/,\\,\[,\],%,&]/;
		
		if(($searchInput.val()).match(reg)){
			$searchInput.val(legalCue);
			$searchInput.css("color","red");
			islegal = false;
		}else{
			islegal = true;
		};
		
		return islegal;
	}

	
	function search(){
		
		var key = $("#searchInput").val();
		var fined = false;
		
		if(key=='应用名搜索'){
			
			 $("#searchInput").attr("value","");
			 key = "";
		}
		
		$("#temp-searchkey").text('"'+key+'"的');
		
		$("#tab-result").empty();
		
		$("#lblist").children().each(function(){
			
			var temp = $(this).html();
			if(temp.indexOf(key)!=-1){
				
				fined=true;
				$("<div class='section' style='min-height:100px'/>").append(temp).appendTo("#tab-result");
				
			}
			
		});
		
		
		$("[tab-data='tab-result']").show().click();
		
	}
	
	function getservers() {
		
		$.ajax({

			type : "get",
			url : "../rest/thirdparty/lb/status",
			success : success,
			error : error,
			timeout: 4000

		});
		
	}

	function success(data) {
		
		if(data.length!=0&&data!=null&&data!=undefined){
			
			var length = data.length;
			
			var servers = new Array();
			var spxname = new Array();
			
			for(var j = 0 ; j < length ; j++){
				
				var s = new server(data[j].pxname,data[j].svname,data[j].scur,data[j].smax,data[j].slim,data[j].status,data[j].bout,data[j].bin);
				if(data[j].pxname != 'stats' && data[j].pxname != '# pxname'&&data[j].pxname!=="http-in"){
					servers.push(s);
				}
				
			}
			
			spxname = servers.pxnamelist();  //获取到pxname列表
			$("#lblist").empty();
			for(var i=0;i<spxname.length;i++){
				
				var lbs = view.lbs
					.replace(/{pxname}/g,spxname[i]);
				
				$("[table-content='tbody-"+spxname[i]+"']").empty();
				
				$("#lblist").append(lbs);
				
			}
			
			
			
			for(var i=0;i<data.length;i++){
				
				var item = data[i];
				for(var j=0;j<spxname.length;j++){
					
					var pxname = spxname[j];
					if(pxname==item.pxname){
						
						var views = view.item
							.replace(/{name}/g,item.svname)
							.replace(/{qcur}/g,item.qcur)
							.replace(/{smax}/g,item.smax)
							.replace(/{stot}/g,item.stot)
							.replace(/{bin}/g,item.bin)
							.replace(/{bout}/g,item.bout)
							
							.replace(/{econ}/g,item.econ)
							.replace(/{ereq}/g,item.ereq)
							.replace(/{eresp}/,item.eresp);
						
							if(item.checkstatus!=null&&item.checkstatus!=""){
								
								views = views.replace(/{status}/g,'<span class="label label-info">'+item.checkstatus+'</span>');
								
							}else{
								views = views.replace(/{status}/g,item.checkstatus);
							}
							
							if(item.status=="UP"){
								
								views = views.replace(/{runstatus}/g,'<span class="label label-success">'+item.status+'</span>');
								
							}else{
								
								views = views.replace(/{runstatus}/g,'<span class="label label-important">'+item.status+'</span>');
								
							}
						
							
							if(item.svname=="BACKEND"){
							
								views = views.replace(/{opt}/g,"<a href='privateEvnDetail.html?pxname=" + pxname +"'>监控详情</a>");
								
							}else{
								views = views.replace(/{opt}/g,"");
							}
							
						$("[table-content='tbody-"+pxname+"']").append(views);
						
					}
					
				}
				
			}
			
		}else{
			
			//数据为空
			$("#loadinfo").text("加载结果为空！");
		}
    
		
		
	}

	function error() {
		$("#loadinfo").text("数据加载异常，请联系管理员确保负载均衡已经开启");
	}
	
	var view = {
			
			lbs:'<div class="section" style="min-height:100px" id="section-{pxname}">'+
				'<div class="lb_title"><h2>{pxname}</h2></div>'+
				'<div class="lb_body">'+
					'<table cellpadding="0" cellspacing="0" class="table" style="text-align: center;">'+
						'<thead><tr>'+
						'<th style="width:300px">名称</th><th style="width:60px">请求队列</th><th style="width:100px">Session最大值</th><th style="width:100px">访问总量</th><th style="width:100px">请求流量</th><th style="width:100px">响应流量</th><th style="width:60px">错误连接</th><th style="width:60px">错误请求</th><th style="width:60px">错误响应</th><th style="width:60px">检查状态</th><th style="width:60px">运行状态</th><th>操作</th></tr></thead>'+
						'<tbody table-content="tbody-{pxname}"></tbody>'+
					'</table>'+
				'</div>'+
			'</div>',
			item:'<tr><td>{name}</td><td>{qcur}</td><td>{smax}</td><td>{stot}</td><td>{bin}</td><td>{bout}</td><td>{econ}</td><td>{ereq}</td><td>{eresp}</td><td>{status}</td><td>{runstatus}</td><td>{opt}</td></tr>'
				
	}

});

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