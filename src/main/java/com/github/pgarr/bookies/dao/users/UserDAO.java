package com.github.pgarr.bookies.dao.users;

import com.github.pgarr.bookies.models.users.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserDAO {

	@Autowired
	private EntityManager entityManager;

	public User findByUserName(String theUserName) {
		Session session = entityManager.unwrap(Session.class);
		Query<User> query = session.createQuery("from User where userName=:uName", User.class);
		query.setParameter("uName", theUserName);

		User user = null;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			user = null;
		}
		return user;
	}

	public void save(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);
	}

}
