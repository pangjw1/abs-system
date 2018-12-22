<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/css/icon.css"  type="text/css" rel="styleSheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tree.js"></script>
</head>
<body>
 <header>
      <img src="${pageContext.request.contextPath}/img/logo白白.png"/>
        <span>ABS原料供应链管理系统</span>
        <span style="font-size:12px;margin-top:1px;">Supply Chain Management</span>
        <%-- <div class="user-txt">${cookie.lUser_type.value }：${cookie.lUser_name.value}   <a href="UserServlet?flag=out">&nbsp;&nbsp;&nbsp;&nbsp;『退出』</a></div>
          <div class="user-icon"><img alt="" src="${pageContext.request.contextPath}/img/user-icon.png"></div> --%>
         <div class="user-txt">${loginUser.type }：${loginUser.username}   <a href="UserServlet?flag=out">&nbsp;&nbsp;&nbsp;&nbsp;『退出』</a></div>
          <div class="user-icon"><img alt="" src="${pageContext.request.contextPath}/img/user-icon.png"></div> 
 </header>
   <nav>
      <ul>
         <li><a class="ina">
            <i class="icon-need"></i>
           <span>需求计划管理</span></a>
            <ul>
           <c:if test="${(loginUser.type eq '需求计划员')||(loginUser.type eq '采购业务员')||(loginUser.type eq '采购业务主管')||(loginUser.type eq '超级管理员') }">
            <li><a href="NeedServlet?flag=findAll&isCheck=add">需求计划提交</a></li>
            <li><a href="NeedServlet?flag=findAll&isCheck=false">需求计划汇总</a></li>
           </c:if>
            <c:if test="${(loginUser.type eq '采购业务主管')||(loginUser.type eq '超级管理员') }">
            <li><a href="NeedServlet?flag=findAll&isCheck=true">需求计划审批</a></li>
            </c:if>
            </ul>
         </li>
         <li><a class="ina">
         <i class="icon-purchase"></i>
         <span>采购管理</span></a>
         <ul>
       <c:if test="${(loginUser.type eq '采购业务员')||(loginUser.type eq '超级管理员')||(loginUser.type eq '采购业务主管') }">
            <li><a href="PurchaseServlet?flag=findAll&oper=plan">采购计划管理</a></li>
            <li><a href="PurchaseServlet?flag=findAll&oper=offer">报价单提交</a></li>
            <li><a href="PurchaseServlet?flag=findAll&oper=money">请款管理</a></li>
            <li><a href="PurchaseServlet?flag=findAll&oper=bill">采购发票管理</a></li>
            <li><a href="PurchaseServlet?flag=findAll&oper=proof">采购凭证管理</a></li>
         </c:if>
             <c:if test="${(loginUser.type eq '超级管理员')||(loginUser.type eq '结算员')||(loginUser.type eq '保管员') }">
            <li><a href="PurchaseServlet?flag=findAll&oper=state">结算单生成</a></li>
            </c:if>
         </ul>
         </li>
         
         <li><a class="ina">
         <i class="icon-supplier"></i>
         <span>供应商管理</span></a>
          <ul>
          <c:if test="${(loginUser.type eq '需求计划员')||(loginUser.type eq '采购业务员')||(loginUser.type eq '超级管理员')||(loginUser.type eq '采购业务主管') }">
            <li><a href="SupplierServlet?flag=findAll&isCheck=false">供应商选择</a></li>
         </c:if>
         <c:if test="${(loginUser.type eq '超级管理员')||(loginUser.type eq '采购业务主管') }">
            <li><a href="SupplierServlet?flag=findAll&isCheck=true">供应商考核</a></li>
         </c:if>
         </ul>
         </li>
         <li><a class="ina"><i class="icon-stock"></i>
         <span>库存管理</span></a>
         <ul>
          <c:if test="${(loginUser.type eq '超级管理员')||(loginUser.type eq '保管员') }">
            <li><a href="PurchaseServlet?flag=findAll&oper=inbound">入库管理</a></li>
           </c:if>
         <c:if test="${(loginUser.type eq '超级管理员')||(loginUser.type eq '结算员')||(loginUser.type eq '保管员') }">
            <li><a href="InBoundServlet?flag=findAllAccount">保管账管理</a></li>
            </c:if>
             <c:if test="${(loginUser.type eq '超级管理员')||(loginUser.type eq '保管员') }">
            <li><a href="SaleServlet?flag=findAll">物资出库管理</a></li>
            <li><a href="LossServlet?flag=findAll">盘点耗损管理</a></li>
            </c:if>
         </ul>
         </li>
          <li><a class="ina"><i class="icon-data"></i>
         <span>基础数据管理</span></a>
         <ul>
          <c:if test="${(loginUser.type eq '超级管理员')||(loginUser.type eq '需求计划员')||(loginUser.type eq '采购业务员') ||(loginUser.type eq '采购业务主管')||(loginUser.type eq '保管员')}">
            <li><a href="GoodsServlet?flag=findAll">物资信息管理</a></li>
            </c:if>
            <c:if test="${(loginUser.type eq '超级管理员')}">
            <li><a href="AreaServlet?flag=findAll">物资地区价格</a></li>
            </c:if>
             <c:if test="${(loginUser.type eq '超级管理员') ||(loginUser.type eq '结算员')}">
            <li><a href="AgreeServlet?flag=show">合同条款维护</a></li>
            </c:if>
             <c:if test="${(loginUser.type eq '超级管理员')}">
            <li><a href="UserServlet?flag=findAll">用户管理</a></li>
            </c:if>
         </ul>
         </li>
          <li><a class="ina"><i class="icon-search"></i>
         <span>查询分析</span></a>
         <ul>
         <c:if test="${(loginUser.type eq '超级管理员')||(loginUser.type eq '需求计划员')||(loginUser.type eq '采购业务员')||(loginUser.type eq '采购业务主管') }">
            <li><a href="find_need.jsp">需求查询</a></li>
            </c:if>
              <c:if test="${(loginUser.type eq '超级管理员')||(loginUser.type eq '采购业务员')||(loginUser.type eq '采购业务主管') }">
            <li><a href="find_purchase.jsp">采购查询</a></li>
             </c:if>
             <c:if test="${(loginUser.type eq '超级管理员')||(loginUser.type eq '采购业务员')||(loginUser.type eq '采购业务主管')||(loginUser.type eq '保管员') }">
            <li><a href="find_goods.jsp">库存查询</a></li>
            </c:if>
         </ul>
         </li>
      </ul>
</nav>
</body>
</html>