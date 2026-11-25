
package railway.payment;

public class PaymentMethodFactory {

    public static PaymentMethod getPaymentMethod(int choice) {

        switch (choice) {

            case 1:
                return new UPIPayment();

            case 2:
                return new CardPayment();

            case 3:
                return new NetBankingPayment();

            default:
                throw new IllegalArgumentException(
                    "Invalid payment method selected"
                );
        }
    }
}
