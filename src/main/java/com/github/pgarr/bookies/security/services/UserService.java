package com.github.pgarr.bookies.security.services;


import com.github.pgarr.bookies.security.dao.UserDAO;
import com.github.pgarr.bookies.security.dto.UserDTO;
import com.github.pgarr.bookies.security.exceptions.UserAlreadyExistException;
import com.github.pgarr.bookies.security.models.BookiesUser;
import com.github.pgarr.bookies.security.models.Privilege;
import com.github.pgarr.bookies.security.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        try {
            final BookiesUser user = userDAO.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with email: " + email);
            }

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<>();
        final List<Privilege> collection = new ArrayList<>();
        for (final Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public BookiesUser registerNewUserAccount(UserDTO userDTO) throws UserAlreadyExistException {
        if (emailExist(userDTO.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDTO.getEmail());
        }
        BookiesUser user = new BookiesUser();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());

        return userDAO.add(user);
    }

    private boolean emailExist(String email) {
        return userDAO.findByEmail(email) != null;
    }
}
