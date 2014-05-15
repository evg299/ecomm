package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Person;
import ru.ecom4u.web.domain.db.entities.PersonContact;

import java.util.List;

/**
 * Created by Evgeny on 15.05.14.
 */
@Service
public class PersonService extends AbstractService {

    @Transactional(readOnly = true)
    public List<PersonContact> getPersonContacts(Person person) {
        Session session = getCurrentSession();
        return session.createCriteria(PersonContact.class)
                .add(Restrictions.eq("person", person))
                .list();
    }

}
