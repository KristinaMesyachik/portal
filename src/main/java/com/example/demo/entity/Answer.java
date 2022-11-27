package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "answer_seq_gen", sequenceName = "answer_id_seq",
            initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq_gen")
    private Long id;

    @NotBlank
    @Column(name = "answer")
    private String answer;

    @Column(name = "response_id")
    private Long responseId;

    //@NotBlank
    @NotNull
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
}
