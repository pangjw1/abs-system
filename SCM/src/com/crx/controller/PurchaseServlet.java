package com.crx.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.crx.dao.GoodsDAO;
import com.crx.dao.InBoundDAO;
import com.crx.dao.LossDAO;
import com.crx.dao.PurchaseDAO;
import com.crx.model.Goods;
import com.crx.model.Inbound;
import com.crx.model.Loss;
import com.crx.model.Need;
import com.crx.model.Page;
import com.crx.model.Purchase;
import com.crx.model.User;
import com.crx.utils.Pager;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
@WebServlet("/PurchaseServlet")
@MultipartConfig
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PurchaseDAO pdao = new PurchaseDAO();
	private InBoundDAO idao =new InBoundDAO();
	private GoodsDAO gdao = new GoodsDAO();
	private LossDAO ldao =new LossDAO();
	private String oper="";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String flag=request.getParameter("flag");
	   oper=request.getParameter("oper");
	   request.setAttribute("oper", oper);
	 
	   switch (flag) {
	      case "findAll":
		    findAll(request,response);
		  break;
	      case "add":
			   add(request,response);
			  break;
	      case "deleteOne":
	    	deleteOne(request,response);
			  break;
	      case "yupdate":
	    	  yupdate(request,response);
			  break;
	      case "update":
	    	  update(request,response);
			  break;
	      case "deledeSome":
	    	  deledeSome(request,response);
			  break; 
	      case "findByName":
	    	  findByName(request,response);
			  break; 
	      case "findByGoodsName":
	    	  findByGoodsName(request,response);
			  break; 
	      case "findByFullGoodsName":
	    	  findByFullGoodsName(request,response);
			  break; 
	      case "see":
	    	  see(request,response);
			  break;  
	     case "check":
	    	  check(request,response);
			  break;
	     case "ywriteOffer":
	    	 ywriteOffer(request,response);
	    	 break;
	     case "writeOffer":
	    	 writeOffer(request,response);
	    	 break;
	     case "giveMoney":
	    	 giveMoney(request,response);
	    	 break;
	     case "ybill":
	    	 ybill(request,response);
	    	 break;
	     case "bill":
	    	 bill(request,response);
	    	 break;
	     case "yinbound":
	    	 yinbound(request,response);
	    	 break;
	     case "inbound":
	    	 inbound(request,response);
	    	 break;
	     case "resetinbound":
	    	 resetinbound(request,response);
	    	 break;
	     /*case "stateSome":
	    	 stateSome(request,response);
	    	 break;*/
	     case "gfind":
	    	 gfind(request,response);
	    	 break;
	     case "state":
	    	 state(request,response);
	    	 break;
	     case "export":
	    	 export(request,response);
	    	 break;
	   /*  case "checkIsState":
	    	 checkIsState(request,response);
	    	 break;*/
	   
	default:
		break;
	}
	   
	  
	}





	private void export(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 String d = request.getParameter("d");
		 String operate="";
		 if(d.equals("findAll")){
			  switch (oper) {
				case "plan"://采购计划管理
					operate="'未审核','审核未通过','请款中-未提交报价单'";
					break;
				case "offer"://报价单提交
					operate="'请款中-已提交报价单','请款中-未提交报价单'";
					break;
				case "money"://请款管理
					operate="'请款中-已提交报价单','请款失败','请款成功-开始采购'";
					break;
				case "bill"://采购发票管理
					operate="'请款成功-开始采购','采购失败','已采购完成-等待入库'";
					break;
				case "proof"://采购凭证管理
					operate="'已采购完成-等待入库','已入库-未结算','已结算'";
					break;
				case "state"://结算单生成
					operate="'已入库-未结算','已结算'";
					break;
				case "inbound"://入库
					operate="'已采购完成-等待入库','已入库-未结算','已结算'";
					break;
				default:
					break;
				}
			 
			  List<Purchase> purs =pdao.findAllByCondition(operate);
			  JSONArray jsarr = JSONArray.fromObject(purs);
				 try {
					response.getWriter().print(jsarr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
	}

	private void state(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		String res =request.getParameter("res");
		System.out.println(id);
		Purchase p= pdao.findById(Integer.parseInt(id));
		if(res.equals("true")){
		    p.setStatus("已结算");
		    pdao.updateStatus(p);
		}
		findAll(request, response);
	}

	/*private void checkIsState(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String ids = request.getParameter("ids");
		 if(ids!=null&&!ids.equals("")){
			  ids=ids.substring(0,ids.length()-1);
		 }
		 String[] arr = ids.split(",");
		 String isState="false";
		 for(String id:arr){
			 Purchase pur = pdao.findById(Integer.parseInt(id));
			 if(pur.getStatus().equals("已结算")){
				 isState="true";
				 break;
			 }
		 }
		 try {
			response.getWriter().print(isState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}*/
/*	private void stateSome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("inininininininini");
		String ids = request.getParameter("ids");
		 if(ids!=null&&!ids.equals("")){
			  ids=ids.substring(0,ids.length()-1);
		 }
		 List<Purchase> ps = new ArrayList<>();
		 String[] arr = ids.split(",");
		 for(String id:arr){
			 Purchase p = pdao.findById(Integer.parseInt(id));
			 ps.add(p);
		 }
		 request.setAttribute("purs", ps);
		 request.setAttribute("opa", "state");
		 findAll(request, response);
	}*/
	private void gfind(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		name=name.replace("'", "");
		String stprice=request.getParameter("stprice");
		stprice=stprice.replace("'", "");
		String endprice=request.getParameter("endprice");
		endprice=endprice.replace("'", "");
		String stdate = request.getParameter("stdate");
		stdate=stdate.replace("'", "");
		String enddate = request.getParameter("enddate");
		enddate=enddate.replace("'", "");
		String minnum = request.getParameter("minnum");
		minnum=minnum.replace("'", "");
		String maxnum = request.getParameter("maxnum");
		maxnum=maxnum.replace("'", "");
		String isbill =request.getParameter("isbill");
		isbill=isbill.replace("'", "");
		String[] stat = request.getParameterValues("stat");
		
		String ids = "";
		String stats = "";
       
		if (!name.equals("") && name != null) {
			List<Goods> gs = gdao.findByName(name);
			for (Goods g : gs) {
				ids += g.getId() + ",";
			}
			ids = ids.substring(0, ids.length() - 1);
		}
		if (stat != null) {
			for (String st : stat) {
				stats += "'" + st + "',";
			}
			stats = stats.substring(0, stats.length() - 1);
		}
	
		List<Purchase> purs =findByPageCondition(request,response,ids,stprice,endprice, stdate, enddate, minnum, maxnum,isbill, stats);//pdao.findByCondition(ids,stprice,endprice, stdate, enddate, minnum, maxnum,isbill, stats);
		request.setAttribute("purlist", purs);
		  try {
			request.getRequestDispatcher("pur_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	private void resetinbound(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		Purchase pur = pdao.findById(id);
		pur.setStatus("已采购完成-等待入库");//改采购状态
		pdao.updateStatus(pur);
		
		Inbound inbound = idao.findByPId(id);
		Loss loss = ldao.findByPId(id);
		//删除入库单
		idao.deleteByPID(id);
		if(inbound!=null){
		//更改商品库存
		gdao.updateStore(pur.getGood().getStore()-inbound.getNum(),pur.getGood().getId());//更改商品库存
		//删除入库表
		idao.delete(inbound.getId());
		}
		//删除损耗表
		if(loss!=null)
		  ldao.delete(loss.getId());
		findAll(request, response);	
	}

	private void inbound(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	int id = Integer.parseInt(request.getParameter("id"));
	Purchase pur = pdao.findById(id);
	
	int innum=Integer.parseInt(request.getParameter("innum"));//入库数量
	String reason=request.getParameter("reason");
	//计算 损耗 或者退回数
	int  outnum = pur.getNum()-innum;
//	更改采购表 状态 
	pur.setStatus("已入库-未结算");
	pdao.updateStatus(pur);
	System.out.println("updateok"+reason);
	String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	Inbound inb = new Inbound(0, pur, now, innum, "");
	idao.insert(inb);//入库
	
	gdao.updateStore(pur.getGood().getStore()+innum,pur.getGood().getId());//更改商品库存
	if(outnum>0){//写入损耗表
		Loss loss = new Loss(0, pur.getGood(), outnum, now, reason, pur);
		ldao.insert(loss);
	}
	findAll(request, response);	
	}

private void yinbound(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Purchase upPur = pdao.findById(Integer.parseInt(id));
	request.setAttribute("upPur", upPur);
	if(upPur.getStatus().equals("已采购完成-等待入库"))
	request.setAttribute("opa", "bound");
	findAll(request, response);
	}

private void bill(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Purchase wPur = pdao.findById(Integer.parseInt(id));
	
	String st = request.getParameter("st");
	if(st.equals("yes")){
		String purdate= request.getParameter("purdate");
        Part part = request.getPart("bills");
        File f = new File("d://SCM//bills");
        if(!f.exists())
        	f.mkdirs();
        String header = part.getHeader("content-disposition");
        String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String p=f.getPath() + File.separator + wPur.getGood().getName()+now+header.substring(header.lastIndexOf("."),header.length()-1); 
        part.write(p);
        wPur.setBills(p); 
        wPur.setPurdate(purdate);
        wPur.setStatus("已采购完成-等待入库");
	}else{
		 wPur.setStatus("采购失败");
	}
	
	
	pdao.update(wPur);
	findAll(request, response);
	}

private void ybill(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Purchase upPur = pdao.findById(Integer.parseInt(id));
	
	
	request.setAttribute("upPur", upPur);
	if(upPur.getStatus().equals("请款成功-开始采购"))
	request.setAttribute("opa", "bill");
	findAll(request, response);
	}

private void giveMoney(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Boolean res=Boolean.parseBoolean(request.getParameter("res"));
	Purchase p= pdao.findById(Integer.parseInt(id));
	p.setStatus(res?"请款成功-开始采购":"请款失败");
	pdao.updateStatus(p);
	findAll(request, response);
}

private void writeOffer(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Purchase wPur = pdao.findById(Integer.parseInt(id));
	double price = Double.parseDouble(request.getParameter("price"));
	wPur.setPrice(price);
	wPur.setStatus("请款中-已提交报价单");
	pdao.update(wPur);
	findAll(request, response);
	}

private void ywriteOffer(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Purchase upPur = pdao.findById(Integer.parseInt(id));
	
	
	request.setAttribute("upPur", upPur);
	if(!upPur.getStatus().equals("请款中-已提交报价单"))
	   request.setAttribute("opa", "offer");
	findAll(request, response);
	}

private void see(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id=request.getParameter("id");
	Purchase pur=null;
	String str="";

	if(Integer.parseInt(id)==0){
		str="nodata";
	}else{
		pur=pdao.findById(Integer.parseInt(id));
		if(pur==null){
				str="nodata";	
			}else{
				str=new Gson().toJson(pur);
			}
	}
	try {
		response.getWriter().print(str);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

private void findByFullGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String name=request.getParameter("name");
    List<Goods> goods =gdao.findByFullGoodsName(name.replace("'", ""));
    
    try {
	if(goods.size()>0)
        response.getWriter().print(new Gson().toJson(goods.get(0)));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

private void findByGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
        List<Goods> goods =gdao.findByGoodsName(name.replace("'", ""));
        
	    String json = new Gson().toJson(goods);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void check(HttpServletRequest request, HttpServletResponse response) {
          // TODO Auto-generated method stub
		String id = request.getParameter("id");
		Boolean res=Boolean.parseBoolean(request.getParameter("res"));
		Purchase p= pdao.findById(Integer.parseInt(id));
		p.setStatus(res?"请款中-未提交报价单":"审核未通过");
		pdao.updateStatus(p);
		findAll(request, response);
	}


	private void findByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		name=name.replaceAll("'", "");
		String oper =request.getParameter("oper");
		
		List<Goods> goods=gdao.findByName(name);
		switch (oper) {
		case "plan"://采购计划管理
			oper ="'未审核','审核未通过','请款中-未提交报价单'";//查询采购计划 ：采购单未审核或审核未通过的
			break;
		case "offer"://报价单提交
			oper ="'请款中-已提交报价单','请款中-未提交报价单'";
			break;
		case "money"://请款管理
			oper ="'请款中-已提交报价单','请款失败','请款成功-开始采购'";/*"请款成功-开始采购":"请款失败"*/
			break;
		case "bill"://采购发票管理
			oper ="'请款成功-开始采购','采购失败','已采购完成-等待入库'";/*'已采购完成==待入库'*/
			break;
		case "proof"://采购凭证管理
			oper ="'已采购完成-等待入库','已入库-未结算','已结算'";
			break;
		case "state"://结算单生成
			oper ="'已入库-未结算','已结算'";
			break;
		case "inbound"://入库
			oper ="'已采购完成-等待入库','已入库-未结算','已结算'";
			break;
		default:
			break;
		}
		String ids="";
		for(Goods g:goods){
			ids+=g.getId()+",";
		}
		ids=ids.length()>0?ids.substring(0, ids.length()-1):ids;
		List<Purchase> purs=findByPage(request, response, ids, oper);//pdao.findByGIdsAndOper(ids.substring(0, ids.length()-1),oper);
		request.setAttribute("purlist", purs);
		  try {
			request.getRequestDispatcher("pur_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		
	}

	private void deledeSome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 String ids = request.getParameter("ids");
		 if(ids!=null&&!ids.equals("")){
			  ids=ids.substring(0,ids.length()-1);
			  pdao.deleteSome(ids);
		 }
		 findAll(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String id = request.getParameter("id");
		int num=Integer.parseInt(request.getParameter("num"));
		String demo =request.getParameter("demo");
	    Purchase pur = pdao.findById(Integer.parseInt(id));
	    pur.setNum(num);
	    pur.setDemo(demo);
	    pdao.update(pur);
	    findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		Purchase upPur = pdao.findById(Integer.parseInt(id));
		request.setAttribute("upPur", upPur);
		if(upPur.getStatus().equals("未审核"))
		   request.setAttribute("opa", "edit");
		findAll(request, response);
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  pdao.delete(Integer.parseInt(request.getParameter("id")));
		  findAll(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println(oper);
		if(oper.equals("")){
			
		}
		String name=request.getParameter("goods");
		int num=Integer.parseInt(request.getParameter("num"));
		String demo=request.getParameter("demo");
	    Goods gid =gdao.findByName(name).get(0);
	    String status="未审核";
	    double price=0.0;
	    String purdate="";
	    String bills="暂未上传";
	    
	    
		Purchase p = new Purchase(0,gid,price,num,purdate,status,bills,demo);
		pdao.insert(p);
		findAll(request, response);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  List<Purchase> purs = null;
		  String operate="";
		  switch (oper) {
		case "plan"://采购计划管理
//			purs =pdao.findAllByCondition("'未审核','审核未通过','请款中-未提交报价单'");//查询采购计划 ：采购单未审核或审核未通过的
			operate="'未审核','审核未通过','请款中-未提交报价单'";
			break;
		case "offer"://报价单提交
//			purs =pdao.findAllByCondition("'请款中-已提交报价单','请款中-未提交报价单'");
			operate="'请款中-已提交报价单','请款中-未提交报价单'";
			break;
		case "money"://请款管理
//			purs =pdao.findAllByCondition("'请款中-已提交报价单','请款失败','请款成功-开始采购'");/*"请款成功-开始采购":"请款失败"*/
			operate="'请款中-已提交报价单','请款失败','请款成功-开始采购'";
			break;
		case "bill"://采购发票管理
//			purs =pdao.findAllByCondition("'请款成功-开始采购','采购失败','已采购完成-等待入库'");/*'已采购完成==待入库'*/
			operate="'请款成功-开始采购','采购失败','已采购完成-等待入库'";
			break;
		case "proof"://采购凭证管理
//			purs =pdao.findAllByCondition("'已采购完成-等待入库','已入库-未结算','已结算'");
			operate="'已采购完成-等待入库','已入库-未结算','已结算'";
			break;
		case "state"://结算单生成
//			purs =pdao.findAllByCondition("'已入库-未结算','已结算'");
			operate="'已入库-未结算','已结算'";
			break;
		case "inbound"://入库
//			purs =pdao.findAllByCondition("'已采购完成-等待入库','已入库-未结算','已结算'");
			operate="'已采购完成-等待入库','已入库-未结算','已结算'";
			break;
		default:
			break;
		}
		  purs=findByPage(request, response, null, operate);
			if(oper.equals("state")){
				Collections.sort(purs, new Comparator<Purchase>() {

					@Override
					public int compare(Purchase o1, Purchase o2) {
						// TODO Auto-generated method stub
						return o1.getGood().getSup().getName().compareTo(o2.getGood().getSup().getName());
					}
				});
			}
		  request.setAttribute("purlist", purs);
		  request.setAttribute("oper", oper);
		  try {
			request.getRequestDispatcher("pur_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
	
	
	private List<Purchase> findByPage(HttpServletRequest request, HttpServletResponse response,String ids,String oper) {
		int size=9;
		int rows=0;
		String url="";
		System.out.println("OPER:"+oper);
		rows=pdao.findCount(ids,oper);
		if(ids==null ||ids.equals("")){
			url="PurchaseServlet?flag=findAll&oper="+oper;
		}else{
			url="PurchaseServlet?flag=findByName&ids="+ids+"&oper="+oper;
		}
		
		int cpage=request.getParameter("pager")==null?1:Integer.parseInt(request.getParameter("pager"));
		String pager=Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page=new Page((cpage-1)*size, size, "logtime", "desc");
		List<Purchase> list=pdao.findByPage(page,ids,oper);
		return list;
	}
	
	private List<Purchase> findByPageCondition(HttpServletRequest request, HttpServletResponse response,String ids,String stprice,String endprice, String stdate,String enddate,String minnum,String maxnum,String isbill,String stats){
		int size=9;
		int rows=0;
		String url="";
		rows=pdao.findCountByCondition(ids,stprice,endprice, stdate, enddate, minnum, maxnum,isbill, stats);
		url="PurchaseServlet?flag=gfind";
		
		int cpage=request.getParameter("pager")==null?1:Integer.parseInt(request.getParameter("pager"));
		String pager=Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page=new Page((cpage-1)*size, size, "", "");
		List<Purchase> list=pdao.findByCondition(ids,stprice,endprice, stdate, enddate, minnum, maxnum,isbill, stats,page);
		return list;
	}
}