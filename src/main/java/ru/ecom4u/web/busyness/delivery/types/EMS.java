package ru.ecom4u.web.busyness.delivery.types;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import ru.ecom4u.web.busyness.delivery.AbstractDelivery;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.controllers.dto.json.DeliveryAddress;
import ru.ecom4u.web.controllers.dto.json.DeliveryCalcResult;
import ru.ecom4u.web.utils.HTTPUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Evgeny on 17.05.14.
 */
public class EMS extends AbstractDelivery implements IDelivery
{
    public String getCountryFrom()
    {
        return getSitePropertiesService().getValue("rus_delivery_ems_country_from");
    }

    public static enum DeliveryType
    {
        doc, // документы (до 2-х килограм)
        att  // товарные вложения
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Map<String, String> citiesMap;
    private static Map<String, String> regionsMap;
    private static Map<String, String> countriesMap;

    public static Map<String, String> getCities()
    {
        if (null == citiesMap)
            citiesMap = getLocation("http://emspost.ru/api/rest/?method=ems.get.locations&type=cities&plain=true");
        return citiesMap;
    }

    public static Map<String, String> getRegions()
    {
        if (null == regionsMap)
            regionsMap = getLocation("http://emspost.ru/api/rest/?method=ems.get.locations&type=regions&plain=true");
        return regionsMap;
    }

    public static Map<String, String> getCountries()
    {
        if (null == countriesMap)
            countriesMap = getLocation("http://emspost.ru/api/rest/?method=ems.get.locations&type=countries&plain=true");
        return countriesMap;
    }

    public static Map<String, String> getLocation(String reqUrl)
    {
        Map<String, String> result = new TreeMap<String, String>();

        try
        {
            String resp = HTTPUtil.requestGETByJsoup(reqUrl);
            System.err.println(resp);

            JsonNode rootNode = objectMapper.readTree(resp);
            JsonNode locationsNode = rootNode.get("rsp").get("locations");
            // System.err.println(locationsNode);

            if (locationsNode.isArray())
            {
                for (final JsonNode locationNode : locationsNode)
                {
                    result.put(locationNode.get("name").asText(), locationNode.get("value").asText());
                }
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    private static String resolveCode(DeliveryAddress address)
    {
        print();

        if (null != address.getCity() && getCities().containsKey(address.getCity().toUpperCase()))
        {
            return getCities().get(address.getCity().toUpperCase());
        } else if (null != address.getRegion() && getRegions().containsKey(address.getRegion().toUpperCase()))
        {
            return getRegions().get(address.getRegion().toUpperCase());
        } else
        {
            throw new RuntimeException("Cant resolve EMSAddress Code");
        }
    }

    @Override
    public String getDeliveryName()
    {
        return getSitePropertiesService().getValue("rus_delivery_ems_name");
    }

    @Override
    public String getDeliveryLogoUrl()
    {
        return "http://www.emspost.ru/images/logo.png";
    }

    @Override
    public String getUnicName()
    {
        return this.getClass().getName();
    }

    @Override
    public DeliveryCalcResult forecast(DeliveryAddress warehouseAddress, DeliveryAddress deliveryAddress, double weight)
    {
        String country = getCountryFrom();
        if (country.equalsIgnoreCase(warehouseAddress.getCountry()))
        {
            if (country.equalsIgnoreCase(deliveryAddress.getCountry()))
            {
                String fromCode = resolveCode(warehouseAddress);
                String toCode = resolveCode(deliveryAddress);

                String url = String.format("http://emspost.ru/api/rest?method=ems.calculate&from=%s&to=%s&weight=%s", fromCode, toCode, weight);
                try
                {
                    String resp = HTTPUtil.requestGET(url);
                    JsonNode rootNode = objectMapper.readTree(resp);
                    JsonNode respNode = rootNode.get("rsp");

                    DeliveryCalcResult result = new DeliveryCalcResult();
                    result.setPrice(respNode.get("price").asInt());
                    result.setMinDays(respNode.get("term").get("min").asInt());
                    result.setMinDays(respNode.get("term").get("max").asInt());
                    return result;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                String toCode = getCountries().get(deliveryAddress.getCountry().toUpperCase());
                if (null == toCode)
                {
                    throw new RuntimeException("Cant resolve EMSAddress Code");
                }

                String url = String.format("http://emspost.ru/api/rest?method=ems.calculate&to=%s&weight=%s&type=%s", toCode, weight, DeliveryType.att);
                try
                {
                    String resp = HTTPUtil.requestGET(url);
                    JsonNode rootNode = objectMapper.readTree(resp);
                    JsonNode respNode = rootNode.get("rsp");

                    DeliveryCalcResult result = new DeliveryCalcResult();
                    result.setPrice(respNode.get("price").asInt());
                    return result;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        throw new RuntimeException("From country not " + country);
    }

    @Override
    public boolean isNeedMap()
    {
        return true;
    }

    private static void print()
    {
        for (String regionKey : getRegions().keySet())
        {
            System.err.println(regionKey + ": " + getRegions().get(regionKey));
        }
    }
}
