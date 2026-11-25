
package railway.payment;

public class NetBankingPayment extends AbstractPaymentMethod {

    @Override
    public boolean pay(double amount) {

        System.out.println("Processing Net Banking Payment...");
        System.out.println("Amount: Rs. " + amount);

        System.out.println("Net Banking Payment Successful!");

        showTransactionDetails();

        return true;
    }
}
