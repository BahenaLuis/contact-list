package com.demo.contactlist.service;

import org.springframework.security.core.userdetails.User;
import com.demo.contactlist.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.demo.contactlist.persistence.entity.User user = this.userRepository.findByEmailAndDeletedFalse(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        String password = "{noop}" + user.getPassword();
        return new User(user.getEmail(), password, new ArrayList<>());
    }


    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
