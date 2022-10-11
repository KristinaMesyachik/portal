package com.example.demo.service.interf;

import com.example.demo.dto.AnswerDTO;
import com.example.demo.entity.Answer;

import java.util.List;

public interface IAnswerService extends IService<AnswerDTO, Long, Answer> {
    List<AnswerDTO> findByFieldId(Long fieldId);

    Answer saveEntity(Answer answer);
}
