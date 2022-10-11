package com.example.demo.service.impl;

import com.example.demo.dto.AnswerDTO;
import com.example.demo.entity.Answer;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IAnswerRepository;
import com.example.demo.service.interf.IAnswerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerService implements IAnswerService {

    @Autowired
    private IAnswerRepository answerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AnswerDTO save(AnswerDTO answerDTO) {
        Answer answer = convertDtoToEntity(answerDTO);
        return convertEntityToDto(saveEntity(answer));
    }

    @Override
    public Answer saveEntity(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Answer findByIdEntity(Long id) {
        Optional<Answer> answerOptional = answerRepository.findById(id);
        if (answerOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no answer with ID = " + id + "in database");
        }
        return answerOptional.get();
    }

    @Override
    public AnswerDTO findById(Long id) {
        Answer answer = findByIdEntity(id);
        return convertEntityToDto(answer);
    }

    @Override
    public List<AnswerDTO> findByFieldId(Long id) {
        return answerRepository.findByFieldId(id)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public AnswerDTO convertEntityToDto(Answer answer) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(answer, AnswerDTO.class);
    }

    @Override
    public Answer convertDtoToEntity(AnswerDTO answerDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(answerDTO, Answer.class);
    }
}
