/**
 * 
 */
  $(function(){
	   
	   var isdateok=true;
	   var isgoodsok=true;
	   var isnum=true;
	 /*  //单击出现查询条件div
	   $("#showCon").click(function(){
		    if($(this).html()=="V"){
		    	$(this).html("^");
		    	$(".findbox").hide(500);
		    	$(".gfindbox").slideDown(500);

		    } else{
		    	$(this).html("V");
		    	$(".findbox").show(500);
		    	$(".gfindbox").slideUp(500);
		    }
	   });*/
	   
	   $(".gfindbox").submit(function(){
		   var start = new Date($("#stdate").val());
		   var end = new Date($("#enddate").val());
		   if(start>end){
			   alert("开始时间不能大于结束时间");
			   return false;
		   }else{
			   return true;
		   }
		   });
	   
	 //当添加表单提交时
	   $(".alertDiv-add").submit(function(){
		   return isdateok&&isgoodsok&&isnum;
	   });
	   
	   //判断数量不能为负数
	   $("#need_num").blur(function(){
		   if($(this).val()<=0){
			   $("#mess_num").html("需求数量不能小于或者等于零");
			   isnum=false;
		   }else{
			   $("#mess_num").html("");
			   isnum=true;
		   }
	   });
	   
	   //当选择截止日期之后 验证日期是否在当前日期之后
	   $(".alertDiv-add input[name=stopdate]").blur(function(){
		   var cdate =new Date($(this).val());
		   var nowdate = new Date();
		   if(nowdate>cdate){
			   $("#mess_date").html("截至日期不能在当前日期之前");
			   isdateok=false;
		   }else{
			   $("#mess_date").html("");
			   isdateok=true;
		   }
		   
	   });
	   
	   
	   //只能搜  当输入商品时  触发keyup
	   $(".alertDiv-add input[name=goods]").keyup(function(){
		   $(".alertDiv-add #searchbox").show();
		   $(".alertDiv-add #searchbox ul").children("li").remove();
		   $.ajax({
			   url:'NeedServlet?flag=findByGoodsName',
			   type:"post",
			   async:true,
			   dataType:'json',
			   data:{"name":$(this).val()},
			   success:function(data){
				  
				   var appstr="";
				  var str =JSON.stringify(data);
			       var go=eval("("+str+")");
			       $.each(go,function(i,n){
			    	   appstr+="<li>"+n.name+"</li>";
				    });
			       $(".alertDiv-add #searchbox ul").append(appstr);
			       //当单击提示的信息时 填入文本框
				   $(".alertDiv-add #searchbox ul li").click(function(){
					   alert(1)
					   $(".alertDiv-add input[name=goods]").val($(this).html());
					   $(".alertDiv-add #searchbox").hide();
				   });
			    }
		   });
	   });
	   
	  
	  //当填写完商品时 验证是否为存在的商品 ：已经审核通过的商品才可以
	   $(".alertDiv-add input[name=goods]").blur(function(){
		   $(".alertDiv-add #searchbox").hide();
		   $.ajax({
			    url:"NeedServlet?flag=findByFullGoodsName",
			    type:"post",
			    async:true,
			    data:{"name":$(this).val()},
			    success:function(data){
			    	$("#mess_name").html(data);
			    	if(data!="")
			    		isgoodsok=false;
			    	else
			    		isgoodsok=true;
			    }
			    });
	   });
	   
	   
	 /*   $(".btn-edit").click(function(){
	    	choose='edit';
	    	if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
	    		window.location.href="NeedServlet?flag=yupdate&id="+$(this).attr('id');
	    	}
	   });*/
	   

	    
	    //删除多个选取的复选框
		$(".btn-delete").click(function(){
			var checks = $("input[name=check]");
			var str="";
			 $.each(checks,function(index,value){
				 if(value.checked && index!=0){
					str+=$(this).attr('id').substr(1)+',';
				 }
			});
			 location.href='NeedServlet?flag=deledeSome&ids='+str+'&isCheck='+$("#isCheck").val();
		});
		
		//单击审查按钮
		$(".btn-check").click(function(){
			if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
				var res =confirm("是否审核通过？");
				var id=$(this).attr("id");
	    		window.location.href='NeedServlet?flag=check&res='+res+'&id='+id+'&isCheck=true';
	    	}
		});
		//导出
		  $(".btn-export").click(function(){
			
		 $.ajax({
				   url:'NeedServlet?flag=export',
				   type:"post",
				   async:true,
				   dataType:'json',
			       data:{"d":"findAll"},
				   success:function(data){
					   var str = "编号,商品名,需求数量,截至日期,状态\n";
					   $.each(data, function(i,value) {
							 with(value){
							str+=id+","+goods.name+","+num+","+stopdate+","+status;
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
					      link.download =  "需求表"+dt+".xls";
					      document.body.appendChild(link);
					      link.click();
					      document.body.removeChild(link);  
				   }
			      
		  }); });
	 
   });