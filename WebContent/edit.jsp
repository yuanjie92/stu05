<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>edit student</title>
</head>
<body>
	<h1>edit student</h1>
	<hr>
	<c:set var="gender" value="true"></c:set>
	<c:if test="${stu.gender==0 }">
		<c:set var="gender" value="false"></c:set>
	</c:if>
	<form action="student" method="post">
		<input type="hidden" name="act" value="update">	
		<input type="hidden" name="id" value="${stu.id }">
		name:<input type="text" name="name" value="${stu.name }"><br>
		grade:<input type="text" name="grade" value="${stu.grade }"><br>
		gender:<input type="radio" name="gender" value="1" ${gender?'checked':'' }>男
			<input type="radio" name="gender" value="0" ${!gender?'checked':'' }>女<br>
		birthday:<input type="datetime" name="birthday" value="<fmt:formatDate value='${stu.birthday }' pattern='yyyy-MM-dd HH:mm:ss' />">
		<input type="submit" vlaue="update">
	</form>
</body>
</html>