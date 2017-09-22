package org.jie.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jie.dao.StudentDao;
import org.jie.dao.impl.StudentDaoImpl;
import org.jie.model.Student;
import org.jie.pagination.Pagination;
import org.jie.pagination.SearchResult;
import org.jie.service.StudentService;

public class StudentServiceImpl implements StudentService {
	
	private StudentDao studentDao = new StudentDaoImpl();

	@Override
	public SearchResult<Student> findStudentByNameAndGender(Pagination page, String name, int gender) {
		//调用dao的查询方法，并且将结果返回
		return studentDao.queryStudentByNameAndGenderWithPagination(page,name,gender);
	}

	@Override
	public void addStudent(String name, String grade, String gender, String birthday) {
		Student stu = new Student();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			stu.setBirthday(sdf.parse(birthday));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		stu.setGender(Integer.parseInt(gender));
		stu.setGrade(grade);
		stu.setName(name);
		//调用dao的添加方法
		studentDao.saveStudent(stu);
	}

	@Override
	public void deleteStudent(String id) {
		int stuId = Integer.parseInt(id);
		//调用dao的删除方法
		studentDao.deleteStudentById(stuId);
	}

	@Override
	public Student findStudentById(String id) {
		int stuId = Integer.parseInt(id);
		//调用dao的查询方法
		return studentDao.queryStudentById(stuId);
	}

	@Override
	public void updateStudent(String id, String name, String grade, String gender, String birthday) {
		Student stu = new Student();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			stu.setBirthday(sdf.parse(birthday));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		stu.setGender(Integer.parseInt(gender));
		stu.setGrade(grade);
		stu.setName(name);
		stu.setId(Integer.parseInt(id));
		//调用dao的修改方法
		studentDao.updateStudentById(stu);
	}
	
}
