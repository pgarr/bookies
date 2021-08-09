package com.github.pgarr.bookies.security.dao;

import com.github.pgarr.bookies.security.models.BookiesUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public BookiesUser findByEmail(String email) {
        return entityManager.createQuery("FROM BookiesUser U WHERE U.email=:email", BookiesUser.class)
                .setParameter("email", email)
                .getResultList().stream().findFirst().orElse(null);
    }

    public BookiesUser add(BookiesUser user) {
        entityManager.persist(user);
        return user;
    }

}
