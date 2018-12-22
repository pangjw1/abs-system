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
import com.crx.model.Area;
import com.crx.model.Goods;
import com.crx.model.Loss;
import com.crx.model.Need;
import com.crx.model.Page;
import com.crx.model.Purchase;
import com.crx.model.Supplier;
import com.crx.model.User;
import com.crx.utils.Pager;
import com.google.gson.Gson;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class AreaServlet
 */
@WebServlet("/AreaServlet")
public class AreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GoodsDAO gdao = new GoodsDAO();
	private AreaDAO adao = new AreaDAO();

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
		case "deledeSome":
			deledeSome(request, response); 
			break;
		case
		  "yupdate": 
		   yupdate(request, response); 
		   break;
		case "update":
		   update(request, response); 
		   break;  
		case "findByName":
		   findByName(request, response);
           break;
		case "checkName":
			checkName(request, response);
			break;
		case "checkNameUpdate":
			checkNameUpdate(request, response);
			break;
		case "findGoodsName":
			findGoodsName(request, response);
			break;
		case "export":
			export(request, response);
			break;
		default:
			break;
		}
	}
	private void export(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String d = request.getParameter("d");
		 if(d.equals("findAll")){
			  List<Area> los =adao.findAll();
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
		System.out.println(ids);
		List<Area> as = findByPage(request, response, ids);
		request.setAttribute("arealist", as);
		try {
			request.getRequestDispatcher("area_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkNameUpdate(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String gname = request.getParameter("goods");
		Goods goods = gdao.findByFullGoodsName(gname).get(0);
		String id=request.getParameter("id");
		Area a = adao.findById(Integer.parseInt(id));
		String res = "";
			String name=request.getParameter("city");
			List<Area> as = adao.findGoodsAndArea(goods,name,a.getName());
			if(as.size()>0){
				res="此商品已经填写过此区域价格！";
			}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		double min = Double.parseDouble(request.getParameter("minprice"));
		double max = Double.parseDouble(request.getParameter("maxprice"));

		Area a = adao.findById(id);
         a.setName(name);
         a.setMinprice(min);
         a.setMaxprice(max);
		adao.update(a);
		findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		Area updateArea = adao.findById(Integer.parseInt(id));
		request.setAttribute("updateArea", updateArea);
		request.setAttribute("opa", "edit");
		findAll(request, response);
	}

	private void deledeSome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String ids = request.getParameter("ids");
		 if(ids!=null&&!ids.equals("")){
			  ids=ids.substring(0,ids.length()-1);
			  adao.deleteSome(ids);
		 }
		 findAll(request, response);
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		adao.delete(Integer.parseInt(id));
		findAll(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String gname = request.getParameter("goods");
		String name = request.getParameter("name");
		double min = Double.parseDouble(request.getParameter("minprice"));
		double max = Double.parseDouble(request.getParameter("maxprice"));

		Goods goods = gdao.findByFullGoodsName(gname).get(0);
		Area a = new Area(0, goods, name, min, max, "");
		adao.insert(a);
		findAll(request, response);
	}

	private void findGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("goods");
		List<Goods> goods = gdao.findByGoodsName(name.replace("'", ""));
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

	private void checkName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String gname = request.getParameter("goods");
		List<Goods> gs = gdao.findByFullGoodsName(gname);
		String res = "";
		if (gs.size() == 0) {
			res = "该商品不存在或暂未通过审核";
		}else{
			String name=request.getParameter("city");
			List<Area> as = adao.findGoodsAndArea(gs.get(0),name);
			if(as.size()>0){
				res="此商品已经填写过此区域价格！";
			}
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Area> as = findByPage(request, response, null);// gdao.findAll();
		request.setAttribute("arealist", as);
		try {
			request.getRequestDispatcher("area_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Area> findByPage(HttpServletRequest request, HttpServletResponse response,String ids) {
		int size = 9;
		int rows = 0;
		String url = "";
		rows=adao.findCount(ids);
		if(ids==null ||ids.equals("")){
			url = "AreaServlet?flag=findAll";
		}else{
			url = "AreaServlet?flag=findByName";
		}
		
		int cpage = request.getParameter("pager") == null ? 1 : Integer.parseInt(request.getParameter("pager"));
		String pager = Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page = new Page((cpage - 1) * size, size, "", "");
		List<Area> list = adao.findByPage(page, ids);
		return list;
	}
}
