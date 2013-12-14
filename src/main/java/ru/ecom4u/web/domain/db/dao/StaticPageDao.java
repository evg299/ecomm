package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.StaticPage;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StaticPageDao extends AbstractDao {

	public List<StaticPage> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(StaticPage.class).list();
	}

	public StaticPage getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (StaticPage) hSession.get(StaticPage.class, id);
	}

	/*********************/
	public void save(StaticPage staticPage) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(staticPage);
	}

	public void update(StaticPage staticPage) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(staticPage);
	}

	public void saveOrUpdate(StaticPage staticPage) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(staticPage);
	}

	public void delete(StaticPage staticPage) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(staticPage);

	}

}
