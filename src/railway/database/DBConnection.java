package railway.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
	
	private static final String URL = "jdbc:mysql://localhost:3306/railway_reservation";
	private static final String USER = "root";
	private static final String PASSWORD = "tiger";

	public static Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return DriverManager.getConnection(URL,USER,PASSWORD);
		

	}

}
