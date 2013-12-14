package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.SiteProperty;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SitePropertyDao extends AbstractDao {

	public List<SiteProperty> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(SiteProperty.class).list();
	}

	public SiteProperty getById(String id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (SiteProperty) hSession.get(SiteProperty.class, id);
	}

	/*********************/
	public void save(SiteProperty siteProperty) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(siteProperty);
	}

	public void update(SiteProperty siteProperty) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(siteProperty);
	}

	public void saveOrUpdate(SiteProperty siteProperty) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(siteProperty);
	}

	public void delete(SiteProperty siteProperty) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(siteProperty);

	}

}
