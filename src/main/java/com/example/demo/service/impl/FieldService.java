package com.example.demo.service.impl;

import com.example.demo.entity.Field;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IFieldRepository;
import com.example.demo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FieldService implements IService<Field, Long> {

    @Autowired
    private IFieldRepository fieldRepository;

    public Page<Field> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable);
    }

    public Field findById(Long id) {
        Optional<Field> fieldOptional = fieldRepository.findById(id);
        if (fieldOptional.isEmpty()) {
            throw new NoSuchPortalException("There is no field with ID = " + id + "in database");
        }
        return fieldOptional.get();
    }

    public void delete(Long id) {
        fieldRepository.deleteById(id);
    }
}
