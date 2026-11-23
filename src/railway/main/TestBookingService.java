package railway.main;

import railway.service.BookingService;
import railway.model.Ticket;

public class TestBookingService {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        String pnr = "PNR100000001";

        Ticket ticket = service.getTicketByPNR(pnr);

        if (ticket != null) {

            System.out.println(ticket);

        } else {

            System.out.println("Ticket not found!");
        }
    }
}