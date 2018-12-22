<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
<style>

</style>
</head>
<body>
<div id="title">
  ABS原料供应链管理系统
  </div>
	<form action="UserServlet?flag=login" method="post">
		<input type="text" placeholder="用户名" name="username" value=""/><br /> 
		<input type="password" placeholder="密码"  name="password"  value=""/><br /> 
		
			<div id="message">${message }</div>
			<input type="submit" value="登    录" class="btn" />
	</form>
</body>
</html>