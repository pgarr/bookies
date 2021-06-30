package com.github.pgarr.bookies.dao.users;

import com.github.pgarr.bookies.models.users.Privilege;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PrivilegeDAO {


    @Autowired
    private SessionFactory sessionFactory;

    public Privilege findByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM Privilege R WHERE R.name=:name", Privilege.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
