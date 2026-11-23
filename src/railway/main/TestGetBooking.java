package railway.main;

import railway.dao.BookingDao;
import railway.model.Booking;

public class TestGetBooking {

    public static void main(String[] args) {

        BookingDao dao = new BookingDao();

        Booking booking = dao.getBookingByPNR("PNR100000001");

        if (booking != null) {
            System.out.println("Booking found!");
            System.out.println(booking);
        } else {
            System.out.println("Booking not found!");
        }
    }
}