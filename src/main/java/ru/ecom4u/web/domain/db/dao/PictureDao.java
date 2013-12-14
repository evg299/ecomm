package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Picture;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PictureDao extends AbstractDao {

	public List<Picture> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Picture.class).list();
	}

	public Picture getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Picture) hSession.get(Picture.class, id);
	}

	/*********************/
	public void save(Picture picture) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(picture);
	}

	public void update(Picture picture) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(picture);
	}

	public void saveOrUpdate(Picture picture) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(picture);
	}

	public void delete(Picture picture) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(picture);

	}

}
