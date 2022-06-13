package com.example.demo.service.impl;

import com.example.demo.entity.Answer;
import com.example.demo.entity.Field;
import com.example.demo.entity.Response;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IAnswerRepository;
import com.example.demo.repository.IResponseRepository;
import com.example.demo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseService implements IService<Response, Long> {

    @Autowired
    private IResponseRepository responseRepository;

    @Autowired
    private IAnswerRepository answerRepository;

    public Page<Response> findAll(Pageable pageable) {
        return responseRepository.findAll(pageable);
    }

    public List<Response> findAll() {
        return responseRepository.findAll();
    }

    @Transactional
    public Response create(Response response) {
        List<Answer> answers = new ArrayList<>();
        for (Answer answer : response.getAnswers()) {
            Answer answer1 = answerRepository.save(answer);
            answers.add(answerFindById(answer1.getId()));
        }
        response.setAnswers(answers);
        Response save = responseRepository.save(response);
        for (Answer answer : response.getAnswers()) {
            answer.setResponseId(save.getId());
            answerRepository.save(answer);
        }
        return findById(response.getId());
    }

    public Answer answerFindById(Long id) {
        Optional<Answer> answerOptional = answerRepository.findById(id);
        if (answerOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no answer with ID = " + id + "in database");
        }
        return answerOptional.get();
    }

    public Response findById(Long id) {
        Optional<Response> responseOptional = responseRepository.findById(id);
        if (responseOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no response with ID = " + id + "in database");
        }
        return responseOptional.get();
    }
}
