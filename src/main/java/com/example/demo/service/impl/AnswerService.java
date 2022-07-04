package com.example.demo.service.impl;

import com.example.demo.entity.Answer;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IAnswerRepository;
import com.example.demo.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService implements IAnswerService {

    @Autowired
    private IAnswerRepository answerRepository;

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Answer findById(Long id) {
        Optional<Answer> answerOptional = answerRepository.findById(id);
        if (answerOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no answer with ID = " + id + "in database");
        }
        return answerOptional.get();
    }

    @Override
    public List<Answer> findByFieldId(Long id) {
        return answerRepository.findByFieldId(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }

}
