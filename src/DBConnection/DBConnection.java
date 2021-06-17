package DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
	public static Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "green";
		String pw = "green1234";
		String driver = "oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(Connection c, PreparedStatement p, ResultSet r) {
		try {
			if (r != null)
				r.close();
			if (p != null)
				p.close();
			if (c != null)
				c.close();
		} catch (Exception e) {
		}
	}

	public static void close(Connection c, PreparedStatement p) {
		try {
			if (p != null)
				p.close();
			if (c != null)
				c.close();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		getConnection();
	}
}
