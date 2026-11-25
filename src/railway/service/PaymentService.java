
package railway.service;

import railway.dao.BookingDao;
import railway.dao.PaymentDao;
import railway.dao.SeatDao;
import railway.database.DBConnection;
import railway.model.Booking;
import railway.model.Payment;
import railway.messaging.BookingMessageProducer;

import java.sql.Connection;
import java.sql.SQLException;
import railway.payment.PaymentMethod;

public class PaymentService {

    private PaymentDao paymentDao;
    private BookingDao bookingDao;
    private SeatDao seatDao;
    private BookingMessageProducer messageProducer;

    public PaymentService() {

        paymentDao = new PaymentDao();
        bookingDao = new BookingDao();
        seatDao = new SeatDao();
        messageProducer = new BookingMessageProducer();
    }

    public Payment processPayment(String pnr, PaymentMethod paymentMethod) {

        Booking booking = bookingDao.getBookingByPNR(pnr);

        if (booking == null) {
            System.out.println("Booking not found!");
            return null;
        }

        if (!booking.getBookingStatus().equals("PENDING")) {
            System.out.println(
                "Payment can only be processed for pending bookings!"
            );
            return null;
        }

        if (paymentDao.hasSuccessfulPayment(booking.getBookingId())) {
            System.out.println("Payment has already been completed!");
            return null;
        }
        if (paymentMethod == null) {
            System.out.println("Invalid payment method!");
            return null;
        }

        boolean paymentSuccess =
                paymentMethod.pay(booking.getTotalAmount());

        String status = paymentSuccess ? "SUCCESS" : "FAILED";

        Payment payment = new Payment(
            booking.getBookingId(),
            booking.getTotalAmount(),
            status
        );

        int paymentId = paymentDao.addPayment(payment);

        if (paymentId == -1) {
            System.out.println("Payment record could not be created!");
            return null;
        }

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            if (paymentSuccess) {

                boolean confirmed =
                    bookingDao.confirmBooking(con, pnr);

                if (!confirmed) {
                    throw new SQLException(
                        "Booking confirmation failed!"
                    );
                }

                con.commit();

                System.out.println("Payment processed successfully!");
                System.out.println("Booking confirmed!");
                System.out.println("Payment Status: SUCCESS");

                // Send JMS notification after successful commit
                messageProducer.sendBookingConfirmation(pnr);

            } else {

                boolean cancelled =
                    bookingDao.cancelBooking(con, pnr);

                if (!cancelled) {
                    throw new SQLException(
                        "Booking cancellation failed!"
                    );
                }

                boolean released =
                    seatDao.releaseSeats(con, pnr);

                if (!released) {
                    throw new SQLException("Seat release failed!");
                }

                con.commit();

                System.out.println("Payment failed!");
                System.out.println("Booking cancelled.");
                System.out.println("Reserved seats released.");
            }

            System.out.println(
                "Amount: ₹" + booking.getTotalAmount()
            );

            return paymentDao.getPaymentByBookingId(
                booking.getBookingId()
            );

        } catch (SQLException e) {

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            System.out.println("Payment processing failed!");
            e.printStackTrace();

            return null;

        } finally {

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Payment getPaymentByBookingId(int bookingId) {
        return paymentDao.getPaymentByBookingId(bookingId);
    }

    public boolean updatePaymentStatus(int paymentId, String status) {
        return paymentDao.updatePaymentStatus(paymentId, status);
    }
}
