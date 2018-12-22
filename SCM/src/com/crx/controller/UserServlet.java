package com.crx.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.crx.dao.UserDAO;
import com.crx.model.Page;
import com.crx.model.User;
import com.crx.utils.Pager;
import com.google.gson.Gson;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO udao = new UserDAO();

	public UserServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String flag = request.getParameter("flag");
		switch (flag) {
		case "login":
			login(request, response);
			break;
		case "findAll":
			findAll(request, response);
			break;
		case "add":
			AddUser(request, response);
			break;
		case "check":
			check(request, response);
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
		case "findByName":
			findByName(request, response);
			break;
		case "deledeSome":
			deledeSome(request, response);
			break;
		case "out":
			out(request, response);
			break;
		/*
		 * case "index": index(request,response); break;
		 */

		default:
			break;
		}
	}

	/*private void index(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String username = "";
		String password = "";
		String s;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			s = cookie.getName(); // 通过getName方法获得cookie的名称
			if (s.equals("username")) {
				if (cookie.getValue() != null) {
					username = username + cookie.getValue(); // 通过getValue方法获得cookie的值
				}
			} else if (s.equals("password")) {
				if (cookie.getValue() != null) {
					password = password + cookie.getValue();
				}
			}
		}

	}*/

	private void out(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		session.removeAttribute("loginUser");
		session.invalidate();
		try {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deledeSome(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ids = request.getParameter("ids");
		if (ids != null && !ids.equals("")) {
			ids = ids.substring(0, ids.length() - 1);
			udao.deleteSome(ids);
		}
		findAll(request, response);
	}

	private void findByName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		username=username.replace("'", "");
		List<User> list = findByPage(request, response, username);
		request.setAttribute("userlist", list);
		request.getRequestDispatcher("user_list.jsp").forward(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("updateUser");
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setType(request.getParameter("type"));
		udao.update(user);
		findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		User user = udao.findById(Integer.parseInt(id));
		request.getSession().setAttribute("updateUser", user);
		response.getWriter().print(new Gson().toJson(user));
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		udao.delete(Integer.parseInt(id));
		findAll(request, response);
	}

	private void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String choose = request.getParameter("choose");
		User user = (User) request.getSession().getAttribute("updateUser");
		if (choose.equals("edit")) {
			if (udao.findByNameExceptSelf(username, user.getUsername()).size() > 0) {
				response.getWriter().print("no");
			}
		} else {
			if (udao.findByName(username).size() > 0) {
				response.getWriter().print("no");
			} else {
				response.getWriter().print("yes");
			}
		}

	}

	private void AddUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		User user = new User(0, username, password, "", type, "");
		udao.insert(user);
		request.setAttribute("opr", "add");
		findAll(request, response);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> list = findByPage(request, response, null);
		request.setAttribute("userlist", list);
		request.getRequestDispatcher("user_list.jsp").forward(request, response);
	}

	private List<User> findByPage(HttpServletRequest request, HttpServletResponse response, String name) {
		int size = 9;
		int rows = 0;
		String url = "";
		rows = udao.findCount(name);
		if (name == null || name.equals("")) {
			url = "UserServlet?flag=findAll";
		} else {
			url = "UserServlet?flag=findByName&username=" + name;
		}

		int cpage = request.getParameter("pager") == null ? 1 : Integer.parseInt(request.getParameter("pager"));
		String pager = Pager.getPagerStr(url, size, rows, cpage, 1);
		request.setAttribute("pager", pager);
		Page page = new Page((cpage - 1) * size, size, "logtime", "desc");
		List<User> list = udao.findByPage(page, name);
		return list;
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");



		User user = new User(0, username, password, "", "", "");
		List<User> users = udao.login(user);
		if (users.size() > 0) {
			String logtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
			User u = users.get(0);
			u.setLogtime(logtime);
			udao.update(u);

			/*Cookie cookies[] = request.getCookies();
			Cookie cusername = null;
			Cookie ctype=null;
			if (cookies != null) {
				for (Cookie coo : cookies) {
					if (coo.getName().equals("lUser_name")) {
						cusername=coo;
					}
					if(coo.getName().equals("lUser_type")){
						ctype=coo;
					}
				}
			}else{
				cusername = new Cookie("lUser_name", u.getUsername());
				ctype = new Cookie("lUser_type", u.getType());
				response.addCookie(cusername);
				response.addCookie(ctype);
				
			}*/
            
			

			request.getSession().setAttribute("loginUser", u);
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} else {
			request.setAttribute("message", "您的用户名或密码错误！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		return;
	}

}
