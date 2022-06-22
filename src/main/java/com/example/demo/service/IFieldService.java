package com.example.demo.service;

import com.example.demo.entity.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFieldService extends IService<Field, Long> {
    Page<Field> findAll(Pageable pageable);

    List<Field> findByIsActiveTrue();

    Field update(Long id, Field newField);

    Field webSocketGet (Field field);
}
