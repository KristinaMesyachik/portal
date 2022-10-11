package com.example.demo.dto;

import com.example.demo.dto.interf.Marker;
import com.example.demo.entity.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO {

    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = {Marker.OnUpdate.class, Marker.OnDelete.class})
    private Long id;

    @NotBlank
    private String label;

    @NotNull
    private Type type;

    @NotNull
    private Boolean isRequired;

    @NotNull
    private Boolean isActive;

    @NotNull
    private List<OptionDTO> options;
}
