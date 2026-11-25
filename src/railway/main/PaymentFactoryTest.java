package railway.main;

import railway.payment.PaymentMethod;
import railway.payment.PaymentMethodFactory;

public class PaymentFactoryTest {

    public static void main(String[] args) {

        PaymentMethod upi =
                PaymentMethodFactory.getPaymentMethod(1);

        upi.pay(2500);

        System.out.println("----------------");

        PaymentMethod card =
                PaymentMethodFactory.getPaymentMethod(2);

        card.pay(3000);

        System.out.println("----------------");

        PaymentMethod netBanking =
                PaymentMethodFactory.getPaymentMethod(3);

        netBanking.pay(1500);
    }
}