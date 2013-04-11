<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="assect/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="assect/css/bootstrap-responsive.css"/>
<link rel="stylesheet" type="text/css" href="assect/css/login.css"/>
<style type="text/css">

</style>
<title>用户登录</title>
</head>
<body>
	<div class="loadimg">
	</div>
	<div class="container ">
	
		<div class="login">
			 <div class="modal-header">
			    <h3>欢迎登录用户中心</h3>
			  </div>
		     <form action="service/foo/users/login" method="POST" class="form-signin">
		       <div class="modal-body clearfix">
		       	 <label for="">邮箱地址</label>
		       	 <input name="email"  type="text" class="input-block-level" >
		       	 <label for="">登录密码</label>
		       	 <input name="password"  type="password" class="input-block-level">
		       </div>
		       <div class="modal-footer">
				  <button type="submit" class="btn btn-primary pull-right">登录</button>
			   </div>
		     </form>
	    </div>
	
    </div>
	<script type="text/javascript" src="assect/js/jquery.js"></script>
	<script type="text/javascript" src="assect/js/bootstrap.js"></script>
</body>
</html>