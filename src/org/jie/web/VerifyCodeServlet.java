package org.jie.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jie.util.VerifyCodeUtils;

public class VerifyCodeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		req.getSession().setAttribute("verifyCode", verifyCode);
		VerifyCodeUtils.outputImage(120, 50, resp.getOutputStream(), verifyCode);
	}
	

}
