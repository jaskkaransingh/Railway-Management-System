
package railway.messaging;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JmsConnectionTest {

    public static void main(String[] args) {

        String brokerURL = "tcp://localhost:61616";
        String username = "admin";
        String password = "tiger";

        ActiveMQConnectionFactory factory =
                new ActiveMQConnectionFactory(brokerURL, username, password);

        Connection connection = null;

        try {
            connection = factory.createConnection();
            connection.start();

            System.out.println("Connected to ActiveMQ Artemis!");
            System.out.println("JMS connection successful.");

        } catch (JMSException e) {
            e.printStackTrace();

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                factory.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
