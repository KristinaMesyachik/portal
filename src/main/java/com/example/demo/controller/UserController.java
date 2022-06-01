package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(HttpSession session) {
        return "login";
    }

    @GetMapping("/registration")
    public String addUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "user") User user) {
        userService.save(user);
        return "success-submit";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(value = {"/after-sing_up"}, method = RequestMethod.GET)
    public String afterRegistration(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        session.setAttribute("login", login);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.GET)
    public String viewPeople(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = userService.findByLogin(username);
        model.addAttribute("user", user);
        return "edit-profile";
    }
}
