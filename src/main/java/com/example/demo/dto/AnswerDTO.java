package com.example.demo.dto;

import com.example.demo.dto.interf.Marker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    @Null(groups = Marker.OnCreate.class, message = "Answer id be null when creating")
    @NotNull(groups = {Marker.OnUpdate.class, Marker.OnDelete.class},
            message = "Answer id cannot be null when updating or deleting")
    private Long id;

    @NotBlank(message = "Answer cannot be blank")
    private String answer;

    private Long responseId;

    @NotNull(message = "Field cannot be null")
    private FieldDTO field;
}
