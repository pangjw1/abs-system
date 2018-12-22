package com.crx.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crx.dao.AgreeDAO;
import com.crx.model.Agree;

@WebServlet("/AgreeServlet")
public class AgreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AgreeDAO adao = new AgreeDAO();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag=request.getParameter("flag");
		switch (flag) {
		case "update":
			update(request,response);
			break;
		case "show":
			show(request,response);
			break;

		default:
			break;
		}
	}
	private void show(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Agree a = adao.findAll().get(0);
		request.setAttribute("agree",a );
		try {
			request.getRequestDispatcher("agree.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String content = request.getParameter("content");
		Agree a = new Agree(1,content);
		adao.update(a);
		show(request, response);
	}

}
