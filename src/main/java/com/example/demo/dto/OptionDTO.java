package com.example.demo.dto;

import com.example.demo.dto.interf.Marker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDTO {

    @Null(groups = Marker.OnCreate.class, message = "Option id be null when creating")
    @NotNull(groups = {Marker.OnUpdate.class, Marker.OnDelete.class},
            message = "Option id cannot be null when updating or deleting")
    private Long id;

    @NotBlank(message = "Option title cannot be blank")
    private String title;

    @Min(value = 0, message = "Age should not be less than 15")
    private Long fieldId;
}
