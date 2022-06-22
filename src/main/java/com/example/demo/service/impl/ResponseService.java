package com.example.demo.service.impl;

import com.example.demo.entity.Answer;
import com.example.demo.entity.Response;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IResponseRepository;
import com.example.demo.service.IResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseService implements IResponseService {

    @Autowired
    private IResponseRepository responseRepository;

    @Autowired
    private AnswerService answerService;

    @Override
    public Page<Response> findAll(Pageable pageable) {
        return responseRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Response save(Response response) {
        List<Answer> answers = new ArrayList<>();
        for (Answer answer : response.getAnswers()) {
            Answer answer1 = answerService.save(answer);
            answers.add(answerService.findById(answer1.getId()));
        }
        response.setAnswers(answers);
        Response save = responseRepository.save(response);
        for (Answer answer : response.getAnswers()) {
            answer.setResponseId(save.getId());
            answerService.save(answer);
        }
        return findById(response.getId());
    }

    @Override
    public Response findById(Long id) {
        Optional<Response> responseOptional = responseRepository.findById(id);
        if (responseOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no response with ID = " + id + "in database");
        }
        return responseOptional.get();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        responseRepository.deleteById(id);
    }
}
