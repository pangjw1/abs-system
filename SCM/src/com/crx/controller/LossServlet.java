package com.crx.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crx.dao.GoodsDAO;
import com.crx.dao.InBoundDAO;
import com.crx.dao.LossDAO;
import com.crx.model.Goods;
import com.crx.model.Inbound;
import com.crx.model.Loss;
import com.crx.model.Page;
import com.crx.utils.Pager;
import com.google.gson.Gson;

import net.sf.json.JSONArray;

@WebServlet("/LossServlet")
public class LossServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private LossDAO ldao = new LossDAO();
	private GoodsDAO gdao = new GoodsDAO();
	private InBoundDAO idao = new InBoundDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  String flag=request.getParameter("flag");
		  // oper=request.getParameter("oper");
		  // request.setAttribute("oper", oper);
		   switch (flag) {
		      case "findAll":
			    findAll(request,response);
			  break;
		      case "findGoodsName":
		    	  findGoodsName(request,response);
		    	  break;
		      case "findFullGoodsName":
		    	  findFullGoodsName(request,response);
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
		      case "findNowStore":
		    	  findNowStore(request,response);
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
			  List<Loss> los =ldao.findAll();
			  JSONArray jsarr = JSONArray.fromObject(los);
				 try {
					response.getWriter().print(jsarr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		
		/*// 获取数据
		HttpSession session = request.getSession();
		 List<Loss> los =ldao.findAll();
		// excel标题
		String[] title = { "编号","商品名","损耗数量","损耗日期","损耗原因" };
		String fileName = "损耗" + DateUtil.getByPattern("yyyyMMddHHmmss") + ".xls";
		String sheetName = "选定结果";
		

		String[][] content = new String[los.size()][];
		for (int i = 0; i < content.length;) {
			for (Loss l : los) {
				   if (l!= null) {
							content[i] = new String[title.length];
							content[i][0] =l.getId()+"";
							content[i][1] =l.getGoods().getName();
							content[i][2] = l.getNum()+"";
							content[i][3] = l.getLossdate();
							content[i][4] = l.getReason();
							i++;
						}
					}

			}

		

		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
		try {
			this.setResponseHeader(response, fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	// 发送响应流方法
		public void setResponseHeader(HttpServletResponse response, String fileName) {
			try {
				try {
					fileName = new String(fileName.getBytes(), "ISO8859-1");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.setContentType("application/octet-stream;charset=ISO8859-1");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
				response.addHeader("Pargam", "no-cache");
				response.addHeader("Cache-Control", "no-cache");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	private void findNowStore(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name=request.getParameter("goods");
		List<Goods> goods = gdao.findByFullGoodsName(name);
		
		Goods good = goods.size()>0?goods.get(0):null;
		try {
			response.getWriter().print(new Gson().toJson(good));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void findByName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		name=name.replace("'", "");
		List<Goods> goods = gdao.findByName(name);
//		List<Loss> los = new ArrayList<>();
		String ids = "";
		for (Goods g : goods) {
			ids += g.getId() + ",";
		}
		ids=ids.length()>0?ids.substring(0,ids.length()-1):ids;
		List<Loss> los = findByPage(request, response, ids);//ndao.findByGIds(ids);
		 request.setAttribute("loslist", los);
		  try {
			request.getRequestDispatcher("loss_list.jsp").forward(request, response);
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
					  Loss los = ldao.findById(Integer.parseInt(id));
					  gdao.updateStore(los.getGoods().getStore()+los.getNum(), los.getGoods().getId());
				 }
			  ldao.deleteSome(ids);
		 }
		 
		 findAll(request, response);
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  int  id = Integer.parseInt(request.getParameter("id"));
		  //在删除之前 应把损耗的数量 加回商品库存里
		  Loss los = ldao.findById(id);
		  gdao.updateStore(los.getGoods().getStore()+los.getNum(), los.getGoods().getId());
		  
		//修改入库表   如果日常损耗  不需要修改入库表
			if(los.getPur()!=null ||!los.getReason().equals("日常损耗")){
				Inbound inb = idao.findByPId(los.getPur().getId());
				inb.setNum(los.getPur().getNum());
				idao.update(inb);
			}
		  
		  ldao.delete(id);
		  findAll(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id =Integer.parseInt(request.getParameter("id"));
		Loss loss = ldao.findById(id);
		
		int num=Integer.parseInt(request.getParameter("num"));
		
		//修改商品表的库存
		Goods good = loss.getGoods();
		gdao.updateStore(good.getStore()+loss.getNum()-num, good.getId());
		
		//修改入库表   如果日常损耗  不需要修改入库表
		if(loss.getPur()!=null ||!loss.getReason().equals("日常损耗")){
			Inbound inb = idao.findByPId(loss.getPur().getId());
			inb.setNum(loss.getPur().getNum()-num);
			idao.update(inb);
		}
		
		//修改损耗表
		loss.setNum(num);
		ldao.update(loss);
		findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		Loss loss = ldao.findById(Integer.parseInt(id));
		request.setAttribute("uploss", loss);
		request.setAttribute("opa", "edit");
		findAll(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Goods goods=gdao.findByFullGoodsName(request.getParameter("goods")).get(0);
		int num=Integer.parseInt(request.getParameter("num"));
		String lossdate=request.getParameter("lossdate");
	
		Loss  loss = new Loss(0, goods, num, lossdate, "日常损耗", null);
		ldao.insert(loss);
		//增加损耗  将该商品的库存减少相应的数量
		goods.setStore(goods.getStore()-num);
		gdao.update(goods);
		findAll(request, response);
	}

	private void findFullGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name=request.getParameter("goods");
        List<Goods> goods =gdao.findByFullGoodsName(name.replace("'", ""));
        
	   // System.out.println(goods.get(0).getSup().getName()+"aaaaaaaaaaaaaaaaaa");
		try {
			if(goods.size()>0)
			  response.getWriter().print(goods.get(0).getSup().getName());
			else
				response.getWriter().print("nodata");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//findAll(request, response);
	}

	private void findGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name=request.getParameter("goods");
        List<Goods> goods =gdao.findByGoodsName(name.replace("'", ""));
        
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
		  List<Loss> los = findByPage(request, response, null);//ldao.findAll();
		  request.setAttribute("loslist", los);
		  try {
			request.getRequestDispatcher("loss_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private List<Loss> findByPage(HttpServletRequest request, HttpServletResponse response,String ids) {
		int size=9;
		int rows=0;
		String url="";
		rows=ldao.findCount(ids);
		if(ids==null ||ids.equals("")){
			url="LossServlet?flag=findAll";
		}else{
			url="LossServlet?flag=findByName";
		}
		
		int cpage=request.getParameter("pager")==null?1:Integer.parseInt(request.getParameter("pager"));
		String pager=Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page=new Page((cpage-1)*size, size, "", "");
		List<Loss> list=ldao.findByPage(page,ids);
		return list;
	}

}
