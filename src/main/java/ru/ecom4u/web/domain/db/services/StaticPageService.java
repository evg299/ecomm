package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.ecom4u.web.domain.db.entities.StaticPage;

@Service
public class StaticPageService extends AbstractService {

	@Transactional(readOnly = true)
	public StaticPage getStaticPageByUrlName(String urlName) {
		Session session = getCurrentSession();
		return (StaticPage) session.createCriteria(StaticPage.class)
				.add(Restrictions.eq("urlName", urlName))
				.uniqueResult();
	}
}
