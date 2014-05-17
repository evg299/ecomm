package ru.ecom4u.web.busyness.delivery.types;

import ru.ecom4u.web.busyness.delivery.DeliveryForecast;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.controllers.dto.CardProductDTO;
import ru.ecom4u.web.domain.db.entities.Address;

import java.util.List;

/**
 * Created by Evgeny on 16.05.14.
 */
public class Pickup implements IDelivery {

    @Override
    public DeliveryForecast deliveryForecast(Address from, Address to, List<CardProductDTO> content) {
        return new DeliveryForecast(0, null);
    }

    @Override
    public String getDeliveryName() {
        return "Самовывоз";
    }

    @Override
    public String getDeliveryLogoUrl() {
        return null;
    }

    @Override
    public String getUnicName() {
        return this.getClass().getName();
    }
}
