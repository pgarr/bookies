package com.github.pgarr.bookies.dao.users;

import javax.persistence.EntityManager;

import com.github.pgarr.bookies.models.users.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO {

	@Autowired
	private EntityManager entityManager;

	public Role findRoleByName(String roleName) {
		Session session = entityManager.unwrap(Session.class);
		Query<Role> query = session.createQuery("from Role where name=:roleName", Role.class);
		query.setParameter("roleName", roleName);

		Role role = null;
		try {
			role = query.getSingleResult();
		} catch (Exception e) {
			role = null;
		}
		return role;
	}
}
