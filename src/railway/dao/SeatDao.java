package railway.dao;

import railway.database.DBConnection;
import railway.model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeatDao {

    public ArrayList<Seat> getAvailableSeats(int scheduleId) {

        ArrayList<Seat> seats = new ArrayList<>();

        String query = """
                SELECT * FROM seats
                WHERE schedule_id = ? AND status = 'AVAILABLE'
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, scheduleId);

             try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Seat seat = new Seat(
                            rs.getInt("seat_id"),
                            rs.getInt("schedule_id"),
                            rs.getString("seat_number"),
                            rs.getString("seat_class"),
                            rs.getString("status")
                    );

                    seats.add(seat);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seats;
    }
    public boolean bookSeat(int seatId) {

        String query = """
                UPDATE seats
                SET status = 'BOOKED'
                WHERE seat_id = ? AND status = 'AVAILABLE'
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, seatId);

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public Seat getSeatById(int seatId) {

        String query = """
                SELECT *
                FROM seats
                WHERE seat_id = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, seatId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Seat(
                            rs.getInt("seat_id"),
                            rs.getInt("schedule_id"),
                            rs.getString("seat_number"),
                            rs.getString("seat_class"),
                            rs.getString("status")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean bookSeat(Connection con, int seatId, int scheduleId) {

        String sql = "UPDATE seats "
                + "SET status = 'BOOKED' "
                + "WHERE seat_id = ? "
                + "AND schedule_id = ? "
                + "AND status = 'AVAILABLE'";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, seatId);
            ps.setInt(2, scheduleId);

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean releaseSeats(Connection con, String pnr) {

        String sql = """
                UPDATE seats
                SET status = 'AVAILABLE'
                WHERE seat_id IN (
                    SELECT bp.seat_id
                    FROM booking_passengers bp
                    JOIN bookings b
                        ON bp.booking_id = b.booking_id
                    WHERE b.pnr = ?
                )
                AND status = 'BOOKED'
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean generateSeats(int scheduleId) {

        String checkQuery =
                "SELECT COUNT(*) FROM seats WHERE schedule_id = ?";

        String insertQuery = """
                INSERT INTO seats
                (schedule_id, seat_number, seat_class, status)
                VALUES (?, ?, ?, 'AVAILABLE')
                """;

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (
                PreparedStatement checkPs =
                        con.prepareStatement(checkQuery);
                PreparedStatement insertPs =
                        con.prepareStatement(insertQuery)
            ) {

                // Prevent duplicate seat generation
                checkPs.setInt(1, scheduleId);

                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        con.rollback();
                        System.out.println(
                                "Seats already exist for this schedule!"
                        );
                        return false;
                    }
                }

                // Generate 4 AC seats
                for (int i = 1; i <= 4; i++) {

                    insertPs.setInt(1, scheduleId);
                    insertPs.setString(2, "A" + i);
                    insertPs.setString(3, "AC");
                    insertPs.addBatch();
                }

                // Generate 4 Sleeper seats
                for (int i = 1; i <= 4; i++) {

                    insertPs.setInt(1, scheduleId);
                    insertPs.setString(2, "B" + i);
                    insertPs.setString(3, "SLEEPER");
                    insertPs.addBatch();
                }

                insertPs.executeBatch();
                con.commit();

                System.out.println(
                        "8 seats generated successfully for Schedule ID: "
                        + scheduleId
                );

                return true;

            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}