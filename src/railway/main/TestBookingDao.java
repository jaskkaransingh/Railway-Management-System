package railway.main;

import railway.dao.BookingDao;
import railway.model.Booking;

public class TestBookingDao {

    public static void main(String[] args) {

        BookingDao dao = new BookingDao();

        Booking booking = new Booking(
                1,                  // user_id
                1,                  // schedule_id
                "PNR100000001",      
                "CONFIRMED",         
                1500.00              
        );

        int bookingId = dao.addBooking(booking);

        if (bookingId != -1) {
            System.out.println("Booking created successfully!");
            System.out.println("Booking ID: " + bookingId);
        } else {
            System.out.println("Booking failed!");
        }
    }
}