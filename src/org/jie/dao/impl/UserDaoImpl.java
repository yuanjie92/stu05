package org.jie.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jie.dao.UserDao;
import org.jie.model.User;
import org.jie.util.DBUtils;

public class UserDaoImpl implements UserDao {

	private Connection conn;
	private PreparedStatement ps;
	
	@Override
	public User queryUserByUserName(String userName) {
		conn = DBUtils.getConn();
		User user = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM tb_user WHERE userName=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if(rs == null){
				return null;
			}
			if(rs.next()){
				user = new User();
				user.setUserName(rs.getString("userName"));
				user.setUserPwd(rs.getString("userPwd"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps, rs);
		}
		return user;
	}
	
}
