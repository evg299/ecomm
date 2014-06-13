package ru.ecom4u.web.controllers.dto.json;

/**
 * Created by Evgeny on 12.06.14.
 */
public class DeliveryCalcResult
{
    private int price;
    private int minDays;
    private int maxDays;

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getMinDays()
    {
        return minDays;
    }

    public void setMinDays(int minDays)
    {
        this.minDays = minDays;
    }

    public int getMaxDays()
    {
        return maxDays;
    }

    public void setMaxDays(int maxDays)
    {
        this.maxDays = maxDays;
    }
}
