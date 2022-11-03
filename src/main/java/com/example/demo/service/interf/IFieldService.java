package com.example.demo.service.interf;

import com.example.demo.dto.FieldDTO;
import com.example.demo.entity.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFieldService extends IService<FieldDTO, Long, Field> {
    Page<FieldDTO> findAll(Pageable pageable);

    List<FieldDTO> findByIsActiveTrue();

    FieldDTO update(Long id, FieldDTO newField);

    List<FieldDTO> findAll();
}
