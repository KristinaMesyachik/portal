package com.example.demo.controller;

import com.example.demo.dto.interf.Marker;
import com.example.demo.dto.UserAddDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.interf.IMailService;
import com.example.demo.service.impl.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(description = "Endpoints for Creating, Retrieving, Updating and Deleting of Users.",
        tags = {"user"})
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IMailService mailService;

    @ApiOperation(value = "Add a new user", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 400, message = "Invalid input")})
    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public UserDTO create(
            @ApiParam("User to add. Cannot null or empty.")
            @Valid @RequestBody UserAddDTO user) {
        UserDTO userDTO = userService.saveNewUser(user);
        mailService.sendSimpleEmail(user.getUsername(),
                "About registration",
                "You have successfully registered");
        return userDTO;
    }

    @ApiOperation(value = "Update an existing user (need authenticated)", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Field not found"),
            @ApiResponse(code = 405, message = "Validation exception")})
    @Validated({Marker.OnUpdate.class})
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/"}, method = RequestMethod.PUT)
    public UserDTO update(
            @ApiParam("User to update. Cannot null or empty.")
            @Valid @RequestBody UserDTO user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.update(user, auth.getName());
    }

    @ApiOperation(value = "Find user by username (need authenticated)", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = UserDTO.class),
            @ApiResponse(code = 404, message = "User not found")})
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public UserDTO findByUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }

    @ApiOperation(value = "Update password (need authenticated)", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)})
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/editPassword/"}, method = RequestMethod.GET)
    public Boolean savePass(
            @ApiParam("New password. Cannot be empty.")
            @RequestParam(name = "newPassword") @NotBlank String newPassword,
            @ApiParam("Old password. Cannot be empty.")
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
