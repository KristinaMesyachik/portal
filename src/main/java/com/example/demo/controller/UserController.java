package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.IMailService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
//@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IMailService mailService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)//TODO IN REST
    public String login() {
        return "login";
    }

    @GetMapping("/registration")//TODO IN REST
    public String addUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.POST) //return?
    public void create(@RequestBody User user) {
        userService.registrationSave(user);
        mailService.sendSimpleEmail(user.getUsername(),
                "About registration",
                "You have successfully registered");
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.PUT)
    public User update(@ModelAttribute(name = "user") User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.update(user, auth.getName());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")//TODO IN REST
    @RequestMapping(value = {"/after-sing_up"}, method = RequestMethod.GET)
    public String afterRegistration(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        session.setAttribute("username", login);
        return "redirect:/api/fields/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")//TODO IN REST
    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.GET)
    public String viewPeople(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "edit-profile";
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")//TODO IN REST
    @RequestMapping(value = {"/edit-password"}, method = RequestMethod.GET)
    public String editPass(HttpSession session) {
//        String password = "", newPassword = "";
//        session.setAttribute("password", password);
//        session.setAttribute("newPassword", newPassword);
        return "edit-password";
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")//TODO IN REST
    @RequestMapping(value = {"/edit-password"}, method = RequestMethod.POST)
    public String savePass(HttpSession session,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "newPassword") String newPassword) {
        String username = (String) session.getAttribute("username");
        boolean isChangePassword = userService.changePassword(username, password, newPassword);
        if (isChangePassword) {
            mailService.sendSimpleEmail(username,
                    "About change password",
                    "You have successfully change password");
            return "redirect:/api/fields/";
        } else {
            return "edit-password";
        }
    }
}
