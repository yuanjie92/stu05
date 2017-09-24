<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add student</title>
</head>
<body>
	<h1>add student</h1>
	<hr>
	<form action="student" method="post">
		<input type="hidden" name="act" value="add">
		name:<input type="text" name="name"><br>
		grade:<input type="text" name="grade"><br>
		gender:<input type="radio" name="gender" value="1">男
			<input type="radio" name="gender" value="0">女<br>
		birthday:<input type="datetime" name="birthday"><br>
		<input type="submit" value="add">
	</form>
</body>
</html>