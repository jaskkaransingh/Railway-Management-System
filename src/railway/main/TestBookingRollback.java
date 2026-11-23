package railway.main;

import railway.service.BookingService;
import railway.model.Passenger;
import railway.model.Ticket;

import java.util.Arrays;
import java.util.List;

public class TestBookingRollback {

    public static void main(String[] args) {

        BookingService bookingService = new BookingService();

        Passenger p1 = new Passenger(
                "Rollback Test 1",
                20,
                "MALE"
        );

        Passenger p2 = new Passenger(
                "Rollback Test 2",
                21,
                "MALE"
        );

        List<Passenger> passengers = Arrays.asList(p1, p2);

        
        List<Integer> seatIds = Arrays.asList(4, 1);

        Ticket ticket = bookingService.bookTicket(
                1,
                1,
                passengers,
                seatIds,
                3000.0
        );

        if (ticket == null) {
            System.out.println("\nEXPECTED RESULT: Booking failed.");
            System.out.println("Rollback test completed.");
        } else {
            System.out.println("\nUNEXPECTED: Booking succeeded!");
        }
    }
}