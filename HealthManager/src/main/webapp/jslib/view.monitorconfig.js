/**对话框组*/
var delDialog;
var addMemDialog;

$(document).ready(function() {
	
		$.ajaxSetup({cache:false});
	
		var url = window.location.search;
		var pageMode = "update"// update更新模式 new创建模式
		var threshlod = {
			cpu:50,
			disk:50,
			io:50,
			mem:50
		};
			
		var showcpu = false;
		var showdisk = false;
		var showio = false;
		var showmem = false;
		var jointime = "1990/12/31";
		var ip = $.getParam("ip");
		var deyling = 300000;
		
		var cpu_hold = 90,mem_hold=90,io_hold=90,disk_hold=90;
		
		$("#view_ip").text(ip);
		
		MonitorConfig.get(ip, success, error);
		var account = $.cookie('account');
		
		Member.listByIp(ip,listMemSuccess, listMemError);
		Member.listByIpConfig(ip,listConfigMemSuccess, listConfigMemError);

		
		MonitorThreshold.get(ip, initThreadHoldSuccess, initThreadHoldError);
		/**
		 * 初始化阀值
		 * */
		function initThreadHoldSuccess(data){
			
			cpu_hold = data.cpuThreshlod;
			mem_hold = data.memThreshlod;
			io_hold = data.ioThreshlod;
			disk_hold = data.diskThreshlod;
			$("#select_cpu").attr("value",cpu_hold);
			$("#select_mem").attr("value",mem_hold);
			$("#select_io").attr("value",io_hold);
			$("#select_disk").attr("value",disk_hold);
			
		}
		function initThreadHoldError(){
			
		}
		
		$("#alertmemberlist").delegate("button","click",function(){
			
			//委托事件
			var $tr = $(this).parent().parent();
			$tr.find("[type='checkbox']").each(function(){
				
				$(this).removeAttr("checked");
				
			});
			$tr.appendTo("#view_memberlist");
			$tr.removeClass("second").addClass("first");
			
			var option = {
					pagesize : 5,
					currentpage : 1,
					target : "#footer1"
			};

			$("#view_memberlist").qucikPage(option);
			
		});
		
		$("#addMember").bind("click",function(){
			
			//Member.listByIp(ip,listMemSuccess, listMemError);
			
			blockParent();
			addMemDialog = art.dialog({
			    content: document.getElementById("mem-window"),
		    	title:'添加预警人员',
				lock:true,
				top:'150px',
				width:'600px',
				height:'80px',
				okValue:'确定',
				resize:false,
				
			    button: [
			             {
			                 name: '确定',
			                 focus: true,
			                 disabled:true
			             },{
			            	 name:"取消"
			             }
			         ],
				
				close:function(){
					unblockParent();
				},
				time:0
				
			});
			

		

			/**
			 * 
			 * */
			
			/**
			 * 检测是否有复选框选中
			 */
	
		});
		

		
		$(".set").bind("change",function(){
		
			if($(this).attr("checked")=="checked"){
				
				var _for = "#"+$(this).attr("for");
				$(_for).removeAttr("disabled");
			
			}else{
				
				var _for = "#"+$(this).attr("for");
				$(_for).attr("disabled","disabled");

			}
		

			
		});
		
		
		$("#btn_update").bind("click",function() {
			
					var querydata = "";
					/**
					 * 获取监控人员
					 * */
					$("#alertmemberlist").children().each(function(){
						
						var trid = $(this).attr("id");
						var id = trid.substring(trid.indexOf("-")+1,trid.length);
						var mailchecked = $(this).children().children("[alert-tab='"+id+"-1']").attr("checked");
						var tellchecked = $(this).children().children("[alert-tab='"+id+"-2']").attr("checked");
						
						var mailtag = false;
						var telltag = false;
						
						if(mailchecked=="checked"){
							mailtag = true;
						}
						
						if(tellchecked=="checked"){
							telltag=true;
						}
						
						querydata = querydata+"&data="+id+"-"+mailtag+"-"+telltag;
						
						
					});
					
					querydata = "?ip="+ip+querydata
					//console.log(querydata);
					AlertMember.saveBatch(querydata,alertmemupdatesuccess,alertmemupdateerror);
					
					
					var holdData = {
							"ip" : ip,
							"cpuThreshlod" : $("#select_cpu").find("option:selected").val(),
							"memThreshlod" : $("#select_mem").find("option:selected").val(),
							"ioThreshlod" : $("#select_io").find("option:selected").val(),
							"diskThreshlod" : $("#select_disk").find("option:selected").val()
						}
					
					MonitorThreshold.saveOrUpdate(holdData, holdSaveOrUpdateSuccess, holdSaveOrUpdateError);
					
					if ($("#check_cpu").attr("checked") == "checked") {
						
						var showcpu = true;
						
					}
		
					if ($("#check_disk").attr("checked") == "checked") {
						var showdisk = true;
					}
		
					if ($("#check_io").attr("checked") == "checked") {
						var showio = true;
					}
		
					if ($("#check_mem").attr("checked") == "checked") {
						var showmem = true;
					}

					$("#check_deyling option").each(function(){
						
						if($(this).attr("selected")=="selected"){
						
							deyling = $(this).attr("value");
						}
					
					});
					
					
					/**
					 * 获取监控法制配置
					 * */
				
					
					if(pageMode=="update"){
				
						var monitorConfig = new MonitorConfig(ip, showcpu,
								showmem, showio, showdisk, deyling);
						monitorConfig.update(success, error);
						
						blockParent();
						art.dialog({
						    content: "虚拟机监控设置保存成功!",
					    	title:'信息',
							lock:true,
							top:'200px',
							width:'250px',
							height:'70px',
							okValue:'确定',
							resize:false,
							ok:function(){
							
								$("#mode_title").text("修改监控设置");
								MonitorConfig.get(ip, success, error);
								
								
							},
							cancelVal: '取消',
						    cancel: true,
							close:function(){
								unblockParent();
							},
							time:0
							
						});
						


					
					}else if(pageMode=="new"){
						
						var monitorConfig = new MonitorConfig(ip, showcpu,
								showmem, showio, showdisk, deyling);
						monitorConfig.save(success, error);
						
						$("#windows-info").text("添加监控设置成功");
						
						blockParent();
						art.dialog({
						    content: "虚拟机监控设置添加成功!",
					    	title:'信息',
							lock:true,
							top:'200px',
							width:'250px',
							height:'70px',
							okValue:'确定',
							resize:false,
							ok:function(){
							
								$("#mode_title").text("修改监控设置");
								MonitorConfig.get(ip, success, error);
								
							},
							cancelVal: '取消',
						    cancel: true,
							close:function(){
								unblockParent();
							},
							time:0
							
						});
						
					
						
					}
					
					return false;

				});

		function success(data) {

			
			if (data != undefined && data != "") {

				pageMode = "update"
				$("#mode_title").text("修改监控设置");
				
				ip = data.ip;
				deyling = data.deyling;

				var btndele = $("<button class='btn btn-danger'>删除监控配置</button>");
				btndele.bind("click",function(){
				
					blockParent();
					delDialog = art.dialog({
					    content: "确认删除该虚拟机监控设置？",
				    	title:'警告',
						lock:true,
						top:'150px',
						width:'300px',
						height:'80px',
						okValue:'确定',
						ok:function(){
						
							MonitorConfig.removeById(ip,removeConfigSuccess,error);
							return false;
							
						},
						cancelVal: '取消',
					    cancel: true,
						close:function(){
							unblockParent();
						},
						time:0
						
					});
					
				});
				$("#btn_delete_span").empty().append(btndele);
				
				if (data.showCpu == "true" || data.showCpu == true) {

					var ele = $("#check_cpu");
					ele.attr("checked", "checked");
					var _for = "#"+ele.attr("for");
					$(_for).removeAttr("disabled");
					
				}

				if (data.showDisk == "true" || data.showDisk == true) {

					var ele = $("#check_disk");
					ele.attr("checked", "checked");
					var _for = "#"+ele.attr("for");
					$(_for).removeAttr("disabled");

				}

				if (data.showIo == "true" || data.showIo == true) {

					var ele = $("#check_io");
					ele.attr("checked", "checked");
					var _for = "#"+ele.attr("for");
					$(_for).removeAttr("disabled");

				}

				if (data.showMem == "true" || data.showMem == true) {

					var ele = $("#check_mem");
					ele.attr("checked", "checked");
					var _for = "#"+ele.attr("for");
					$(_for).removeAttr("disabled");

				}

				$("#check_deyling option").each(function() {

					if($(this).attr("value") == deyling){
						
						$(this).attr("selected","selected");
					
					}else{
					}
					
				});

			} else {

				pageMode = "new"
				$("#mode_title").text("创建监控设置");
				$("#btn_delete_span").empty();
				$("input:checked").removeAttr("checked");
				$(".hold").attr("disabled","disabled");				
				
			}

		}
		
		function removeConfigSuccess(data){
			
			delDialog.close();
			setTimeout(function(){
				
				MonitorConfig.get(ip, success, error);
				Member.listByIp(ip,listMemSuccess, listMemError);
				Member.listByIpConfig(ip,listConfigMemSuccess, listConfigMemError);
				
				art.dialog({
				    content: "监控设置删除成功",
			    	title:'信息',
			    	close:function(){
						unblockParent();
					}
			    });
				
				
			},1000);
			
			
			
			
		}

		function error() {
			
		}
		
		function listConfigMemError(){
			
		}
		
		function listConfigMemSuccess(data){
		
			if (!$.isArray(data)) {
				var temp = new Array();
				temp.push(data);
				data = temp;
			}

			$("#alertmemberlist").hide();
			$("#alertmemberlist").empty();

			for ( var i = 0; i < data.length; i++) {

				var obj = data[i];

				var member = new Member(obj.id, obj.name, obj.email,
						obj.tell, obj.creater, obj.joinTime);

				var item = memberview.item;

				var tr = $("<tr id='item-" + member.id + "' class='first'></tr>");
				var td = $("<td></td>");
				

				item = item.replace(/{index}/g, (i+1))
						.replace(/{id}/g,obj.id)
						.replace(/{creater}/g,obj.creater)
						.replace(/{email}/g, obj.email)
						.replace(/{name}/g, obj.name)
						.replace(/{tell}/g,obj.tell)
						.replace(/{joinTime}/g,obj.joinTime);

				tr.append(item);
				tr.removeClass("first").addClass("second");
				$("#alertmemberlist").append(tr);

			}

			$("#alertmemberlist").show();

			var option = {
				pagesize : 5,
				currentpage : 1,
				target : "#footer1"
			};

			$("#alertmemberlist").qucikPage(option);
			
		}
		
		
		function listMemSuccess(data) {

			if (!$.isArray(data)) {
				var temp = new Array();
				temp.push(data);
				data = temp;
			}

			$("#view_memberlist").hide();
			$("#view_memberlist").empty();

			for ( var i = 0; i < data.length; i++) {

				var obj = data[i];

				var member = new Member(obj.id, obj.name, obj.email,
						obj.tell, obj.creater, obj.joinTime);

				var item = memberview.item;

				var tr = $("<tr id='item-" + member.id + "' class='first'></tr>");
				var td = $("<td></td>");
				

				item = item.replace(/{index}/g, (i+1))
						.replace(/{creater}/g,obj.creater)
						.replace(/{email}/g, obj.email)
						.replace(/{name}/g, obj.name)
						.replace(/{tell}/g,obj.tell)
						.replace(/{joinTime}/g,obj.joinTime)
						.replace(/{id}/g,obj.id);

				var temp = $("#item-"+obj.id);
			
				if(temp.length!=1){
					
					tr.append(item);
					
				}
				
			
				$("#view_memberlist").append(tr);

			}

			$("#view_memberlist").show();

			var option = {
				pagesize : 4,
				currentpage : 1,
				target : "#footer1"
			};

			$("#view_memberlist").qucikPage(option);
			
			dialogMemIsChecked();

		}

		
		function listMemError() {
		}
		
		
		function holdSaveOrUpdateSuccess(data){
			//console.log(data);
		}
		
		function holdSaveOrUpdateError(){
			
		}
		

		var memberview = {

				item : '<td><input type="checkbox" class="step1"><span class="step1">{id}</span><button class="btn btn-mini btn-danger step2" style="margin:0 auto">移除</button></td>' + '<td>{name}</td>'
						 + '<td><input alert-tab="{id}-1" class="step2" type="checkbox"/>{email}</td>' 
						 + '<td><input alert-tab="{id}-2" class="step2" type="checkbox"/>{tell}</td>'+ '<td>{joinTime}</td>'
						
		};
		
		
		function checkSuccess(data) {
			$("#view_memberlist").empty();
			var tiems = "";
			for ( var i = 0; i < data.length; i++) {
				tiems = "<tr><td>" + data[i]["id"] +
				"</td><td>" + data[i]["name"] +
				"</td><td>" + data[i]["creater"] +
				"</td><td>" + data[i]["joinTime"] +
				"</td><td>" + data[i]["email"] +
				"</td><td>" + data[i]["tell"] +
				"</td><td><a href='configmember.html?id='" + data[i]["id"]
						+ "'>编辑</a><span>|</span><a id='" + data[i]["id"]
						+ "' data='" + data[i]["name"]
						+ "' href='#'>删除</a>" + "</td></tr>";
				
				$("#view_memberlist").append(tiems);
			}
		}

		function checkError() {
			//console.log("check error");
		}

		function alertmemupdatesuccess(data){
			//console.log(data);
		}
		
		function alertmemupdateerror(){
			
		}
		
		/**
		 * 查询
		 */
		$(".searchButton:[type='button']").click(function() {
			var content = $(".searchInput:[type='text']").val();
			if(content != ""){
				var reg = /[\/,\\,\[,\],%,&]/;
				var result = content.match(reg);
				if(!result){
					Check(content, checkSuccess, checkError);
				}else{
					alert("请输入合法的内容");
				}
				
			}else{
				alert("输入的内容不能为空");
			}
		});
		
		$(".searchInput:[type='text']").bind('keyup',function(event){
			if(event.keyCode==13){  
				var content = $(".searchInput:[type='text']").val();
				if(content != ""){
					var reg = /[\/,\\,\[,\],%,&]/;
					var result = content.match(reg);
					if(!result){
						Check(content, checkSuccess, checkError);
					}else{
						alert("请输入合法的内容");
					}
				}else{
					alert("输入的内容不能为空");
				}
			}  
		});
		
		
		function dialogMemIsChecked(){
			
			$("[type=checkbox].step1").bind("change",function(){
				
				var checked = false;
				$("[type=checkbox].step1").each(function(){
					
					if($(this).attr("checked")=="checked"){
						checked = true
						
					}
					
				});
				
				if(checked){
					
					addMemDialog.button({
		                name: '确定',
		                callback: function () {
		                  
		                	
							$("#view_memberlist").children().find(".step1").each(function(){
								if($(this).attr("checked")=="checked"){
									
									var $tr = $(this).parent().parent();
									$tr.appendTo("#alertmemberlist");
									$tr.removeClass("first").addClass("second");
									
									var option = {
											pagesize : 5,
											currentpage : 1,
											target : "#footer1"
										};
			
									$("#view_memberlist").qucikPage(option);
									
								}else{
									
								}
								
							});
							
							addMemDialog.button( {
				                 name: '确定',
				                 focus: true,
				                 disabled:true
				             },{
				            	 name:"取消"
				             });
							
							return false;
		               	 
		                },
		                focus: true
	            },{
	           	 name:"取消"
	            });
					
				}else{
					
					addMemDialog.button( {
		                 name: '确定',
		                 focus: true,
		                 disabled:true
		             },{
		            	 name:"取消"
		             });
					
				}
			
				
			});
			
		}
		
		/**
		 * 隐藏或显示搜索图片
		 */
		$(".searchInput:[type='text']").blur(function(){
			if($(this).val() != ""){
				$(".searchimg:eq(0)").css("display","inline");
			}
		});
		
		
		/**
		 * 点击查询处图表
		 */
		$(".searchimg:eq(0)").click(function(){
			/**将输入框清空*/
			$(".searchInput:[type='text']").val("");
			$(this).css("display","none");
			Member.listbyname(account, listMemSuccess, listMemError);
		});
		
		/**
		 * 返回正确页面
		 */
		$("#back_clur").bind("click",function(){
			if(url.indexOf("&server") != -1){
				$(this).attr("href","servers.html?SERVER");
			}
			else if(url.indexOf("&moniter") != -1){
				$(this).attr("href","servers.html?moniter");
			}
		});
});