
package railway.messaging;

import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class BookingMessageProducer {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "tiger";

    private static final String QUEUE_NAME =
            "railway.booking.confirmation";

    public void sendBookingConfirmation(String pnr) {

        try (ActiveMQConnectionFactory factory =
                     new ActiveMQConnectionFactory(
                             BROKER_URL, USERNAME, PASSWORD);
             JMSContext context =
                     factory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

            Queue queue = context.createQueue(QUEUE_NAME);

            String message = "Your railway ticket is confirmed! "
                    + "Your PNR is: " + pnr;

            context.createProducer().send(queue, message);

            System.out.println("JMS: Booking confirmation sent!");

        } catch (Exception e) {
            System.out.println("JMS message sending failed!");
            e.printStackTrace();
        }
    }
}
