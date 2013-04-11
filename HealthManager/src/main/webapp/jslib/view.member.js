var memberview = {

	item : '<td>{id}</td>' + '<td>{name}</td>' + '<td>{creater}</td>'
			+ '<td>{joinTime}</td>' + '<td>{email}</td>' + '<td>{tell}</td>',

	ajaxinfo : '<div id="ajaxinfo" class="alert {status}">'
			+ '<strong>{message}</div>'
			
};

var json;
var orginname;
var account;
var amount = 0;
var option = {
		pagesize : 5,
		currentpage : 1,
		target : "#table-footer"
	};

/**
 * 定义对话框组
 * */
var updateDialog;
var addDialog;
var isAdd = false;

$(document).ready(function() {
	
	$.ajaxSetup({cache:false});
	
		
	var $success_dialog;
	
	var creater = $.cookie('account') ? $.cookie('account'): "guest@guest.com";
	account = $.cookie('account');
	
	addUser();
	
	$(".searchInput:[type='text']").focus(function() {
		var $text = $(".searchInput:[type='text']");

		if ($text.attr("value") === "账户名搜索") {
			$text.css("color", "gray");
			$text.attr("value", "");
		}
		$text.css("color", "black");

	});

	$("#memconfigform").submit(function(){
		
		try{
			
			if(isAdd){
				//console.log(addDialog);
				addDialog.config.ok();
				
			}else{
				//console.log(updateDialog);
				updateDialog.config.ok();
				
			}
			
		}catch (e) {
			//console.log(e);
		}
		
		
		return false;
		
	});
	
	Member.listbyname(creater, listMemSuccess, listMemError);

	function listMemSuccess(data) {
		
		json = data;
		
		if(data!=null&&data!=""&&data!=undefined&&data.length!=0){
			
			amount = data.length; // 记录总的纪录条数
			if (!$.isArray(data)) {
				var temp = new Array();
				temp.push(data);
				data = temp;
			}

			$("#view_memberlist").hide();
			$("#view_memberlist").empty();

			for ( var i = data.length-1; i >=0; i--) {

				var obj = data[i];

				var member = new Member(obj.id, obj.name,
						obj.email, obj.tell, obj.creater,
						obj.joinTime);

				var item = memberview.item;

				var tr = $("<tr id='item-" + member.id + "'></tr>");
				var td = $("<td></td>");
				var updat = $('<a  id=' + member.id+ '>修改</a>');

				var updateId;
				
				$(updat).bind("click",function() {
					
					//需要更新的预警人员编号
					updateId = ($(this).attr("id"));
					
					isAdd = false;
					
					Member.get(updateId,getSuccess, getError);
					
					blockParent();
					
					updateDialog = art.dialog({
					    content: document.getElementById("add-user"),
				    	title:'更新预警人员信息',
						lock:true,
						top:'150px',
						width:'300px',
						height:'80px',
						okValue:'更新',
						ok:function(){
						
							var email = $("#email").attr("value");
							var name = $("#name").attr("value");
							var tell = $("#tell").attr("value");

							if (editVailde()) {
								
								var nmember = new Member(updateId,name,email,tell,creater);
								nmember.update(updateSuccess,updateError);
								

							}
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

				var dele = $('<a dataid="' + member.id + '" data="'
						+ obj.name + '" >删除</a></td>');

				dele.bind("click", function() {

					var id = $(this).attr("dataid");
					var name = $(this).attr("data");

					$("#xxxid").attr("value", id);
					
					blockParent();
					art.dialog({
					    content: "确认删除该预警人员"+name+"相关信息？",
				    	title:'警告信息',
						lock:true,
						top:'150px',
						width:'300px',
						height:'80px',
						okValue:'确认',
						ok:function(){
							
							var _id = $("#xxxid").attr("value");
							Member.removeById(_id, removeSuccess,listMemError);
						
						},
						cancelVal: '取消',
					    cancel: true,
						close:function(){
							unblockParent();
						},
						time:0
					});

					

				});

				item = item.replace(/{id}/g, obj.id).replace(
						/{creater}/g, obj.creater).replace(
						/{email}/g, obj.email).replace(/{name}/g,
						obj.name).replace(/{tell}/g, obj.tell)
						.replace(/{joinTime}/g, obj.joinTime);

				tr.append(item);
				td.append(updat);
				td.append("&nbsp;&nbsp;");
				td.append(dele);
				tr.append(td);

				$("#view_memberlist").append(tr);

			}

			$("#view_memberlist").show();

			
			$("#view_memberlist").qucikPage(option);

			var ajaxinfo = memberview.ajaxinfo.replace(/{status}/g,
					"alert-succes").replace(/{message}/g,
					"load success!");

			$("#ajaxinfo").replaceWith(ajaxinfo);
			$("#ajaxinfo").hide().show(500);
			setTimeout(function() {
				$("#ajaxinfo").hide(500);
			}, 1500);
		
		}else{
			
			$("#view_memberlist").empty().append('<tr><td colspan="7"><span>数据为空</span></td></tr>');
			
		}
		
		

	}



	function removeSuccess(data) {

		amount = amount - 1;

		if (data.code == 200) {

			$("#item-" + data.key).hide(2000).remove();

			var option = {
				pagesize : 5,
				currentpage : 1,
				target : "#table-footer"
			};

			if (amount == 0) {

				$(".section").find("#table-footer").empty();
				$("#table-footer").append(
						'<div class="table-data-summary">第 '
								+ (0) + ' - ' + (0)
								+ '条纪录 / 共 ' + 0 + '条纪录</div>');
				$("#table-footer")
						.append(
								'<div class="table-pagination">'
										+ '<span class="pagination-info">第'
										+ option.currentpage
										+ '页 / 共 '
										+ 1
										+ '页 </span>'
										+ '<a  class="pagination-button" rel="first"><img src="../assets/images/pagination-first-arrow.png" /></a> '
										+ '<a  class="pagination-button" rel="brfore"><img src="../assets/images/pagination-prev-arrow.png" /></a>'
										+ '<a  class="pagination-button" rel="after"><img src="../assets/images/pagination-next-arrow.png" /></a>'
										+ '<a  class="pagination-button" rel="last"><img src="../assets/images/pagination-last-arrow.png" /></a>'
										+ '</div>');

				$("#table-footer").find(".pagination-button")
						.each(function() {

							$(this).addClass("disable");

						});
			} else {
				$("#view_memberlist").qucikPage(option);
			}

			var ajaxinfo = memberview.ajaxinfo.replace(
					/{status}/g, "alert-succes").replace(
					/{message}/g, "remove success!");

			$("#ajaxinfo").replaceWith(ajaxinfo);
			$("#ajaxinfo").hide().show(500);
			setTimeout(function() {
				$("#ajaxinfo").hide(500);
			}, 1500);

		}

	}

	function listMemError() {
	}


	function checkError() {
		//console.log("check error");
	}
	
	/**
	 * 添加相同用户的验证
	 */
	
	function updateSuccess(data) {
		
		$(".windows-info").css("display", "block");
		$("#windows-info").text("信息修改成功");
		Member.listbyname(creater, listMemSuccess,listMemError);
		setTimeout(function(){
			updateDialog.close();
			$(".windows-info").css("display", "none");
			$("#windows-info").text("");
		},1000);
		
	}
	
	function updateError() {
		
		$(".windows-info").css("display", "block");
		$("#windows-info").text("信息修改失败");
		
	}
	
	
	/**
	 * 搜索实现
	 */
	focusHandler($("#searchInput"));
	
	function focusHandler(searchInput){
		
		var searchCondition = "账户名搜索";
		var legal = "请输入合法内容";
		var inputVal = searchInput.val();
		
		if(inputVal == legal){
			searchInput.css("color","red");
		}
		
		searchInput.focus(function(){
			inputVal = searchInput.val();
			if(inputVal == searchCondition || inputVal == ""  || inputVal == legal){
				searchInput.val("");
				searchInput.css("color","black");
			}

		});
		
	   searchInput.blur(function(){
		   
		   inputVal = searchInput.val();
		   if(inputVal == ""){
			   searchInput.val(searchCondition);
			   searchInput.css("color","gray");
		   }
		   
	   });
	}
	
	/**
	 * 验证查询是否合格
	 */
	function isLegal(searchInput){
		
		var islegal = true;
		var legalCue = "请输入合法内容";
		//var $searchInput = $("#clusterSearchInput");
		var reg = /[\/,\\,\[,\],%,&]/;
		
		if((searchInput.val()).match(reg)){
			searchInput.val(legalCue);
			searchInput.css("color","red");
			islegal = false;
		}else{
			islegal = true;
		};
		
		return islegal;
	}
	
	/**
	 * 查询
	 */
	
	$("#searchButton").bind("click",function(){
		var $searchInput = $("#searchInput");
		var inputVal = $searchInput.val();
		if(!isLegal($searchInput)){
			$(this).blur();
		}else{
			if(inputVal == "账户名搜索"){
				inputVal = "null";
			}
			Check(inputVal, listMemSuccess, checkError);
		}
	});

	$("#searchInput").bind("keyup",function(event){
		var inputVal = $(this).val();
		if(event.keyCode == 13){
			if(!isLegal($(this))){
				$(this).blur();
			}else{
				if(inputVal == "账户名搜索" || inputVal == ""){
					inputVal = "null";
				}
				Check(inputVal, listMemSuccess, checkError);
			}
		}
	});




	/**
	 * 添加用户
	 */
	function addUser() {
		
		$("#add-user-button").bind("click",function(){
			
			isAdd = true;
			$("#email").attr("value", "");
			$("#name").attr("value", "");
			$("#tell").attr("value", "");
			
			blockParent();
			addDialog = art.dialog({
			    content: document.getElementById("add-user"),
		    	title:'添加预警人员信息',
				lock:true,
				top:'150px',
				width:'300px',
				height:'80px',
				okValue:'更新',
				ok:function(){
				
					var name = $("#name").val();
					var email = $("#email").val();
					var tell = $("#tell").val();

					if (validate()) {
						
						var member = new Member(0,name,email, tell,creater);
						member.save(saveSuccess, saveError);
					
					}
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

		

	}

	function saveSuccess(data) {
		
		$(".windows-info").css("display", "block");
		$("#windows-info").text("信息添加成功");
		Member.listbyname(creater, listMemSuccess,listMemError);
		setTimeout(function(){
			unblockParent();
			addDialog.close();
			$(".windows-info").css("display", "none");
			$("#windows-info").text("");
		},1000);
	}


	function saveError() {
		
		$(".windows-info").css("display", "block");
		$("#windows-info").text("信息添加失败");

	}


	function getSuccess(data) {

		$("#email").attr("value", data.email);
		$("#id").attr("value", data.id);
		$("#name").attr("value", data.name);
		$("#tell").attr("value", data.tell);
		orginname = data.name;
		
	}

	function getError() {
		//console.log("getError");
	}
	
});



function validate() {
	
	var flag = true;
	var name = $("#name").val();
	var email = $("#email").val();
	var tell = $("#tell").val();

	var nameVal = "成员名不能相同";
	var emailVal = "邮件不能为空";
	var emailFormate = "确保你的邮件格式正确";
	var phoneVal = "电话号码不能为空";
	var phoneFormate = "确保你的电话号码格式正确";
	
	var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
	var phone = /^(\d)+([-])*(\d)+(\d)$/;
	
	for(var i=0;i<json.length;i++){
		if(name == json[i].name){
			$("#name").focus(function() {
				$(this).val("");
				$("#name").css("color", "#000");
			});
			$("#name").css("color", "gray");
			$("#name").val(nameVal);
			flag = false;
		}
	}
	
	if (name == "" || name == nameVal) {
		$("#name").focus(function() {
			$(this).val("");
			$("#name").css("color", "#000");
		});
		$("#name").css("color", "gray");
		$("#name").val(nameVal);
		flag = false;
	}

	if (email == "" || email == emailVal) {

		$("#email").focus(function() {
			$(this).val("");
			$("#email").css("color", "#000");
		});
		$("#email").css("color", "gray");
		$("#email").val(emailVal);
		flag = false;
	} else if (!reg.test(email)) {
		$("#email").focus(function() {
			$(this).val("");
			$("#email").css("color", "#000");
		});
		$("#email").val(emailFormate);
		$("#email").css("color", "gray");
		flag = false;
	}

	if (tell == "" || tell == phoneVal) {
		$("#tell").focus(function() {
			$(this).val("");
			$("#tell").css("color", "#000");
		});
		$("#tell").css("color", "gray");
		$("#tell").val(phoneVal)
		flag = false;
	} else if (tell.length < 8) {
		$("#tell").focus(function() {
			$(this).val("");
			$("#tell").css("color", "#000");
		});
		$("#tell").css("color", "gray");
		$("#tell").val(phoneFormate);
		flag = false;
	} else if (!phone.test(tell)) {
		$("#tell").focus(function() {
			$(this).val("");
			$("#tell").css("color", "#000");
		});
		$("#tell").css("color", "gray");
		$("#tell").val(phoneFormate);
		flag = false;
	}
	
	return flag;
}


function editVailde(){
	
	var flag = true;
	var name = $.trim($("#name").val());
	var email = $.trim($("#email").val());
	var tell = $.trim($("#tell").val());
	
	var nameVal = "成员名不能相同";
	var emailVal = "邮件不能为空";
	var emailFormate = "确保你的邮件格式正确";
	var phoneVal = "电话号码不能为空";
	var phoneFormate = "确保你的电话号码格式正确";
	
	var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
	var phone = /^(\d)+([-])*(\d)+(\d)$/;
	var names = new Array();
	for(var i=0;i<json.length;i++){
		if(json[i].name != orginname){
			names.push(json[i].name);
		}
	}
	
	for(var i=0;i<names.length;i++){
		
		if(name == names[i] || name == nameVal){
			$("#name").focus(function() {
				$(this).val("");
				$("#name").css("color", "#000");
			});
			$("#name").css("color", "gray");
			$("#name").val(nameVal);
			flag = false;
		}
	}
	
	if (name == "" || name == nameVal) {
		$("#name").focus(function() {
			$(this).val("");
			$("#name").css("color", "#000");
		});
		$("#name").css("color", "gray");
		$("#name").val(nameVal);
		flag = false;
	}

	if (email == "" || email == emailVal) {

		$("#email").focus(function() {
			$(this).val("");
			$("#email").css("color", "#000");
		});
		$("#email").css("color", "gray");
		$("#email").val(emailVal);
		flag = false;
	} else if (!reg.test(email)) {
		$("#email").focus(function() {
			$(this).val("");
			$("#email").css("color", "#000");
		});
		$("#email").val(emailFormate);
		$("#email").css("color", "gray");
		flag = false;
	}

	if (tell == "" || tell == phoneVal) {
		$("#tell").focus(function() {
			$(this).val("");
			$("#tell").css("color", "#000");
		});
		$("#tell").css("color", "gray");
		$("#tell").val(phoneVal)
		flag = false;
	} else if (tell.length < 8) {
		$("#tell").focus(function() {
			$(this).val("");
			$("#tell").css("color", "#000");
		});
		$("#tell").css("color", "gray");
		$("#tell").val(phoneFormate);
		flag = false;
	} else if (!phone.test(tell)) {
		$("#tell").focus(function() {
			$(this).val("");
			$("#tell").css("color", "#000");
		});
		$("#tell").css("color", "gray");
		$("#tell").val(phoneFormate);
		flag = false;
	}
	
	return flag;
}
