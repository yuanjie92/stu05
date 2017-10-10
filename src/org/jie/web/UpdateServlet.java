package org.jie.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jie.model.Student;
import org.jie.service.StudentService;
import org.jie.service.impl.StudentServiceImpl;

public class UpdateServlet extends HttpServlet{

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
		if("showStu".equals(act)){
			showStu(req,resp);
		}
		if("update".equals(act)){
			update(req,resp);
		}
	}

	//修改学生信息
	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//获取前台传过来的参数
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String grade = req.getParameter("grade");
		String gender = req.getParameter("gender");
		String birthday = req.getParameter("birthday");
		//调用service的修改方法
		studentService.updateStudent(id,name,grade,gender,birthday);
		//重定向到studentServlet中
		resp.sendRedirect("student");
	}

	//修改学生前，需查出这个学生的信息
	private void showStu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取前台传过来的id
		String id = req.getParameter("id");
		//调用service的查询方法
		Student stu = studentService.findStudentById(id);
		//将查询出来的结果放到request域
		req.setAttribute("stu", stu);
		//请求转发到edit.jsp页面
		req.getRequestDispatcher("edit.jsp").forward(req, resp);
	}

}
