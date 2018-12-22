/**
 * 
 */
  $(function(){
	   var choose="";//增加或修改
	   var isOK=true;
      //验证供应商是否重复
	   $(".alertDiv input[name=name]").blur(function(){
		
		   $.ajax({
			   url:'SupplierServlet?flag=checkName',
			   type:"post",
 			    async:true,
 			    data:{"name":$(this).val()},
 			    success:function(data){
 			    	if(data=="")
 			    	   isOK=true;
 			    	else
 			    	   isOK=false;
 			    		 $("#wname").html(data);
 			    }
		   });
	   });
		
	   
	   
	   //已经审核的 不能编辑  ：判断sat  是不是disabled  如果是全部弄成disabled
	   var eles =$(".alertCheck > .alertCheck-add>input[type=text],input[type=radio]");
	   if($(".alertCheck > .alertCheck-add>input[name=sat]").attr("disabled")=="disabled"){
		   $.each(eles,function(){
			      $(this).attr("disabled","disabled");
		   });
	   }
	  
	   
	   //当鼠标移入 详细信息层  保持其不动
	/*   $(".tablebox .see").mouseover(function(){
		   $(this).show();
	   });*/
	 //当鼠标移入 显示详细信息
		$("table tr").hover(function(e){
			var y=$(this).offset().top;
	        var x=$(this).offset().left;
	        $(".tablebox .see").css("top",y-120+"px").css("left",x+100+"px");
	        $(".tablebox .see").fadeIn(500);
			 $(".see ul").children("li").remove();
			 var id=$(this).children().eq(1).html();
	        $.ajax({
	        	    url:"SupplierServlet?flag=see",
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
		  			    	var sup=eval("("+str+")");
		  			    	$(".see ul").append("<li><div>名称：</div>"+sup.name+"</li>" +
		  			    			"<li><div>地址：</div>"+sup.address+"</li>" +
		  			    					"<li><div>电话：</div>"+sup.tel+"</li>" +
		  			    			    "<li><div>Email：</div>"+sup.email+"</li>" +
		  			    			    "<li><div>传真：</div>"+sup.fax+"</li>" +
		  			    			    "<li><div>联系人：</div>"+sup.linkman+"</li>" +
		  			    			    "<li><div>联系人电话：</div>"+sup.link_tel+"</li>" +
		  			    			    "<li style='border-bottom:none;'><div>状态：</div>"+sup.status+"</li>");
	  			    	}
	  			    }
	        });
		},function(){
			$(".see ul").children("li").remove();
			 $(".tablebox .see").hide();
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
			 location.href='SupplierServlet?flag=deledeSome&ids='+str+'&isCheck='+$("#isCheck").val();
		});

   });