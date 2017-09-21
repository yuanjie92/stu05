<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>register</title>
</head>
<body>
	<h1>user register</h1>
	<hr>
	<font color="red" size="3"> ${errorMsg }</font>
	<form action="user" method="post">
		<input type="hidden" name="act" value="register">
		userName:<input type="text" name="userName"><br>
		userPwd:<input type="password" name="userPwd"><br>
		<input type="submit" value="register">
	</form>
</body>
</html>