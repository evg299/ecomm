package ru.ecom4u.web.controllers.rest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ecom4u.web.controllers.dto.json.DeliveryAddress;
import ru.ecom4u.web.controllers.dto.json.DeliveryCalcResult;
import ru.ecom4u.web.utils.Stringifyer;

/**
 * Created by Evgeny on 12.06.14.
 */
@Controller
@RequestMapping(value = "deliveryCalculate")
public class DeliveryCalculateController
{

    @RequestMapping(value = "{deliveryName}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable(value = "deliveryName") String deliveryName)
    {
        System.err.println("deliveryName: " + deliveryName);
        return "ok";
    }

    // TODO
    @RequestMapping(value = "{deliveryName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DeliveryCalcResult forecast(@PathVariable String deliveryName, @RequestBody DeliveryAddress deliveryAddress)
    {
        System.err.println("deliveryName: " + deliveryName);
        System.err.println("deliveryAddress: " + Stringifyer.stringify(deliveryAddress));

        return new DeliveryCalcResult();
    }
}
