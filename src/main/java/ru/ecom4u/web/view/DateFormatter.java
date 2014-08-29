package ru.ecom4u.web.view;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Evgeny on 30.08.14.
 */
@Service
public class DateFormatter
{

    public String formatDate(Date date)
    {
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        return df.format(date);
    }
}
