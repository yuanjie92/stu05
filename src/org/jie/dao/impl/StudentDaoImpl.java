package org.jie.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jie.dao.StudentDao;
import org.jie.model.Student;
import org.jie.pagination.Pagination;
import org.jie.pagination.SearchResult;
import org.jie.util.DBUtils;

public class StudentDaoImpl implements StudentDao {

	private Connection conn;
	private PreparedStatement ps;
	
	@SuppressWarnings("resource")
	@Override
	public SearchResult<Student> queryStudentByNameAndGenderWithPagination(Pagination page, String name, int gender) {
		conn = DBUtils.getConn();
		SearchResult<Student> result = new SearchResult<Student>();
		List<Student> list = new ArrayList<Student>();
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		sb.append("SELECT * FROM tb_student WHERE 1=1 ");
		sb2.append("SELECT COUNT(1) FROM tb_student WHERE 1=1 ");
		if(null != name && !"".equals(name)){
			sb.append(" AND name=? ");
			sb2.append(" AND name=? ");
		}
		if(2 != gender){
			sb.append(" AND gender=? ");
			sb2.append(" AND gender=? ");
		}
		sb.append(" LIMIT ?,? ");
		try {
			ps = conn.prepareStatement(sb.toString());
			int index = 0;
			int indexCount = 0;
			if(null != name && !"".equals(name)){
				ps.setString(++index, name);
			}
			if(2 != gender){
				ps.setInt(++index, gender);
			}
			ps.setInt(++index, (page.getCurrentPage()-1)*page.getPageSize());
			ps.setInt(++index, page.getPageSize());
			rs = ps.executeQuery();
			if(rs == null){
				return null;
			}
			while(rs.next()){
				Student stu = new Student();
				stu.setBirthday(rs.getTimestamp("birthday"));
				stu.setGender(rs.getInt("gender"));
				stu.setGrade(rs.getString("grade"));
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				list.add(stu);
			}
			ps = null;
			ps = conn.prepareStatement(sb2.toString());
			if(null != name && !"".equals(name)){
				ps.setString(++indexCount, name);
			}
			if(2 != gender){
				ps.setInt(++indexCount, gender);
			}
			rs = ps.executeQuery();
			if(rs == null){
				return null;
			}
			int count = 0;
			if(rs.next()){
				count = rs.getInt(1);
			}
			page.setTotalCount(count);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps, rs);
		}
		result.setPage(page);
		result.setResults(list);
		return result;
	}

	@Override
	public boolean saveStudent(Student stu) {
		conn = DBUtils.getConn();
		boolean flag = false;
		String sql = "INSERT INTO tb_student (name,grade,gender,birthday) VALUES (?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, stu.getName());
			ps.setString(2, stu.getGrade());
			ps.setInt(3, stu.getGender());
			ps.setTimestamp(4, new Timestamp(stu.getBirthday().getTime()));
			int rs = ps.executeUpdate();
			if( rs > 0){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps);
		}
		return flag;
	}

	@Override
	public boolean deleteStudentById(int stuId) {
		conn = DBUtils.getConn();
		boolean flag = false;
		String sql = "DELETE FROM tb_student WHERE id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, stuId);
			int rs = ps.executeUpdate();
			if(rs > 0){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps);
		}
		return flag;
	}

	@Override
	public Student queryStudentById(int stuId) {
		conn = DBUtils.getConn();
		Student stu = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM tb_student WHERE id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, stuId);
			rs = ps.executeQuery();
			if(rs == null){
				return null;
			}
			if(rs.next()){
				stu = new Student();
				stu.setBirthday(rs.getTimestamp("birthday"));
				stu.setGender(rs.getInt("gender"));
				stu.setGrade(rs.getString("grade"));
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps, rs);
		}
		return stu;
	}

	@Override
	public boolean updateStudentById(Student stu) {
		conn = DBUtils.getConn();
		boolean flag = false;
		String sql = "UPDATE tb_student SET name=?,grade=?,gender=?,birthday=? WHERE id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, stu.getName());
			ps.setString(2, stu.getGrade());
			ps.setInt(3, stu.getGender());
			ps.setTimestamp(4, new Timestamp(stu.getBirthday().getTime()));
			ps.setInt(5, stu.getId());
			int rs = ps.executeUpdate();
			if(rs > 0){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps);
		}
		return flag;
	}
	
	
}
