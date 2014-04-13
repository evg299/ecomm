package ru.ecom4u.web.services;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

/**
 * Created by Evgeny(e299792459@gmail.com) on 13.04.14.
 */
@Service
public class DateFormatService {

    private static DateFormatSymbols rusDateFormatSymbols = new DateFormatSymbols() {
        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }
    };

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", rusDateFormatSymbols);

    public String formatTimestamp(Timestamp timestamp) {
        return dateFormat.format(timestamp);
    }

    public String countYears(Timestamp currentTimestamp, Timestamp birthTimestamp) {
        long millsCurrent = currentTimestamp.getTime();
        long millsBirth = birthTimestamp.getTime();

        Double yearsD = new Double((millsCurrent - millsBirth) / (1000 * 60 * 60 * 24 * 365.25));
        int years = yearsD.intValue();

        String suffix = "лет";
        if (1 != years / 10) {
            switch (years % 10) {
                case 1:
                    suffix = "год";
                    break;
                case 2:
                case 3:
                case 4:
                    suffix = "года";
                    break;
            }
        }

        return "" + years + " " + suffix;
    }
}
