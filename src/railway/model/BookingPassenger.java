package railway.model;

public class BookingPassenger {

    private int bookingId;
    private int passengerId;
    private Integer seatId;

    public BookingPassenger(int bookingId, int passengerId, Integer seatId) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.seatId = seatId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId
                + " | Passenger ID: " + passengerId
                + " | Seat ID: " + seatId;
    }
}