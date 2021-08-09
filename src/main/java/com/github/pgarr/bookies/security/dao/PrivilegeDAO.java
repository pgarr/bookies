package com.github.pgarr.bookies.security.dao;

import com.github.pgarr.bookies.security.models.Privilege;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PrivilegeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Privilege findByName(String name) {
        return entityManager.createQuery("FROM Privilege R WHERE R.name=:name", Privilege.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Privilege add(Privilege privilege) {
        entityManager.persist(privilege);
        return privilege;
    }
}
