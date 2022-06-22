package com.example.demo.repository;

import com.example.demo.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByIsActiveTrue();
}
