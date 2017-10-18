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


public class AuthFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		//System.out.println("uri:"+uri);
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		if(null == user){
			chain.doFilter(req, resp);
		}else{
			String resources = user.getResources();
			String resource[] = resources.split(",");
			boolean access = false;
			for (String res : resource) {
				if(uri.contains(res)){
					access = true;
					break;
				}
			}
			if(access){
				chain.doFilter(req, resp);
			}else{
				resp.sendRedirect("noAccess.jsp");
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
