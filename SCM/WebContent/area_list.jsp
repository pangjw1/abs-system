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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/area_check.js" charset="gb2312"></script>
 <style type="text/css">
 /* 增加框 复写*/
 section  .alertDiv-add{
	height:330px;
}
 
section  .alertDiv-add #searchbox{
width:130px;
top:90px;
left:140px;
pdding-top:5px;
}
 </style>
 
</head>
<body>
 
<jsp:include page="head.jsp"></jsp:include>
<section>
 <input type="hidden" value="goods" id="hid"/>
<div class="dirbox">
    <span>基础数据管理&nbsp;&nbsp;>&nbsp;&nbsp;物资地区价格</span>
</div>
<div class="tablebox">
<form action="AreaServlet?flag=findByName" method="post" class="findbox">
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
      <td width="200">商品名称</td>
      <td width="200">地区名称</td>
      <td width="200">最低价格</td>
      <td width="200">最高价格</td>
      <td width="200">平均价格</td>
      <td width="200">操作</td>
   </tr>
   <c:forEach items="${arealist }" var="al" begin="0" end="9">
         <tr>
      <td><input type="checkbox" name="check" id="c${al.id }"/></td>
      <td>${al.id }</td>
      <td>${al.goods.name }</td>
      <td>${al.name}</td>
      <td>${al.minprice}</td>
      <td>${al.maxprice}</td>
      <td>${(al.maxprice+al.minprice)/2}</td>
      <td>
 
      <div class="btn-delete" <%-- ${al.status ne "未审核"?"style='background:gray;'":"" } --%>>
      <a href="AreaServlet?flag=deleteOne&id=${al.id }">
      <img src="${pageContext.request.contextPath}/img/btn-delete.png"/>删除</a></div>
     <div class="btn-edit" id="${al.id }" <%-- ${gl.status ne "未审核"?"style='background:gray;'":"" } --%>>
     <a class="edit-data" href="AreaServlet?flag=yupdate&id=${al.id }">
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
         <form action="AreaServlet?flag=${opa eq 'edit'?'update':'add' }" method="post" id="addform" class="alertDiv-add">
           <div class="hid">×</div>
           <input type="hidden" placeholder="" name="id" required value="${updateArea.id}" id="aid"/>
           <input type="hidden" placeholder="" id="opa" required value="${opa}"/>
           
 商品名称：<input type="text"  placeholder="" name="goods" required value="${updateArea.goods.name}" ${opa eq 'edit'?'disabled':'' } style="width:120px;"/>
          <span id="mess_name"></span>
          <div id="searchbox"><ul></ul></div>
 <br>
  地区名称：<select name="name" required="required" id="aname">
        <option value="">---  请选择地区  ---</option>
        <option value="东北区" ${updateArea.name eq '东北区'?'selected':'' }>&nbsp;&nbsp;&nbsp;东北区&nbsp;&nbsp;&nbsp;</option>
        <option value="华东区" ${updateArea.name eq '华东区'?'selected':'' }>&nbsp;&nbsp;&nbsp;华东区&nbsp;&nbsp;&nbsp;</option>
        <option value="华北区" ${updateArea.name eq '华北区'?'selected':'' }>&nbsp;&nbsp;&nbsp;华北区&nbsp;&nbsp;&nbsp;</option>
        <option value="华中区" ${updateArea.name eq '华中区'?'selected':'' }>&nbsp;&nbsp;&nbsp;华中区&nbsp;&nbsp;&nbsp;</option>
        <option value="华南区" ${updateArea.name eq '华南区'?'selected':'' }>&nbsp;&nbsp;&nbsp;华南区&nbsp;&nbsp;&nbsp;</option>
        <option value="西北区" ${updateArea.name eq '西北区'?'selected':'' }>&nbsp;&nbsp;&nbsp;西北区&nbsp;&nbsp;&nbsp;</option>
        <option value="西南区" ${updateArea.name eq '西南区'?'selected':'' }>&nbsp;&nbsp;&nbsp;西南区&nbsp;&nbsp;&nbsp;</option>
      </select><br>
 
  地区价格：<input type="number" style="width:80px;" placeholder="" name="minprice" required value="${updateArea.minprice}" step="0.01"/>&nbsp;元&nbsp; -&nbsp;
  <input type="number" style="width:80px;" placeholder="" name="maxprice" required value="${updateArea.maxprice}" step="0.01"/>&nbsp;元 
  
  <br>
   <span id="mess_price"></span><br><br><br>
			<input type="submit" value="${opa eq 'edit'?'修     改':'增    加' }" class="btn" />
			<input type="reset" value="取     消" class="btn" />
         </form>
       </div>
</section>
</body>
</html>