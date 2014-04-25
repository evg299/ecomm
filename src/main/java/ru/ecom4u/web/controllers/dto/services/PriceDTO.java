package ru.ecom4u.web.controllers.dto.services;

/**
 * Created by Evgeny(e299792459@gmail.com) on 27.03.14.
 */
public class PriceDTO {
    private String integralPart;
    private String fractionalPart;

    public String getIntegralPart() {
        return integralPart;
    }

    public void setIntegralPart(String integralPart) {
        this.integralPart = integralPart;
    }

    public String getFractionalPart() {
        return fractionalPart;
    }

    public void setFractionalPart(String fractionalPart) {
        this.fractionalPart = fractionalPart;
    }

}
