package com.crx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crx.dao.InBoundDAO;
import com.crx.dao.LossDAO;
import com.crx.dao.PurchaseDAO;
import com.crx.dao.SaleDAO;
import com.crx.model.Account;
import com.crx.model.Inbound;
import com.crx.model.Loss;
import com.crx.model.Need;
import com.crx.model.Page;
import com.crx.model.Purchase;
import com.crx.model.Sale;
import com.crx.model.User;
import com.crx.utils.Pager;



@WebServlet("/InBoundServlet")
public class InBoundServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private InBoundDAO bdao = new InBoundDAO();
	private SaleDAO sdao = new SaleDAO();
	private LossDAO ldao = new LossDAO();
	private PurchaseDAO pdao = new PurchaseDAO();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  String flag=request.getParameter("flag");
		   switch (flag) {
		      
		      case "findAllAccount":
		    	  findAllAccount(request,response);
		    	  break;
		      case "findTime":
		    	  findTime(request,response);
		    	  break;
		default:
			break;
		}
}

	private void findTime(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String st=request.getParameter("startDate");
		String en=request.getParameter("endDate");
		
		List<Inbound> ins = bdao.findByDate(st,en);
		List<Sale> sls = sdao.findByDate(st,en);
		List<Loss> los = ldao.findByDate(st,en);
		
		List<Account> accs = new ArrayList<>();
		
		int id=1;
		for(Inbound in:ins){
			Account a = new Account();
			a.setId(id);
			a.setType("Èë¿â");
			a.setGoods(in.getPur().getGood());
			a.setNum(in.getNum());
			a.setTime(in.getIndate());
			accs.add(a);
			id++;
		}
		for(Sale s:sls){
			Account a = new Account();
			a.setId(id);
			a.setType("³ö¿â");
			a.setGoods(s.getGoods());
			a.setNum(s.getNum());
			a.setTime(s.getOutdate());
			accs.add(a);
			id++;
		}
		for(Loss l :los){
			Account a = new Account();
			a.setId(id);
			a.setType("ËðºÄ");
			a.setGoods(l.getGoods());
			a.setNum(l.getNum());
			a.setTime(l.getLossdate());
			accs.add(a);
			id++;
		}
		accs=findByPage(request, response, accs, "time");
		//¶ÔListÅÅÐò
				Collections.sort(accs,new Comparator<Account>() {

					@Override
					public int compare(Account o1, Account o2) {
						// TODO Auto-generated method stub
						return o2.getTime().compareTo(o1.getTime());
					}
				});
		
		request.setAttribute("acclist", accs);
		try {
			request.getRequestDispatcher("account_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findAllAccount(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Inbound> ins = bdao.findAll();
		List<Sale> sls = sdao.findAll();
		List<Loss> los = ldao.findAll();
		
		List<Account> accs = new ArrayList<>();
		
		int id=1;
		for(Inbound in:ins){
			Account a = new Account();
			a.setId(id);
			a.setType("Èë¿â");
			a.setGoods(in.getPur().getGood());
			a.setNum(in.getNum());
			a.setTime(in.getIndate());
			accs.add(a);
			id++;
		}
		for(Sale s:sls){
			Account a = new Account();
			a.setId(id);
			a.setType("³ö¿â");
			a.setGoods(s.getGoods());
			a.setNum(s.getNum());
			a.setTime(s.getOutdate());
			accs.add(a);
			id++;
		}
		for(Loss l :los){
			Account a = new Account();
			a.setId(id);
			a.setType("ËðºÄ");
			a.setGoods(l.getGoods());
			a.setNum(l.getNum());
			a.setTime(l.getLossdate());
			accs.add(a);
			id++;
		}
		accs=findByPage(request, response, accs, null);
		//¶ÔListÅÅÐò
		Collections.sort(accs,new Comparator<Account>() {
			@Override
			public int compare(Account o1, Account o2) {
				// TODO Auto-generated method stub
				return o2.getTime().compareTo(o1.getTime());
			}
		});
		
		
		request.setAttribute("acclist", accs);
		try {
			request.getRequestDispatcher("account_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private List<Account> findByPage(HttpServletRequest request, HttpServletResponse response,List<Account> accs,String name) {
		int size=9;
		int rows=0;
		String url="";
		rows=accs.size();
		if(name==null || name.equals("")){
			url="InBoundServlet?flag=findAllAccount";
		}else{
			url="InBoundServlet?flag=findTime";
		}
		
		int cpage=request.getParameter("pager")==null?1:Integer.parseInt(request.getParameter("pager"));
		String pager=Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page=new Page((cpage-1)*size, size, "", "");
		int end=page.getStart()+size;
		int x=end<accs.size()?end:accs.size();
		List<Account> list=accs.subList(page.getStart(), x);
		return list;
	}
	
}
