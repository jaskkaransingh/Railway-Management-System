package railway.service;

import railway.dao.BookingDao;
import railway.dao.PaymentDao;
import railway.model.Booking;
import railway.model.Payment;

public class PaymentService {

    private PaymentDao paymentDao;
    private BookingDao bookingDao;

    public PaymentService() {
        paymentDao = new PaymentDao();
        bookingDao = new BookingDao();
    }

    
    public Payment processPayment(String pnr, boolean paymentSuccess) {

        
        Booking booking = bookingDao.getBookingByPNR(pnr);

        if (booking == null) {
            System.out.println("Booking not found!");
            return null;
        }
 
        if (!booking.getBookingStatus().equals("CONFIRMED")) {
            System.out.println("Payment cannot be processed for this booking!");
            return null;
        }

     
        if (paymentDao.hasSuccessfulPayment(booking.getBookingId())) {
            System.out.println("Payment has already been completed for this booking!");
            return null;
        }
        String status;

        if (paymentSuccess) {
            status = "SUCCESS";
        } else {
            status = "FAILED";
        }

        
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

        System.out.println("Payment processed successfully!");
        System.out.println("Payment Status: " + status);
        System.out.println("Amount: ₹" + booking.getTotalAmount());

        
        return paymentDao.getPaymentByBookingId(
                booking.getBookingId()
        );
    }


    
    public Payment getPaymentByBookingId(int bookingId) {

        return paymentDao.getPaymentByBookingId(bookingId);
    }


    
    public boolean updatePaymentStatus(int paymentId, String status) {

        return paymentDao.updatePaymentStatus(paymentId, status);
    }
}