<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    
    import ="com.cloud.elastic.commons.bean.*"%>
<% 

	User user = (User)request.getSession().getAttribute("User"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../assect/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="../assect/css/application_status.css"/>
<link rel="stylesheet" type="text/css" href="../assect/css/bootstrap-responsive.css"/>
<link rel="stylesheet" type="text/css" href="../assect/css/base.css"/>
<link rel="stylesheet" type="text/css" href="../assect/css/create.css"/>
<style type="text/css">

</style>
<title>应用管理控制台</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
	    <div class="head">
	    	<div class="container-fluid">
	    		<a  href="home.jsp" class="title">云监控子系统</a>
	      		<div class="login-info pull-right" >
	      			<%=user.getNickName()%>
	      			<a href="../service/foo/users/logout" class="navbar-link">退出</a>
	      		</div>
	    	</div>
	    </div>
	   
    </div>
	<div class="row-fluid cloud-fluid">
		
			<div class="topbar">
				<h2>创建监控项>>创建新的监控项</h2>
			</div>
			<div class="space"></div>
			<div class="create_content">
				<form class="form">
					
					<fieldset>
						<legend>填写监控信息</legend>
						<div class="file_item">
							<label>监控项目名称:</label>
							<input type="text"/>
							<span class="mes">给监控项目起一个名字，比如：Apache@Web117 或 MySQL@DB45。</span>
						</div>
						<div class="file_item">
							<label>Tomcat状态页：</label>
							<input type="text"/>
							<span class="mes">请填写Tomcat状态页地址，如：http://IP:端口/manager/status</span>
						</div>
						
						<div class="file_item">
							<label>Tomcat用户名:</label>
							<input type="text"/>
							<span class="mes">请填写Tomcat用户名，建议您为监控宝创建专用Tomcat登录帐号</span>
						</div>
						<div class="file_item">
							<label>Tomcat密码:</label>
							<input type="text"/>
							<span class="mes">请填写以上Tomcat用户的密码。</span>
						</div>
						<div class="file_item">
							<label>所要监控服务名:</label>
							<input type="text"/>
							<span class="mes">请填写Tomcat所要监控服务名。如：http-bio-8080</span>
						</div>
					</fieldset>
					
					<fieldset>
						<legend>填写监控信息</legend>
						<div class="file_item">
							<label>监控频率:</label>
							<input type="radio"/><span>5分钟</span>
						</div>
					</fieldset>
					
				</form>
				<button style="margin-left:400px" type="button" class="btn btn-success">创建监控项</button>
				
			</div>
		</div>

	<script type="text/javascript" src="../assect/js/jquery.js"></script>
	<script type="text/javascript" src="../assect/js/bootstrap.js"></script>
</body>
</html>