
var smsConfig = function(sid, username, userpass, smtphost, mail, mailpass,encoding) {

	this.sid = sid;
	this.username = username;
	this.userpass = userpass;
	this.smtphost = smtphost;
	this.mail = mail;
	this.mailpass = mailpass;
	this.encoding = encoding;

	this.save = function(success, error) {
		$.ajax({
			type : "post",
			url : "../rest/smsConfig",
			data : {
				"sid" : this.sid,
				"username" : this.username,
				"userpass" : this.userpass,
				"smtphost" : this.smtphost,
				"mail" : this.mail,
				"mailpass" : this.mailpass,
				"encoding":this.encoding
			},
			success : success,
			error : error,
			timeOut : 3000

		});
	}
};


var smsNetConstruction = function(){}

smsNetConstruction.getlist = function(success, error){
	
	$.ajax({
		
		type : "get",
		url : "../rest/sms",
		success : success,
		error : error,
		timeOut : 3000

	});
	
}
	
	
/**
 * @param object  被操作对象
 * @param operation 需要进行的操作
 */
function dialogOper(object, operation) {
	
	var $dialog = $('#' + object).dialog({

		autoOpen : true,
		modal : true,
		title : "数据操作"

	});

	if (operation) {
		$dialog.dialog('open');
	} else {
		$dialog.dialog('close');
	}

	return $dialog;
}


