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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/goods_check.js" charset="gb2312"></script>
 <style type="text/css">
 /* 增加框 复写*/
 section  .alertDiv-add{
	width:460px;
	height:500px;
	position:absolute;
    top:5%;
    left:30%; 
}
section  .alertDiv-add input[placeholder]{
width:100px;
margin-right:60px;
}
section  .alertDiv-add #searchbox{
width:310px;
top:290px;
left:125px;
pdding-top:5px;
}
 </style>
 
</head>
<body>
 
<jsp:include page="head.jsp"></jsp:include>
<section>
 <input type="hidden" value="goods" id="hid"/>
<div class="dirbox">
    <span>基础数据管理&nbsp;&nbsp;>&nbsp;&nbsp;物资信息管理</span>
</div>
<div class="tablebox">
<form action="GoodsServlet?flag=findByName" method="post" class="findbox">
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
      <td width="200">价格</td>
      <td width="200">供应商</td>
      <td width="200">库存</td>
      <td width="200">状态</td>
      <td width="300">操作</td>
   </tr>
   <c:forEach items="${goodslist }" var="gl" begin="0" end="9">
         <tr>
      <td><input type="checkbox" name="check" id="c${gl.id }"/></td>
      <td>${gl.id }</td>
      <td>${gl.name }</td>
      <td>${gl.price}</td>
      <td>${gl.sup.name}</td>
      <td>${gl.store}</td>
      <td>${gl.status }</td>
      <td>
    <c:if test="${(loginUser.type eq '采购业务主管')||(loginUser.type eq '超级管理员') }">
       <div class="btn-check" id="${gl.id }"  ${gl.status ne "未审核"?"style='background:gray;'":"" }>
       <a><img src="${pageContext.request.contextPath}/img/btn-check.png"/>审核</a></div>
   </c:if>
      <div class="btn-delete" ${gl.status ne "未审核"?"style='background:gray;'":"" }><a href="GoodsServlet?flag=deleteOne&id=${gl.id }">
      <img src="${pageContext.request.contextPath}/img/btn-delete.png"/>删除</a></div>
     <div class="btn-edit" id="${gl.id }" ${gl.status ne "未审核"?"style='background:gray;'":"" }>
     <a class="edit-data" href="GoodsServlet?flag=yupdate&id=${gl.id }">
     <img src="${pageContext.request.contextPath}/img/btn-edit.png" />编辑</a></div>
     
     </td>
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
   
   <div class="alertDiv" <c:if test="${opa eq 'edit' }">style="display:block;"</c:if>>
         <form action="GoodsServlet?flag=${opa eq 'edit'?'update':'add' }" method="post" id="addform" class="alertDiv-add">
           <div class="hid">×</div>
           <input type="hidden" size="10" placeholder="" name="id" required value="${updateGoods.id}"/>
   <%-- <input type="hidden" size="10" placeholder="" name="status" required value="${updateGoods.status}"/> --%>
 名称：<input type="text" size="10" placeholder="" name="name" required value="${updateGoods.name}" ${opa eq 'edit'?'disabled':'' }/>
  
  库存：<input type="number" placeholder="" name="store" required value="${updateGoods.store}" /> 
<br />
  价格：<input type="number" size="10" placeholder="" name="price"  required value="${updateGoods.price}" step="0.1"/> 
  产地：<input type="text" size="10" placeholder="" name="address" required value="${updateGoods.address}"/><br />
  规格：<input type="text" size="10" placeholder="" name="size" required value="${updateGoods.size}"/> 
  批号：<input type="text" placeholder="" name="batch"  required value="${updateGoods.batch}"/><br />

  供应商：<input type="text" placeholder="" name="sup"  style="width:300px;" value="${updateGoods.sup.name}"/>
        <div id="searchbox"><ul></ul></div>
          <br /> 
          <span id="mess_name"></span><br>
           <span id="mess_sup"></span><br><br>
   <textarea id="area" rows="5" cols="30" placeholder="描述..." name="describe">${updateGoods.describe}</textarea><br /> <br />  <br /> 
			<input type="submit" value="${opa eq 'edit'?'修     改':'增    加' }" class="btn" />
			<input type="reset" value="取     消" class="btn" />
         </form>
       </div>
</section>
</body>
</html>