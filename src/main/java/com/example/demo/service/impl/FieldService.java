package com.example.demo.service.impl;

import com.example.demo.entity.Field;
import com.example.demo.entity.Option;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IFieldRepository;
import com.example.demo.repository.IOptionRepository;
import com.example.demo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FieldService implements IService<Field, Long> {

    @Autowired
    private IFieldRepository fieldRepository;

    @Autowired
    private IOptionRepository optionRepository;

    public Page<Field> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable);
    }

    public List<Field> findAll() {
        return fieldRepository.findAll();
    }

    public Field create(Field field) {
        Field save = fieldRepository.save(field);
        for (Option option : save.getOptions()) {
            Optional<Option> byId = optionRepository.findById(option.getId());
            byId.get().setFieldId(save.getId());
            optionRepository.save(byId.get());
        }
        return save;
    }

    public Field findById(Long id) {
        Optional<Field> fieldOptional = fieldRepository.findById(id);
        if (fieldOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no field with ID = " + id + "in database");
        }
        return fieldOptional.get();
    }

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

    public void deleteById(Long id) {
        Field field = findById(id);
        for (Option o : field.getOptions()) {
            optionRepository.deleteById(o.getId());
        }
        fieldRepository.deleteById(id);
    }
}
