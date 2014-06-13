package ru.ecom4u.web.controllers.dto.json;

/**
 * Created by Evgeny on 12.06.14.
 */
public class DeliveryAddress
{
    private String country;
    private String region;
    private String city;

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
}
