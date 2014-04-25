package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Currency;
import ru.ecom4u.web.domain.db.entities.SiteProperty;
import ru.ecom4u.web.domain.db.entities.Unit;

/**
 * Created by Evgeny(e299792459@gmail.com) on 09.02.14.
 */
@Service
public class SitePropertiesService extends AbstractService {

    private SiteProperty getById(String name) {
        Session session = getCurrentSession();
        return (SiteProperty) session.get(SiteProperty.class, name);
    }

    @Transactional(readOnly = true)
    public String getValue(String name) {
        String result = null;
        SiteProperty property = getById(name);
        if (null != property) {
            result = property.getValue();
        }
        return result;
    }

    @Transactional()
    public void putValue(String name, String value) {
        SiteProperty property = getById(name);
        if (null == property) {
            property = new SiteProperty();
            property.setName(name);
        }
        property.setValue(value);
        saveOrUpdate(property);
    }

    @Transactional(readOnly = true)
    public Currency getSiteCurrency() {
        Session session = getCurrentSession();
        SiteProperty sp = (SiteProperty) session.get(SiteProperty.class, "site_price_currency_id");
        Integer scId = Integer.parseInt(sp.getValue());
        return (Currency) session.get(Currency.class, scId);
    }

    @Transactional(readOnly = true)
    public Unit getSiteWeightUnit() {
        Session session = getCurrentSession();
        SiteProperty sp = (SiteProperty) session.get(SiteProperty.class, "site_weight_unit_id");
        Integer uId = Integer.parseInt(sp.getValue());
        return (Unit) session.get(Unit.class, uId);
    }
}
