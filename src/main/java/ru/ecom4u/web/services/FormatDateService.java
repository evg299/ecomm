package ru.ecom4u.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 13.04.14.
 */
@Service
public class FormatDateService
{

    @Autowired
    private SitePropertiesService sitePropertiesService;

    private DateFormatSymbols rusDateFormatSymbols;
    private SimpleDateFormat dateFormat;

    public FormatDateService()
    {
        rusDateFormatSymbols = new DateFormatSymbols()
        {
            @Override
            public String[] getMonths()
            {
                String[] months = new String[12];
                for (int i = 1; i <= 12; i++)
                {
                    months[i-1] = sitePropertiesService.getValue("rus_date_format_month_" + i);
                }
                return months;
            }
        };

        dateFormat = new SimpleDateFormat("dd MMMM yyyy", rusDateFormatSymbols);
    }

    public String formatTimestamp(Timestamp timestamp)
    {
        return dateFormat.format(timestamp);
    }

    public String countYears(Timestamp birthTimestamp)
    {
        return countYears(new Timestamp(System.currentTimeMillis()), birthTimestamp);
    }

    public String countYears(Timestamp currentTimestamp, Timestamp birthTimestamp)
    {
        long millsCurrent = currentTimestamp.getTime();
        long millsBirth = birthTimestamp.getTime();

        Double yearsD = new Double((millsCurrent - millsBirth) / (1000 * 60 * 60 * 24 * 365.25));
        int years = yearsD.intValue();

        String suffix = sitePropertiesService.getValue("rus_years_suffix_3");
        if (1 != years / 10)
        {
            switch (years % 10)
            {
                case 1:
                    suffix = sitePropertiesService.getValue("rus_years_suffix_1");
                    break;
                case 2:
                case 3:
                case 4:
                    suffix = sitePropertiesService.getValue("rus_years_suffix_2");
                    break;
            }
        }

        return "" + years + " " + suffix;
    }
}
