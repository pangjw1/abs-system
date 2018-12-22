/**
 * 
 */

$(function(){
	var isOK=true;
	var issup=true;
	var isdate=true;
	var isNum=true;
	  $(".alertDiv-add").submit(function(){
		  /* alert("isOK:"+isOK);
		   alert("issup:"+issup);
		   alert("isdate:"+isdate);
		   alert("isNum:"+isNum);*/
		   return isOK&&issup&&isdate&&isNum;
	   });
	//导出
	  $(".btn-export").click(function(){
		
	 $.ajax({
			   url:'LossServlet?flag=export',
			   type:"post",
			   async:true,
			   dataType:'json',
		       data:{"d":"findAll"},
			   success:function(data){
				   var str = "编号,商品名,损耗数量,损耗日期,损耗原因\n";
				   $.each(data, function(i,value) {
						 with(value){
						str+=id+","+goods.name+","+num+","+lossdate+","+reason;
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
				      link.download =  "盘损"+dt+".xls";
				      document.body.appendChild(link);
				      link.click();
				      document.body.removeChild(link);  
			   }
		      
	  }); });
	//损耗数量不能大于现有库存
	  $("#lossnum").blur(function(){
		  $("#wnum").html("");
		 $.ajax({
			   url:'LossServlet?flag=findNowStore',
			   type:"post",
			   async:true,
			   dataType:'json',
			   data:{"goods":$("#goods").val()},
			   success:function(data){
			       if(data==null){
			    	   $("sup_name").html("暂无此商品或暂未通过审核");
			    	   isOK=false;
			       }else{
			    	   $("sup_name").html("");
			    	   isOK=true;
			    	   if(parseInt($("#lossnum").val())>parseInt(data.store)){
						    $("#wnum").html("损耗数量不能大于库存数量");
						    isNum=false;
						}else{
							 if(parseInt($("#lossnum").val())<=0){
								 $("#wnum").html("损耗数量不能小于或等于零");
								 isNum=false;
							}else{
								 $("#wnum").html("");
								 isNum=true;
							} 	
						}
                          
				    }
			       }
						
	});
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
		 
		 window.location.href='LossServlet?flag=deleteSome&ids='+str; 
		
	});
	
	
	
	   //当添加表单提交时  验证日期是否再当前日期之前
	 $(".alertDiv-add #lossdate").blur(function(){
		 $("#mess").html("");
		 var cdate =new Date($(this).val());
		   var nowdate = new Date();
		   if(nowdate<cdate){
			   $("#mess").html("损耗日期不能超过当前时间");
			   isdate=false;
		   }else{
			   $("#mess").html("");
			   isdate=true;
		   }
	 });
    	
	 
	 //智能搜索
	$("input[name=goods]").keyup(function(){
		   $(".alertDiv-add #searchbox").show();
		   $(".alertDiv-add #searchbox ul").children("li").remove();
		   $.ajax({
			   url:'LossServlet?flag=findGoodsName',
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
	
	
	    //当失去焦点时 写入供应商
	   $("input[name=goods]").blur(function(){
		   $(".alertDiv-add #searchbox").hide();
		   $(".alertDiv-add #sup_name").html("");
		   $.ajax({
			    url:'LossServlet?flag=findFullGoodsName',
			    type:"post",
			    async:true,
			    data:{"goods":$("input[name=goods]").val()},
			    success:function(data){
			  
			    	if(data=='nodata'){
			    		 $(".alertDiv-add #searchbox").hide();
			    		 $(".alertDiv-add #sup_name").html("暂无此商品或该商品暂未通过审核");
				    	   issup=false;
			    	}else{
					       $(".alertDiv-add #sup_name").html(data);
					       $(".alertDiv-add #mess").html("");
				    	   issup=true;
			    	}
			    	  
				      }});
	   });

	 
}); 
	 
