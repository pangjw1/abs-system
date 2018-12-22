<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/css/main.css"
	type="text/css" rel="styleSheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/table.js" charset="gb2312"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/formcheck.js"
	charset="gb2312"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/purchase_check.js"
	charset="gb2312"></script>
<style type="text/css">
/* 增加框 复写*/
section  .alertDiv-add {
	width: 400px;
	height: 350px;
	position: absolute;
	top: 20%;
	left: 30%;
	/*padding-bottom:70px; */
}

section  .alertDiv-add #searchbox {
	left: 110px;
	width: 248px;
}
</style>

</head>
<body>

	<jsp:include page="head.jsp"></jsp:include>
	<section>
		<input type="hidden" value="goods" id="hid" />
		<div class="dirbox">
			<span> <c:choose>
					<c:when test="${(oper eq 'plan')}">采购管理&nbsp;&nbsp;>&nbsp;&nbsp;采购计划管理</c:when>
					<c:when test="${(oper eq 'offer')}">采购管理&nbsp;&nbsp;>&nbsp;&nbsp;报价单提交</c:when>
					<c:when test="${(oper eq 'money')}">采购管理&nbsp;&nbsp;>&nbsp;&nbsp;请款管理</c:when>
					<c:when test="${(oper eq 'bill')}">采购管理&nbsp;&nbsp;>&nbsp;&nbsp;采购发票管理</c:when>
					<c:when test="${(oper eq 'proof')}">采购管理&nbsp;&nbsp;>&nbsp;&nbsp;采购凭证管理</c:when>
					<c:when test="${(oper eq 'state')}">采购管理&nbsp;&nbsp;>&nbsp;&nbsp;结算单生成</c:when>
					<c:when test="${(oper eq 'inbound')}">库存管理&nbsp;&nbsp;>&nbsp;&nbsp;入库管理</c:when>
				</c:choose>
			</span>
		</div>
		<div class="tablebox">
			<form action="PurchaseServlet?flag=findByName&oper=${oper}"
				method="post" class="findbox">
				<c:if test="${(oper eq 'plan') }">
				<div class="btn-add" 	<%-- style="${(oper eq 'offer')||(oper eq 'money')||(oper eq 'bill')||(oper eq 'proof')||(oper eq 'state')||(oper eq 'inbound') ?'background:gray':''}" --%>>+&nbsp;新增</div>
				<div class="btn-delete">🗑&nbsp;删除</div>
				</c:if>
				<div class="btn-export">🖨&nbsp;导出</div>
				<%-- <c:if test="${oper eq 'state' }"><div class="btn-state" style="background-color:orange;">📜&nbsp;结算</div></c:if> --%>
				<input type="hidden" value="${oper }" name="oper" id="oper">
				<input type="text" placeholder="请输入商品名称..." name="name" />
				<div id="btn-find"></div>
			</form>
			<table>
				<tr>
					<td width="50"><input type="checkbox" name="check"
						id="checkAll" /></td>
					<td width="50">编号</td>
					<td width="150">商品名称</td>
					<td width="200">供应商</td>
					<td width="100">采购数量</td>

					<td width="100">采购单价</td>
					<td width="100">采购总价</td>
					<c:if
						test="${(oper eq 'money')||(oper eq 'bill')||(oper eq 'state') }">
						<td width="100">请款金额</td>
					</c:if>
					<td width="200">状态</td>

					<td width="${(oper eq 'proof')||(oper eq 'state')?100:300 }">操作</td>
				</tr>
				<c:forEach items="${purlist }" var="pl" begin="0" end="9">
					<tr>
						<td><input type="checkbox" name="check" id="c${pl.id }" /></td>
						<td>${pl.id }</td>
						<td>${pl.good.name }</td>
						<td>${pl.good.sup.name }</td>
						<td>${pl.num}</td>

						<td>${pl.good.price}</td>
						<td><fmt:formatNumber value="${pl.good.price*pl.num}"
								pattern="#.00" /></td>
						<c:if
							test="${(oper eq 'money')||(oper eq 'bill')||(oper eq 'state') }">
							<td style="color: ${pl.price<pl.good.price*pl.num?'orange':'red' };">${pl.price }</td>
						</c:if>
						<td>${pl.status }</td>

						<td><c:if test="${(oper eq 'plan')&&((loginUser.type eq '采购业务主管')||(loginUser.type eq '超级管理员'))}">
								<div class="btn-delete" ${pl.status ne '未审核'?'background:gray;':''}>
									<a href="PurchaseServlet?flag=deleteOne&id=${pl.id }&oper=${oper}">
										<img
										src="${pageContext.request.contextPath}/img/btn-delete.png" />删除 
									</a>
								</div>
							</c:if> <c:choose>
								<c:when test="${oper eq 'plan' }">
									<!-- 计划 -->
									<!-- 普通采购业务员 无此项功能  只有采购主管有审核 -->
									<div class="btn-check" id="${pl.id }"
										${pl.status ne "未审核"?"style='background:gray;'":""}>
										<a> <img
											src="${pageContext.request.contextPath}/img/btn-check.png" />审核
										</a>
									</div>
									<div class="btn-edit" id="${pl.id }"
										${pl.status ne "未审核"?"style='background:gray;'":"" }>
										<a class="edit-data"
											href="PurchaseServlet?flag=yupdate&id=${pl.id }&oper=${oper}">
											<img
											src="${pageContext.request.contextPath}/img/btn-edit.png" />编辑
										</a>
									</div>
								</c:when>
								<c:when test="${oper eq 'offer' }">
									<!-- 报价单 -->
									<div class="btn-edit"
										style="width:80px;${pl.status eq '请款中-已提交报价单'?'background:gray;':''}"
										id="${pl.id }">
										<a class="edit-data"
											href="PurchaseServlet?flag=ywriteOffer&id=${pl.id }&oper=${oper}">
											<img
											src="${pageContext.request.contextPath}/img/btn-edit.png" />填写报价单
										</a>
									</div>
								</c:when>
								<c:when test="${oper eq 'money' }">
									<!-- 请款 -->
									<div class="btn-check" id="${pl.id }"
										${pl.status ne "请款中-已提交报价单"?"style='background:gray;'":""}>
										<a> <img
											src="${pageContext.request.contextPath}/img/btn-check.png" />请款
										</a>
									</div>
								</c:when>
								<c:when test="${oper eq 'bill' }">
									<!-- 发票 -->
									<div class="btn-edit"
										style="width:65px;${pl.status ne '请款成功-开始采购'?'background:gray;':''}"
										id="${pl.id }">
										<a class="edit-data"
											href="PurchaseServlet?flag=ybill&id=${pl.id }&oper=${oper}">
											<img
											src="${pageContext.request.contextPath}/img/btn-edit.png" />上传发票
										</a>
									</div>
								</c:when>
								<c:when test="${oper eq 'inbound' }">
									<!-- 入库 -->
									<div class="btn-edit"
										style="width:80px;  ${pl.status eq '已采购完成-等待入库'?'background:gray;':''} "
										id="${pl.id }">
										<a class="edit-data"
											href="PurchaseServlet?flag=resetinbound&id=${pl.id }&oper=${oper}">
											<img
											src="${pageContext.request.contextPath}/img/btn-edit.png" />取消入库
										</a>
									</div>
									<div class="btn-edit" style="width:80px;${pl.status ne '已采购完成-等待入库'?'background:gray;':''}"
										id="${pl.id }">
										<a class="edit-data"
											href="PurchaseServlet?flag=yinbound&id=${pl.id }&oper=${oper}">
											<img
											src="${pageContext.request.contextPath}/img/btn-edit.png" />填写入库单
										</a>
									</div>
								</c:when>

								<c:when test="${oper eq 'state' }">
									<div class="btn-check" id="${pl.id }" style="${pl.status eq '已结算'?'background:gray;':''}">
										<a class="state">
											📜&nbsp;结算
										</a>
									</div>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>


			</table>
			<div class="see">
				<ul></ul>
			</div>
		</div>
		<div class="inpage">${pager }</div>
		<footer>
			CopyRight &copy; 2018 创睿信教育集团 <br> <br> Powered by Teacher
			Lei版权所有
		</footer>

		<!-- 弹出层  -->
		<%-- <c:if test="${opa eq 'edit' }">style='height:420px' enctype='multipart/form-data'</c:if> --%>

		<div class="alertDiv"
			style="${(opa eq 'edit')||(opa eq 'offer')||(opa eq 'bill')||(opa eq 'inbound')?'display:block;':''}">


			<form
				action="PurchaseServlet?flag=<c:choose><c:when test="${opa eq 'edit'}">update</c:when><c:when test="${opa eq 'offer'}">writeOffer</c:when><c:when test="${opa eq 'bill'}">bill</c:when><c:otherwise>add</c:otherwise></c:choose>&oper=${oper}"
				method="post" id="addform" class="alertDiv-add"
				style="${(opa eq 'offer')?'height:450px;':''}${opa eq 'bill'?'height:470px;top: 10%;':'' }"
				enctype="multipart/form-data">
				<div class="hid">×</div>

				品名：<input type="text" placeholder="" autocomplete="on" name="goods"
					required value="${upPur.good.name }"
					${(opa eq 'edit')||(opa eq 'offer')||(opa eq 'bill' )?'disabled':'' } />
				<br />
				<div id="searchbox">
					<ul></ul>
				</div>

				<input type="hidden" value="${upPur.id }" name="id"> 供应商：<span
					id="goods_sup" style="color: gray; margin-right: 80px;">${upPur.good.sup.name }</span>
				<br><br><br>进价：<span id="goods_pice" style="color: gray">${upPur.good.price }
				</span> <br /> <br /> <br /> 数量：<input type="text" placeholder=""
					name="num" required value="${upPur.num }"
					style="width: 60px; readonly margin-right: 30px;"
					${(opa eq 'offer')||(opa eq 'bill' )?'disabled':''} /> <span
					id="mess" style="margin: 10px"></span><br /> 用途：<input type="text"
					placeholder="" name="demo" style="width: 280px"
					value="${upPur.demo }"
					${(opa eq 'offer')||(opa eq 'bill' )?'disabled':'' } /><br />

				<c:if test="${(opa eq 'offer')||(opa eq 'bill') }">
 总价：<fmt:formatNumber value="${upPur.num*upPur.good.price }"
						pattern="#.00" />元<br>
					<br>
 请款金额：<input type="text" placeholder="" name="price" required
						value="${upPur.price }" ${opa eq 'bill'?'disabled':'' } />
					<br />
				</c:if>
				<c:if test="${opa eq 'bill' }">
