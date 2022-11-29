package com.example.demo.entity;

import com.example.demo.entity.enums.Type;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "Class representing a field in the application.")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "field")
public class Field {

    @ApiModelProperty(notes = "Unique identifier of the Field.",
            example = "10", required = true, position = 0)
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "field_seq_gen", sequenceName = "field_id_seq",
            initialValue = 6, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field_seq_gen")
    private Long id;

    @ApiModelProperty(notes = "Label of the field.",
            example = "Name", required = true, position = 1)
    @NotBlank(message = "Field label cannot be blank")
    @Column(name = "label")
    private String label;

    @ApiModelProperty(notes = "Type of the field.",
            example = "SINGLE_LINE_TEXT", required = true, position = 2)
    @NotNull(message = "Field type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @ApiModelProperty(notes = "Field is required?",
            example = "true", required = true, position = 3)
    @NotNull(message = "isRequired field cannot be null")
    @Column(name = "is_required")
    private Boolean isRequired;

    @ApiModelProperty(notes = "Field is active?",
            example = "true", required = true, position = 4)
    @NotNull(message = "isActive field cannot be null")
    @Column(name = "is_active")
    private Boolean isActive;

    @ApiModelProperty(notes = "Field options",
            example = " [\n" +
                    "{\n" +
                    "\"id\": 1,\n" +
                    "\"title\": \"Male\",\n" +
                    "\"fieldId\": 3\n" +
                    "},\n" +
                    "{\n" +
                    "\"id\": 2,\n" +
                    "\"title\": \"Female\",\n" +
                    "\"fieldId\": 3\n" +
                    "}\n" +
                    "]\n",
            position = 5)
    @OneToMany(mappedBy = "fieldId"
            , cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "Field options cannot be null")
    private List<Option> options;
}
