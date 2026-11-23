package railway.main;

import railway.dao.BookingPassengerDao;
import railway.model.BookingPassenger;

public class TestBookingPassengerDao {

    public static void main(String[] args) {

        BookingPassengerDao dao = new BookingPassengerDao();

        BookingPassenger bp = new BookingPassenger(
                1,  // booking_id
                1,  // passenger_id
                1   // seat_id
        );

        boolean success = dao.addBookingPassenger(bp);

        if (success) {
            System.out.println("Passenger linked to booking successfully!");
        } else {
            System.out.println("Failed to link passenger!");
        }
    }
}