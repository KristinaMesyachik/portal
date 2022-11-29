package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "Class representing a answer in the application.")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answer")
public class Answer {

    @ApiModelProperty(notes = "Unique identifier of the Answer.",
            example = "10", required = true, position = 0)
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "answer_seq_gen", sequenceName = "answer_id_seq",
            initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq_gen")
    private Long id;

    @ApiModelProperty(notes = "Answer for question.",
            example = "Rita", required = true, position = 1)
    @NotBlank(message = "Answer cannot be blank")
    @Column(name = "answer")
    private String answer;

    @ApiModelProperty(notes = "Id response for answer.",
            example = "10", position = 2)
    @Column(name = "response_id")
    private Long responseId;

    @ApiModelProperty(notes = "Field for answer.",
            example = "{\n" +
                    "\"id\": 2,\n" +
                    "\"label\": \"Email\",\n" +
                    "\"type\": \"SINGLE_LINE_TEXT\",\n" +
                    "\"isRequired\": true,\n" +
                    "\"isActive\": true,\n" +
                    "\"options\": []\n" +
                    "}\n",
            position = 3)
    @NotNull(message = "Field cannot be null")
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
}
