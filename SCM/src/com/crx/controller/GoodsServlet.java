package com.crx.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crx.dao.AreaDAO;
import com.crx.dao.GoodsDAO;
import com.crx.dao.InBoundDAO;
import com.crx.dao.LossDAO;
import com.crx.dao.NeedDAO;
import com.crx.dao.PurchaseDAO;
import com.crx.dao.SaleDAO;
import com.crx.dao.SupplierDAO;
import com.crx.model.Goods;
import com.crx.model.Page;
import com.crx.model.Purchase;
import com.crx.model.Supplier;
import com.crx.model.User;
import com.crx.utils.Pager;
import com.google.gson.Gson;

@WebServlet("/GoodsServlet")
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GoodsDAO gdao = new GoodsDAO();
	private NeedDAO ndao = new NeedDAO();
	private PurchaseDAO pdao = new PurchaseDAO();
	private LossDAO ldao = new LossDAO();
	private SaleDAO sadao = new SaleDAO();
	private SupplierDAO sdao = new SupplierDAO();
	private InBoundDAO  idao = new InBoundDAO();
	private AreaDAO  adao = new AreaDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flag = request.getParameter("flag");
		switch (flag) {
		case "findAll":
			findAll(request, response);
			break;
		case "add":
			add(request, response);
			break;
		case "deleteOne":
			deleteOne(request, response);
			break;
		case "yupdate":
			yupdate(request, response);
			break;
		case "update":
			update(request, response);
			break;
		case "deledeSome":
			deledeSome(request, response);
			break;
		case "findByName":
			findByName(request, response);
			break;
		case "check":
			check(request, response);
			break;
		case "findSupName":
			findSupName(request, response);
			break;
		case "findSupFullNameBlur":
			findSupFullNameBlur(request, response);
			break;
		case "see":
			see(request, response);
			break;
		case "checkName":
			checkName(request, response);
			break;
		case "gfind":
			gfind(request, response);
			break;
		default:
			break;
		}

	}

	private void gfind(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String stprice = request.getParameter("stprice");
		String endprice = request.getParameter("endprice");
		String batch = request.getParameter("batch");
		String supname = request.getParameter("supname");
		String minnum = request.getParameter("minnum");
		String maxnum = request.getParameter("maxnum");
		String[] stat = request.getParameterValues("stat");
		name=name.replace("'","");
		stprice=stprice.replace("'","");
		endprice=endprice.replace("'","");
		batch=batch.replace("'","");
		supname=supname.replace("'","");
		minnum=minnum.replace("'","");
		maxnum=maxnum.replace("'","");
		
		
		String ids = "";
		String stats = "";

		if (!supname.equals("") && supname != null) {
			List<Supplier> gs = sdao.findByName(supname);
			for (Supplier g : gs) {
				
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

		List<Goods> goods = findByPageCondition(request, response, name,batch, stprice, endprice, ids, minnum, maxnum, stats); //gdao.findByCondition(name,batch,stprice,endprice,ids,minnum,maxnum,stats);
		request.setAttribute("goodslist", goods);
		try {
			request.getRequestDispatcher("goods_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void checkName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		List<Goods> gs = gdao.findByFullName(name);
		// System.out.println(name+"--"+gs.size());
		String res = "";
		if (gs.size() > 0) {
			res = "此商品已存在或还未被审核请勿重复添加";
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
		String id = request.getParameter("id");
		Goods goods = null;
		String str = "";

		if (Integer.parseInt(id) == 0) {
			str = "nodata";
		} else {
			goods = gdao.findById(Integer.parseInt(id));
		}
		try {
			if (goods == null) {
				str = "nodata";
				response.getWriter().print(str);
			} else {
				response.getWriter().print(new Gson().toJson(goods));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findSupFullNameBlur(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		List<Supplier> sups = sdao.findSupFullNameByCon(name.replace("'", ""));
		String json = new Gson().toJson(sups);
		try {
			if (sups.size() > 0)
				response.getWriter().print(json);
			else {
				response.getWriter().print("nodata");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findSupName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		List<Supplier> goods = sdao.findSupNameByCon(name.replace("'", ""));
		String json = new Gson().toJson(goods);
		try {
			if (goods.size() > 0)
				response.getWriter().print(json);
			else {
				response.getWriter().print("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void check(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		Boolean res = Boolean.parseBoolean(request.getParameter("res"));
		Goods g = gdao.findById(Integer.parseInt(id));
		g.setStatus(res ? "已审核通过" : "审核未通过");
		gdao.updateStatus(g);
		findAll(request, response);
	}

	private void findByName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		name=name.replace("'", "");
		List<Goods> list=findByPage(request, response,name);//udao.findByLikeName(username);
		request.setAttribute("goodslist", list);
		//request.setAttribute("goodslist", gdao.findByName(name));
		request.getRequestDispatcher("goods_list.jsp").forward(request, response);
	}

	private void deledeSome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String ids = request.getParameter("ids");
		User logUser = (User)request.getSession().getAttribute("loginUser");
		 String newids="";
		 if(ids!=null&&!ids.equals("")){
			  List<Goods> goods =  gdao.findByIds(ids.substring(0, ids.length()-1));
			  for(Goods s:goods){
				  if(s.getStatus().equals("未审核")||logUser.getType().equals("超级管理员"))
					  newids+=s.getId()+",";
			  }
			  newids=newids.length()>0?newids.substring(0, newids.length()-1):"0";
				//主外键关系  需要先 删除 需求、采购、损耗、销售表数据
			  ndao.deleteSomeByGID(newids);
			    //主外键关系：删除采购表之前 先删除有关联的 入库表
			     List<Purchase>  purs = pdao.findByGIds(ids);
			     for(Purchase p:purs){
			    	 idao.deleteByPID(p.getId());
			     }
			  pdao.deleteSomeByGID(newids);
			  ldao.deleteSomeByGID(newids);
			  sadao.deleteSomeByGID(newids);
			  adao.deleteSomeByGID(newids);
			  gdao.deleteSome(newids);
		 }
		
		
		findAll(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		String address = request.getParameter("address");
		String batch = request.getParameter("batch");
		String desc = request.getParameter("describe");
		String status = request.getParameter("status");
		double price = Double.parseDouble(request.getParameter("price"));
		String size = request.getParameter("size");
		int store = Integer.parseInt(request.getParameter("store"));
		Supplier sup = sdao.findSupFullNameByCon(request.getParameter("sup")).get(0);

		Goods goods = gdao.findById(id);
		goods.setAddress(address);
		goods.setBatch(batch);
		goods.setDescribe(desc);
		goods.setPrice(price);
		goods.setSize(size);
		goods.setStore(store);
		goods.setSup(sup);
		// goods = new Goods(id, name, address, size, batch, desc, price,
		// status, sup, store, "");
		gdao.update(goods);
		findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		String id = request.getParameter("id");
		Goods updateGoods = gdao.findById(Integer.parseInt(id));
		request.setAttribute("updateGoods", updateGoods);
		if (updateGoods.getStatus().equals("未审核"))
			request.setAttribute("opa", "edit");
		findAll(request, response);
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		User logUser = (User)request.getSession().getAttribute("loginUser");
		  Goods goods = gdao.findById(Integer.parseInt(id));
		 if(goods.getStatus().equals("未审核")||logUser.getType().equals("超级管理员")){
			 adao.deleteSomeByGID(goods.getId()+"");
			 gdao.delete(Integer.parseInt(id));
		 }
		findAll(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String size = request.getParameter("size");
		String batch = request.getParameter("batch");
		String describe = request.getParameter("describe");
		double price = Double.parseDouble(request.getParameter("price"));
		int store = Integer.parseInt(request.getParameter("store"));

		Supplier sup = sdao.findSupFullNameByCon(request.getParameter("sup")).get(0);

		Goods g = new Goods(0, name, address, size, batch, describe, price, "未审核", sup, store, "");
		gdao.insert(g);
		findAll(request, response);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Goods> goods = findByPage(request, response,null);//gdao.findAll();
		request.setAttribute("goodslist", goods);
		try {
			request.getRequestDispatcher("goods_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private List<Goods> findByPage(HttpServletRequest request, HttpServletResponse response,String name) {
		int size=9;
		int rows=0;
		String url="";
		rows=gdao.findCount(name);
		if(name==null ||name.equals("")){
			url="GoodsServlet?flag=findAll";
		}else{
			url="GoodsServlet?flag=findByName";
		}
		
		int cpage=request.getParameter("pager")==null?1:Integer.parseInt(request.getParameter("pager"));
		String pager=Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page=new Page((cpage-1)*size, size, "", "");
		List<Goods> list=gdao.findByPage(page,name);
		return list;
	}
	private List<Goods> findByPageCondition(HttpServletRequest request, HttpServletResponse response,String name,String batch,String stprice,String endprice,String ids,String minnum,String maxnum,String stats){
		int size=9;
		int rows=0;
		String url="";
		rows=gdao.findCountByCondition(name, batch, stprice, endprice, ids, minnum, maxnum, stats);
		url="GoodsServlet?flag=gfind";
		
		int cpage=request.getParameter("pager")==null?1:Integer.parseInt(request.getParameter("pager"));
		String pager=Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page=new Page((cpage-1)*size, size, "", "");
		List<Goods> list=gdao.findByCondition(name, batch, stprice, endprice, ids, minnum, maxnum, stats, page);
		return list;
	}
}