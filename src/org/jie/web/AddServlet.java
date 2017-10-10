package org.jie.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jie.service.StudentService;
import org.jie.service.impl.StudentServiceImpl;

public class AddServlet extends HttpServlet{

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
		resp.sendRedirect("add.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String act = req.getParameter("act");
		if("add".equals(act)){
			add(req,resp);
		}
	}

	//添加学生
	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//接收前台传过来的参数
		String name = req.getParameter("name");
		String grade = req.getParameter("grade");
		String gender = req.getParameter("gender");
		String birthday = req.getParameter("birthday");
		//调用service的添加方法
		studentService.addStudent(name,grade,gender,birthday);
		//重定向到studentServlet中
		resp.sendRedirect("student");
				
	}

}
