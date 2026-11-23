package railway.main;

import railway.dao.BookingDao;

public class TestCancelBooking {

    public static void main(String[] args) {

        BookingDao dao = new BookingDao();

        String pnr = "PNR100000001";

        boolean cancelled = dao.cancelBooking(pnr);

        if (cancelled) {
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Cancellation failed!");
            System.out.println("Booking may not exist or is already cancelled.");
        }
    }
}