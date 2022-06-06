package com.example.demo.service.impl;

import com.example.demo.entity.Field;
import com.example.demo.exception.NoSuchPortalException;
import com.example.demo.repository.IFieldRepository;
import com.example.demo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FieldService implements IService<Field, Long> {

    @Autowired
    private IFieldRepository fieldRepository;

    public Page<Field> findAll(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Field> collect = fieldRepository.findAll();
        List<Field> list;
        if (collect.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, collect.size());
            list = collect.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage,
                pageSize),
                collect.size());
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
