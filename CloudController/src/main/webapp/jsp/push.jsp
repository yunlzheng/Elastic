<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import ="com.cloud.elastic.commons.bean.*"%>
<% 
	
	User user = (User)request.getSession().getAttribute("User"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../assect/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="../assect/css/bootstrap-responsive.css"/>
<link rel="stylesheet" type="text/css" href="../assect/css/base.css"/>
<link rel="stylesheet" href="../assect/js/skins/twitter.css?4.1.7">
<style type="text/css">

</style>
<title>创建应用程序-应用管理控制台</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
	    <div class="head">
	    	<div class="container-fluid">
	    		<img src="../assect/img/logo.png"/>
	      		<div class="login-info pull-right" >
	      			<%=user.getNickName()%>
	      			<a href="../service/foo/users/logout" class="navbar-link">退出</a>
	      		</div>
	    	</div>
	    </div>
	   
    </div>
	<div class="row-fluid cloud-fluid">
		
		<div class="span2 side">
			<h3>管理控制台</h3>
			<ul class="nav nav-list">
              
              <li class="active"><a href="home.jsp">我的应用</a></li>
              <li><a href="#">日志</a></li>
            </ul>
             
		</div>
		<div class="span10 content">
			<div class="topbar">
				<h2>创建应用</h2>
			</div>
			<div class="space"></div>
			 <form action="../proxy" id="formUploadApplication" method="post" enctype="multipart/form-data" class="form-horizontal push-form">
			 	<fieldset>
			 		<legend>应用程序</legend>
				 	<div class="control-group">
					    <label class="control-label" >应用包:</label>
					    <div class="controls">
					      <input name="application" type="file" placeholder="上传应用">
					     <button id="btnUpload" type="submit" class="btn btn-success">上传</button>
					    </div>
					</div>
			 	</fieldset>
				
			</form>
			<form id="formCreateApplication" action="../service/rest/applications/form" method="post" class="form-horizontal push-form">
				<fieldset>
					<legend>应用基本信息</legend>
						<div class="control-group">
				    	<label class="control-label">域名:</label>
				    	<div class="controls">
					     	<div class="input-prepend input-append">
					     	  <span class="add-on">http://</span>
							  <input name="url" class="span3" type="text" placeholder="访问域名"/>
							  <span class="add-on">.app.cloud.dev</span>
							</div>
							<span>定制个性化域名</span>
					    </div>
					    
					 </div>
				
					<div class="control-group">
					    <label class="control-label" >应用名:</label>
					    <div class="controls">
					       <input name="name" type="text"  placeholder="应用名称">     
					       <span class="field-info">应用的别名，便于应用管理</span>
					    </div>
					   
					 </div>
		
				</fieldset>
				<fieldset>
					
					<legend>运行环境信息</legend>
					<div class="control-group">
					    <label class="control-label" for="inputApplicationName">内存最大值:</label>
					    <div class="controls">
					      <select name="maxMemory">
							  <option value="256">256M</option>
							  <option value="512">512M</option>
							  <option value="1024">1024M</option>
						  </select>
						   <span class="field-info">运行单元所能使用的最大内存值</span>
					    </div>
					 </div>
					 
					 <div class="control-group">
					    <label class="control-label" for="inputApplicationName">内存最小值:</label>
					    <div class="controls">
					      <select name="minMemory">
							  <option value="256">256M</option>
						  </select>
						  <span class="field-info">运行单元所能使用的最小内存值</span>
					    </div>
					 </div>
					 
				</fieldset>
				<div style="text-align: right;padding-right:350px;" >
					<button type="submit" class="btn btn-large btn-danger">创建</button>
				</div>
			</form>
		</div>
		
	</div>
	<script type="text/javascript" src="../assect/js/jquery.js"></script>
	<script type="text/javascript" src="../assect/js/jquery.form.js"></script>
	<script type="text/javascript" src="../assect/js/jquery.from.push.js"></script>
	<script type="text/javascript" src="../assect/js/bootstrap.js"></script>
	<script type="text/javascript" src="../assect/js/jquery.artDialog.js"></script>
	<script type="text/javascript">
		
		$(function(){
			
			$('.login-info').click(function(){
			
				$("#menu3").toggle();
				
			});
		});
	
	</script>
</body>
</html>