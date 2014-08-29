package ru.ecom4u.web.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ecom4u.web.busyness.DeliveryLogic;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.domain.db.entities.Delivery;

/**
 * Created by Evgeny on 29.08.14.
 */
@Service
public class DeliveryFormatter
{
    @Autowired
    private DeliveryLogic deliveryLogic;

    public String format(Delivery delivery)
    {
        if(null == delivery)
            return "";

        String className = delivery.getDeliveryClass();
        IDelivery del = deliveryLogic.getDeliveryByUnicName(className);
        return del.getDeliveryName();
    }
}
