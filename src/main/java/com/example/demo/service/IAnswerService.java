package com.example.demo.service;

import com.example.demo.entity.Answer;

import java.util.List;

public interface IAnswerService extends IService<Answer, Long> {

    List<Answer> findByFieldId(Long fieldId);

}
