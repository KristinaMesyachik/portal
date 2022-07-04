package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.IMailService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IMailService mailService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "Success login";
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.PUT)
    public User update(@RequestBody User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.update(user, auth.getName());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public User findByUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User byUsername = userService.findByUsername(auth.getName());
        System.err.println(byUsername);
        return byUsername;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/editPassword/"}, method = RequestMethod.GET)
    public Boolean savePass(
            @RequestParam(name = "newPassword") String newPassword,
            @RequestParam(name = "password") String password) {
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
