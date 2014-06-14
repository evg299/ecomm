package ru.ecom4u.web.busyness.delivery;

import ru.ecom4u.web.controllers.dto.CardProductDTO;
import ru.ecom4u.web.controllers.dto.json.DeliveryAddress;
import ru.ecom4u.web.controllers.dto.json.DeliveryCalcResult;
import ru.ecom4u.web.domain.db.entities.Address;

import java.util.List;

/**
 * Created by Evgeny on 15.05.14.
 */
public interface IDelivery {

    public String getDeliveryName();

    public String getDeliveryLogoUrl();

    public String getUnicName();

    public DeliveryCalcResult forecast(DeliveryAddress warehouseAddress, DeliveryAddress deliveryAddress, double weight);

    public boolean isNeedMap();
}
