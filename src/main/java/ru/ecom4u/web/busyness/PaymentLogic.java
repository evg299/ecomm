package ru.ecom4u.web.busyness;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.busyness.payment.IPayment;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Evgeny on 19.07.14.
 */
@Component
public class PaymentLogic
{
    private static final String PAYMENT_TYPES_PACKAGE = "ru.ecom4u.web.busyness.payment.types";
    private List<IPayment> allIPayments = new ArrayList<IPayment>();

    @PostConstruct
    private void init() throws IllegalAccessException, InstantiationException
    {
        Reflections reflections = new Reflections(PAYMENT_TYPES_PACKAGE);

        Set<Class<? extends IPayment>> paymentClasses = reflections.getSubTypesOf(IPayment.class);
        for (Class<? extends IPayment> paymentClass : paymentClasses)
        {
            allIPayments.add(paymentClass.newInstance());
        }
    }

    public List<IPayment> getAllIPayments()
    {
        return allIPayments;
    }

    public List<IPayment> getAvailablePayments(IDelivery delivery)
    {
        List<IPayment> result = new ArrayList<IPayment>();
        for (IPayment payment : allIPayments)
        {
            if (payment.supportDelivery(delivery.getClass()))
                result.add(payment);
        }
        return result;
    }

    public IPayment getPaymentByUnicName(String unicName)
    {
        for (IPayment payment : allIPayments)
        {
            if (unicName.equals(payment.getUnicName()))
                return payment;
        }

        return null;
    }
}
