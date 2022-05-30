package com.example.demo.repository;

import com.example.demo.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFieldRepository extends JpaRepository<Field, Long> {
}
