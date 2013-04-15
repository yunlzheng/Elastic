<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    
    import ="
    com.cloud.elastic.commons.bean.*,
    com.cloud.elastic.commons.monitor.bean.*,
    com.cloud.elastic.commons.dao.*,
    org.springframework.web.context.support.WebApplicationContextUtils,
    org.springframework.context.ApplicationContext,
    org.hibernate.Criteria,
    org.hibernate.criterion.Order,
    java.util.*"%>
    
<% 

	User user = (User)request.getSession().getAttribute("User"); 
	ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	TomcatDao tomcatDao = applicationContext.getBean(TomcatDao.class);
	Criteria criteria = tomcatDao.createCriteria();
	List<Tomcat> tomcats = criteria.addOrder(Order.desc("createDate")).list();

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
<title>服务性能监控</title>
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
					<h2>服务性能监控</h2>
					<button id="btn_create" type="button" class="btn btn-danger" style="float: right;"><a href="create.jsp">创建监控项目</a></button>
					
				</div>
				
				<ul class="nav nav-tabs">
					  <li class="active">
					    <a href="#">TOMCAT监控</a>
					  </li>
					 
				</ul>
				<table class="table table-bordered">
	  				<thead>
	  					<tr>
		                  <th>#</th>
		                  <th>监控项目名</th>
		                  <th>摘要</th>
		                  <th>关键字</th>
		                  <th>监控类型</th>
		                  <th>创建时间</th>
		                  <th>操作</th>
		                </tr>
	  				</thead>
	  				<tbody id="table_applications">
		  				<% 
		  				if(tomcats!=null){
		  					
		  					
		  				
		  					for(int i=0;i<tomcats.size();i++){
		  						Tomcat tomcat = tomcats.get(i);
		  						
		  						%>
		  						<tr>
				                  <td><%=(i+1) %></td>
				                  <td><%=tomcat.getName() %></td>
				                  <td><%=tomcat.getStatusPageUrl() %></td>
				                  <td><%=tomcat.getServicesName()%></td>
				                  <td>Tomcat</td>
				                  <td><%=tomcat.getCreateDate() %></td>
				                  <td>
				                  	
				                  	<div class="btn-group">  
				                  		<button class="btn btn-small"><a href="tomcatDetail.jsp?id=<%=tomcat.getUuid() %>">详情</a></button> 
				                  		<button class="btn btn-small dropdown-toggle" data-toggle="dropdown">  <span class="caret"></span> </button>
				                  		 <ul class="dropdown-menu">
				                  		 	<li><a href="#">开启/关闭预警</a></li>
				                  		 	
				                  		 	<li class="divider"></li>
				                  		 	<li><a href="javascript:deleteTomcat('<%=tomcat.getUuid() %>')">删除</a></li>
				                  		 	<li><a href="#">修改</a></li>
				                  		 </ul>
				                  	</div>
				                  </td>
				                </tr>
		  							
		  						<%
		  						
		  					}
		  				}else{
		  					
		  					%>
		  						
		  						<tr>
				                  <td colspan="6">您尚未创建监控项目，请 创建监控项目</td>
				                 
				                </tr>
		  						
		  					<%
		  				}
		  				%>
	  				
	  					
	  				
	  				</tbody>
				</table>
		
			</div>
		</div>
		
	</div>
	<script type="text/javascript" src="../assect/js/jquery.js"></script>
	<script type="text/javascript" src="../assect/js/application.js"></script>
	
	<script type="text/javascript" src="../assect/js/bootstrap.js"></script>
	<script type="text/javascript">
	
		function deleteTomcat(uuid){
			
			$.ajax({
				
				url:"../service/rest/tomcat/"+uuid,
				type:'DELETE',
				success:function(data){
					
					console.log(data);
					
				},
				error:function(){
					
					
				}
				
			});
		}
	
	</script>
	
</body>
</html>