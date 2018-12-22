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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/need_check.js" charset="gb2312"></script>
 <style type="text/css">
 section  .alertDiv-add input{
  width:110px;
  height:30px;
  color:gray;
  font-size:16px;
  outline: none;
}
/*弹出增加框 中的搜索提示*/
 section  .alertDiv-add #searchbox{
width:130px;

}

section  .alertDiv-add{
	width:480px;
	height:270px;
}

 
 </style>
 
</head>
<body>
 
<jsp:include page="head.jsp"></jsp:include>
<section>
<div class="dirbox">
    <span>${isCheck eq 'find'?'查询分析':'需求计划管理' }&nbsp;&nbsp;>&nbsp;&nbsp;
   <c:if test="${isCheck eq 'add' }"> 需求计划提交</c:if>  
   <c:if test="${isCheck eq 'true' }"> 需求计划审批</c:if>  
   <c:if test="${isCheck eq 'false' }"> 需求计划汇总</c:if>  
   <c:if test="${isCheck eq 'gfind' }">需求查询</c:if>
   </span>
</div>
<div class="tablebox">
<form action="NeedServlet?flag=findByName&ischeck=${isCheck }" method="post" class="findbox">
       <div class="btn-add">+&nbsp;新增</div>
       <div class="btn-delete">🗑&nbsp;删除</div>
       <div class="btn-export">🖨&nbsp;导出</div>
        <input type="hidden" value="${isCheck }" name="isCheck" id="isCheck">
       <input type="text" placeholder="请输入商品名称..." name="name"/>
       <div id="btn-find"></div>
      
 </form>
 
    <table>
   <tr>
      <td width="50"><input type="checkbox" name="check" id="checkAll"/></td>
      <td width="50" >编号</td>
      <td width="220">商品名称</td>
      <td width="200">数量</td>
      <td width="220">截至日期</td>
      <td width="200">状态</td>
      <td width="260">操作</td>
   </tr>
   <c:forEach items="${needlist }" var="nl" begin="0" end="9">
         <tr>
      <td><input type="checkbox" name="check" id="c${nl.id }"/></td>
      <td>${nl.id }</td>
      <td>${nl.goods.name }</td>
      <td>${nl.num}</td>
      <td>${nl.stopdate}</td>
      <td>${nl.status }</td>
      <td>
     <c:if test="${isCheck eq 'true' }">
     <div class="btn-check" id="${nl.id }" ${nl.status ne "未审核"?" style='background:gray;'":"" }>
     <a <%-- href="NeedServlet?flag=ycheck&id=${nl.id }&isCheck=${isCheck}" --%>>
      <img src="${pageContext.request.contextPath}/img/btn-check.png"/>审批</a></div>
     </c:if>
      <div class="btn-delete" ${nl.status ne "未审核"?"style='background:gray;'":"" }><a href="NeedServlet?flag=deleteOne&id=${nl.id }&isCheck=${isCheck}">
      <img src="${pageContext.request.contextPath}/img/btn-delete.png"/>删除</a></div>
      
     <div class="btn-edit" id="${nl.id }" ${nl.status ne "未审核"?"style='background:gray;'":"" }>
     <a class="edit-data" href="NeedServlet?flag=yupdate&id=${nl.id }&isCheck=${isCheck}">
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
<div class="alertDiv" <c:if test="${isCheck eq 'add' ||opa eq 'edit'}"> style="display:block;"</c:if>>
         <form action="NeedServlet?flag=${opa eq 'edit'?'update':'add' }&isCheck=${isCheck}" method="post" id="addform" class="alertDiv-add">
           <div class="hid">×</div>
          
 商品名称：<input type="text" size="10" placeholder="" name="goods" required value="${updateNeed.goods.name}"  ${opa eq 'edit'?'disabled':'' } style="width:120px;"/>
      &nbsp;&nbsp;<span id="mess_name"></span>
 
 <br /> <div id="searchbox"><ul></ul></div>
   <input type="hidden" size="10" placeholder="" name="id" required value="${updateNeed.id}"/>
   <input type="hidden" size="10" placeholder="" name="status" required value="${updateNeed.status}"/>
   <input type="hidden" size="10" placeholder="" name="g" required value="${updateNeed.goods.name}"/>
  <%--  <input type="hidden" size="10" placeholder="" name="ischeck" required value="${ischeck}"/> --%>
 截至日期：<input type="date" name="stopdate" placeholder="" value="${updateNeed.stopdate}" required style="width:150px;"/>
 &nbsp;&nbsp;<span id="mess_date"></span>
 <br />
  需求数量：<input type="number"  step="1"  size="10" placeholder="" name="num"  required style="width:80px;" value="${updateNeed.num}"  id="need_num"/> 
           &nbsp;&nbsp;<span id="mess_num"></span><br /> 
        
			<input type="submit" value="${opa eq 'edit'?'修     改':'增    加' }" class="btn" />
			<input type="reset" value="取     消" class="btn" />
            
         </form>
       </div> 
</section>
</body>
</html>