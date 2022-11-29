package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel(description = "Class representing a user in the application.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @ApiModelProperty(notes = "Unique identifier of the User.",
            example = "10", required = true, position = 0)
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq",
            initialValue = 3, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    private Long id;

    @ApiModelProperty(notes = "Email for user.",
            example = "Rita@gmail.com", required = true, position = 1)
    @Column(name = "email", unique = true)
    private String username;

    @NotBlank(message = "Username cannot be blank")
    @ApiModelProperty(notes = "Encoded password using bcrypt for the user.",
            example = "$2a$10$X1MQcXOCZ349yizbMkv8Fei1bSGcCjOADoVascDizkH8LbM68Fdva",
            required = true, position = 2)
    @Column(name = "password")
    private String password;

    @NotBlank(message = "User password cannot be blank")
    @ApiModelProperty(notes = "Firstname user.",
            example = "Rita", position = 3)
    @Column(name = "firstname")
    private String firstname;

    @ApiModelProperty(notes = "Lastname user.",
            example = "Orlova", position = 4)
    @Column(name = "lastname")
    private String lastname;

    @ApiModelProperty(notes = "Number user.",
            example = "80447966275", position = 5)
    @Pattern(regexp = "(^$|[0-9]{11}|[+][0-9]{12})",
            message = "User phone pattern like nothing or eleven count number(10123456789)")
    @Column(name = "phone")
    private String phone;
}
