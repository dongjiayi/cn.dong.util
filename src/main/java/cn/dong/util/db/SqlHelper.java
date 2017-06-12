package cn.dong.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.dong.util.config.PropertiesManager;

public class SqlHelper {


	private Connection conn = null;
	private ConnectionPool pool = null;
	private Boolean autoCommit = true;

	public SqlHelper(String driverName, String connName, String uidName,
			String pwdName) throws Exception {

		String driverStr = PropertiesManager.GetString(driverName);
		if (driverStr == null)
			throw new Exception("DriverName \"" + driverName
					+ "\" can not be found");

		String connStr = PropertiesManager.GetString(connName);
		if (connStr == null)
			throw new Exception("ConnectionName \"" + connName
					+ "\" can not be found");

		String uid = PropertiesManager.GetString(uidName);
		if (uid == null)
			throw new Exception("UserName \"" + uidName + "\" can not be found");

		String pwd = PropertiesManager.GetString(pwdName);
		if (pwd == null)
			throw new Exception("PwdName \"" + pwdName + "\" can not be found");

		pool = new ConnectionPool(driverStr, connStr, uid, pwd);
		// Class.forName(driverStr);
		// conn = DriverManager.getConnection(connStr, uid, pwd);
	}

	public <T> List<T> getList(String sql, ISqlAssistant assistent)
			throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			if (conn == null) {
				conn = pool.connection();
				conn.setAutoCommit(autoCommit);
			}

			stmt = conn.prepareStatement(sql);
			assistent.prepare(stmt);
			rs = stmt.executeQuery();
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				list.add(assistent.<T> extract(rs));
			}
			if (autoCommit)
				conn = null;
			return list;
		} catch (SQLException e) {
			if (!autoCommit) {
				autoCommit = true;
				if (!conn.isClosed())
					conn.rollback();
			}
			conn = null;
			throw e;
		} finally {
			ConnectionPool.close(rs, stmt);
		}
	}

	public <T> T getItem(String sql, ISqlAssistant assistent)
			throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			if (conn == null) {
				conn = pool.connection();
				conn.setAutoCommit(autoCommit);
			}

			stmt = conn.prepareStatement(sql);
			assistent.prepare(stmt);
			rs = stmt.executeQuery();
			T t = null;
			if (rs.next()) {
				t = assistent.<T> extract(rs);
			}

			if (autoCommit)
				conn = null;
			return t;
		} catch (SQLException e) {
			if (!autoCommit) {
				autoCommit = true;
				if (!conn.isClosed())
					conn.rollback();
			}
			conn = null;
			throw e;
		} finally {
			ConnectionPool.close(rs, stmt);
		}
	}

	public int executeNonQuery(String sql, ISqlAssistant assistent)
			throws SQLException {
		PreparedStatement stmt = null;
		try {
			if (conn == null) {
				conn = pool.connection();
				conn.setAutoCommit(autoCommit);
			}
			stmt = conn.prepareStatement(sql);
			assistent.prepare(stmt);
			int ret = stmt.executeUpdate();
			stmt.close();
			if (autoCommit)
				conn = null;
			return ret;
		} catch (SQLException e) {
			if (!autoCommit) {
				autoCommit = true;
				if (!conn.isClosed())
					conn.rollback();
			}
			conn = null;
			throw e;
		} finally {
			ConnectionPool.close(stmt);
		}
	}

	public void beginTran() {
		autoCommit = false;
	}

	public void commitAll() throws SQLException {
		if (!conn.isClosed() && !autoCommit) {
			conn.commit();
			autoCommit = true;
		}
	}
}
