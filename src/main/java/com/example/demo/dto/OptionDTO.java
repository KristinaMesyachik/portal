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

    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = {Marker.OnUpdate.class, Marker.OnDelete.class})
    private Long id;

    @NotBlank
    private String title;

    @Min(0)
    private Long fieldId;
}
