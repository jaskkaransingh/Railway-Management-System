package railway.model;

import java.time.LocalDateTime;

public class Booking {

    private int bookingId;
    private int userId;
    private int scheduleId;
    private String pnr;
    private String bookingStatus;
    private LocalDateTime bookingDate;
    private double totalAmount;

    public Booking(int bookingId, int userId, int scheduleId,
                   String pnr, String bookingStatus,
                   LocalDateTime bookingDate, double totalAmount) {

        this.bookingId = bookingId;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.pnr = pnr;
        this.bookingStatus = bookingStatus;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
    }

    public Booking(int userId, int scheduleId,
                  String pnr, String bookingStatus,
                  double totalAmount) {

        this.userId = userId;
        this.scheduleId = scheduleId;
        this.pnr = pnr;
        this.bookingStatus = bookingStatus;
        this.totalAmount = totalAmount;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public String getPnr() {
        return pnr;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId
                + " | PNR: " + pnr
                + " | User ID: " + userId
                + " | Schedule ID: " + scheduleId
                + " | Status: " + bookingStatus
                + " | Amount: ₹" + totalAmount;
    }
}