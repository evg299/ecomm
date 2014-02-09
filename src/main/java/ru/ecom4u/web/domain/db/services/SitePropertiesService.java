package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import ru.ecom4u.web.domain.db.entities.SiteProperty;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Evgeny(e299792459@gmail.com) on 09.02.14.
 */
@Service
public class SitePropertiesService extends AbstractService {
    private Map<String, String> cash = new TreeMap<String, String>();

    public List<SiteProperty> getAll() {
        Session session = getCurrentSession();
        return session.createCriteria(SiteProperty.class).list();
    }
}
