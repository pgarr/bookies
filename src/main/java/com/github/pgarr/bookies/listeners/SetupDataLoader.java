package com.github.pgarr.bookies.listeners;

import com.github.pgarr.bookies.security.dao.PrivilegeDAO;
import com.github.pgarr.bookies.security.dao.RoleDAO;
import com.github.pgarr.bookies.security.dao.UserDAO;
import com.github.pgarr.bookies.security.models.Privilege;
import com.github.pgarr.bookies.security.models.Role;
import com.github.pgarr.bookies.security.models.BookiesUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PrivilegeDAO privilegeDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    List<String> privilegesNames = Arrays.asList("PRIVILEGE_USER", "PRIVILEGE_LIBRARIAN", "PRIVILEGE_ADMIN");
    //TODO: extract enum for roles and privileges

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        List<Privilege> allPrivileges = createAllPrivilegesIfNotFound();

        // create initial roles
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", allPrivileges);

        // create initial user
        BookiesUser bookiesUser = new BookiesUser();
        bookiesUser.setFirstName("bookies");
        bookiesUser.setLastName("bookies");
        bookiesUser.setPassword(passwordEncoder.encode("test"));
        bookiesUser.setEmail("bookies@bookies.com");
        bookiesUser.setRoles(Arrays.asList(adminRole));
        bookiesUser.setEnabled(true);
        userDAO.add(bookiesUser);

        alreadySetup = true;


    }

    private List<Privilege> createAllPrivilegesIfNotFound() {
        List<Privilege> privileges = new ArrayList<>();
        for (String privilegeName : privilegesNames) {
            privileges.add(createPrivilegeIfNotFound(privilegeName));
        }
        return privileges;
    }


    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeDAO.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeDAO.add(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleDAO.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleDAO.add(role);
        }
        return role;
    }
}
