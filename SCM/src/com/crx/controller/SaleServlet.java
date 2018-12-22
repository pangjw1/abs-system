package com.crx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crx.dao.GoodsDAO;
import com.crx.dao.SaleDAO;
import com.crx.model.Goods;
import com.crx.model.Loss;
import com.crx.model.Page;
import com.crx.model.Sale;
import com.crx.utils.Pager;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/SaleServlet")
public class SaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private SaleDAO sdao = new SaleDAO();
	private GoodsDAO gdao = new GoodsDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String flag = request.getParameter("flag");
		System.out.println(flag);
		switch (flag) {
		case "findAll":
			findAll(request, response);
			break;
		case "findGoodsName":
			findGoodsName(request, response);
			break;
		case "findFullGoodsName":
			findFullGoodsName(request, response);
			break;
		 case "add":
		 add(request,response);
		 break;
		 case "yupdate":
		 yupdate(request,response);
		 break;
		 case "update":
		 update(request,response);
		 break;
		 case "deleteOne":
		 deleteOne(request,response);
		 break;
		 case "deleteSome":
		 deleteSome(request,response);
		 break;
		 case "findByName":
		 findByName(request,response);
		 break;
		 case "export":
	    	  export(request,response);
	    	  break;
		default:
			break;
		}
	}
	private void export(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 String d = request.getParameter("d");
		 if(d.equals("findAll")){
			  List<Sale> los =sdao.findAll();
			  JSONArray jsarr = JSONArray.fromObject(los);
				 try {
					response.getWriter().print(jsarr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		
	}

	private void findByName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		name=name.replace("'", "");
		List<Goods> goods = gdao.findByName(name);
		String ids = "";
		for (Goods g : goods) {
			ids += g.getId() + ",";
		}
		ids=ids.length()>0?ids.substring(0,ids.length()-1):ids;
		List<Sale> los = findByPage(request, response, ids);//ndao.findByGIds(ids);
		 request.setAttribute("salist", los);
		  try {
			request.getRequestDispatcher("sale_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteSome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 String ids = request.getParameter("ids");
		 //删除之前 把他们的损耗的数量 分别加入相应的商品库存
		 if(ids!=null&&!ids.equals("")){
			  ids=ids.substring(0,ids.length()-1);
			  String[] idarr = ids.split(",");
				 for(String id:idarr){
					  Sale s = sdao.findById(Integer.parseInt(id));
					  gdao.updateStore(s.getGoods().getStore()+s.getNum(), s.getGoods().getId());
				 }
			  sdao.deleteSome(ids);
		 }
		 
		 findAll(request, response);
	}
	private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  int  id = Integer.parseInt(request.getParameter("id"));
		  //在删除之前 应把出库的数量 加回商品库存里
		  Sale sal = sdao.findById(id);
		  gdao.updateStore(sal.getGoods().getStore()+sal.getNum(), sal.getGoods().getId());
		  
		  sdao.delete(id);
		  findAll(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id =Integer.parseInt(request.getParameter("id"));
		Sale sal = sdao.findById(id);
		
		int num=Integer.parseInt(request.getParameter("num"));
		
		Goods good = sal.getGoods();//修改损耗表  相应商品的库存 也应该修改
		gdao.updateStore(good.getStore()+sal.getNum()-num, good.getId());
		
		sal.setNum(num);
		sdao.update(sal);
		findAll(request, response);
	}
	private void yupdate(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		Sale sal = sdao.findById(Integer.parseInt(id));
		request.setAttribute("upsale", sal);
		request.setAttribute("opa", "edit");
		findAll(request, response);
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		Goods goods=gdao.findByFullGoodsName(request.getParameter("goods")).get(0);
		int num=Integer.parseInt(request.getParameter("num")); 
		double price =Double.parseDouble(request.getParameter("price"));
		String outdate=request.getParameter("outdate");
		Sale sal = new Sale(0, goods, num, outdate, price, "");
		 sdao.insert(sal); //增加损耗 将该商品的库存减少相应的数量
		goods.setStore(goods.getStore()-num);
		gdao.update(goods);
		findAll(request, response);
		 
	}

	private void findFullGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("goods");
		List<Goods> goods = gdao.findByFullGoodsName(name.replace("'", ""));

		try {
			if (goods.size() > 0) {
				String json = new Gson().toJson(goods.get(0));
				response.getWriter().print(json);
			} else {
				response.getWriter().print("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("goods");
		List<Goods> goods = gdao.findByGoodsName(name.replace("'", ""));

		String json = new Gson().toJson(goods);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Sale> sales = findByPage(request, response, null);///sdao.findAll();
		request.setAttribute("salist", sales);
		try {
			request.getRequestDispatcher("sale_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private List<Sale> findByPage(HttpServletRequest request, HttpServletResponse response,String ids) {
		int size=9;
		int rows=0;
		String url="";
		rows=sdao.findCount(ids);
		if(ids==null ||ids.equals("")){
			url="SaleServlet?flag=findAll";
		}else{
			url="SaleServlet?flag=findByName";
		}
		
		int cpage=request.getParameter("pager")==null?1:Integer.parseInt(request.getParameter("pager"));
		String pager=Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page=new Page((cpage-1)*size, size, "", "");
		List<Sale> list=sdao.findByPage(page,ids);
		return list;
	}
}
