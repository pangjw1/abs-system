/**
 * 
 */
$(function(){
	  var isGoods=true;
	//当添加表单提交时
	   $(".alertDiv-add").submit(function(){
		   //choose="add";
		   return isGoods;
	   });
	  
	 //导出
		  $(".btn-export").click(function(){
			  var oper = $("#oper").val();
			 $.ajax({
				   url:'PurchaseServlet?flag=export&oper='+oper,
				   type:"post",
				   async:true,
				   dataType:'json',
			       data:{"d":"findAll"},
				   success:function(data){
					   var str = "编号,商品名,数量,日期,请款金额,状态,发票地址,用途\n";
					   $.each(data, function(i,value) {
							 with(value){
							str+=id+","+good.name+","+num+","+purdate+","+price+","+status+","+bills+","+demo;
						    }
						    str+="\n";
						});
					   alert(str);
				  var  uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(str);
					      //通过创建a标签实现
					      var link = document.createElement("a");
					      link.href = uri;
					      //对下载的文件命名
					      var d =new Date();
					      var dt =(d.getYear()+1900)+""+(d.getMonth()+1)+(d.getDate()<10?"0"+d.getDate():d.getDate())+d.getHours()+d.getMinutes()+d.getSeconds();
					      if($("#oper").val()=='inbound')
					       link.download =  "入库单"+dt+".xls";
					      else if($("#oper").val()=='state')
					       link.download =  "结算单"+dt+".xls"; 
					      else if($("#oper").val()=='proof')
						       link.download =  "采购凭证"+dt+".xls";
					      else if($("#oper").val()=='bills')
						       link.download =  "请款单"+dt+".xls";
					      else if($("#oper").val()=='offer')
						       link.download =  "报价单"+dt+".xls";
					      else if($("#oper").val()=='plan')
						       link.download =  "采购计划单"+dt+".xls";
					      document.body.appendChild(link);
					      link.click();
					      document.body.removeChild(link);  
				   }
			      
		  }); }); 
	 //--等会写--当选完图片后  发票div填入图片
	  
	  
	/*  //单击 结算按钮
	  $(".btn-state").click(function(){
		  var checks = $("input[name=check]");
			var str="";
			 $.each(checks,function(index,value){
				 if(value.checked && index!=0){
					str+=$(this).attr('id').substr(1)+',';
				 }
			});
			 $.ajax({
				 url:"PurchaseServlet?flag=checkIsState",
	  			    type:"post",
	  			    async:true,
	  			    data:{"ids":str},
	  			    success:function(data){
	  			    	 if(data=='true')
	  			    		 alert("请勿选择已经审核过的订单！");
	  			    	 else
	  			    		 location.href='PurchaseServlet?flag=stateSome&ids='+str+'&oper='+$("#oper").val();
	  			    		 
	  			    }
				 
			 });
			 
			 
	  });*/
	  
	 $("#inbound-form").submit(function(){
		    var rios = $("input[type=radio]");
			var rio="";
			 $.each(rios,function(index,value){
				 if(value.checked ){
					rio=$(this).val();
				 }
			});
			  if($("#mulnum").html()>0&&rio=='零剩余'){
				  $("#bmess").html("请选择剩余商品去向");
				  return false;
			  }else{
				  $("#bmess").html("");
					  return true;
			  }
	  });
	  
	  //当入库单提交时 验证原因 与剩余数 是否相符
	 /* $("#subm").click(function(){
		
	  });*/
	  //入库单 填入数量 失去焦点后 将剩余数量写入
	   $(".alertCheck .alertCheck-add #innum").blur(function(){
		   //验证输入是否为数字
			  var innum = $("#innum").val();
			  var pattern = /^[1-9]*[1-9][0-9]*$/;//0和非0开头的数字
		
			 
			  if(pattern.test(innum)){
			 if(parseInt($(this).val())>parseInt($("#purnum").html())){
					   $("#bmess").html("入库数量不能大于采购数量");
				   }else{
					   $("#bmess").html("");
					   $("#mulnum").html($("#purnum").html()-$(this).val());
				   }
				
			  }else{
				  $("#bmess").html("输入的数量不合法");  
			  }
		  
	   });
	  
	  //单击采购状态单选按钮 选择采购成功或失败
	  $(".alertDiv-add input[name=st]").click(function(){
		  if($(this).val()=='yes'){
			  $(".alertDiv-add").css("height","570px");
			  $(".alertDiv-add  #upbill_box").show();
		  }else{
			  $(".alertDiv-add").css("height","470px");
			  $(".alertDiv-add  #upbill_box").hide();
		  }
	  });
	  
	  //删除多个选取的复选框
		$(".btn-delete").click(function(){
			var checks = $("input[name=check]");
			var str="";
			 $.each(checks,function(index,value){
				 if(value.checked && index!=0){
					str+=$(this).attr('id').substr(1)+',';
				 }
			});
			 location.href='PurchaseServlet?flag=deledeSome&ids='+str+'&oper='+$("#oper").val();
		});
	
		
		
	//单击审查按钮
		$(".btn-check").click(function(){
			if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
				var res =confirm("是否审核通过？");
				var id=$(this).attr("id");
	    		window.location.href='PurchaseServlet?flag=check&res='+res+'&id='+id+'&oper='+$("#oper").val();
	    	}
			if($(this).parent().prev("td").html()=='请款中-已提交报价单'){
				var res =confirm("是否确定此请款？");
				var id=$(this).attr("id");
	    		window.location.href='PurchaseServlet?flag=giveMoney&res='+res+'&id='+id+'&oper='+$("#oper").val();
	    	}
			if($(this).parent().prev("td").html()=='已入库-未结算'){
				var res =confirm("结算金额为"+$(this).parent().prev("td").prev("td").prev("td").html()+"是否确定此结算？");
				var id=$(this).attr("id");
				window.location.href='PurchaseServlet?flag=state&res='+res+'&id='+id+'&oper='+$("#oper").val();
			}
		});
	  
		//编辑
	/*	 $(".btn-edit").click(function(){
			// alert(1)
		    	choose='edit';
		    	if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
		    		window.location.href="PurchaseServlet?flag=yupdate&id="+$(this).attr('id');
		    	}
		});*/
	  
	  //当鼠标移入 显示详细信息
		$("table tr").mouseover(function(){
			var y=$(this).offset().top;
	        var x=$(this).offset().left;
	        $(".tablebox .see").css("top",y-120+"px").css("left",x+100+"px");
	        $(".tablebox .see").fadeIn(500);
			 $(".see ul").children("li").remove();
			 var id=$(this).children().eq(1).html();
				if($(this).index()==0){
					$(".tablebox .see").css("display","none");
					$(".see").hide();
				}else{
					 $.ajax({
			        	    url:"PurchaseServlet?flag=see",
			  			    type:"post",
			  			    async:true,
			  			    dataType:'json',
			  			    data:{"id":isNaN(Number(id))?0:id},
			  			    success:function(data){
			  			    	 $(".see ul").children().empty();
			  			    	if(data=='nodata'){
			  			    		 $(".see").hide();
			  			    	}else{
			  			    		var str =JSON.stringify(data);
				  			    	var pur=eval("("+str+")");
				  			    	
				  			    	$(".see ul").append("<li><div>商品名称：</div>"+pur.good.name+"</li>" +
				  			    			"<li><div>供应商：</div>"+pur.good.sup.name+"</li>" +
				  			    			"<li><div>单价：</div>"+pur.good.price+"</li>" +
				  			    			"<li><div>数量：</div>"+pur.num+"</li>" +
				  			    			"<li><div>请款金额：</div>"+pur.price+"</li>" +
				  			    			    "<li><div>日期：</div>"+pur.purdate+"</li>" +
				  			    			    "<li><div>用途：</div>"+pur.demo+"</li>" +
				  			    			    "<li><div>状态：</div>"+pur.status+"</li>" +
				  			    			    "<li style='border-bottom:none;'><div>发票：</div>"+pur.bills+"</li>");

			  			    	}
			  			    }
	       
	        });}
		}).mouseout(function(){
			$(".see ul").children("li").remove();
			 $(".tablebox .see").hide();
		});
	  //智能搜索商品
	$(".alertDiv-add input[name=goods]").keyup(function(){
		 $(".alertDiv-add #searchbox").show();
		   $(".alertDiv-add #searchbox ul").children("li").remove();
		   $.ajax({
			   url:'PurchaseServlet?flag=findByGoodsName',
			   type:"post",
			   async:true,
			   dataType:'json',
			   data:{"name":$(this).val()},
			   success:function(data){
						  var appstr="";
						  var str =JSON.stringify(data);
					       var pur=eval("("+str+")");
					       $.each(pur,function(i,n){
					    	   appstr+="<li>"+n.name+"</li>";
						    });
					       $(".alertDiv-add #searchbox ul").append(appstr);
					       //当单击提示的信息时 填入文本框
						   $(".alertDiv-add #searchbox ul li").click(function(){
							   $(".alertDiv-add input[name=goods]").val($(this).html());
							   $(".alertDiv-add #searchbox").hide();
						   });
				
			    }
		   });
	});
	
	 //当填写完商品时 验证是否为存在的符合条件的商品 
	   $(".alertDiv-add input[name=goods]").blur(function(){
		  $(".alertDiv-add #searchbox").hide();
		 $.ajax({
			    url:"PurchaseServlet?flag=findByFullGoodsName",
			    type:"post",
			    dataType:"json",
			    data:{"name":$(this).val()},
			    success:function(data){
			    	if(data==null){
			    		$(".alertDiv-add #mess").html("暂无此商品或此商品还未审核");
			    		isGoods=false;
			    	}else{
			    		$(".alertDiv-add #mess").html("");
			    		$(".alertDiv-add #goods_sup").html(data.sup.name);
			    		$(".alertDiv-add #goods_pice").html(data.price);
			    		isGoods=true;
			    	}
			    }
			    });
	   });
	   
	   

});
