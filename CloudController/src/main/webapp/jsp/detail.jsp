<%@page import="javax.persistence.criteria.CriteriaBuilder.Case"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import =
    "com.cloud.elastic.commons.bean.*,
    com.cloud.elastic.commons.bean.Runtime,
    com.cloud.elastic.commons.dao.*,
    org.springframework.web.context.support.WebApplicationContextUtils,
    org.springframework.context.ApplicationContext,
    java.util.*"
 %>
<% 

	User user = (User)request.getSession().getAttribute("User"); 
	ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	
	String uuid = request.getParameter("uuid");
	
	if(uuid==null){
		
		response.sendRedirect("home.jsp");
		return;
	}
	
	ApplicationDao applicationDao = applicationContext.getBean(ApplicationDao.class);
	
	Application app = applicationDao.get(uuid);
	
	RuntimeDao runtimeDao = applicationContext.getBean(RuntimeDao.class);
	RUnitDao rUnitDao = applicationContext.getBean(RUnitDao.class);
	
	Runtime templateRuntime = new Runtime();
	templateRuntime.setApplication_uuid(app.getUuid());
	List<Runtime> list = runtimeDao.findEqualByEntity(templateRuntime, new String[]{"application_uuid"});
	
	
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
<title>应用详情</title>
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
				<h2>应用<%=app.getName() %>详情</h2>
			</div>
			
			<div class="space"></div>
			<div class="row-fluid">
				<div class="hero-unit auto">
					<div class="span6"><span>域名:</span><span>http://app.app.cloue.dev</span></div>
					<div class="span6"><span>运行状态：</span><span class="label label-success">运行中</span></div>
				</div>
			</div>
			
			<%
				if(list!=null){
					
					for(int i=0;i<list.size();i++){
						
						Runtime runtime = list.get(i);
						
						RUnit templateUnit = new RUnit();
						templateUnit.setRuntime(runtime);
						String[] properteNames = {"runtime"};
						
						List<RUnit> rUnits = rUnitDao.findEqualByEntity(templateUnit, properteNames);
						
						%>
						
							<h3>运行的实例：</h3>
							<table class="table table-bordered">
							
				  				<thead>
				  					<tr>
					                  <th>#</th>
					                  <th>HTTP端口</th>
					                  <th>AJP端口</th>
					                  <th>shutdown端口</th>
					                  <th>类型</th>
					                  <th>创建时间</th>
					                  <th>状态</th>
					                 
					                </tr>
				  				</thead>
				  				<tbody id="table_applications">
				  				
				  				<% 
				  					
				  					if(rUnits!=null){
				  						
				  						for(int j=0;j<rUnits.size();j++){
				  							
				  							RUnit runit = rUnits.get(j);
				  							
				  							int status = runit.getHealthStatus();
				  							
				  							String desc = null;
				  							
				  							switch(status){
				  								case 0:
				  									desc = "<span class='label label-success'>运行中</span>";
				  									break;
				  								case 1:
				  									desc = "<span class='label label-warning'>暂停</span>";
				  									break;
				  								default:
				  									desc="<span class='label label-important'>未知状态</span>";
				  							}
				  							
				  							
				  							%>
				  								
				  								<tr>
						  							<td><%=(j+1)%></td>
						  							<td><%=runit.getPortHttp() %></td>
						  							<td><%=runit.getPortAjp() %></td>
						  							<td><%=runit.getPortShutDown() %></td>
						  							<td>Tomcat</td>
						  							<td><%=runit.getCreateDate() %></td>
						  							<td><%=desc %></td>
						  							
						  						</tr>
						  						
				  							<% 
				  							
				  						}
				  						
				  					}else{
				  						
				  						%>
				  						<tr>
				  							<td colspan="6">未运行实例</td>
				  							
				  						</tr>
				  						<% 
				  						
				  					}
				  				
				  				%>
				  					
				  				</tbody>
							</table>
							
							
							
							
						<%
						
					}
					
				}
			%>
			
		
	
		</div>
		
	</div>
	<script type="text/javascript" src="../assect/js/jquery.js"></script>
	<script type="text/javascript" src="../assect/js/application.js"></script>
	<script type="text/javascript" src="../assect/js/bootstrap.js"></script>
	<script type="text/javascript" src="../assect/js/jquery.detail.js"></script>
</body>
</html>