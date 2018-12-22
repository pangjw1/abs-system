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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sale_check.js" charset="gb2312"></script>
 <style type="text/css">

/*弹出增加框 中的搜索提示*/
section  .alertDiv-add #searchbox{
width:280px;

} 

section  .alertDiv-add{
	width:450px;
	height:360px;
} 

 
 </style>
 
</head>
<body>
 
<jsp:include page="head.jsp"></jsp:include>
<section>
<div class="dirbox">
    <span>库存管理&nbsp;&nbsp;>&nbsp;&nbsp;物资出库管理 
   </span>
</div>
<div class="tablebox">
<form action="SaleServlet?flag=findByName" method="post" class="findbox">
       <div class="btn-add">+&nbsp;新增</div>
       <div class="btn-delete">🗑&nbsp;删除</div>
       <div class="btn-export">🖨&nbsp;导出</div>
        <%-- <input type="hidden" value="${isCheck }" name="isCheck" id="isCheck"> --%>
       <input type="text" placeholder="请输入商品名称..." name="name"/>
       <div id="btn-find"></div>
 </form>
    <table>
   <tr>
      <td width="50"><input type="checkbox" name="check" id="checkAll"/></td>
      <td width="50" >编号</td>
      <td width="220">商品名称</td>
      <td width="220">销售数量</td>
      <td width="200">销售价格</td>
      <td width="200">销售日期</td>
      <td width="200">操作</td>
   </tr>
   <c:forEach items="${salist }" var="sl" begin="0" end="9">
         <tr>
      <td><input type="checkbox" name="check" id="c${sl.id }"/></td>
      <td>${sl.id }</td>
      <td>${sl.goods.name }</td>
      <td>${sl.num}</td>
      <td>${sl.price }</td>
      <td>${sl.outdate}</td>
      <td>
      <div class="btn-delete"><a href="SaleServlet?flag=deleteOne&id=${sl.id }">
      <img src="${pageContext.request.contextPath}/img/btn-delete.png"/>删除</a></div>
      
     <div class="btn-edit" id="${sl.id }" <%-- ${nl.status ne "未审核"?"style='background:gray;'":"" } --%>>
     <a class="edit-data" href="SaleServlet?flag=yupdate&id=${sl.id }">
     <img src="${pageContext.request.contextPath}/img/btn-edit.png"/>编辑</a></div></td>
   </tr> 
   </c:forEach>
   
   
   </table> 
   </div>
   <div class="inpage">${pager }</div>
   <footer>CopyRight &copy; 2018 创睿信教育集团 <br> <br>
   Powered by Teacher Lei版权所有  
   </footer>
   
   <!-- 弹出层  --> 
<div class="alertDiv"  style="${(opa eq 'edit')?'display:block':''}">
         <form action="SaleServlet?flag=${opa eq 'edit'?'update':'add' }" method="post" id="addform" class="alertDiv-add">
           <div class="hid">×</div>
          
   <input type="hidden" size="10" placeholder="" name="id" required value="${upsale.id}"/>
 商品名称：<input type="text" size="10" placeholder="" name="goods" required value="${upsale.goods.name}"  ${opa eq 'edit'?'disabled':'' }/><br />
          <div id="searchbox"><ul></ul></div>
 现有库存：<span id="nowstore" style="color:gray;">${upsale.goods.store}</span>&nbsp;&nbsp; <span id="gmess"></span><br><br><br>
   
  销售数量：<input type="number"  step="1"  size="10" placeholder="" name="num"  required style="width:80px;" value="${upsale.num}" id="sale_num"/> &nbsp; &nbsp;
  销售价格：<input type="number"  step="0.01"  size="10" placeholder="" name="price"  required style="width:80px;" value="${upsale.price}"/> <br>
   <span id="nmess"></span><br /><br />
 出库日期：<input type="date" name="outdate" id="outdate" placeholder="" value="${upsale.outdate}" required/><br />
           <span id="mess"></span><br /> <br />
        
			<input type="submit" value="${opa eq 'edit'?'修     改':'增    加' }" class="btn" id="subBtn" />
			<input type="reset" value="取     消" class="btn" />
            
         </form>
       </div> 
</section>
</body>
</html>