package ru.ecom4u.web.utils;

import ru.ecom4u.web.controllers.dto.services.PriceDTO;
import ru.ecom4u.web.domain.db.entities.Price;

/**
 * Created by Evgeny(e299792459@gmail.com) on 27.03.14.
 */
public class ProductUtil {

    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    }

    public static PriceDTO convertPrice(Price price){
        PriceDTO result = new PriceDTO();
        double value = price.getValue();
        result.setIntegralPart((int) value);
        double ost = value - (int) value;
        ost = Math.round(100 * ost);
        result.setFractionalPart((int) ost);
        if(null != price.getCurrency())
        result.setCurrency(price.getCurrency().getShortName());
        return result;
    }
}
