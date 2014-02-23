package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Currency;

/**
 * Created by Evgeny(e299792459@gmail.com) on 23.02.14.
 */
@Service
public class CurrencyService extends AbstractService {

    @Transactional(readOnly = true)
    public Currency getById(Integer id){
        Session session = getCurrentSession();
        return (Currency) session.get(Currency.class, id);
    }
}
