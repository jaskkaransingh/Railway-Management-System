package railway.main;

import railway.messaging.BookingMessageProducer;

public class JmsTest {

    public static void main(String[] args) {

        BookingMessageProducer producer =
                new BookingMessageProducer();

        producer.sendBookingConfirmation("9876543210");
    }
}	