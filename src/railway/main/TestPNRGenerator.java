package railway.main;

import railway.service.BookingService;

public class TestPNRGenerator {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        System.out.println("Generated PNRs:\n");

        for (int i = 1; i <= 5; i++) {

            String pnr = service.generatePNR();

            System.out.println(i + ". " + pnr);
        }
    }
}