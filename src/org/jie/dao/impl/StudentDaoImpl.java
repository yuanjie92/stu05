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

	// 按条件查询学生，并分页
	@SuppressWarnings("resource")
	@Override
	public SearchResult<Student> queryStudentByNameAndGenderWithPagination(Pagination page, String name, int gender) {
		// 获取连接
		conn = DBUtils.getConn();
		SearchResult<Student> result = new SearchResult<Student>();
		List<Student> list = new ArrayList<Student>();
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		// 1=1是为了下面加and做前提
		sb.append("SELECT * FROM tb_student WHERE 1=1 ");
		sb2.append("SELECT COUNT(1) FROM tb_student WHERE 1=1 ");
		// 只有查询条件写了姓名才会执行
		if (null != name && !"".equals(name)) {
			sb.append(" AND name=? ");
			sb2.append(" AND name=? ");
		}
		// 只有选择了性别才会执行
		if (2 != gender) {
			sb.append(" AND gender=? ");
			sb2.append(" AND gender=? ");
		}
		sb.append(" LIMIT ?,? ");
		try {
			// 预编译
			ps = conn.prepareStatement(sb.toString());
			int index = 0;
			int indexCount = 0;
			// 只有查询条件写了姓名才会执行
			if (null != name && !"".equals(name)) {
				ps.setString(++index, name);
			}
			// 只有选择了性别才会执行
			if (2 != gender) {
				ps.setInt(++index, gender);
			}
			// 设置分页数据，第一个参数是从第几条数据开始，第二个参数是每页有多少条记录
			ps.setInt(++index, (page.getCurrentPage() - 1) * page.getPageSize());
			ps.setInt(++index, page.getPageSize());
			// 得到结果集，查出学生集合
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				Student stu = new Student();
				stu.setBirthday(rs.getTimestamp("birthday"));
				stu.setGender(rs.getInt("gender"));
				stu.setGrade(rs.getString("grade"));
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				list.add(stu);
			}
			// 先给预编译置空
			ps = null;
			// 预编译
			ps = conn.prepareStatement(sb2.toString());
			// 只有查询条件写了姓名才会执行
			if (null != name && !"".equals(name)) {
				ps.setString(++indexCount, name);
			}
			// 只有选择了性别才会执行
			if (2 != gender) {
				ps.setInt(++indexCount, gender);
			}
			// 得到结果集，共有多少条记录
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			int count = 0;
			if (rs.next()) {
				// 得到记录数
				count = rs.getInt(1);
			}
			// 把结果赋给page的总记录数
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

	// 添加学生
	@Override
	public boolean saveStudent(Student stu) {
		// 建立连接
		conn = DBUtils.getConn();
		boolean flag = false;
		// 编写sql语句
		String sql = "INSERT INTO tb_student (name,grade,gender,birthday) VALUES (?,?,?,?)";
		try {
			// 预编译
			ps = conn.prepareStatement(sql);
			// 依次给4个问号传值
			ps.setString(1, stu.getName());
			ps.setString(2, stu.getGrade());
			ps.setInt(3, stu.getGender());
			ps.setTimestamp(4, new Timestamp(stu.getBirthday().getTime()));
			// 得到结果
			int rs = ps.executeUpdate();
			if (rs > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps);
		}
		return flag;
	}

	// 通过id删除学生
	@Override
	public boolean deleteStudentById(int stuId) {
		// 建立连接
		conn = DBUtils.getConn();
		boolean flag = false;
		// 编写sql语句
		String sql = "DELETE FROM tb_student WHERE id=?";
		try {
			// 预编译
			ps = conn.prepareStatement(sql);
			// 给第一个问号传值
			ps.setInt(1, stuId);
			// 得到结果
			int rs = ps.executeUpdate();
			if (rs > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps);
		}
		return flag;
	}

	// 通过id查询学生
	@Override
	public Student queryStudentById(int stuId) {
		conn = DBUtils.getConn();
		Student stu = null;
		ResultSet rs = null;
		// 写sql语句
		String sql = "SELECT * FROM tb_student WHERE id=?";
		try {
			// 预编译
			ps = conn.prepareStatement(sql);
			// 给第一个问号传值
			ps.setInt(1, stuId);
			// 得到结果
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			if (rs.next()) {
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

	// 通过id修改学生信息
	@Override
	public boolean updateStudentById(Student stu) {
		// 获取连接
		conn = DBUtils.getConn();
		boolean flag = false;
		// 编写sql语句
		String sql = "UPDATE tb_student SET name=?,grade=?,gender=?,birthday=? WHERE id=?";
		try {
			// 预编译
			ps = conn.prepareStatement(sql);
			// 依次给5个问号传值
			ps.setString(1, stu.getName());
			ps.setString(2, stu.getGrade());
			ps.setInt(3, stu.getGender());
			ps.setTimestamp(4, new Timestamp(stu.getBirthday().getTime()));
			ps.setInt(5, stu.getId());
			// 得到结果
			int rs = ps.executeUpdate();
			if (rs > 0) {
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
