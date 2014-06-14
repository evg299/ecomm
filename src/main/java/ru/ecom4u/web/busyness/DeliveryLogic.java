package ru.ecom4u.web.busyness;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import ru.ecom4u.web.busyness.delivery.IDelivery;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Evgeny on 16.05.14.
 */
@Component
public class DeliveryLogic
{
    private static final String DELIVERY_TYPES_PACKAGE = "ru.ecom4u.web.busyness.delivery.types";
    private List<IDelivery> availableDeliveries = new ArrayList<IDelivery>();

    @PostConstruct
    private void init() throws IllegalAccessException, InstantiationException
    {
        Reflections reflections = new Reflections(DELIVERY_TYPES_PACKAGE);

        Set<Class<? extends IDelivery>> deliveryClasses = reflections.getSubTypesOf(IDelivery.class);
        for (Class<? extends IDelivery> deliveryClass : deliveryClasses)
        {
            availableDeliveries.add(deliveryClass.newInstance());
        }
    }

    public List<IDelivery> getAvailableDeliveries()
    {
        return availableDeliveries;
    }

    public IDelivery getDeliveryByUnicName(String unicName)
    {
        for (IDelivery delivery : availableDeliveries)
        {
            if (unicName.equals(delivery.getUnicName()))
                return delivery;
        }

        return null;
    }
}
