package railway.dao;

import railway.database.DBConnection;
import railway.model.BookingPassenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import railway.model.Passenger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import railway.model.TicketPassenger;

public class BookingPassengerDao {

    public boolean addBookingPassenger(BookingPassenger bp) {

        String query = """
                INSERT INTO booking_passengers
                (booking_id, passenger_id, seat_id)
                VALUES (?, ?, ?)
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, bp.getBookingId());
            ps.setInt(2, bp.getPassengerId());

            if (bp.getSeatId() != null) {
                ps.setInt(3, bp.getSeatId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            int rowsInserted = ps.executeUpdate();

            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public ArrayList<Passenger> getPassengersByBookingId(int bookingId) {

        ArrayList<Passenger> passengers = new ArrayList<>();

        String query = """
                SELECT p.passenger_id, p.name, p.age, p.gender
                FROM booking_passengers bp
                JOIN passengers p
                ON bp.passenger_id = p.passenger_id
                WHERE bp.booking_id = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Passenger passenger = new Passenger(
                            rs.getInt("passenger_id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender")
                    );

                    passengers.add(passenger);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return passengers;
    }
    
    public ArrayList<TicketPassenger> getTicketPassengers(int bookingId) {

        ArrayList<TicketPassenger> passengers = new ArrayList<>();

        String query = """
                SELECT
                    p.passenger_id,
                    p.name,
                    p.age,
                    p.gender,
                    s.seat_number,
                    s.seat_class
                FROM booking_passengers bp
                JOIN passengers p
                    ON bp.passenger_id = p.passenger_id
                LEFT JOIN seats s
                    ON bp.seat_id = s.seat_id
                WHERE bp.booking_id = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    TicketPassenger passenger = new TicketPassenger(
                            rs.getInt("passenger_id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("seat_number"),
                            rs.getString("seat_class")
                    );

                    passengers.add(passenger);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return passengers;
    }
    
    public boolean addBookingPassenger(Connection con, BookingPassenger bp) {

        String sql = "INSERT INTO booking_passengers "
                + "(booking_id, passenger_id, seat_id) "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bp.getBookingId());
            ps.setInt(2, bp.getPassengerId());

            if (bp.getSeatId() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, bp.getSeatId());
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}