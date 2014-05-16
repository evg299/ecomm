package ru.ecom4u.web.busyness.delivery;

/**
 * Created by Evgeny on 15.05.14.
 */
public class DeliveryForecast {
    private float coast;
    private String travelTime;

    public DeliveryForecast() {
    }

    public DeliveryForecast(float coast, String travelTime) {
        this.coast = coast;
        this.travelTime = travelTime;
    }

    public float getCoast() {
        return coast;
    }

    public void setCoast(float coast) {
        this.coast = coast;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelDays) {
        this.travelTime = travelDays;
    }
}
