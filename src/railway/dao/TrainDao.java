package railway.dao;

import railway.database.DBConnection;
import railway.model.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrainDao {

	public ArrayList<Train> getAllTrains(){
		ArrayList<Train> trains = new ArrayList<>();
		
		String query = "Select * from trains";
		
		try (
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
		){
			while(rs.next()) {
				Train train = new Train(
						rs.getInt("train_id"),
						rs.getString("train_number"),
						rs.getString("train_name"),
						rs.getString("source"),
						rs.getString("destination")
						);
				
				trains.add(train);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return trains;
	}

}
