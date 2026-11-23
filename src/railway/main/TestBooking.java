package railway.main;

import railway.service.BookingService;
import railway.model.Passenger;
import railway.model.Ticket;

import java.util.Arrays;
import java.util.List;

public class TestBooking {

    public static void main(String[] args) {

        BookingService bookingService = new BookingService();

        
        Passenger p1 = new Passenger(
                "Jaskaran Singh",
                20,
                "MALE"
        );

        Passenger p2 = new Passenger(
                "Rahul",
                21,
                "MALE"
        );

        List<Passenger> passengers = Arrays.asList(p1, p2);

        
        List<Integer> seatIds = Arrays.asList(2, 3);

        
        double totalAmount = 3000.0;

        
        Ticket ticket = bookingService.bookTicket(
                1,              // User ID
                1,              // Schedule ID
                passengers,
                seatIds,
                totalAmount
        );

        
        if (ticket != null) {

            System.out.println("\n========== BOOKING SUCCESS ==========");
            System.out.println(ticket);

        } else {

            System.out.println("\n========== BOOKING FAILED ==========");
        }
    }
}