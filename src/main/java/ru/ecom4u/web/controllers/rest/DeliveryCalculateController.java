package ru.ecom4u.web.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ecom4u.web.busyness.DeliveryLogic;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.controllers.dto.json.DeliveryAddress;
import ru.ecom4u.web.controllers.dto.json.DeliveryCalcResult;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;
import ru.ecom4u.web.utils.Stringifyer;

/**
 * Created by Evgeny on 12.06.14.
 */
@Controller
@RequestMapping(value = "deliveryCalculate")
public class DeliveryCalculateController
{
    @Autowired
    private DeliveryLogic deliveryLogic;
    @Autowired
    private SitePropertiesService sitePropertiesService;

    @RequestMapping(value = "{deliveryName}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable(value = "deliveryName") String deliveryName)
    {
        System.err.println("deliveryName: " + deliveryName);
        return "ok";
    }

    // TODO
    @RequestMapping(value = "{deliveryName:.+}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DeliveryCalcResult forecast(@PathVariable String deliveryName, @RequestBody DeliveryAddress deliveryAddress)
    {
        System.err.println("deliveryName: " + deliveryName);
        System.err.println("deliveryAddress: " + Stringifyer.stringify(deliveryAddress));

        DeliveryAddress warehouseAddress = new DeliveryAddress();
        warehouseAddress.setCountry(sitePropertiesService.getValue("address_country"));
        warehouseAddress.setRegion(sitePropertiesService.getValue("address_region"));
        warehouseAddress.setCity(sitePropertiesService.getValue("address_city"));

        IDelivery currDelivery = deliveryLogic.getDeliveryByUnicName(deliveryName);
        DeliveryCalcResult result = currDelivery.forecast(warehouseAddress, deliveryAddress, 1);

        return result;
    }
}
