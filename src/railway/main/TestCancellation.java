package railway.main;

import railway.service.BookingService;
import railway.model.Passenger;
import railway.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TestCancellation {

    public static void main(String[] args) {

        BookingService bookingService = new BookingService();

        // Create test passengers
        List<Passenger> passengers = new ArrayList<>();

        passengers.add(new Passenger("Cancellation Test 1", 20, "MALE"));
        passengers.add(new Passenger("Cancellation Test 2", 21, "MALE"));

        // Available seats: A4 and B1
        List<Integer> seatIds = new ArrayList<>();

        seatIds.add(4);
        seatIds.add(5);

        // Create temporary booking
        Ticket ticket = bookingService.bookTicket(
                1,
                1,
                passengers,
                seatIds,
                2000.00
        );

        if (ticket == null) {
            System.out.println("Test booking failed!");
            return;
        }

        System.out.println("\n========== TEST BOOKING ==========");
        System.out.println(ticket);

        // Cancel the newly created booking
        String pnr = ticket.getBooking().getPnr();

        System.out.println("\nCancelling PNR: " + pnr);

        boolean cancelled = bookingService.cancelTicket(pnr);

        if (cancelled) {
            System.out.println("Cancellation test PASSED!");
        } else {
            System.out.println("Cancellation test FAILED!");
        }
    }
}