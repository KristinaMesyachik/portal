package com.example.demo.controller;

import com.example.demo.dto.interf.Marker;
import com.example.demo.dto.UserAddDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.interf.IMailService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
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
    public UserDTO create(@Valid @RequestBody UserAddDTO user) {
        UserDTO userDTO = userService.saveNewUser(user);
        mailService.sendSimpleEmail(user.getUsername(),
                "About registration",
                "You have successfully registered");
        return userDTO;
    }

    @Validated({Marker.OnUpdate.class})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.PUT)
    public UserDTO update(@Valid @RequestBody UserDTO user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.update(user, auth.getName());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public UserDTO findByUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/editPassword/"}, method = RequestMethod.GET)
    public Boolean savePass(
            @RequestParam(name = "newPassword") @NotBlank String newPassword,
            @RequestParam(name = "password") @NotBlank String password) {
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
