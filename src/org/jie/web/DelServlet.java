package org.jie.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jie.service.StudentService;
import org.jie.service.impl.StudentServiceImpl;

public class DelServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StudentService studentService;
	
	@Override
	public void init() throws ServletException {
		studentService = new StudentServiceImpl();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String act = req.getParameter("act");
		if("del".equals(act)){
			del(req,resp);
		}
	}

	//删除学生
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接收前台传过来的id
		String id = req.getParameter("id");
		//调用service的删除方法
		studentService.deleteStudent(id);
		//请求转发到studentServlet中
		req.getRequestDispatcher("student?act=").forward(req, resp);
	}
}
