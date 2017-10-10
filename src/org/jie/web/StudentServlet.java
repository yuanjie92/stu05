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
