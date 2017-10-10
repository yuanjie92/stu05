<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>student list</title>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/query.js"></script>
</head>
<body>
	<h1>student list</h1>
	<hr>
	<a href="add">add student</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="user?act=logout">logout</a>
	<hr>
	<div><b>query</b></div>
	name:<input type="text" name="queryName" value="${name }">&nbsp;&nbsp;
	gender:<input type="radio" name="gender" value="1" ${gender==1?'checked':'' }>男
		<input type="radio" name="gender" value="0" ${gender==0?'checked':'' }>女
		<input type="radio" name="gender" value="2" ${gender==2?'checked':'' }>全部&nbsp;
		<input type="hidden" name="act" value="query">
		<a id="query">查询</a>
	<table border="1">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>生日</th>
				<th>班级</th>
				<th colspan="2">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${searchResults.results }" var="stu" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>
				<td>${stu.name }</td>
				<td>
					<c:if test="${stu.gender==1 }">男</c:if>
					<c:if test="${stu.gender==0 }">女</c:if>
				</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${stu.birthday }"/></td>
				<td>${stu.grade }</td>
				<td><a href="update?act=showStu&id=${stu.id }">修改</a></td>
				<td><a href="del?act=del&id=${stu.id }">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>	
	</table>
	<c:set var="page" value="${searchResults.page }"/>
	<a href="student?act=query&name=${name }&gender=${gender}&currentPage=1">首页</a>
	<c:if test="${page.currentPage>1 }">
		<a href="student?act=query&name=${name }&gender=${gender}&currentPage=${page.currentPage-1}">上一页</a>
	</c:if>
	${page.currentPage }
	<c:if test="${page.currentPage<page.totalPage }">
		<a href="student?act=query&name=${name }&gender=${gender}&currentPage=${page.currentPage+1}">下一页</a>
	</c:if>
	<a href="student?act=query&name=${name }&gender=${gender}&currentPage=${page.totalPage}">尾页</a>
	<br>
	共${page.totalPage }页，${page.totalCount }个学生
</body>
</html>