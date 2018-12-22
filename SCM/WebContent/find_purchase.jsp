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
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<section>
<div class="dirbox">
    <span>查询分析&nbsp;&nbsp;>&nbsp;&nbsp;采购查询

   </span>
</div>
<form action="PurchaseServlet?flag=gfind" method="post" class="gfindbox">
   商品名称：<input type="text" placeholder="请输入商品名称..." name="name" class="txt"/><br><br>
   价格范围：<input type="number" name="stprice" id="stprice"  step="0.01" class="txt" style="width:80px"/>-
              <input type="number" name="endprice" id="endprice" step="0.01" class="txt" style="width:80px"/><br><br>
              
    起止时间：<input type="date" name="stdate" id="stdate" class="txt" style="width:120px"/>-
          <input type="date" name="enddate" id="enddate" class="txt" style="width:120px"/><br><br>
          
        数量范围：<input type="number" id="minnum" name="minnum" class="txt" style="width:80px"/>-
                       <input type="number" id="maxnum" name="maxnum" class="txt" style="width:80px"/><br><br>
                       
         是否上传发票：<input type="radio" name="isbill" value="已经上传发票" />    已经上传发票          
                     <input type="radio" name="isbill" value="暂未上传发票"/>    暂未上传发票  <br><br>
                             
                       采购单状态：<br><br><input type="checkbox" name="stat" value="请款成功-开始采购"/>请款成功-开始采购&nbsp;&nbsp;&nbsp;
                       <input type="checkbox" name="stat" value="请款失败"/>请款失败
                       <input type="checkbox" name="stat" value="请款中-已提交报价单"/>请款中-已提交报价单<br><br>
                       <input type="checkbox" name="stat" value="已采购完成-等待入库"/>已采购完成-等待入库
                       <input type="checkbox" name="stat" value="已入库"/>已入库&nbsp;&nbsp;&nbsp;
                       <input type="checkbox" name="stat" value="请款中-未提交报价单"/>请款中-未提交报价单
                       <input type="checkbox" name="stat" value="审核未通过"/>审核未通过<br><br><br><br>
                       <input type="submit" value="查询" class="btn"/>
                       <input type="reset" value="取消" class="btn"/>
 
 </form></section>
</body>
</html>