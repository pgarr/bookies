package com.github.pgarr.bookies.security.dao;

import com.github.pgarr.bookies.security.models.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO {


    @Autowired
    private SessionFactory sessionFactory;

    public Role findByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM Role R WHERE R.name=:name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Role add(Role role) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(role);
        return role;
    }
}
