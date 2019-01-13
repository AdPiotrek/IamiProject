package com.programowanie.zespolowe.pz.authapi.security;

import com.programowanie.zespolowe.pz.dao.UserDAO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserDAO userDAO;

    public UserDetailsServiceImpl(UserDAO applicationUserRepository) {
        this.userDAO = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.programowanie.zespolowe.pz.entities.User applicationUser = userDAO.findByEmail(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList()) {
        };
    }
}