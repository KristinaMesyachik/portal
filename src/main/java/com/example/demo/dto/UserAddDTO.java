package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddDTO {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "User password cannot be blank")
    private String password;

    private String firstname;
    private String lastname;

    @Pattern(regexp =  "(^$|[0-9]{11}|[+][0-9]{12})",
            message = "User phone pattern like nothing or 80447966275 or +375447966275")
    private String phone;
}
