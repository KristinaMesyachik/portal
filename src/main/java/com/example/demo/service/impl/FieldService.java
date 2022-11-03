package com.example.demo.service.impl;

import com.example.demo.dto.FieldDTO;
import com.example.demo.entity.Field;
import com.example.demo.entity.Option;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IFieldRepository;
import com.example.demo.repository.IOptionRepository;
import com.example.demo.service.interf.IFieldService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FieldService implements IFieldService {

    @Autowired
    private IFieldRepository fieldRepository;

    @Autowired
    private IOptionRepository optionRepository;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<FieldDTO> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable)
                .map(this::convertEntityToDto);
    }

    @Override
    public List<FieldDTO> findByIsActiveTrue() {
        return fieldRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FieldDTO save(FieldDTO fieldDTO) {
        Field field = convertDtoToEntity(fieldDTO);
        Field save = fieldRepository.save(field);
        if (!save.getOptions().isEmpty()) {
            save.getOptions().forEach((o) -> {
                Option byId = optionRepository.findById(o.getId()).get();
                byId.setFieldId(save.getId());
                optionRepository.save(byId);
            });
        }
        return convertEntityToDto(save);
    }

    @Override
    public FieldDTO findById(Long id) {
        return convertEntityToDto(findByIdEntity(id));
    }

    @Override
    public Field findByIdEntity(Long id) {
        Optional<Field> fieldOptional = fieldRepository.findById(id);
        if (fieldOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no field with ID = " + id + " in database");
        }
        return fieldOptional.get();
    }

    @Override
    public FieldDTO update(Long id, FieldDTO newFieldDTO) {
        Field field = findByIdEntity(id);
        Field newField = convertDtoToEntity(newFieldDTO);
        if (!field.getOptions().isEmpty()) {
            field.getOptions().forEach((opOLD) -> {
                if (newField.getOptions()
                        .stream()
                        .map(Option::getFieldId)
                        .anyMatch(opOLD.getId()::equals)) {
                    optionRepository.deleteById(opOLD.getId());
                }
            });
        }
        Field save = fieldRepository.save(newField);
        if (!save.getOptions().isEmpty()) {
            for (Option option : save.getOptions()) {
                Option optionById = optionRepository.findById(option.getId()).orElseThrow();
                if (optionById.getFieldId() == null) {
                    optionById.setFieldId(save.getId());
                    optionRepository.save(optionById);
                }
            }
        }
        return convertEntityToDto(save);
    }

    @Override
    public List<FieldDTO> findAll() {
        return fieldRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Field field = findByIdEntity(id);
        field.getOptions()
                .forEach(option -> optionRepository.deleteById(option.getId()));
        answerService.findByFieldId(id)
                .forEach(answer -> answerService.deleteById(answer.getId()));
        fieldRepository.deleteById(id);
    }

    @Override
    public FieldDTO convertEntityToDto(Field field) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(field, FieldDTO.class);
    }

    @Override
    public Field convertDtoToEntity(FieldDTO fieldDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(fieldDTO, Field.class);
    }
}
