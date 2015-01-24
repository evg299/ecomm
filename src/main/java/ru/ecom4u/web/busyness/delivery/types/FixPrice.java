package ru.ecom4u.web.busyness.delivery.types;

import org.springframework.context.ApplicationContext;
import ru.ecom4u.web.busyness.ApplicationContextProvider;
import ru.ecom4u.web.busyness.delivery.AbstractDelivery;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.controllers.dto.json.DeliveryAddress;
import ru.ecom4u.web.controllers.dto.json.DeliveryCalcResult;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;

/**
 * Created by Evgeny on 15.05.14.
 */
public class FixPrice extends AbstractDelivery implements IDelivery
{
    @Override
    public String getDeliveryName()
    {
        return getSitePropertiesService().getValue("rus_delivery_fixprice_name");
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
        Integer price = Integer.parseInt(getSitePropertiesService().getValue("delivery_fixprice_price"));
        Integer minDays = Integer.parseInt(getSitePropertiesService().getValue("delivery_fixprice_min_days"));
        Integer maxDays = Integer.parseInt(getSitePropertiesService().getValue("delivery_fixprice_max_days"));

        DeliveryCalcResult result = new DeliveryCalcResult();
        result.setPrice(price);
        result.setMinDays(minDays);
        result.setMaxDays(maxDays);
        return result;
    }

    @Override
    public boolean isNeedMap()
    {
        return true;
    }
}
