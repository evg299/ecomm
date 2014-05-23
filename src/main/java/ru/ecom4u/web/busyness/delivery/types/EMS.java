package ru.ecom4u.web.busyness.delivery.types;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import ru.ecom4u.web.busyness.delivery.DeliveryForecast;
import ru.ecom4u.web.busyness.delivery.IDelivery;
import ru.ecom4u.web.controllers.dto.CardProductDTO;
import ru.ecom4u.web.domain.db.entities.Address;
import ru.ecom4u.web.utils.HTTPUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Evgeny on 17.05.14.
 */
public class EMS implements IDelivery {
    public static final String COUNTRY = "РОССИЯ";

    public static enum DeliveryType {
        doc, // документы (до 2-х килограм)
        att  // товарные вложения
    }

    public static class CalcResult {
        private int priceRub;
        private int minDays;
        private int maxDays;

        public int getPriceRub() {
            return priceRub;
        }

        public void setPriceRub(int priceRub) {
            this.priceRub = priceRub;
        }

        public int getMinDays() {
            return minDays;
        }

        public void setMinDays(int minDays) {
            this.minDays = minDays;
        }

        public int getMaxDays() {
            return maxDays;
        }

        public void setMaxDays(int maxDays) {
            this.maxDays = maxDays;
        }
    }

    public static class EMSAddress {
        private String country;
        private String region;
        private String city;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }


    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Map<String, String> citiesMap;
    private static Map<String, String> regionsMap;
    private static Map<String, String> countriesMap;

    public static Map<String, String> getCities() {
        if (null == citiesMap)
            citiesMap = getLocation("http://emspost.ru/api/rest/?method=ems.get.locations&type=cities&plain=true");
        return citiesMap;
    }

    public static Map<String, String> getRegions() {
        if (null == regionsMap)
            regionsMap = getLocation("http://emspost.ru/api/rest/?method=ems.get.locations&type=regions&plain=true");
        return regionsMap;
    }

    public static Map<String, String> getCountries() {
        if (null == countriesMap)
            countriesMap = getLocation("http://emspost.ru/api/rest/?method=ems.get.locations&type=countries&plain=true");
        return countriesMap;
    }

    public static Map<String, String> getLocation(String reqUrl) {
        Map<String, String> result = new TreeMap<String, String>();

        try {
            String resp = HTTPUtil.requestGET(reqUrl);
            JsonNode rootNode = objectMapper.readTree(resp);
            JsonNode locationsNode = rootNode.get("rsp").get("locations");
            // System.err.println(locationsNode);

            if (locationsNode.isArray()) {
                for (final JsonNode locationNode : locationsNode) {
                    result.put(locationNode.get("name").asText(), locationNode.get("value").asText());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String resolveCode(EMSAddress address) {
        if (null != address.getCity() && getCities().containsKey(address.getCity())) {
            return getCities().get(address.getCity());
        } else if (null != address.getRegion() && getRegions().containsKey(address.getRegion())) {
            return getRegions().get(address.getRegion());
        } else {
            throw new RuntimeException("Cant resolve EMSAddress Code");
        }
    }

    public static CalcResult calculate(EMSAddress from, EMSAddress to, double weight, DeliveryType type) {
        if (COUNTRY.equals(from.getCountry())) {
            if (COUNTRY.equals(to.getCountry())) {
                String fromCode = resolveCode(from);
                String toCode = resolveCode(to);

                String url = String.format("http://emspost.ru/api/rest?method=ems.calculate&from=%s&to=%s&weight=%d", fromCode, toCode, weight);
                try {
                    String resp = HTTPUtil.requestGET(url);
                    JsonNode rootNode = objectMapper.readTree(resp);
                    JsonNode respNode = rootNode.get("rsp");

                    CalcResult result = new CalcResult();
                    result.setPriceRub(respNode.get("price").asInt());
                    result.setMinDays(respNode.get("term").get("min").asInt());
                    result.setMinDays(respNode.get("term").get("max").asInt());
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String toCode = getCountries().get(to.getCountry());
                if(null == toCode) {
                    throw new RuntimeException("Cant resolve EMSAddress Code");
                }

                String url = String.format("http://emspost.ru/api/rest?method=ems.calculate&to=%s&weight=%d&type=%s", toCode, weight, type);
                try {
                    String resp = HTTPUtil.requestGET(url);
                    JsonNode rootNode = objectMapper.readTree(resp);
                    JsonNode respNode = rootNode.get("rsp");

                    CalcResult result = new CalcResult();
                    result.setPriceRub(respNode.get("price").asInt());
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new RuntimeException("From country not valid");
    }

    @Override
    public DeliveryForecast deliveryForecast(Address from, Address to, List<CardProductDTO> content) {
        return new DeliveryForecast(0, null);
    }

    @Override
    public String getDeliveryName() {
        return "ЕМС";
    }

    @Override
    public String getDeliveryLogoUrl() {
        return "http://www.emspost.ru/images/logo.png";
    }

    @Override
    public String getUnicName() {
        return this.getClass().getName();
    }
}
