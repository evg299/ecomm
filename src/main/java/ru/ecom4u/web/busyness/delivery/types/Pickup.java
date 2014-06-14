package ru.ecom4u.web.busyness.delivery.types;

import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.controllers.dto.json.DeliveryAddress;
import ru.ecom4u.web.controllers.dto.json.DeliveryCalcResult;

/**
 * Created by Evgeny on 16.05.14.
 */
public class Pickup implements IDelivery
{

    @Override
    public String getDeliveryName()
    {
        return "Самовывоз";
    }

    @Override
    public String getDeliveryLogoUrl()
    {
        return null;
    }

    @Override
    public String getUnicName()
    {
        return this.getClass().getName();
    }

    @Override
    public DeliveryCalcResult forecast(DeliveryAddress warehouseAddress, DeliveryAddress deliveryAddress, double weight)
    {
        return new DeliveryCalcResult();
    }

    @Override
    public boolean isNeedMap()
    {
        return false;
    }
}
