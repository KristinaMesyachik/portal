package com.example.demo.service.impl;

import com.example.demo.entity.MyUserDetails;
import com.example.demo.entity.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new MyUserDetails(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found"));
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean changePassword(String username, String pass, String newPass) {
        User user = findByUsername(username);
        if (BCrypt.checkpw(pass, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User update(User user, String username) {
        User byUsername = findByUsername(username);
        user.setId(byUsername.getId());
        user.setPassword(byUsername.getPassword());
        return userRepository.save(user);
    }

    @Override
    public Boolean existsByUsernameAndPassword(String username, String password){
        User user = findByUsername(username);
        return BCrypt.checkpw(password, user.getPassword());
    }
}
