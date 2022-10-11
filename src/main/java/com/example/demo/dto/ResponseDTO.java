package com.example.demo.dto;

import com.example.demo.dto.interf.Marker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

     @Null(groups = Marker.OnCreate.class)
     @NotNull(groups = {Marker.OnUpdate.class, Marker.OnDelete.class})
     private Long id;

     @NotEmpty
     private List<AnswerDTO> answers;
}
