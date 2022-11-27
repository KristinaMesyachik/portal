package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "option")
public class Option {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "option_seq_gen", sequenceName = "option_id_seq",
            initialValue = 7, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_seq_gen")
    private Long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(name = "field_id")
    private Long fieldId;
}
