package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Unit;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UnitDao extends AbstractDao {

	public List<Unit> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Unit.class).list();
	}

	public Unit getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Unit) hSession.get(Unit.class, id);
	}

	/*********************/
	public void save(Unit unit) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(unit);
	}

	public void update(Unit unit) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(unit);
	}

	public void saveOrUpdate(Unit unit) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(unit);
	}

	public void delete(Unit unit) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(unit);

	}

}
