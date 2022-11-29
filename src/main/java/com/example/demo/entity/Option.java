package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@ApiModel(description = "Class representing a option in the application.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "option")
public class Option {

    @ApiModelProperty(notes = "Unique identifier of the Option.",
            example = "10", required = true, position = 0)
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "option_seq_gen", sequenceName = "option_id_seq",
            initialValue = 7, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_seq_gen")
    private Long id;

    @ApiModelProperty(notes = "Title for option.",
            example = "Email", required = true, position = 1)
    @NotBlank(message = "Option title cannot be blank")
    @Column(name = "title")
    private String title;

    @ApiModelProperty(notes = "Id field for option.",
            example = "10", position = 2)
    @Column(name = "field_id")
    private Long fieldId;
}
