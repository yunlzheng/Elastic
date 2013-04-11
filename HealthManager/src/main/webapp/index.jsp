<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>联通云计算综合管理平台</title>
</head>
<body>
	<%
	
		String userAccount = (String)session.getAttribute("user");
		if(userAccount==null){
			
			response.sendRedirect(this.getServletContext().getInitParameter("redirectURL"));
			return;
			
		}else{
			
			if(userAccount.indexOf("@")!=-1){
				
				response.sendRedirect(this.getServletContext().getInitParameter("adminHome"));
				
			}else{
				
				response.sendRedirect(this.getServletContext().getInitParameter("tenantHome"));
				
			}
			
		}
		
	%>
</body>
</html>