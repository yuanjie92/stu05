package org.jie.service;

import org.jie.model.Student;
import org.jie.pagination.Pagination;
import org.jie.pagination.SearchResult;

public interface StudentService {

	SearchResult<Student> findStudentByNameAndGender(Pagination page, String name, int gender);

	void addStudent(String name, String grade, String gender, String birthday);

	void deleteStudent(String id);

	Student findStudentById(String id);

	void updateStudent(String id, String name, String grade, String gender, String birthday);

}
