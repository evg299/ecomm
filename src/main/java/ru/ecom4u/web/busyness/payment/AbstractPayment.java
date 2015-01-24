package ru.ecom4u.web.busyness.payment;

import org.springframework.context.ApplicationContext;
import ru.ecom4u.web.busyness.ApplicationContextProvider;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;

/**
 * Created by Evgeny on 24.01.2015.
 */
public abstract class AbstractPayment
{
    protected SitePropertiesService getSitePropertiesService() {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(SitePropertiesService.class);
    }
}
