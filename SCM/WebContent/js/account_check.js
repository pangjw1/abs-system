$(function(){
$(".findbox").submit(function(){
	var st=new Date($("#startDate").val());
	var en=new Date($("#endDate").val());
	var now =new Date();
	if(st>en){
		$("#mess").html("开始时间不能大于结束时间");
		return false;
	}
   if(st>now){
	   $("#mess").html("开始时间不能在当前时间之后");
		return false;
   }
   if(en>now){
	   $("#mess").html("结束时间不能在当前时间之后");
		return false;
   }
   return true;
});

});