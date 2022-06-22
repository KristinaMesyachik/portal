package com.example.demo.controller;

import com.example.demo.entity.MyUserDetails;
import com.example.demo.entity.User;
import com.example.demo.service.IMailService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IMailService mailService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public Boolean login(@RequestBody User user) {
        return userService.existsByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public User create(@RequestBody User user) {
        userService.save(user);
        mailService.sendSimpleEmail(user.getUsername(),
                "About registration",
                "You have successfully registered");
        user.setPassword(null);
        return user;
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.PUT)
    public User update(@RequestBody User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.update(user, auth.getName());
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/edit-password"}, method = RequestMethod.POST)
    public Boolean savePass(@RequestParam(name = "password") String password,
                            @RequestParam(name = "newPassword") String newPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isChangePassword = userService.changePassword(username, password, newPassword);
        if (isChangePassword) {
            mailService.sendSimpleEmail(username,
                    "About change password",
                    "You have successfully change password");
        }
        return isChangePassword;
    }
}
