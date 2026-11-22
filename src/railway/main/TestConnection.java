package railway.main;

import railway.database.DBConnection;
import java.sql.Connection;
public class TestConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Connection con = DBConnection.getConnection();
			
			if(con != null) {
				System.out.println("Database connected successfully!!");
				con.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	
}
