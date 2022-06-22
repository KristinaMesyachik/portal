package com.example.demo.service.impl;

import com.example.demo.entity.Answer;
import com.example.demo.entity.Field;
import com.example.demo.entity.Option;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IFieldRepository;
import com.example.demo.repository.IOptionRepository;
import com.example.demo.service.IFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Page<Field> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable);
    }

    @Override
    public List<Field> findByIsActiveTrue() {

        return fieldRepository.findByIsActiveTrue();
    }

    @Override
    public Field save(Field field) {
        Field save = fieldRepository.save(field);
        if(save.getOptions() != null){
            for (Option option : save.getOptions()) {
                Optional<Option> byId = optionRepository.findById(option.getId());
                byId.get().setFieldId(save.getId());
                optionRepository.save(byId.get());
            }
        }
        return save;
    }

    @Override
    public Field findById(Long id) {
        Optional<Field> fieldOptional = fieldRepository.findById(id);
        if (fieldOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no field with ID = " + id + "in database");
        }
        return fieldOptional.get();
    }

    @Override
    public Field update(Long id, Field newField) {
        boolean flag = false;
        Field field = findById(id);
        if (!field.getOptions().isEmpty()) {
            for (Option opOLD : field.getOptions()) {
                for (Option opNew : newField.getOptions()) {
                    if (Objects.equals(opOLD.getId(), opNew.getId())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    optionRepository.deleteById(opOLD.getId());
                }
                flag = false;
            }
        }
        Field save = fieldRepository.save(newField);
        if (!save.getOptions().isEmpty()) {
            for (Option option : save.getOptions()) {
                Optional<Option> byId = optionRepository.findById(option.getId());
                if (byId.get().getFieldId() == null) {
                    byId.get().setFieldId(save.getId());
                    optionRepository.save(byId.get());
                }
            }
        }
        return save;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Field field = findById(id);
        for (Option o : field.getOptions()) {
            optionRepository.deleteById(o.getId());
        }
        for (Answer answer : answerService.findByFieldId(id)) {
            answerService.deleteById(answer.getId());
        }
        fieldRepository.deleteById(id);
    }

    @Override
    public Field webSocketGet(Field field) {
        if (field.getId() != null && field.getLabel().isEmpty()) {
            deleteById(field.getId());
            return field;
        } else if (field.getId() == null && !field.getLabel().isEmpty()) {
            return save(field);
        } else
            return update(field.getId(), field);
    }
}
