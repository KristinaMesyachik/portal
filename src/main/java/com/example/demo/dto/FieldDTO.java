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

    @Null(groups = Marker.OnCreate.class, message = "Field id be null when creating")
    @NotNull(groups = {Marker.OnUpdate.class, Marker.OnDelete.class},
            message = "Field id cannot be null when updating or deleting")
    private Long id;

    @NotBlank(message = "Field label cannot be blank")
    private String label;

    @NotNull(message = "Field type cannot be null")
    private Type type;

    @NotNull(message = "isRequired field cannot be null")
    private Boolean isRequired;

    @NotNull(message = "isActive field cannot be null")
    private Boolean isActive;

    @NotNull(message = "Field options cannot be null")
    private List<OptionDTO> options;
}
