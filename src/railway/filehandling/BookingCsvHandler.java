package railway.filehandling;

import railway.model.Booking;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BookingCsvHandler {

    public void exportBookings(ArrayList<Booking> bookings) {

        String filePath = "files/bookings.csv";

        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("Booking ID,User ID,Schedule ID,PNR,Status,Booking Date,Amount\n");

            for (Booking booking : bookings) {

                writer.write(
                    booking.getBookingId() + "," +
                    booking.getUserId() + "," +
                    booking.getScheduleId() + "," +
                    booking.getPnr() + "," +
                    booking.getBookingStatus() + "," +
                    booking.getBookingDate() + "," +
                    booking.getTotalAmount() + "\n"
                );
            }
            
            System.out.println("Bookings exported successfully!");
            System.out.println("File location: " + filePath);

        } catch (IOException e) {
            System.out.println("Error exporting CSV file!");
            e.printStackTrace();
        }
    }
}