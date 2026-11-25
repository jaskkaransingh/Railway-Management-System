package railway.dao;

import railway.database.DBConnection;
import railway.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

public class BookingDao {

    public int addBooking(Booking booking) {

        String query = """
                INSERT INTO bookings
                (user_id, schedule_id, pnr, booking_status, total_amount)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    query,
                    PreparedStatement.RETURN_GENERATED_KEYS
            )
        ) {

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getScheduleId());
            ps.setString(3, booking.getPnr());
            ps.setString(4, booking.getBookingStatus());
            ps.setDouble(5, booking.getTotalAmount());

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    public Booking getBookingByPNR(String pnr) {

        String query = """
                SELECT *
                FROM bookings
                WHERE pnr = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, pnr);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Booking booking = new Booking(
                            rs.getInt("booking_id"),
                            rs.getInt("user_id"),
                            rs.getInt("schedule_id"),
                            rs.getString("pnr"),
                            rs.getString("booking_status"),
                            rs.getTimestamp("booking_date").toLocalDateTime(),
                            rs.getDouble("total_amount")
                    );

                    return booking;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean cancelBooking(String pnr) {

        String query = """
                UPDATE bookings
                SET booking_status = 'CANCELLED'
                WHERE pnr = ?
                AND booking_status = 'CONFIRMED'
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, pnr);

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public int addBooking(Connection con, Booking booking) {

        String sql = "INSERT INTO bookings "
                + "(user_id, schedule_id, pnr, booking_status, total_amount) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getScheduleId());
            ps.setString(3, booking.getPnr());
            ps.setString(4, booking.getBookingStatus());
            ps.setDouble(5, booking.getTotalAmount());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    public boolean cancelBooking(Connection con, String pnr) {

        String query = """
                UPDATE bookings
                SET booking_status = 'CANCELLED'
                WHERE pnr = ?
                AND booking_status = 'CONFIRMED'
                """;

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, pnr);

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean confirmBooking(Connection con, String pnr) {

        String query = """
                UPDATE bookings
                SET booking_status = 'CONFIRMED'
                WHERE pnr = ?
                AND booking_status = 'PENDING'
                """;

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, pnr);

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Booking> getAllBookings() {

        ArrayList<Booking> bookings = new ArrayList<>();

        String query = """
                SELECT *
                FROM bookings
                ORDER BY booking_date DESC
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("schedule_id"),
                        rs.getString("pnr"),
                        rs.getString("booking_status"),
                        rs.getTimestamp("booking_date").toLocalDateTime(),
                        rs.getDouble("total_amount")
                );

                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
    
}