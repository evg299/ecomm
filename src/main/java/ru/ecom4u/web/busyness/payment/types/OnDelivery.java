package ru.ecom4u.web.busyness.payment.types;

import ru.ecom4u.web.busyness.delivery.types.EMS;
import ru.ecom4u.web.busyness.delivery.types.FixPrice;
import ru.ecom4u.web.busyness.delivery.types.Pickup;
import ru.ecom4u.web.busyness.payment.IPayment;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Evgeny on 06.07.14.
 */
public class OnDelivery implements IPayment
{
    @Override
    public boolean isPayFirst()
    {
        return false;
    }

    @Override
    public boolean supportDelivery(Class<?> deliveryClass)
    {
        if(deliveryClass.equals(EMS.class))
            return true;
        if(deliveryClass.equals(FixPrice.class))
            return true;
        if(deliveryClass.equals(Pickup.class))
            return true;

        return false;
    }

    @Override
    public String getPaymentName()
    {
        return "Оплата при получении";
    }

    @Override
    public String getUnicName()
    {
        return this.getClass().getName();
    }

    @Override
    public Map<String, String> getStaticProperties()
    {
        return new LinkedHashMap<String, String>();
    }

    @Override
    public Map<String, String> getDbKeyDescMap()
    {
        return new LinkedHashMap<String, String>();
    }

}
