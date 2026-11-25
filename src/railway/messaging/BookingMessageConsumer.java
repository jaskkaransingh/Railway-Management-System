
package railway.messaging;

import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.jms.Message;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class BookingMessageConsumer {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "tiger";

    private static final String QUEUE_NAME =
            "railway.booking.confirmation";

    public void receiveBookingConfirmation() {

        try (ActiveMQConnectionFactory factory =
                     new ActiveMQConnectionFactory(
                             BROKER_URL, USERNAME, PASSWORD);
             JMSContext context =
                     factory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

            Queue queue = context.createQueue(QUEUE_NAME);

            JMSConsumer consumer = context.createConsumer(queue);

            System.out.println("Waiting for booking messages...");

            Message message = consumer.receive(10000);

            if (message != null) {
                System.out.println("Received confirmation:");
                System.out.println(message.getBody(String.class));
            } else {
                System.out.println("No messages received within 10 seconds.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
