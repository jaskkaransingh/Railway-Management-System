
package railway.payment;

public class UPIPayment extends AbstractPaymentMethod {

    @Override
    public boolean pay(double amount) {

        System.out.println("Processing UPI Payment...");
        System.out.println("Amount: Rs. " + amount);

        System.out.println("UPI Payment Successful!");

        showTransactionDetails();

        return true;
    }
}
