<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    
    import ="com.cloud.elastic.commons.bean.*"%>
<% 

	User user = (User)request.getSession().getAttribute("User"); 
	String uuid = request.getParameter("id");
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
	            <li class="active"><a href="services.jsp">服务性能监控</a></li>
	            <li ><a href="server.jsp">服务器性能监控</a></li>
            </ul>
             
		</div>
		<div class="main_content">
			<div class="content">
				<div class="topbar">
					<h2><a href="services.jsp">服务性能监控</a>>>Tomcat详情</h2>
					<button id="btn_create" type="button" class="btn btn-danger" style="float: right;"><a href="create.jsp">创建监控项目</a></button>
					
				</div>
				
				<ul class="nav nav-tabs">
					  <li class="tab" tab-data="memory">
					    <a href="#">JVM内存</a>
					  </li>
					  <li class="tab" tab-data="flow">
					    <a href="#">Tomcat网络访问量</a>
					  </li>
					  <li class="tab" tab-data="thread">
					    <a href="#">Tomcat线程</a>
					  </li>
					  <li class="tab" tab-data="processTime">
					    <a href="#">Tomcat处理时间</a>
					  </li>
					  <li class="tab" tab-data="request">
					    <a href="#">Tomcat请求数</a>
					  </li>
				</ul>
				<div id="tabs">
					<div class="row-fluid" tab-data="memory">
						<div class="hero-unit monitor">
							<div class="hero-head">JVM内存变化率</div>
							<div id="jvmMemoryContainer" class="container"></div>
						</div>
					</div>
					<div class="row-fluid" tab-data="processTime">
						<div class="hero-unit monitor">
							<div class="hero-head">Tomcat处理时间变化率</div>
							<div id="tomcatProcessingContainer" class="container"></div>
						</div>
					</div>
					<div class="row-fluid" tab-data="flow">
						<div class="hero-unit monitor" >
							<div class="hero-head">Tomcat网络访问量变化率</div>
							<div id="tomcatBytesContainer" class="container"></div>
						</div>
					</div>
					<div class="row-fluid" tab-data="thread">
						<div class="hero-unit monitor" >
							<div class="hero-head">Tomcat线程变化率</div>
							<div id="tomcatThreadContainer" class="container"></div>
						</div>
					</div>
					<div class="row-fluid" tab-data="request">
						<div class="hero-unit monitor" >
							<div class="hero-head">Tomcat请求数</div>
							<div id="tomcatRequestContainer" class="container"></div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<table class="table table-bordered">
			  				<thead>
			  					<tr>
				                  <th>监控项目</th>
				                  <th>开始时间</th>
				                  <th>恢复时间</th>
				                  <th>故障原因</th>
				                </tr>
			  				</thead>
			  				
					</table>
				</div>
			</div>
		</div>
		
	</div>
	<script type="text/javascript" src="../assect/js/jquery.js"></script>
	<script type="text/javascript" src="../assect/js/jquery.plugin.tabs.js"></script>
	<script type="text/javascript" src="../assect/js/highcharts.js"></script>
	<script type="text/javascript" src="../assect/js/jquery.tomcat.charts.js"></script>
	<script type="text/javascript" src="../assect/js/plugin.tomcat.data.js"></script>
	<script type="text/javascript" src="../assect/js/bootstrap.js"></script>
	<script type="text/javascript">
	<%
		
		if(uuid!=null&&!uuid.equals("")){
			
			%>
			
			var charts = {
					
					memoryCharts:'jvmMemoryContainer',
					flow:'tomcatBytesContainer',
					thread:'tomcatThreadContainer'
					
			};
			
			$(".nav-tabs").tabs("#tabs");
			
			$(function(){
				
				loadMemoryData('<%=uuid%>',callbackMemoryChart);
				loadThreadData('<%=uuid%>',callbackThreadChart);
				loadFlowData('<%=uuid%>',callbackFlowChart);
				
			});
			
			<%
			
		}
	
	%>
	
		
	
	</script>
</body>
</html>