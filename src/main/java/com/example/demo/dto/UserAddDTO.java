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

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String firstname;
    private String lastname;

    @Pattern(regexp="(^$|[0-9]{11})")
    private String phone;
}
