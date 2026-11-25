package railway.dao;

import railway.database.DBConnection;
import railway.model.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleDao {

    public ArrayList<Schedule> getAllSchedules() {

        ArrayList<Schedule> schedules = new ArrayList<>();

        String query = "SELECT * FROM schedules";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Schedule schedule = new Schedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("train_id"),
                        rs.getDate("journey_date").toLocalDate(),
                        rs.getTime("departure_time").toLocalTime(),
                        rs.getTime("arrival_time").toLocalTime()
                );

                schedules.add(schedule);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return schedules;
    }
    
    public Schedule getScheduleById(int scheduleId) {

        String sql = "SELECT * FROM schedules WHERE schedule_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Schedule schedule = new Schedule(
                            rs.getInt("schedule_id"),
                            rs.getInt("train_id"),
                            rs.getDate("journey_date").toLocalDate(),
                            rs.getTime("departure_time").toLocalTime(),
                            rs.getTime("arrival_time").toLocalTime()
                    );

                    return schedule;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean addSchedule(Schedule schedule) {

        String query = """
                INSERT INTO schedules
                (train_id, journey_date, departure_time, arrival_time)
                VALUES (?, ?, ?, ?)
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ) {

            ps.setInt(1, schedule.getTrainId());

            ps.setDate(2, java.sql.Date.valueOf(
                    schedule.getJourneyDate()));

            ps.setTime(3, java.sql.Time.valueOf(
                    schedule.getDepartureTime()));
            
            ps.setTime(4, java.sql.Time.valueOf(
                    schedule.getArrivalTime()));

            int rowsInserted = ps.executeUpdate();

            return rowsInserted > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}