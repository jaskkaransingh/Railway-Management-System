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
}