package wicket_lecture;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSetting {

	public static Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/board", "postgres", "postgres");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;

	}

}
