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
<script type="text/javascript" charset="gb2312" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="gb2312" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
<script type="text/javascript" charset="gb2312" src="${pageContext.request.contextPath}/kindeditor/plugins/code/prettify.js"></script>
 <style type="text/css">
 .btn-add{
    width:70px;
	height:35px;
	border:none;
	border-radius:5px;
	color:white;
	margin-left:20px;
	float:left;
	text-align:center;
    padding-top: 8px;
    padding-bottom:10px;
    cursor: pointer;
    background:green;
    margin-top:10px;
 }
  .btn-export{
    width:70px;
	height:25px;
	border:none;
	border-radius:5px;
	color:white;
	margin-left:20px;
	float:left;
	text-align:center;
    padding-top: 8px;
    cursor: pointer;
    background-color:#5CACEE;
     margin-top:10px;
 }
.agreement{
width:92%;
height:90%;
overflow-y:auto; 
padding-left: 3%;
padding-right: 3%;

margin-top:20px;
float:left;
}

 </style>
 <script type="text/javascript">
 
   $(function(){
  KindEditor.ready(function(K) {
     window.editor = K.create('#editor_id');
   });
	

   });
 </script>
</head>
<body>
 
<jsp:include page="head.jsp"></jsp:include>
<section>

<div class="dirbox">
    <span>基础数据管理&nbsp;&nbsp;>&nbsp;&nbsp;合同条款维护</span>
</div>

<form action="AgreeServlet?flag=update" method="post">
    <div >
    <!--    <div class="btn-add" id="edit_agree">📝&nbsp;编辑</div> -->
       <input type="submit" value="📝&nbsp;编辑" class="btn-add"/>
      <!--  <div class="btn-export">🖨&nbsp;导出</div> -->
       </div>
    <div class="agreement">
   <textarea id="editor_id" name="content" style="width:99%;height:450px;display:none;">
    ${agree.cont}
    </textarea>
  </div>
 </form>
  


  
   <footer>CopyRight &copy; 2018 创睿信教育集团 <br> <br>
   Powered by Teacher Lei版权所有
   </footer>
  <%--  <div class="alertDiv" >
         <form action="AgreeServlet?flag=update" method="post" id="addform" class="alertDiv-add">
           <div class="hid">×</div>
        <textarea id="editor_id" name="content" style="width:700px;height:300px;">
           ${agreement}
        </textarea>
			<input type="submit" value="修     改" class="btn" />
			<input type="reset" value="取     消" class="btn" />
         </form>
       </div> --%>
</section>
 
</body>
</html>