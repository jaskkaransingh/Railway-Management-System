package railway.model;

import java.util.ArrayList;

public class Ticket {

    private Booking booking;
    private ArrayList<TicketPassenger> passengers;

    public Ticket(Booking booking, ArrayList<TicketPassenger> passengers) {
        this.booking = booking;
        this.passengers = passengers;
    }

    public Booking getBooking() {
        return booking;
    }

    public ArrayList<TicketPassenger> getPassengers() {
        return passengers;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        result.append("========== RAILWAY TICKET ==========\n");
        result.append(booking).append("\n");
        result.append("------------------------------------\n");
        result.append("PASSENGERS:\n");

        for (TicketPassenger passenger : passengers) {
            result.append(passenger).append("\n");
        }

        result.append("====================================");

        return result.toString();
    }
}