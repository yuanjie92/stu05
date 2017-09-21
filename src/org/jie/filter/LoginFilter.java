package org.jie.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jie.model.User;


public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		if ((uri.contains("user") && "login".equals(req.getParameter("act")))
				|| (uri.contains("user") && "logout".equals(req.getParameter("act")))
				|| (uri.contains("user") && "register".equals(req.getParameter("act"))) || uri.contains("login")) {
			chain.doFilter(req, resp);
		} else {
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute("user");
			if (null == user || "".equals(user)) {
				req.setAttribute("errorMsg", "请登录");
				resp.sendRedirect("login.jsp");
			} else {
				chain.doFilter(req, resp);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
