<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    
    import ="com.cloud.elastic.commons.bean.*"%>
<% 
	/* ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	ApplicationDao applicationDao = applicationContext.getBean(ApplicationDao.class);
	List<Application> list = applicationDao.loadAll(); */
	User user = (User)request.getSession().getAttribute("User"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="assect/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="assect/css/bootstrap-responsive.css"/>
<link rel="stylesheet" type="text/css" href="assect/css/base.css"/>
<style type="text/css">

</style>
<title>应用管理控制台</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
	    <div class="head">
	      <div class="logo"><img src="assect/img/logo.png"><a href="#">Cloud</a></div>
	      <div class="login-info" ><%=user.getNickName()%><span class="caret"></span></div>
	    </div>
	    <ul class="dropdown-menu" id="menu3">
          <!-- <li role="presentation" class="divider"></li> -->
          <li role="presentation"><a role="menuitem" tabindex="-1" href="#">退出</a></li>
       </ul>
    </div>
	<div class="row-fluid cloud-fluid">
		
		<div class="span2 side">
			<h3>管理控制台</h3>
			<ul class="nav nav-list">
              
              <li class="active"><a href="#">我的应用</a></li>
              <li><a href="#">日志</a></li>
            </ul>
             
		</div>
		<div class="span10 content">
			<div class="topbar">
				<h2>Overview</h2>
			</div>
			<div class="space"></div>
			
			<div class="row-fluid">
				<div class="span4 hero-unit">
					<div class="hero-head">项目一</div>
				</div>
				<div class="span4 hero-unit">
					<div class="hero-head">项目二</div>
				</div>
				<div class="span4 hero-unit">
					<div class="hero-head">项目三</div>
				</div>
			</div>
			
			<button id="btn_create" type="button" class="btn btn-danger" style="float: right;">创建应用</button>
			<br/>
			<br/>
			<table class="table table-bordered">
  				<thead>
  					<tr>
	                  <th>#</th>
	                  <th>应用名</th>
	                  <th>访问域名</th>
	                  <th>创建时间</th>
	                  <th>实例数（个）</th>
	                  <th>应用状态</th>
	                  <th>操作</th>
	                </tr>
  				</thead>
  				<tbody id="table_applications">
  				<%-- <% for(int i=0;i<list.size();i++){
  					
  					Application app = list.get(i);
  					
  					%>
	  					<tr>
		                  <td>1</td>
		                  <td><b><%=app.getName() %></b></td>
		                  <td><a href="http://hello.app.cloud.com" target="_blank">http://<%=app.getUrl() %></a></td>
		                  <td><b>2009-12-31</b></td>
		                  <td><span class="badge badge-info">8</span></td>
		                  <td><span class="label label-success"><%=app.getHealth() %></span></td>
		                  <td>
		                  	<div class="btn-group">
				                <button class="btn btn-small">详情</button>
				                <button class="btn btn-small dropdown-toggle" data-toggle="dropdown">
				                	<span class="caret"></span>
				                </button>
				                <ul class="dropdown-menu">
				                  <li onclick=""><a href="javascript:deploy('<%=app.getUuid()%>')">部署</a></li>
				                  <li onclick=""><a href="#">启动</a></li>
				                  <li onclick=""><a href="#">停止</a></li>
				                  <li onclick=""><a href="#">卸载</a></li>
				                  <li class="divider"></li>
				                  <li onclick=""><a href="#">扩展</a></li>
				                  <li onclick=""><a href="#">收缩</a></li>
				                </ul>
				            </div>
		                  </td>
		                </tr>
  					<%
  					
  				} %> --%>
  				
  				</tbody>
			</table>
		<!-- 	<div class="pagination">
			  <ul>
			    <li class="disabled"><a href="#" >Prev</a></li>
			    <li class="active"><a href="#" >1</a></li>
			    <li><a href="#">2</a></li>
			    <li><a href="#">3</a></li>
			    <li><a href="#">4</a></li>
			    <li><a href="#">5</a></li>
			    <li><a href="#">Next</a></li>
			  </ul>
			</div> -->
		</div>
		
	</div>
	<script type="text/javascript" src="assect/js/jquery.js"></script>
	<script type="text/javascript" src="assect/js/bootstrap.js"></script>
	<script type="text/javascript" src="assect/js/jquery.index.js"></script>
</body>
</html>