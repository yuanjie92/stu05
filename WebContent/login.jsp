<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login111</title>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/verifyCode.js"></script>

</head>
<body>
	<h1>login</h1>
	<hr>
	<font color="red" size="3"> ${errorMsg }</font>
	<form action="user" method="post">
		<input type="hidden" name="act" value="login">
		userName:<input type="text" name="userName"><br>
		userPwd:<input type="password" name="userPwd"><br>
		verifyCode:<input type="text" name="verifyCode"><img class="verifyCodeImg" src="verifyCode"><br>
		remember:<input type="checkbox" name="remember"><br>
		<input type="submit" value="login">
	</form>
	<br>
	<a href="user?act=register">register user</a>
	
</body>
</html>