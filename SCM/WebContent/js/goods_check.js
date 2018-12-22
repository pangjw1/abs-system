/**
 * 
 */

$(function(){
	 var choose="";//增加或修改
	/* $(".alertDiv-add #searchbox ul li").click(function(){
  	   alert($(this).html());
		   $("input[name=sup]").val($(this).html());
		   //$(".alertDiv-add #searchbox").hide();
	   });*/
	 
	 var isName=true;
	 var isSup=true;
	 //增加或修改时  验证商品名称是否重复
	 $(".alertDiv input[name=name]").blur(function(){
		 $.ajax({
			 url:"GoodsServlet?flag=checkName",
			    type:"post",
			    async:true,
			    data:{"name":$(this).val()},
			    success:function(data){
			    	if(data=="")
	 			    	   isOK=true;
	 			    	else
	 			    	   isOK=false;
	 			  $(".alertDiv-add #mess_name").html(data);
			    }
		 });
	 });
	
	//当鼠标移入 显示详细信息
		$("table tr").mouseover(function(){
			var y=$(this).offset().top;
	        var x=$(this).offset().left;
	        $(".tablebox .see").css("top",y-120+"px").css("left",x+100+"px");
	        $(".tablebox .see").fadeIn(500);
			 $(".see ul").children("li").remove();
			 var id=$(this).children().eq(1).html();
				if($(this).index()==0)
					$(".tablebox .see").css("display","none");
	        $.ajax({
	        	    url:"GoodsServlet?flag=see",
	  			    type:"post",
	  			    async:true,
	  			    dataType:'json',
	  			    data:{"id":isNaN(Number(id))?0:id},
	  			    success:function(data){
	  			    	 $(".see ul").children().empty();
	  			    	if(data=='nodata'){
	  			    		 $(".see ul").hide();
	  			    	}else{
	  			    		var str =JSON.stringify(data);
		  			    	var g=eval("("+str+")");
		  			    	$(".see ul").append("<li><div>名称：</div>"+g.name+"</li>" +
		  			    			"<li><div>产地：</div>"+g.address+"</li>" +
		  			    				"<li><div>规格：</div>"+g.size+"</li>" +
		  			    			    "<li><div>批号：</div>"+g.batch+"</li>" +
		  			    			    "<li><div>价格：</div>"+g.price+"</li>" +
		  			    			    "<li><div>库存：</div>"+g.store+"</li>" +
		  			    			    "<li><div>供应商：</div>"+g.sup.name+"</li>" +
		  			    			    "<li><div>状态：</div>"+g.status+"</li>" +
		  			    			    "<li style='border-bottom:none;'><div>描述：</div>"+g.describe+"</li>");
//		  			    	 $(".see").mouseover(function(){
//		  			  		 $(this).show();
//		  			  	 });
	  			    	}
	  			    }
	        });
		}).mouseout(function(){
			$(".see ul").children("li").remove();
			 $(".tablebox .see").hide();
		});
	 
		//单击审查按钮
		$(".btn-check").click(function(){
			if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
				var res =confirm("是否审核通过？");
				var id=$(this).attr("id");
	    		window.location.href='GoodsServlet?flag=check&res='+res+'&id='+id;
	    	}
		});
	 
		//编辑
	 $(".btn-edit").click(function(){
	    	choose='edit';
	    	if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
	    		window.location.href="GoodsServlet?flag=yupdate&id="+$(this).attr('id')
	    	}
	});
		 
		
	//当失去焦点时 验证是否为存在的符合条件的供应商
	   $("input[name=sup]").blur(function(){
		   $(".alertDiv-add #searchbox").hide();
		   $.ajax({
			    url:'GoodsServlet?flag=findSupFullNameBlur',
			    type:"post",
			    async:true,
			    datatype:"txt",
			    data:{"name":$(this).val()},
			    success:function(data){
			    	if(data=='nodata'){
			    		// $(".alertDiv-add #searchbox").hide();
			    		 $(".alertDiv-add #mess_sup").html("暂无此供应商或该供应商未通过审核");
				    	   isSup=false;
			    	}else{
			    		 var str =JSON.stringify(data);
					       var sup=eval("("+str+")");
					       $(".alertDiv-add #mess_sup").html("");
				    	   isSup=true;
			    	}
			    	  
				      }});
	   });
	   $(".l").click(function(){
    	   alert(1);
       });
	 //智能搜索
	$("input[name=sup]").keyup(function(){
		   $(".alertDiv-add #searchbox").show();
		   $(".alertDiv-add #searchbox ul").children("li").remove();
		   $.ajax({
			   url:'GoodsServlet?flag=findSupName',
			   type:"post",
			   async:true,
			   dataType:'json',
			   data:{"name":$(this).val()},
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
						   /*   $(".l").click(function(){
						    	   alert(1);
						       });*/
						       //当单击提示的信息时 填入文本框
						     /*  $(".l").click(function(){
						      	   alert($(this).html());
						    		   $("input[name=sup]").val($(this).html());
						    		   //$(".alertDiv-add #searchbox").hide();
						    	   });*/
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
			$(".findbox").attr('action','GoodsServlet?flag=deledeSome&ids='+str); 
			$(".findbox").submit();
		});
		
		
			   //当添加表单提交时
		   $(".alertDiv-add").submit(function(){
			   //choose="add";
			   return isOK;
		   });
});


	 
	 
	 
	 
