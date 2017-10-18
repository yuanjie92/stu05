package org.jie.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jie.model.User;
import org.jie.service.UserService;
import org.jie.service.impl.UserServiceImpl;

public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService userService;

	@Override
	public void init() throws ServletException {
		userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String act = req.getParameter("act");
		if ("login".equals(act)) {
			login(req, resp);
		}
		if("register".equals(act)){
			register(req,resp);
		}
		if("logout".equals(act)){
			logout(req,resp);
		}
	}

	private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//清空session
		req.getSession().invalidate();
		//清空cookie
		Cookie c = new Cookie("user", "");
		c.setMaxAge(0);
		resp.addCookie(c);
		resp.sendRedirect("login.jsp");
		return;
	}

	private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("userName");
		String userPwd = req.getParameter("userPwd");
		if (null == userName || "".equals(userName)) {
			req.setAttribute("errorMsg", "用户名不能为空");
			req.getRequestDispatcher("register.jsp").forward(req, resp);
			return;
		}
		if (null == userPwd || "".equals(userPwd)) {
			req.setAttribute("errorMsg", "密码不能为空");
			req.getRequestDispatcher("register.jsp").forward(req, resp);
			return;
		}
		userService.add(userName,userPwd);
		req.setAttribute("msg", "注册成功");
		req.getRequestDispatcher("success.jsp").forward(req, resp);
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("userName");
		String userPwd = req.getParameter("userPwd");
		String verifyCode = req.getParameter("verifyCode");
		String verify = (String) req.getSession().getAttribute("verifyCode");
		if(!verifyCode.equals(verify)){
			req.setAttribute("errorMsg", "验证码输入错误!!!");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}
		String remember = req.getParameter("remember");
		if (null == userName || "".equals(userName)) {
			req.setAttribute("errorMsg", "用户名不能为空");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}
		if (null == userPwd || "".equals(userPwd)) {
			req.setAttribute("errorMsg", "密码不能为空");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}
		User user = userService.findUser(userName, userPwd);
		if (null == user) {
			req.setAttribute("errorMsg", "用户不存在");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}
		if(!userPwd.equals(user.getUserPwd())){
			req.setAttribute("errorMsg", "密码不正确");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
		if(null != remember){
			Cookie cookie = new Cookie("user", user.getUserName()+"="+user.getUserPwd());
			cookie.setMaxAge(60*60*24*3);
			resp.addCookie(cookie);
		}
		resp.sendRedirect("student");
	}

}
