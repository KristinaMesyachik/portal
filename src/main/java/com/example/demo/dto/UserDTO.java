package com.example.demo.dto;

import com.example.demo.dto.interf.Marker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull(groups = {Marker.OnUpdate.class, Marker.OnDelete.class},
            message = "User id cannot be null when updating or deleting")
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    private String firstname;
    private String lastname;

    @Pattern(regexp = "(^$|[0-9]{11}|[+][0-9]{12})")
    private String phone;
}
