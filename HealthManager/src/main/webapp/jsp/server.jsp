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

<style type="text/css">

</style>
<title>应用管理控制台</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
	    <div class="head">
	    	<div class="container-fluid">
	    		<a href="home.jsp" class="title">云监控子系统</a>
	      		<div class="login-info pull-right" >
	      			<%=user.getNickName()%>
	      			<a href="../service/foo/users/logout" class="navbar-link">退出</a>
	      		</div>
	    	</div>
	    </div>
	   
    </div>
	<div class="row-fluid cloud-fluid">
		
		<div class="side">
			<h1 class="brand clearfix"><a href="#">OpenStack Dashboard</a></h1>
			<h3>控制面板</h3>
			<ul class="nav nav-list">
              <li ><a href="home.jsp">概况</a></li>
              <li><a href="site.jsp">站点可用性监控</a></li>
              <li ><a  href="services.jsp" >服务性能监控</a></li>
              <li class="active" ><a href="server.jsp">服务器性能监控</a></li>
            </ul>
             
		</div>
		<div class="main_content">
			<div class="content">
				<div class="topbar">
					<h2>服务器性能监控</h2>
					<button id="btn_create" type="button" class="btn btn-danger" style="float: right;">创建监控项目</button>
					
				</div>
				
				<ul class="nav nav-tabs">
					  <li class="active">
					    <a href="#">SNMP监控</a>
					  </li>
					 
				</ul>
				<table class="table table-bordered">
	  				<thead>
	  					<tr>
		                  <th>#</th>
		                  <th>应用名</th>
		                  <th>访问域名</th>
		                  <th>创建时间</th>
		                  <th>应用状态</th>
		                  <th>操作</th>
		                </tr>
	  				</thead>
	  				<tbody id="table_applications">
	  				
	  				
	  				</tbody>
				</table>
		
			</div>
		</div>
		
	</div>
	<script type="text/javascript" src="../assect/js/jquery.js"></script>
	<script type="text/javascript" src="../assect/js/application.js"></script>
	<script type="text/javascript" src="../assect/js/bootstrap.js"></script>
	<script type="text/javascript" src="../assect/js/jquery.index.js"></script>
</body>
</html>