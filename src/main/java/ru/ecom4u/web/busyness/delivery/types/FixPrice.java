package ru.ecom4u.web.busyness.delivery.types;

import org.springframework.context.ApplicationContext;
import ru.ecom4u.web.busyness.ApplicationContextProvider;
import ru.ecom4u.web.busyness.delivery.DeliveryForecast;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.controllers.dto.CardProductDTO;
import ru.ecom4u.web.domain.db.entities.Address;
import ru.ecom4u.web.domain.db.entities.SiteProperty;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;

import java.util.List;

/**
 * Created by Evgeny on 15.05.14.
 */
public class FixPrice implements IDelivery {

    @Override
    public DeliveryForecast deliveryForecast(Address from, Address to, List<CardProductDTO> content) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        SitePropertiesService sitePropertiesService = applicationContext.getBean(SitePropertiesService.class);
        Integer price = Integer.parseInt(sitePropertiesService.getValue("delivery_fixprice_price"));
        String travelTime = sitePropertiesService.getValue("delivery_fixprice_traveltime");

        return new DeliveryForecast(price, travelTime);
    }

    @Override
    public String deliveryName() {
        return "Фиксированная ставка";
    }

    @Override
    public String deliveryLogoUrl() {
        return null;
    }
}
