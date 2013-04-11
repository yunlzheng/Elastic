<%@ page language="java" 
	import="org.springframework.context.ApplicationContext,
		org.springframework.web.context.support.WebApplicationContextUtils,
		com.icp.monitor.commons.dao.*,
		com.icp.monitor.commons.bean.*,
		java.util.List;" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- Le styles -->
<link href="assets/stylesheets/bootstrap.css" rel="stylesheet">
<link href="assets/stylesheets/bootstrap-responsive.css" rel="stylesheet">
<link href="assets/stylesheets/doc.css" rel="stylesheet">
<link href="assets/stylesheets/head.css" rel="stylesheet">
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar">

	<% 
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()); 
		String account = (String)request.getSession().getAttribute("user");
		
		String iCenterUrl = this.getServletContext().getInitParameter("icenter");
		String adminHome = this.getServletContext().getInitParameter("adminHome");
		
		if(account==null){
			response.sendRedirect("IdentityServlet");	
			//return;
		}
		if(account!=null&&account.indexOf("@")==-1){
			
			response.sendRedirect(adminHome);
			//return;
			
		}
		OpenAccountDao dao = (OpenAccountDao)context.getBean("OpenAccountDao");
		List<OpenAccount> list = dao.list("from OpenAccount where account='"+account+"'");
		if(list!=null&&list.size()>0){
			response.sendRedirect(this.getServletContext().getInitParameter("tenantHome"));
		}
		
		boolean linked = false;
		String orderid = request.getParameter("orderid");
		String username = request.getParameter("username");
		if(orderid!=null&&username!=null){
			
			linked=true;
			
		}
		
		
	%>
	
	<div class="jumbotron masthead">
		<div class="container">
			<h1>云监控</h1>
			<p>集群;应用;服务器;状态一手掌握</p>
			<p>
				<Button class="btn btn-primary btn-large" id="btnOpen">开通云监控服务</Button>
			</p>
			<ul class="masthead-links">
				<%
					if(account==null||account.equals("")){
						%>
							<li><a href="IdentityServlet">登陆</a></li>
						<% 
					}else{
						
						%>
							<li>欢迎你，<%=account%>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=iCenterUrl%>">返回用户中心</a>&nbsp;&nbsp;<a href="IdentityServlet?type=logout">退登</a></li>
						<% 
						
					}
				%>
				<li><a href="#">相关案例</a></li>
				<li><a href="#">实例</a></li>
				<li>Version 2.2.1</li>
			</ul>
		</div>
	</div>
	<script src="assets/javascripts/jquery.js"></script>
	<script type="text/javascript" src="jslib/jquery.blockUI.js"></script>
	<script type="text/javascript">
	
		$(document).ready(function(){
			
			(function(){
				
				if(!<%=linked%>){
					
					$.blockUI({ 
						message:  '<h1><a href="http://www.wocloud.com" title="跳转到用户中心申请">未申请的服务</a></h1>', 
						css: { 
					            border: 'none', 
					            padding: '15px', 
					            backgroundColor: '#000', 
					            '-webkit-border-radius': '10px', 
					            '-moz-border-radius': '10px', 
					            opacity: 0.8, 
					            color: '#fff', 
					            cursor: 'default' 
					        },
						overlayCSS:  { 
					        backgroundColor: '#fff', 
					        opacity:         0.8, 
					        cursor:          'default' 
					    }
					}); 
					
				}
				
				
				var listener = function(){
					
					$("#btnOpen").attr("disabled","disabled").addClass("disabled");
					
					$.ajax({
						
						type : "POST",
						url : "rest/products",
						data:{
							"orderid":'<%=request.getParameter("orderid") %>',
							"username":'<%=request.getParameter("username") %>'
						},
						success : success,
						error : error,
						timeout : 3000
						
					});
					
				}
				
				function success(data){
					
					
					if(data.account==""||data.account==undefined){
						
						$("#btnOpen").removeAttr("disabled").removeClass("disabled").text("开通失败,点击重试");
						
					}else{
						$("#btnOpen").text("云监控开通成功").removeAttr("disabled");
						setTimeout(function(){
							
							window.location.reload();
							
						},1000);
					}
				}
				
				function error(){
					
					$("#btnOpen").removeAttr("disabled").removeClass("disabled").text("开通失败,点击重试");
				}
				
				$("#btnOpen").bind("click",listener);
				
			})();
			
		});
	
	</script>
</body>
</html>
