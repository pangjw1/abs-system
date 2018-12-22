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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/loss_check.js" charset="gb2312"></script>
 <style type="text/css">
 /* 增加框 复写*/
 section  .alertDiv-add{
 height:380px;
 width:400px;
}
 section  .alertDiv-add #searchbox{
 width:250px;
}

 </style>
 
</head>
<body>
 
<jsp:include page="head.jsp"></jsp:include>
<section>
<!--  <input type="hidden" value="goods" id="hid"/> -->
<div class="dirbox">
    <span>库存管理&nbsp;&nbsp;>&nbsp;&nbsp;盘点耗损管理</span>
</div>
<div class="tablebox">
<form action="LossServlet?flag=findByName" method="post" class="findbox">
       <div class="btn-add">+&nbsp;新增</div>
       <div class="btn-delete">🗑&nbsp;删除</div>
       <div class="btn-export">🖨&nbsp;导出</div>
       <input type="text" placeholder="请输入商品名称..." name="name"/>
       <div id="btn-find"></div>
 </form>
    <table>
   <tr>
      <td width="50"><input type="checkbox" name="check" id="checkAll"/></td>
      <td width="50" >编号</td>
      <td width="200">名称</td>
      <td width="200">供应商</td>
      <td width="200">损耗数量</td>
      <td width="200">损耗日期</td>
      <td width="200">损耗原因</td>
      <td width="200">操作</td>
   </tr>
   <c:forEach items="${loslist }" var="ll" begin="0" end="9">
         <tr>
      <td><input type="checkbox" name="check" id="c${ll.id }"/></td>
      <td>${ll.id }</td>
      <td>${ll.goods.name }</td>
      <td>${ll.goods.sup.name}</td>
      <td>${ll.num}</td>
      <td>${ll.lossdate}</td>
      <td>${ll.reason}</td>
      <td>
     
       

      <div class="btn-delete"><a href="LossServlet?flag=deleteOne&id=${ll.id }">
      <img src="${pageContext.request.contextPath}/img/btn-delete.png"/>删除</a></div>
      
     <div class="btn-edit" id="${ll.id }" <%-- ${gl.status ne "未审核"?"style='background:gray;'":"" } --%>>
     <a class="edit-data" href="LossServlet?flag=yupdate&id=${ll.id }">
     <img src="${pageContext.request.contextPath}/img/btn-edit.png" />编辑</a></div></td>
   </tr> 
   </c:forEach>
   
   
   </table> 
    <div class="see">
         <ul></ul>
   </div>
   </div>
   <div class="inpage">${pager }</div>
   <footer>CopyRight &copy; 2018 创睿信教育集团 <br> <br>
   Powered by Teacher Lei版权所有
   </footer>
   
   <!-- 弹出层  -->
   
   <div class="alertDiv" style="${opa eq 'edit'?'display:block;':''}">
         <form action="LossServlet?flag=${opa eq 'edit'?'update':'add'}" method="post" id="addform" class="alertDiv-add">
           <div class="hid">×</div>
      <input type="hidden" size="10" placeholder="" name="id" required value="${uploss.id}"/>
 商品名称：<input type="text" size="10" placeholder="" name="goods" required value="${uploss.goods.name}"  ${opa eq 'edit'?'disabled':'' } id="goods" /><br><br>
  <div id="searchbox"> <ul></ul> </div>
 供应商：<span id="sup_name" style="color:gray">${uploss.goods.sup.name}</span><br><br><br>
 
  损耗数量：<input type="number" placeholder="" name="num" required value="${uploss.num}" id="lossnum" style="width:80px;"/>&nbsp;&nbsp;<span id="wnum"></span><br />
  损耗日期：<input type="date" size="10" placeholder="" name="lossdate" id="lossdate" required value="${uploss.lossdate}" step="0.1"/> 
  <%-- 损耗原因：<input type="text" size="10" placeholder="" name="reason" required value="${uploss.reason}" /><br /> --%>
  <br>
          <span id="mess"></span><br><br><br>
  
			<input type="submit" value="${opa eq 'edit'?'修     改':'增    加' }" class="btn" />
			<input type="reset" value="取     消" class="btn" />
         </form>
       </div>
</section>
</body>
</html>