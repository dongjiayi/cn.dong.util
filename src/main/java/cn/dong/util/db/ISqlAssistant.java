package cn.dong.util.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISqlAssistant {
	
	public abstract void prepare(PreparedStatement stmt) throws SQLException;

	public abstract <T> T extract(ResultSet rs) throws SQLException;

}