此单状态：<input type="radio" name="st" value="yes" />采购成功-上传采购单
         <input type="radio" name="st" value="采购失败 " />采购失败<br>
					<div id="upbill_box" style="display: none;">
						采购日期：<input type="date" name="purdate" placeholder=""
							style="width: 240px;" value="${upPur.purdate }" /><br /> 上传发票：<input
							type="file" name="bills" id="bills" placeholder=""
							style="width: 240px;" accept="image/*" /><br />
						<!-- <div class="bills-box"><img/></div> -->
					</div>
				</c:if>
				<input type="submit" value="${opa eq 'edit'?'修     改':'增    加' }"
					class="btn" /> <input type="reset" value="取     消" class="btn" />
			</form>
		</div>



		<div class="alertCheck" ${opa eq "bound"?"style='display:block'":"" }>
			<form action="PurchaseServlet?flag=inbound&oper=${oper}"
				method="post" class="alertCheck-add"
				style="width: 350px; height: 430px;" id="inbound-form">

				<div class="hid">×</div>

				商品名:&nbsp;&nbsp;${upPur.good.name }<br>
				<br>
				<br> <input type="hidden" name="id" value="${upPur.id }" />
				供应商:&nbsp;&nbsp;${upPur.good.sup.name }<br>
				<br>
				<br> 采购数量：<span id="purnum">${upPur.num}</span><br>
				<br>
				<br> 入库数量：<input type="number" name="innum" placeholder=""
					style="width: 150px;" id="innum" step="1" required /><br>
				剩余:&nbsp;&nbsp;<span id="mulnum"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="bmess" style="color: red;"></span><br>
				<br>
				<br> 剩余商品去向：&nbsp;&nbsp;&nbsp;&nbsp;<span id="goodsmess"
					style="color: red;"></span><br>
				<br> <input type="radio" name="reason" value="零剩余" checked />零剩余
				<input type="radio" name="reason" value="不合格退回" /> 不合格退回 <input
					type="radio" name="reason" value="日常损耗" /> 日常损耗<br>
				<br> <input type="submit" value="入     库 " class="btn" /> <input
					type="reset" value="取     消" class="btn" />
			</form>
		</div>



		<!-- 结算单 -->
		<%--    <div class="alertCheck"  ${opa eq "state"?"style='display:block'":"" } >
         <form action="PurchaseServlet" method="post" class="alertCheck-add" style="width:60%;min-height:30%;left:20%;" id="inbound-form">
                       
           <div class="hid">×</div>
        
          采购结算单<br><br>
          <table style="width:88%;margin:10px;">
             <tr>
                <td>商品名称</td>
                <td>实际采购数量</td>
                <td>进货单价</td>
                <td>进货总价</td>
              </tr>
              <c:forEach items="purs" var="s">
                  <tr>
                <td>${s.good.name }</td>
                <td>${s.num }</td>
                <td>${s.good.price}</td>
                <td>${s.num*s.good.price }</td>
            
              </tr>
              </c:forEach>
          </table>
              
         <input type="submit" value="结算"/>
            

           </form>
           </div>  --%>
	</section>
</body>
</html>