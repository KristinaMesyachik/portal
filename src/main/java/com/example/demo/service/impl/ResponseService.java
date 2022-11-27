package com.example.demo.service.impl;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Response;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IResponseRepository;
import com.example.demo.service.interf.IResponseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ResponseService implements IResponseService {

    @Autowired
    private IResponseRepository responseRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ResponseDTO> findAll(Pageable pageable) {
        return responseRepository.findAll(pageable)
                .map(this::convertEntityToDto);
    }

    @Override
    @Transactional
    public ResponseDTO save(ResponseDTO responseDTO) {
        Response response = convertDtoToEntity(responseDTO);
        Response save = responseRepository.save(response);
        response.getAnswers().forEach(s -> {
            s.setResponseId(save.getId());
            answerService.saveEntity(s);
        });
        return findById(response.getId());
    }

    @Override
    public Response findByIdEntity(Long id) {
        Optional<Response> responseOptional = responseRepository.findById(id);
        if (responseOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no response with ID = " + id + "in database");
        }
        return responseOptional.get();
    }

    @Override
    public ResponseDTO findById(Long id) {
        Response response = findByIdEntity(id);
        return convertEntityToDto(response);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        responseRepository.deleteById(id);
    }

    @Override
    public ResponseDTO convertEntityToDto(Response response) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(response, ResponseDTO.class);
    }

    @Override
    public Response convertDtoToEntity(ResponseDTO responseDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(responseDTO, Response.class);
    }
}
