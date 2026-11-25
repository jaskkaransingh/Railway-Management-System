
package railway.payment;

public class CardPayment extends AbstractPaymentMethod {

    @Override
    public boolean pay(double amount) {

        System.out.println("Processing Card Payment...");
        System.out.println("Amount: Rs. " + amount);

        System.out.println("Card Payment Successful!");

        showTransactionDetails();

        return true;
    }
}
