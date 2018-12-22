/**
 * 
 */

$(function(){
//	 var choose="";//增加或修改
//	/* $(".alertDiv-add #searchbox ul li").click(function(){
//  	   alert($(this).html());
//		   $("input[name=sup]").val($(this).html());
//		   //$(".alertDiv-add #searchbox").hide();
//	   });*/
//	 
	 var isName=false;
	 var isPrice=false;
//	 var isSup=true;
	 //增加或修改时  验证商品名称是否重复
	 $(".alertDiv input[name=goods]").focus(function(){
		  $(".alertDiv-add #mess_name").html("");
	 });
	 $(".alertDiv input[name=minprice]").focus(function(){
		 $(".alertDiv-add #mess_price").html("");
	 });
	 $(".alertDiv input[name=maxprice]").focus(function(){
		 $(".alertDiv-add #mess_price").html("");
	 });

 
//		//编辑
//	 $(".btn-edit").click(function(){
//	    	choose='edit';
//	    	if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
//	    		window.location.href="GoodsServlet?flag=yupdate&id="+$(this).attr('id')
//	    	}
//	});

   //智能搜索
	$("input[name=goods]").keyup(function(){
		   $(".alertDiv-add #searchbox").show();
		   $(".alertDiv-add #searchbox ul").children("li").remove();
		   $.ajax({
			   url:'AreaServlet?flag=findGoodsName',
			   type:"post",
			   async:true,
			   dataType:'json',
			   data:{"goods":$(this).val()},
			   success:function(data){
						  var appstr="";
						  var str =JSON.stringify(data);
					       var sup=eval("("+str+")");
					       if(sup=="" || sup==null){
					    	   $(".alertDiv-add #searchbox").hide();
					       }else{
					    	   $.each(sup,function(i,n){
						    	   appstr+="<li class='l'>"+n.name+"</li>";
							    });
						       $(".alertDiv-add #searchbox ul").append(appstr);
						       $(".alertDiv-add #searchbox ul").mouseover(function(){
						    	   $(this).show();
						       });
						
						       //当单击提示的信息时 填入文本框
						       $(".l").click(function(){
						      	   //alert($(this).html());
						    		   $("input[name=goods]").val($(this).html());
						    		   $(".alertDiv-add #searchbox").hide();
						    	   });
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
			location.href='AreaServlet?flag=deledeSome&ids='+str; 
		});
		
		
			   //当添加表单提交时
		   $(".alertDiv-add").submit(function(){
			   //alert(1);
			   //alert($("#opa").val());
			   if($("#opa").val()!='edit'){
			   $.ajax({
					 url:"AreaServlet?flag=checkName",
					    type:"post",
					    async:true,
					    data:{"goods":$("input[name=goods]").val(),"city":$("#aname").val()},
					    success:function(data){
					    	if(data=="")
					    		isName=true;
			 			  $(".alertDiv-add #mess_name").html(data);
					    }
				 });
			   }else{
				   $.ajax({
						 url:"AreaServlet?flag=checkNameUpdate",
						    type:"post",
						    async:true,
						    data:{"goods":$("input[name=goods]").val(),"city":$("#aname").val(),"id":$("#aid").val()},
						    success:function(data){
						    	if(data=="")
						    		isName=true;
				 			  $(".alertDiv-add #mess_name").html(data);
						    }
					 });
			   }
			   var min = $("input[name=minprice]").val();
			   var max = $("input[name=maxprice]").val();
			   if(min>=max){
				   $(".alertDiv-add #mess_price").html("最低价格不允许超过或等于最高价格"); 
			   }else{
				   isPrice=true;
			   }
			   
			   
			   return isName&&isPrice;
		   });
		   
		 //导出
			  $(".btn-export").click(function(){
				
			 $.ajax({
					   url:'AreaServlet?flag=export',
					   type:"post",
					   async:true,
					   dataType:'json',
				       data:{"d":"findAll"},
					   success:function(data){
						   var str = "编号,商品名,区域,最低价格,最高价格,平均价格\n";
						   $.each(data, function(i,value) {
								 with(value){
								str+=id+","+goods.name+","+name+","+minprice+","+maxprice+","+(minprice+maxprice)/2;
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
						      link.download =  "区域价格"+dt+".xls";
						      document.body.appendChild(link);
						      link.click();
						      document.body.removeChild(link);  
					   }
				      
			  }); });
});


	 
	 
	 
	 
