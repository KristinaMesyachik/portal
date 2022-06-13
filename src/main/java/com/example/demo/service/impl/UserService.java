package com.example.demo.service.impl;

import com.example.demo.entity.MyUserDetails;
import com.example.demo.entity.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IService<User, Long>, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new MyUserDetails(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void registrationSave(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean changePassword(String username, String pass, String newPass) {
        User user = findByUsername(username);
        if(BCrypt.checkpw(pass,user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }

    public User update(User user, String username) {
        User byUsername = findByUsername(username);
        user.setPassword(byUsername.getPassword());
        return userRepository.save(user);
    }
}
