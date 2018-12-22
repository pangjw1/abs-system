/**
 * 
 */

$(function(){
	var nisOK=true;
	var disOK=true;
	var sisOK=true;
	
	//导出
	  $(".btn-export").click(function(){
		
		 $.ajax({
			   url:'SaleServlet?flag=export',
			   type:"post",
			   async:true,
			   dataType:'json',
		       data:{"d":"findAll"},
			   success:function(data){
				   var str = "编号,商品名,销售数量,销售日期,销售价格\n";
				   $.each(data, function(i,value) {
						 with(value){
						str+=id+","+goods.name+","+num+","+outdate+","+price;
					    }
					    str+="\n";
					});
			  var  uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(str);
				      //通过创建a标签实现
				      var link = document.createElement("a");
				      link.href = uri;
				      //对下载的文件命名
				      var d =new Date();
				      var dt =(d.getYear()+1900)+""+(d.getMonth()+1)+(d.getDate()<10?"0"+d.getDate():d.getDate())+d.getHours()+d.getMinutes()+d.getSeconds();
				      link.download =  "销售"+dt+".xls";
				      document.body.appendChild(link);
				      link.click();
				      document.body.removeChild(link);  
			   }
		      
	  }); });
	
	//删除多个选取的复选框
	$(".btn-delete").click(function(){
		var checks = $("input[name=check]");
		var str="";
		 $.each(checks,function(index,value){
			 if(value.checked && index!=0){
				str+=$(this).attr('id').substr(1)+',';
			 }
		});
		 
		 window.location.href='SaleServlet?flag=deleteSome&ids='+str; 
		
	});
	//验证销售数量不能大于现有库存
	$("#sale_num").blur(function(){
			 var nowstore =$("#nowstore").html();
			 var salenum=$("#sale_num").val();
			 if(parseInt(salenum)>parseInt(nowstore)){
				 $("#nmess").html("销售数量不能大于库存");
				 nisOK=false;
			 }else{
				 if(parseInt($("#sale_num").val())<=0){
					 $("#nmess").html("销售数量不能小于或等于零");
					 nisOK=false;
				 }else{
					 $("#nmess").html("");
					 nisOK=true;
				 }
			 } 
	});
	
	  //当添加表单提交时  验证日期是否再当前日期之前
	 $(".alertDiv-add #outdate").blur(function(){
		 checkDate();
	 });
	 function checkDate(){
		 var cdate =new Date( $(".alertDiv-add #outdate").val());
		   var nowdate = new Date();
		   if(nowdate<cdate){
			   $(".alertDiv-add #mess").html("出库日期不能超过当前时间");
			   disOK=false;
		   }else{
			   $(".alertDiv-add #mess").html("");
			   disOK=true;
		   }
	 }

	 
	 
	 $(".alertDiv-add").submit(function(){
	  // alert("disOK:"+disOK+"nis:"+nisOK+"sosOK:"+sisOK);
		return disOK && nisOK && sisOK;
	   });
	 //智能搜索
	$("input[name=goods]").keyup(function(){
		   $(".alertDiv-add #searchbox").show();
		   $(".alertDiv-add #searchbox ul").children("li").remove();
		   $.ajax({
			   url:'SaleServlet?flag=findGoodsName',
			   type:"post",
			   async:true,
			   dataType:'json',
			   data:{"goods":$(this).val()},
			   success:function(data){
						 var appstr="";
						  var str =JSON.stringify(data);
					       var goods=eval("("+str+")");
					       if(goods=="" || goods==null){
					    	   $(".alertDiv-add #searchbox").hide();
					       }else{
					    	   $.each(data,function(i,n){
						    	   appstr+="<li>"+n.name+"</li>";
							    });
						       $(".alertDiv-add #searchbox ul").append(appstr);
						       $(".alertDiv-add #searchbox ul").mouseover(function(){
						    	   $(this).show();
						       });
					
						       //当单击提示的信息时 填入文本框
						       $(".alertDiv-add #searchbox ul li").click(function(){
						    		   $("input[name=goods]").val($(this).html());
						    		   $("#searchbox").hide();
						    	   });
					       }
			    }
	});
	});
	
	
	//当失去焦点时 写入现有库存
	   $("input[name=goods]").blur(function(){
	
		 $(".alertDiv-add #searchbox").hide();
		   $.ajax({
			    url:'SaleServlet?flag=findFullGoodsName',
			    type:"post",
			    async:true,
			    dataType:"json",
			    data:{"goods":$(this).val()},
			    success:function(data){
			    	 var str =JSON.stringify(data);
				     var goods=eval("("+str+")");
		
				     if(data==''||data==null||goods==null){
			    		 $(".alertDiv-add #searchbox").hide();
			    		 $(".alertDiv-add #gmess").html("暂无此商品或该商品暂未通过审核");
				    	   sisOK=false;
			    	}else{
			    		 $(".alertDiv-add #nowstore").html(goods.store);
					       $(".alertDiv-add #gmess").html("");
				    	   sisOK=true;
			    	}
			    	
				      }});
	   });

	 
}); 
	 
