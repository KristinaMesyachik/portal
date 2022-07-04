package com.example.demo.repository;

import com.example.demo.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByFieldId(Long fieldId);
}
