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

    @NotNull(groups = {Marker.OnUpdate.class, Marker.OnDelete.class})
    private Long id;

    @NotBlank
    private String username;

    private String firstname;
    private String lastname;

    @Pattern(regexp="(^$|[0-9]{11})")
    private String phone;
}
