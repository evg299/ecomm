package ru.ecom4u.web.domain.db.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {

	@Autowired
	protected SessionFactory sessionFactory;
}
