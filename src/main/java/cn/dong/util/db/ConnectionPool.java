package cn.dong.util.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPool {

    private BasicDataSource dataSource;  

	public ConnectionPool(String driver, String url, String user,
			String password) {

		this.dataSource = new BasicDataSource();
		this.dataSource.setDriverClassName(driver); //驱动
		
		this.dataSource.setUsername(user); //用户
		this.dataSource.setPassword(password); //密码
		this.dataSource.setUrl(url);
		
		this.dataSource.setInitialSize(10); // 初始的连接数；    
		this.dataSource.setMaxTotal(100);  //最大连接数  
		this.dataSource.setMaxIdle(80);  // 设置最大空闲连接  
		this.dataSource.setMaxWaitMillis(6000);  // 设置最大等待时间  
		this.dataSource.setMinIdle(10);  // 设置最小空闲连接  
	}

	public synchronized Connection connection() {
		try {

			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void close(Connection con) {

		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void close(ResultSet rs, Statement stmt) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception ex) {

		}

	}

	public static void close(Statement stmt) {

		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception ex) {

		}

	}
}
