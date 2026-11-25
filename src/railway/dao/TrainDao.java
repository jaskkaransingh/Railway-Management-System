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
	public boolean addTrain(Train train) {

	    String query = """
	            INSERT INTO trains
	            (train_number, train_name, source, destination)
	            VALUES (?, ?, ?, ?)
	            """;

	    try (
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(query);
	    ) {

	        ps.setString(1, train.getTrainNumber());
	        ps.setString(2, train.getTrainName());
	        ps.setString(3, train.getSource());
	        ps.setString(4, train.getDestination());

	        int rowsInserted = ps.executeUpdate();

	        return rowsInserted > 0;

	    } catch (Exception e) {

	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean updateTrain(Train train) {

	    String query = """
	            UPDATE trains
	            SET train_number = ?,
	                train_name = ?,
	                source = ?,
	                destination = ?
	            WHERE train_id = ?
	            """;

	    try (
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(query);
	    ) {

	        ps.setString(1, train.getTrainNumber());
	        ps.setString(2, train.getTrainName());
	        ps.setString(3, train.getSource());
	        ps.setString(4, train.getDestination());
	        ps.setInt(5, train.getTrainId());

	        int rowsUpdated = ps.executeUpdate();

	        return rowsUpdated > 0;

	    } catch (Exception e) {

	        e.printStackTrace();
	        return false;
	    }
	}

}
