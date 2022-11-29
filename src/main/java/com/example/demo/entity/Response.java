package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@ApiModel(description = "Class representing a response in the application.")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "response")
public class Response {

    @ApiModelProperty(notes = "Unique identifier of the Response.",
            example = "10", required = true, position = 0)
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "response_seq_gen", sequenceName = "response_id_seq",
            initialValue = 3, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "response_seq_gen")
    private Long id;

    @ApiModelProperty(notes = "List answer for questions.",
            example = "10", required = true, position = 1)
    @OneToMany(mappedBy = "responseId"
            , cascade = CascadeType.ALL)
    private List<Answer> answers;

}
