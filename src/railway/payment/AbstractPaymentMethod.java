
package railway.payment;

public abstract class AbstractPaymentMethod implements PaymentMethod {

	protected String transactionId;
	
	public AbstractPaymentMethod() {
	transactionId = "TXN" + System.currentTimeMillis();
	}
	
	@Override
	public abstract boolean pay(double amount);
	
	public void showTransactionDetails() {
	System.out.println("Transaction ID: " + transactionId);
	}
}
