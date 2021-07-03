package com.github.pgarr.bookies.security.dao;

import com.github.pgarr.bookies.security.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public User findByEmail(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM User U WHERE U.email=:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public User add(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }

}
