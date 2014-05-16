package ru.ecom4u.web.busyness.delivery;

import ru.ecom4u.web.controllers.dto.CardProductDTO;
import ru.ecom4u.web.domain.db.entities.Address;

import java.util.List;

/**
 * Created by Evgeny on 15.05.14.
 */
public interface IDelivery {
    public DeliveryForecast deliveryForecast(Address from, Address to, List<CardProductDTO> content);

    public String deliveryName();

    public String deliveryLogoUrl();
}
