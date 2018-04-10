package wicket_lecture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public static final String URL = "jdbc:postgresql://localhost:5432/board";
//	public static final String URL = "jdbc:postgresql://ec2-54-92-8-231.ap-northeast-1.compute.amazonaws.com:5432/board";

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(URL,"postgres","pilot15223");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
