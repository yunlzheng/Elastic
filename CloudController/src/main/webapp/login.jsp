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
	<div class="container">

	     <form action="service/foo/users/login" method="POST" class="form-signin">
	        <h2 class="form-signin-heading">用户登录</h2>
	        <input name="email"  type="text" class="input-block-level" placeholder="电子邮箱地址">
	        <input name="password"  type="password" class="input-block-level" placeholder="密码">
	        <label class="checkbox">
	          <input type="checkbox" value="remember-me"> 记住我
	        </label>
	        <button class="btn btn-large btn-primary" type="submit">登录</button>
	     </form>
    
    </div>
	<script type="text/javascript" src="assect/js/jquery.js"></script>
	<script type="text/javascript" src="assect/js/bootstrap.js"></script>
</body>
</html>