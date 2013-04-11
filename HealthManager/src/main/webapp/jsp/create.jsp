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
				<h2>创建监控项</h2>
			</div>
			<div class="space"></div>
			<div class="create_content">
				<form class="form">
					<fieldset>
						<legend>站点监控</legend>
						
						<a href="#">HTTP</a>
						<p>监控Web站点中任何指定的URL，获得可用率报告以及响应时间详细分析。</p>
						<a href="#">Ping</a>
						<p>对指定的服务器进行ICMP Ping检测，获得可用率报告以及响应时间、丢包率等。</p></fieldset>
					
					<fieldset>
						<legend>服务器性能监控</legend>
						<a href="#">创建服务器监控</a>
						<p>通过SNMP监控服务器的系统性能，包括CPU使用率、内存使用率、磁盘空间使用率、网卡流量、磁盘I/O、进程数等。</p>
					</fieldset>
					<fieldset>
						<legend>服务性能监控</legend>
						<a href="tomcat.jsp">Tomcat</a>
						<p>监控Tomcat运行时的各项性能数据</p>
					</fieldset>
				</form>
			</div>
		</div>
	
	<script type="text/javascript" src="../assect/js/jquery.js"></script>
	<script type="text/javascript" src="../assect/js/bootstrap.js"></script>
</body>
</html>