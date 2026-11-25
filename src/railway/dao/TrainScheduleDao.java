package railway.dao;

import railway.database.DBConnection;
import railway.model.TrainSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class TrainScheduleDao {
	
	public ArrayList<TrainSchedule> searchTrains(
	        String source,
	        String destination,
	        LocalDate journeyDate) {

	    ArrayList<TrainSchedule> trainSchedules = new ArrayList<>();

	    String query = """
	            SELECT
	                s.schedule_id,
	                t.train_id,
	                t.train_number,
	                t.train_name,
	                t.source,
	                t.destination,
	                s.journey_date,
	                s.departure_time,
	                s.arrival_time
	            FROM trains t
	            JOIN schedules s
	            ON t.train_id = s.train_id
	            WHERE LOWER(t.source) = LOWER(?)
	            AND LOWER(t.destination) = LOWER(?)
	            AND s.journey_date = ?
	            """;

	    try (
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(query)
	    ) {

	        ps.setString(1, source);
	        ps.setString(2, destination);
	        ps.setDate(3, java.sql.Date.valueOf(journeyDate));

	        try (ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {

	                TrainSchedule trainSchedule = new TrainSchedule(
	                        rs.getInt("schedule_id"),
	                        rs.getInt("train_id"),
	                        rs.getString("train_number"),
	                        rs.getString("train_name"),
	                        rs.getString("source"),
	                        rs.getString("destination"),
	                        rs.getDate("journey_date").toLocalDate(),
	                        rs.getTime("departure_time").toLocalTime(),
	                        rs.getTime("arrival_time").toLocalTime()
	                );

	                trainSchedules.add(trainSchedule);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return trainSchedules;
	}

    public ArrayList<TrainSchedule> getAllTrainSchedules() {

        ArrayList<TrainSchedule> trainSchedules = new ArrayList<>();

        String query = """
                SELECT
                    s.schedule_id,
                    t.train_id,
                    t.train_number,
                    t.train_name,
                    t.source,
                    t.destination,
                    s.journey_date,
                    s.departure_time,
                    s.arrival_time
                FROM trains t
				JOIN schedules s
				ON t.train_id = s.train_id
				ORDER BY s.schedule_id ASC
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                TrainSchedule trainSchedule = new TrainSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("train_id"),
                        rs.getString("train_number"),
                        rs.getString("train_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getDate("journey_date").toLocalDate(),
                        rs.getTime("departure_time").toLocalTime(),
                        rs.getTime("arrival_time").toLocalTime()
                );

                trainSchedules.add(trainSchedule);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return trainSchedules;
    }
    
    public TrainSchedule getTrainScheduleById(int scheduleId) {

        String query = """
                SELECT
                    s.schedule_id,
                    t.train_id,
                    t.train_number,
                    t.train_name,
                    t.source,
                    t.destination,
                    s.journey_date,
                    s.departure_time,
                    s.arrival_time
                FROM trains t
                JOIN schedules s
                ON t.train_id = s.train_id
                WHERE s.schedule_id = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, scheduleId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new TrainSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("train_id"),
                        rs.getString("train_number"),
                        rs.getString("train_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getDate("journey_date").toLocalDate(),
                        rs.getTime("departure_time").toLocalTime(),
                        rs.getTime("arrival_time").toLocalTime()
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}