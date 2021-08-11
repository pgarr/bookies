package com.github.pgarr.bookies.security.dao;

import com.github.pgarr.bookies.security.models.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Role findByName(String name) {
        return entityManager.createQuery("FROM Role R WHERE R.name=:name", Role.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst().orElse(null);
    }

    public Role add(Role role) {
        entityManager.persist(role);
        return role;
    }
}
