package railway.main;

import railway.dao.BookingPassengerDao;
import railway.model.TicketPassenger;

import java.util.ArrayList;

public class TestTicketPassengers {

    public static void main(String[] args) {

        BookingPassengerDao dao = new BookingPassengerDao();

        int bookingId = 1;

        ArrayList<TicketPassenger> passengers =
                dao.getTicketPassengers(bookingId);

        if (passengers.isEmpty()) {

            System.out.println("No passengers found for this booking!");

        } else {

            System.out.println("===== PASSENGER DETAILS =====");

            for (TicketPassenger passenger : passengers) {
                System.out.println(passenger);
            }

            System.out.println("\nTotal Passengers: " + passengers.size());
        }
    }
}