package ru.ecom4u.web.controllers.dto.services;

/**
 * Created by Evgeny(e299792459@gmail.com) on 27.03.14.
 */
public class PriceDTO {
    private int integralPart;
    private int fractionalPart;
    private String currency;

    public int getIntegralPart() {
        return integralPart;
    }

    public void setIntegralPart(int integralPart) {
        this.integralPart = integralPart;
    }

    public int getFractionalPart() {
        return fractionalPart;
    }

    public void setFractionalPart(int fractionalPart) {
        this.fractionalPart = fractionalPart;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
