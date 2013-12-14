package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Person;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PersonDao extends AbstractDao {

	public List<Person> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Person.class).list();
	}

	public Person getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Person) hSession.get(Person.class, id);
	}

	/*********************/
	public void save(Person person) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(person);
	}

	public void update(Person person) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(person);
	}

	public void saveOrUpdate(Person person) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(person);
	}

	public void delete(Person person) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(person);

	}

}
