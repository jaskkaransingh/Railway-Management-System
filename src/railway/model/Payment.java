package railway.model;

import java.time.LocalDateTime;

public class Payment {

    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentStatus;
    private LocalDateTime paymentDate;


    public Payment(int bookingId, double amount, String paymentStatus) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public Payment(int paymentId, int bookingId, double amount,
                   String paymentStatus, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Payment ID: " + paymentId + " | Booking ID: " + bookingId + " | Amount: ₹" + amount
                + " | Status: " + paymentStatus + " | Date: " + paymentDate;
    }
}