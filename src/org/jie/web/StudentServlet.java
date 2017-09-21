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

	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String grade = req.getParameter("grade");
		String gender = req.getParameter("gender");
		String birthday = req.getParameter("birthday");
		studentService.updateStudent(id,name,grade,gender,birthday);
		resp.sendRedirect("student");
	}

	private void showStu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		Student stu = studentService.findStudentById(id);
		req.setAttribute("stu", stu);
		req.getRequestDispatcher("edit.jsp").forward(req, resp);
	}

	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		studentService.deleteStudent(id);
		req.getRequestDispatcher("student?act=").forward(req, resp);
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String name = req.getParameter("name");
		String grade = req.getParameter("grade");
		String gender = req.getParameter("gender");
		String birthday = req.getParameter("birthday");
		studentService.addStudent(name,grade,gender,birthday);
		resp.sendRedirect("student");
				
	}

	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String gender = req.getParameter("gender");
		if(null == gender || "".equals(gender)){
			gender = "2";
		}
		String currentPage = req.getParameter("currentPage");
		if(null == currentPage || "".equals(currentPage)){
			currentPage = "1";
		}
		Pagination page = new Pagination();
		page.setCurrentPage(Integer.parseInt(currentPage));
		page.setPageSize(5);
		SearchResult<Student> searchResults = studentService.findStudentByNameAndGender(page,name,Integer.parseInt(gender));
		req.setAttribute("searchResults", searchResults);
		req.setAttribute("name", name);
		req.setAttribute("gender", gender);
		req.getRequestDispatcher("list.jsp").forward(req, resp);
	}
}
