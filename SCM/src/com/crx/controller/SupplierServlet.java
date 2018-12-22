package com.crx.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crx.dao.AreaDAO;
import com.crx.dao.GoodsDAO;
import com.crx.dao.LossDAO;
import com.crx.dao.NeedDAO;
import com.crx.dao.PurchaseDAO;
import com.crx.dao.SaleDAO;
import com.crx.dao.SupCheckDAO;
import com.crx.dao.SupplierDAO;
import com.crx.model.Goods;
import com.crx.model.Page;
import com.crx.model.SupCheck;
import com.crx.model.Supplier;
import com.crx.model.User;
import com.crx.utils.Pager;
import com.google.gson.Gson;
@WebServlet("/SupplierServlet")
public class SupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SupplierDAO sdao = new SupplierDAO();
	private SupCheckDAO scdao = new SupCheckDAO();
	private GoodsDAO gdao = new GoodsDAO();
//	需求、采购、损耗、销售、地区价格
/*	private NeedDAO ndao = new NeedDAO();
	private PurchaseDAO pdao = new PurchaseDAO();
	private LossDAO ldao = new LossDAO();
	private SaleDAO sadao = new SaleDAO();
	private AreaDAO adao = new AreaDAO();*/
	
	
	private String isCheck="";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String flag=request.getParameter("flag");
	   isCheck=request.getParameter("isCheck");
	   request.setAttribute("isCheck", isCheck);//供应商考核：true  选择：false
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
	      case "ycheck":
	    	  ycheck(request,response);
			  break; 
	      case "supcheck":
	    	  supcheck(request,response);
			  break;
	      case "see":
	    	  see(request,response);
			  break; 
	      case "checkName":
	    	  checkName(request,response);
			  break; 
	default:
		break;
	}
	   
	  
	}

	private void checkName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name =request.getParameter("name");
		List<Supplier> sups = sdao.findByFullName(name);
		String res ="";
		if(sups.size()>0){
			res="请勿重复添加";
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void see(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		Supplier sup=null;
		String str="";
	
		if(Integer.parseInt(id)==0){
			str="nodata";
		}else{
			sup=sdao.findById(Integer.parseInt(id));
		}
		try {
		if(sup==null){
			str="nodata";	
			response.getWriter().print(str);
		}else{
			response.getWriter().print(new Gson().toJson(sup));
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void supcheck(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Supplier sup = sdao.findById(Integer.parseInt(request.getParameter("id")));
		String price=request.getParameter("price");
		String sample=request.getParameter("sample");
		String sat=request.getParameter("sat");
		String delivery=request.getParameter("delivery");
		String f_data=request.getParameter("f_data");
		String quality=request.getParameter("quality");
		String wdate =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        int id = Integer.parseInt(request.getParameter("id"));//((SupCheck)request.getSession().getAttribute("ysc")).getId();
        SupCheck sc = new SupCheck(id,sup,price,sample,sat,delivery,f_data,quality,wdate,"");
        
        
        String status ="";
		//判断审核状态
		if(price.equals("适中")&&sample.equals("合格")&&delivery.equals("未逾期")&&f_data.equals("齐全")&&quality.equals("达标")&&(Integer.parseInt(sat)>=90)){
			status="已审核通过";
		}else{
			status="审核未通过";
		}
		
		
       //插入之前 判断供应商状态是否为已经被审核 
        if(sup.getStatus().equals("未审核")){
        	scdao.insert(sc);//插入审核表
        }else{
        	scdao.update(sc);
        }
        sup.setStatus(status);
		sdao.updateCheckSup(sup);//更改供应商表的审核状态
		isCheck="true";
		//request.setAttribute("ischeck", "true");//设置点完审核之后跳转页面还是审核
		findAll(request, response);
	}

	private void ycheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		Supplier sup =sdao.findById(Integer.parseInt(id));
		request.setAttribute("upSup", sup);
		if(!sup.getStatus().equals("未审核")){
		SupCheck sc =scdao.findBySId(Integer.parseInt(id));
		request.setAttribute("upSupCheck", sc);
		}
//		if(sup.getStatus().equals("未审核"))
		   request.setAttribute("opa", "check");
		
		findAll(request, response);
		//Supplier sup = sdao.findById(Integer.parseInt(id));//查询供应商 看是否被审核过
		
		
		/*JSONObject json=new JSONObject();
		if(!sup.getStatus().equals("未审核")){
			//如果已经审核 查审核表  
			SupCheck sc =scdao.findBySId(Integer.parseInt(id));
			json.put("msg", "ysh");
			JSONObject json1=JSONObject.fromObject(sc);
			json.put("obj", json1);
			
		}else{
			json.put("msg", sup.getName());
		}
		response.getWriter().print(json.toString());*/
		
	}

	private void findByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username=request.getParameter("name");
		username=username.replace("'", "");
		// sdao.findByName(username)
		List<Supplier> list = findByPage(request, response, username);
		request.setAttribute("suplist",list);
		request.getRequestDispatcher("supplier_list.jsp").forward(request, response);
	}

	private void deledeSome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//User logUser = (User)request.getSession().getAttribute("loginUser");
		 String ids = request.getParameter("ids");
		 String newids="";
		 if(ids!=null&&!ids.equals("")){
			  List<Supplier> sups =  sdao.findByIds(ids.substring(0, ids.length()-1));
			  for(Supplier s:sups){
				  if(s.getStatus().equals("未审核"))
					  newids+=s.getId()+",";
			  }
			  sdao.deleteSome(newids.length()>0?newids.substring(0, newids.length()-1):"0");
		 }
		 findAll(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	
		Supplier upsup = sdao.findById(Integer.parseInt(request.getParameter("id")));
		upsup.setAddress(request.getParameter("address"));
		upsup.setTel(request.getParameter("tel"));
		upsup.setEmail(request.getParameter("email"));
		upsup.setFax(request.getParameter("fax"));
		upsup.setLinkman(request.getParameter("linkman"));
		upsup.setLink_tel(request.getParameter("link_tel"));
	    sdao.update(upsup);
	    findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		Supplier updateSup = sdao.findById(Integer.parseInt(id));
		request.setAttribute("updateSup", updateSup);
		if(updateSup.getStatus().equals("未审核"))
		request.setAttribute("opa", "edit");
		findAll(request, response);
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//User logUser = (User)request.getSession().getAttribute("loginUser");
		  String id = request.getParameter("id");
		  Supplier sup = sdao.findById(Integer.parseInt(id));
		  
		  if(sup.getStatus().equals("未审核")){
			  //如果是未审核  就没有此供应商的商品 
			
			scdao.deleteBySID(id);
		    sdao.delete(Integer.parseInt(id));
		  }
		  findAll(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//isCheck=request.getParameter("isCheck");
		String name=request.getParameter("name");
		String address=request.getParameter("address");
		String tel=request.getParameter("tel");
		String email=request.getParameter("email");
		String fax=request.getParameter("fax");
		String linkman=request.getParameter("linkman");
		String link_tel=request.getParameter("link_tel");
		
		Supplier sup = new Supplier(0,name,address,tel,email,fax,linkman,link_tel,"未审核","");
		sdao.insert(sup);
		findAll(request, response);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  List<Supplier> sups = findByPage(request, response,null);//sdaofindAll();
		  request.setAttribute("suplist", sups);
		 
			try {
				request.getRequestDispatcher("supplier_list.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

}
	private List<Supplier> findByPage(HttpServletRequest request, HttpServletResponse response,String name) {
		int size=9;
		int rows=0;
		String url="";
		rows=sdao.findCount(name);
		if(name==null ||name.equals("")){
			url="SupplierServlet?flag=findAll&isCheck="+isCheck;
		}else{
			url="SupplierServlet?flag=findByName&username="+name;
		}
		
		int cpage=request.getParameter("pager")==null?1:Integer.parseInt(request.getParameter("pager"));
		String pager=Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page=new Page((cpage-1)*size, size, "", "");
		List<Supplier> list=sdao.findByPage(page,name);
		return list;
	}
}