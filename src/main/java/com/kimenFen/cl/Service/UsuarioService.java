package com.kimenFen.cl.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

//    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
//
//    public UsuarioService(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
//        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
//    }
//
//    public void createUser(String username, String password, String role) {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username(username)
//                .password(password)
//                .roles(role)
//                .build();
//        inMemoryUserDetailsManager.createUser(user);
//    }
}
