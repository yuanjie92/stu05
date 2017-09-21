package org.jie.dao;

import org.jie.model.Student;
import org.jie.pagination.Pagination;
import org.jie.pagination.SearchResult;

public interface StudentDao {

	SearchResult<Student> queryStudentByNameAndGenderWithPagination(Pagination page, String name, int gender);

	boolean saveStudent(Student stu);

	boolean deleteStudentById(int stuId);

	Student queryStudentById(int stuId);

	boolean updateStudentById(Student stu);

}
