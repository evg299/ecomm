package ru.ecom4u.web.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ecom4u.web.domain.db.entities.Currency;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;

import java.text.DecimalFormat;

/**
 * Created by Evgeny on 25.04.14.
 */
@Service
public class PriceFormatter {
    @Autowired
    private SitePropertiesService sitePropertiesService;

    public String format(Double price) {
        Currency siteCurrency = sitePropertiesService.getSiteCurrency();
        Double convertedPrice = price + siteCurrency.getRate();

        DecimalFormat df = new DecimalFormat("0.00");
        String priceStr = df.format(convertedPrice);
        String parts[] = priceStr.split(",");

        return String.format("%s <sup>%s</sup>", parts[0], parts[1]);
    }

    public String formatInCard(Double price) {
        Currency siteCurrency = sitePropertiesService.getSiteCurrency();
        Double convertedPrice = price + siteCurrency.getRate();

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(convertedPrice).replace(",", ".");
    }
}
