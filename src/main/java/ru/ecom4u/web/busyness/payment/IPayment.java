package ru.ecom4u.web.busyness.payment;

/**
 * Created by Evgeny on 16.05.14.
 */
public interface IPayment {
    public boolean supportDelivery(Class<?> deliveryClass);


}
