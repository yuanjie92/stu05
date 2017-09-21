package org.jie.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
	
	private static Properties pro;
	private static Connection conn;
	
	static {
		pro = new Properties();
		try {
			pro.load(DBUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			Class.forName(pro.getProperty("jdbcName"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConn(){
		try {
			conn = DriverManager.getConnection(pro.getProperty("dbUrl"),pro.getProperty("dbUserName"),pro.getProperty("dbPassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn, PreparedStatement ps, ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Connection conn, PreparedStatement ps){
		close(conn, ps, null);
	}
}
