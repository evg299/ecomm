package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Comment;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CommentDao extends AbstractDao {

	public List<Comment> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Comment.class).list();
	}

	public Comment getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Comment) hSession.get(Comment.class, id);
	}

	/*********************/
	public void save(Comment comment) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(comment);
	}

	public void update(Comment comment) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(comment);
	}

	public void saveOrUpdate(Comment comment) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(comment);
	}

	public void delete(Comment comment) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(comment);

	}

}
