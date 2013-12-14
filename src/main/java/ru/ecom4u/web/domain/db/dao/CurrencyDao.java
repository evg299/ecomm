package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Currency;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CurrencyDao extends AbstractDao {

	public List<Currency> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Currency.class).list();
	}

	public Currency getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Currency) hSession.get(Currency.class, id);
	}

	/*********************/
	public void save(Currency currency) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(currency);
	}

	public void update(Currency currency) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(currency);
	}

	public void saveOrUpdate(Currency currency) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(currency);
	}

	public void delete(Currency currency) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(currency);

	}

}
