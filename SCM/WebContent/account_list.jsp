<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/css/main.css"  type="text/css" rel="styleSheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/table.js" charset="gb2312"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formcheck.js" charset="gb2312"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/account_check.js" charset="gb2312"></script>
 <style type="text/css">
 /* 增加框 复写*/
 section  .alertDiv-add{
 height:380px;
 width:400px;
}
 section  .alertDiv-add #searchbox{
 width:250px;
}

section  .findbox input[type=date]{
	/* float:right; */
	border-radius:5px;
	width:200px;
	height:30px;
	border:1px solid gray;
	list-style:none;
	 /* margin-left:150px;  */
	outline:medium;
	padding-left:10px;
	color:#C7C7C7;
	}
	section  .findbox input[type=submit]{
	width:60px;
	height:30px;
	border-radius:5px;
	border:none;
	background:#7EC0EE;
	color:#EAEAEA;
	font-size: 16px;
	margin-left:10px;
	}
 </style>
 
</head>
<body>
 
<jsp:include page="head.jsp"></jsp:include>
<section>
<div class="dirbox">
    <span>库存管理&nbsp;&nbsp;>&nbsp;&nbsp;保管账管理</span>
</div>
<div class="tablebox">
<form action="InBoundServlet?flag=findTime" method="post" class="findbox">
<input type="date" name="startDate" id="startDate" />
 -
<input type="date" name="endDate" id="endDate" />
   
       <input type="submit" value="查询" />&nbsp;&nbsp;<span id="mess"></span>
      
 </form>
    <table>
   <tr>
    
      <td width="50" >编号</td>
      <td width="200">商品名称</td>
      <td width="200">操作类型</td>
      <td width="200">操作时间</td>
      <td width="200">操作数量</td>
      
   </tr>
   <c:forEach items="${acclist }" var="al" begin="0" end="9">
         <tr>
     
      <td>${al.id }</td>
      <td>${al.goods.name }</td>
      <td>${al.type}</td>
      <td>${al.time}</td>
      <td>${al.num}</td>
    
   </c:forEach>
   
   
   </table> 

   </div>
   <div class="inpage">${pager }</div>
   <footer>CopyRight &copy; 2018 创睿信教育集团 <br> <br>
   Powered by Teacher Lei版权所有
   </footer>

</section>
</body>
</html>