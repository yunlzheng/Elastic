$(function() {

	$.ajaxSetup({cache:false});
	
	smsNetConstruction.getlist(loadSuccess, error);

	var $submit = $("#config_button").find("a:first");

	$submit.bind("click", function() {

		if (validate())
			addUser();

	});

	var $reset = $("#config_button").find("a:eq(1)");

	$reset.bind("click", function() {
		reset();
	});

});

function addUser() {

	var username = $("#username").val();
	var userpass = $("#password").val();
	var smpthost = $("#smpthost").val();
	var email = $("#email").val();
	var emailpass = $("#emailpass").val();
	var encoding = $("input:[checked='checked']").attr("value");
	var id = $("select:first").find("option:selected").attr("id");

	var smsconfig = new smsConfig(id, username, userpass, smpthost, email,
			emailpass, encoding);
	smsconfig.save(success, error);
}

/**
 * 提交之前进行验证
 */
function validate() {

	var flag = true;

	var id = $("select:first").find("option:selected").attr("id");
	var $email = $("#email");
	var $username = $("#username");
	var $password = $("#password");
	var $smpthost = $("#smpthost")
	var $emailpass = $("#emailpass");

	var mail = $email.val();

	var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/; // 匹配邮箱

	if (!id) {
		flag = false;
		//alert("请选择短信运行商");
	}else{
		
		
		if ($username.val() == "") {

			$username.val("用户名不能为空");
			$username.css("color", "gray");
			$username.focus(function() {
				$username.val("");
				$username.css("color", "black");
			});

			flag = false;
		}
		
		if ($password.val() == "") {

			$("#passwordInstea").val("用户密码不能为空");
			$password.css("display","none");
			$("#passwordInstea").css("display","");
			$("#passwordInstea").css("color", "gray");
			$("#passwordInstea").focus(function() {
				
				$(this).css("display","none");
				$password.css("display","");
				
			});
			
			$("#passwordInstea").focus(function(){
				$(this).css("display","none");
				$password.css("display","");
			});

			flag = false;
		}
		
		
		if ($smpthost.val() == "") {

			$smpthost.val("smtp不能为空");
			$smpthost.css("color", "gray");
			$smpthost.focus(function() {
				$smpthost.val("");
				$smpthost.css("color", "black");
			});

			flag = false;
		}
		
		
		if ($emailpass.val() == "") {

			$("#emailpassInstea").css("display","");
			$emailpass.css("display","none");
			$("#emailpassInstea").val("邮件密码不能为空");
			$("#emailpassInstea").css("color", "gray");
			$("#emailpassInstea").focus(function() {
				$(this).css("display","none");
				$emailpass.css("display","");
				$emailpass.css("color", "black");
			});

			flag = false;
		}
		

		if (!(reg.test(mail))) {

			flag = false;
			$email.val("请输入正确的邮箱地址");
			$email.css("color", "gray");
			$email.focus(function() {
				$email.val("");
				$email.css("color", "black");
			});

			$email.blur(function() {
				if (!reg.test(email)) {
					$email.focus(function() {
						// $email.val("请输入正确的邮箱地址");
					});
				}
			});

		}
	}



	return flag;
}



/**
 * 添加下拉内容
 */
function addSelect(data) {

	for ( var i = 0; i < data.length; i++) {
		var $option = "<option id='" + data[i]["id"] + "'> " + data[i]["name"]
				+ " </option>";
		$("select:first").append($option);
	}

}

/**
 * 请求短信运营商成功
 */
function loadSuccess(data) {
	addSelect(data);
}

function success(data) {

	if (data) {
		dialogOper("addsuccess", true);
		reset();
	}

}

function error() {
	//console.log("error");
}

/**
 * 初始化操作
 */
function reset() {

	$("#username").val("");
	$("#password").val("");
	$("#smpthost").val("");
	$("#email").val("");
	$("#emailpass").val("");

}
