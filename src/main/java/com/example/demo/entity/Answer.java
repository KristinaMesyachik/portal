package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answer", schema = "portal")
public class Answer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "answer")
    private String answer;

    @Column(name = "response_id")
    private Long responseId;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
}
