package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    User findByUsername(String username);
    User save(User user);
    boolean changePassword(String username, String pass, String newPass);
    User update(User user, String username);
    Boolean existsByUsernameAndPassword(String username, String password);
}
