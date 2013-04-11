var account;
$(document).ready(

function() {
	$.ajaxSetup({cache:false});
	var creater = $.cookie('account')?$.cookie('account'):"guest@guest.com";
	account = $.cookie('account');

	var mode = "new"// update
	var loaded = false;	
	

	var _userid = $.getParam("id");
	if (_userid == "" || _userid == null) {

		$("#view_title").text("创建预警联系人");
		$("#other_view_title").text("创建预警联系人");
		$("#action").text("创建");
		mode = "new";

	} else {

		$("#view_title").text("修改预警联系人");
		$("#other_view_title").text("修改预警联系人");
		$("#action").text("修改");
		mode = "update";
		Member.get(_userid, success, error);

	}

	// 时间监听
	$("#action").bind("click", function() {

		var name = $("#name").val();
		var email = $("#email").val();
		var tell = $("#tell").val();

		var member = new Member(name, email, tell, creater);

			member.save(success, error);



	});

	function success(data) {

		$("#email").attr("value", data.email);
		$("#id").attr("value", data.id);
		$("#name").attr("value", data.name);
		$("#tell").attr("value", data.tell);

	
			$("#windows-info").text("联系人数据添加成功");
			$("#windows-info").parent().css("display","block");
			Member.listbyname(account, listMemSuccess, listMemError);
			
			
			setTimeout(function(){
				$("#add-user").dialog("close");
			},1000);
			

			
		}
		
	

	}

	function error() {

	}

}

);

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

		var tr = $("<tr id='item-" + member.id + "'></tr>");
		var td = $("<td></td>");
		var updat = $('<a href="configmember.html?id=' + member.id
				+ '">编辑</a>');
		

		var dele = $('<a id="' + member.id + '" data="' + obj.name
				+ '" href="#">删除</a></td>');

		dele.bind("click", function() {

			var id = $(this).attr("id");
			var name = $(this).attr("data");

			var title = "警告信息-删除[" + name + "]";

			//event.preventDefault();
			var $success_dialog = $('#suredelte-window').dialog({

				autoOpen : true,
				modal : false,
				title : title

			});

			$('#suredelte-window').find('.button').click(
					function(event) {
						//event.preventDefault();
						Member.removeById(id, removeSuccess,
								listMemError);
						$success_dialog.dialog('destroy');
					});


		});

		item = item.replace(/{id}/g, obj.id).replace(/{creater}/g,
				obj.creater).replace(/{email}/g, obj.email)
				.replace(/{name}/g, obj.name).replace(/{tell}/g,
						obj.tell).replace(/{joinTime}/g,
						obj.joinTime);

		tr.append(item);
		td.append(updat);
		td.append("<sapn>|</span>");
		td.append(dele);
		tr.append(td);

		$("#view_memberlist").append(tr);

	}

	$("#view_memberlist").show();

	var option = {
		pagesize : 5,
		currentpage : 1,
		target : "#table-footer"
	};

	$("#view_memberlist").qucikPage(option);

	var ajaxinfo = memberview.ajaxinfo.replace(/{status}/g,
			"alert-succes").replace(/{message}/g, "load success!");

	$("#ajaxinfo").replaceWith(ajaxinfo);
	$("#ajaxinfo").hide().show(500);
	setTimeout(function() {
		$("#ajaxinfo").hide(500);
	}, 1500);

}

function listMemError() {
}

function removeSuccess(data){
	$("#suredelte-window").dialog("close");
	Member.listbyname(account, listMemSuccess, listMemError);
}
