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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/supplier_check.js" charset="gb2312"></script> 
 <style type="text/css">
 /* 增加框 复写*/
 section  .alertDiv-add{
	width:500px;
	height:600px;
	position:absolute;
    top:5%;
    left:30%; 
}

 section  .alertCheck-add{
	width:450px;
	height:450px;
	position:absolute;
    top:5%;
    left:30%; 
}
 </style>
 
</head>
<body>
 
<jsp:include page="head.jsp"></jsp:include>
<section>
 <input type="hidden" value="supplier" id="hid"/>
<div class="dirbox">
    <span>供应商管理&nbsp;&nbsp;>&nbsp;&nbsp;${isCheck eq 'true'?'供应商考核':'供应商选择' }</span>
</div>
<div class="tablebox">
<form action="SupplierServlet?flag=findByName&isCheck=${isCheck }" method="post" class="findbox">
       <div class="btn-add">+&nbsp;新增</div>
       <div class="btn-delete">🗑&nbsp;删除</div>
       <div class="btn-export">🖨&nbsp;导出</div>
       <input type="hidden" value="${isCheck }" name="isCheck" id="isCheck">
       <input type="text" placeholder="请输入供应商名称..." name="name"/>
       <div id="btn-find"></div>
 </form>
    <table>
   <tr>
      <td width="50"><input type="checkbox" name="check" id="checkAll"/></td>
      <td width="80" >编号</td>
      <td width="220">供应商名称</td>
      <td width="180">联系人</td>
      <td width="280">联系人电话</td>
      <td width="200">状态</td>
      <td width="260">操作</td>
   </tr>
   <c:forEach items="${suplist }" var="sl" begin="0" end="9">
         <tr>
      <td><input type="checkbox" name="check" id="c${sl.id }"/></td>
      <td>${sl.id }</td>
      <td>${sl.name }</td>
      <td>${sl.linkman}</td>
      <td>${sl.link_tel}</td>
      <td>${sl.status }</td>
      <td>
      <input type="hidden" value="${sl.status }" name="supstatus">
    
      <c:if test="${isCheck eq 'true' }">
       <div class="btn-check" id="${sl.id }" ${sl.status ne "未审核"?"style='background:#458B74;'":""}>
       <a href="SupplierServlet?flag=ycheck&id=${sl.id }&isCheck=${isCheck}" >
      <img src="${pageContext.request.contextPath}/img/btn-check.png"/>${sl.status ne "未审核"?"查看":"审核"}</a></div>
      </c:if>
      
      <div class="btn-delete" ${sl.status ne "未审核"?"style='background:gray;'":""}>
      <a href="SupplierServlet?flag=deleteOne&id=${sl.id }&isCheck=${isCheck}">
      <img src="${pageContext.request.contextPath}/img/btn-delete.png"/>删除</a></div>
      
       <div class="btn-edit" id="${sl.id }" ${sl.status ne "未审核"?"style='background:gray;'":""}>
       <a class="edit-data" href="SupplierServlet?flag=yupdate&id=${sl.id }&isCheck=${isCheck}">
       <img src="${pageContext.request.contextPath}/img/btn-edit.png"/>编辑</a>
     
       </div>
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
   
   <div class="alertDiv" ${opa eq "edit"?"style='display:block'":"" }>
         <form action="SupplierServlet?flag=${opa eq 'edit'?'update':'add' }&isCheck=${isCheck}" method="post" id="addform" class="alertDiv-add"  >
           <div class="hid">×</div>
           <input type="hidden" value="${updateSup.id }" name="id"> 
          名称： <input type="text" placeholder="名称" name="name" required value="${updateSup.name}"  ${opa eq 'edit'?'disabled':'' }/>&nbsp;&nbsp;&nbsp;<span id="wname"></span><br /> 
      地址： <input type="text" placeholder="地址" name="address" required value="${updateSup.address }"/><br />
           <!--  ^(0\d{2,3}-\d{7,8})|(1[3|4|5|7|8][0-9]{9})$ -->
        电话：   <input type="number" placeholder="电话" name="tel" oninvalid="setCustomValidity('请输入正确的手机号码或固定电话');" required value="${updateSup.tel }"/><br /> 
   Email：      <input type="email" placeholder="email" name="email"  value="${updateSup.email }"/><br /><!--  /^(\d{3,4}-)?\d{7,8})|(1[3|4|5|7|8][0-9]{9})$/ -->
      传真：     <input type="text" placeholder="传真" name="fax" value="${updateSup.fax }"/><br /> 
      联系人：     <input type="text" placeholder="联系人" name="linkman"  value="${updateSup.linkman }" required/><br /> 
     联系人电话：      <input type="number" placeholder="联系人手机号码" name="link_tel" value="${updateSup.link_tel }"  required  oninvalid="setCustomValidity('请输入正确的电话号码');"/><br /> 
			<br /> 
			<input type="submit" value="${opa eq 'edit'?'修    改':'增    加' }" class="btn" />
			<input type="reset" value="取     消" class="btn" />
            
         </form>
       </div>
       
        <div class="alertCheck" ${opa eq "check"?"style='display:block'":"" }>
         <form action="SupplierServlet?flag=supcheck&isCheck=true" method="post" id="addform" class="alertCheck-add">
           <div class="hid">×</div>
           
            供应商名称：<input type="text" placeholder="" name="name" value="${upSup.name }" disabled="disabled"/><br /> 
                 
          价格是否合理：
          <input type="radio" name="price" value="偏高" ${upSupCheck.price eq '偏高'||upSupCheck.price eq null?'checked':'' }  ${upSupCheck eq null?'':'disabled' }/>偏高&nbsp;&nbsp;
          <input type="radio" name="price" value="适中" ${upSupCheck.price eq '适中'?'checked':'' } ${upSupCheck eq null?'':'disabled' }/>适中&nbsp;&nbsp;
          <input type="radio" name="price" value="偏低" ${upSupCheck.price eq '偏低'?'checked':'' } ${upSupCheck eq null?'':'disabled' }/>偏低<br>
          
        样品是否合格：
     <input type="radio" name="sample" value="合格" ${upSupCheck.sample eq '合格'||upSupCheck.sample eq null?'checked':'' }/>合格&nbsp;&nbsp;
     <input type="radio" name="sample" value="不合格" ${upSupCheck.sample eq '不合格'?'checked':'' }/>不合格<br>
          
         各部门满意度：
     <input type="number" style="width:35px;" name="sat" placeholder="100" value="${upSupCheck.sat eq null?100:upSupCheck.sat}" ${upSupCheck eq null?'':'disabled' } min="0" max="100"/>(*满分100分，不满意扣5分/部门)<br>
         
         交货能力评判：
     <input type="radio" name="delivery" value="逾期" ${upSupCheck.delivery eq '逾期'||upSupCheck.delivery eq null?'checked':'' }/>逾期&nbsp;&nbsp;
     <input type="radio" name="delivery" value="未逾期" ${upSupCheck.delivery eq '未逾期'?'checked':'' }/>未逾期<br>
          
      材料是否齐全：
      <input type="radio" name="f_data" value="齐全" ${upSupCheck.f_data eq '齐全'||upSupCheck.f_data eq null?'checked':'' }/>齐全&nbsp;&nbsp;
      <input type="radio" name="f_data" value="不齐全"  ${upSupCheck.f_data eq '不齐全'?'checked':'' }/>不齐全<br>    
          
     质量是否达标：
    <input type="radio" name="quality" value="达标" ${upSupCheck.quality eq '达标'||upSupCheck.quality eq null?'checked':'' }/>达标&nbsp;&nbsp;
    <input type="radio" name="quality" value="不达标" ${upSupCheck.quality eq '不达标'?'checked':'' }/>不达标<br>  
    <input type="hidden" name="id" value="${upSup.id }">
   ${upSupCheck eq null?'':' 审核日期：'}${upSupCheck.wdate }<br><br>
    <input type="submit" value="审      核" class="btn"  />
	<input type="reset" value="取     消" class="btn" />
           </form>
           </div>
</section>
</body>
</html>