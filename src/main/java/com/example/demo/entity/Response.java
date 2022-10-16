package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "response")
public class Response {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "response_seq_gen", sequenceName = "response_id_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "response_seq_gen")
    private Long id;

    @OneToMany(mappedBy = "responseId"
            , cascade = CascadeType.ALL)
    private List<Answer> answers;

}
