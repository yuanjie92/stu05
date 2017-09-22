package org.jie.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jie.model.Student;
import org.jie.pagination.Pagination;
import org.jie.pagination.SearchResult;
import org.jie.service.StudentService;
import org.jie.service.impl.StudentServiceImpl;

public class StudentServlet extends HttpServlet{

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
		if(null == act || "".equals(act) || "query".equals(act)){
			query(req,resp);
		}
		if("add".equals(act)){
			add(req,resp);
		}
		if("del".equals(act)){
			del(req,resp);
		}
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

	//删除学生
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接收前台传过来的id
		String id = req.getParameter("id");
		//调用service的删除方法
		studentService.deleteStudent(id);
		//请求转发到studentServlet中
		req.getRequestDispatcher("student?act=").forward(req, resp);
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

	//多条件查询，默认查出所有学生
	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String gender = req.getParameter("gender");
		//默认为选中全部
		if(null == gender || "".equals(gender)){
			gender = "2";
		}
		String currentPage = req.getParameter("currentPage");
		//默认为第一页
		if(null == currentPage || "".equals(currentPage)){
			currentPage = "1";
		}
		Pagination page = new Pagination();
		page.setCurrentPage(Integer.parseInt(currentPage));
		//设置每页记录为5
		page.setPageSize(5);
		//调用service中的查询方法
		SearchResult<Student> searchResults = studentService.findStudentByNameAndGender(page,name,Integer.parseInt(gender));
		//将查询到的结果放到request域中
		req.setAttribute("searchResults", searchResults);
		req.setAttribute("name", name);
		req.setAttribute("gender", gender);
		//请求转发到list.jsp页面
		req.getRequestDispatcher("list.jsp").forward(req, resp);
	}
}
