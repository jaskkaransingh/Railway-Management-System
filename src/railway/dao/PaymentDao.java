package railway.dao;

import railway.database.DBConnection;
import railway.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PaymentDao {

    
    public int addPayment(Payment payment) {

        String query = """
                INSERT INTO payments (booking_id, amount, payment_status)
                VALUES (?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentStatus());

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {

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


   
    public Payment getPaymentByBookingId(int bookingId) {

        String query = """
                SELECT *
                FROM payments
                WHERE booking_id = ?
                ORDER BY payment_id DESC
                LIMIT 1
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Timestamp timestamp = rs.getTimestamp("payment_date");

                    LocalDateTime paymentDate = timestamp != null
                            ? timestamp.toLocalDateTime()
                            : null;

                    return new Payment(
                            rs.getInt("payment_id"),
                            rs.getInt("booking_id"),
                            rs.getDouble("amount"),
                            rs.getString("payment_status"),
                            paymentDate
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


   
    public boolean updatePaymentStatus(int paymentId, String status) {

        String query = """
                UPDATE payments
                SET payment_status = ?
                WHERE payment_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, paymentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean hasSuccessfulPayment(int bookingId) {

        String query = """
                SELECT COUNT(*)
                FROM payments
                WHERE booking_id = ?
                AND payment_status = 'SUCCESS'
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}