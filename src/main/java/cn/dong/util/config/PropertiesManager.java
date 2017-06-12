package cn.dong.util.config;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class PropertiesManager {

	private static Properties properties = null;
	static {
		try {
			//System.out.print(PropertiesManager.class.getClassLoader().getResource("").getPath());
			FileInputStream file = new FileInputStream(PropertiesManager.class.getClassLoader().getResource("").getPath() + "/app.properties");
			properties = new Properties();
			properties.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String GetString(String key) {
		if (properties == null || !properties.containsKey(key))
			return null;
		
		return properties.getProperty(key);
	}

	public static Integer GetInt(String key) throws NumberFormatException {
		if (properties == null || !properties.containsKey(key))
			return null;

		return Integer.parseInt(properties.getProperty(key));
	}
	
	public static Float GetFloat(String key) throws NumberFormatException {
		if (properties == null || !properties.containsKey(key))
			return null;

		return Float.parseFloat(properties.getProperty(key));
	}
	
	public static Date GetDateTime(String key) throws ParseException {
		//Calendar d = Calendar.getInstance();
		if (properties == null || !properties.containsKey(key))
			return null;

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.parse(properties.getProperty(key));
	}
}
