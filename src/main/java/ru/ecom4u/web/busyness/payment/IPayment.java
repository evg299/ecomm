package ru.ecom4u.web.busyness.payment;

/**
 * Created by Evgeny on 16.05.14.
 */
/*
        Оплата при получении
        Yandex деньги
        Webmoney
        PayPal
        Visa или Mastercard
*/
public interface IPayment {
    public boolean supportDelivery(Class<?> deliveryClass);

    public String getUnicName();

    public String getPaymentName();
}
