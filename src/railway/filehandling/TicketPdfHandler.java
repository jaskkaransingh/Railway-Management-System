
package railway.filehandling;

import railway.model.Ticket;
import railway.model.Booking;
import railway.model.TicketPassenger;
import railway.model.TrainSchedule;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class TicketPdfHandler {

    public String generateTicketPDF(
            Ticket ticket,
            TrainSchedule schedule) {

        Booking booking = ticket.getBooking();

        // Create tickets directory if it doesn't exist
        File directory = new File("files/tickets");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = "files/tickets/ticket_"
                + booking.getPnr() + ".pdf";

        Document document = new Document();

        try {

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(filePath)
            );

            document.open();

            Font titleFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    20,
                    BaseColor.BLUE
            );

            Font headingFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    13
            );

            // Title
            Paragraph title = new Paragraph(
                    "RAILWAY RESERVATION TICKET",
                    titleFont
            );

            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Booking details
            document.add(new Paragraph(
                    "BOOKING DETAILS", headingFont));

            document.add(new Paragraph(
                    "PNR: " + booking.getPnr()));

            document.add(new Paragraph(
                    "Booking ID: " + booking.getBookingId()));

            document.add(new Paragraph(
                    "Booking Status: " + booking.getBookingStatus()));

            document.add(new Paragraph(
                    "Booking Date: " + booking.getBookingDate()));

            document.add(new Paragraph(
                    "Total Amount: Rs. " + booking.getTotalAmount()));

            document.add(new Paragraph("\n"));

            // Train details
            document.add(new Paragraph(
                    "JOURNEY DETAILS", headingFont));

            document.add(new Paragraph(
                    "Train Number: " + schedule.getTrainNumber()));

            document.add(new Paragraph(
                    "Train Name: " + schedule.getTrainName()));

            document.add(new Paragraph(
                    "Route: " + schedule.getSource()
                    + " to " + schedule.getDestination()));

            document.add(new Paragraph(
                    "Journey Date: " + schedule.getJourneyDate()));

            document.add(new Paragraph(
                    "Departure: " + schedule.getDepartureTime()));

            document.add(new Paragraph(
                    "Arrival: " + schedule.getArrivalTime()));

            document.add(new Paragraph("\n"));

            // Passenger table
            document.add(new Paragraph(
                    "PASSENGER DETAILS", headingFont));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            table.addCell("Name");
            table.addCell("Age");
            table.addCell("Gender");
            table.addCell("Seat");
            table.addCell("Class");

            for (TicketPassenger passenger :
                    ticket.getPassengers()) {

                table.addCell(passenger.getName());
                table.addCell(String.valueOf(passenger.getAge()));
                table.addCell(passenger.getGender());
                table.addCell(passenger.getSeatNumber());
                table.addCell(passenger.getSeatClass());
            }

            document.add(table);

            document.add(new Paragraph("\n"));

            Paragraph footer = new Paragraph(
                    "Thank you for choosing our Railway Reservation System!"
            );

            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            System.out.println("PDF ticket generated successfully!");
            System.out.println("Location: " + filePath);

            return filePath;

        } catch (Exception e) {

            if (document.isOpen()) {
                document.close();
            }

            System.out.println("Error generating PDF ticket!");
            e.printStackTrace();

            return null;
        }
    }
}
