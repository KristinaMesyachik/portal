package com.example.demo.repository;

import com.example.demo.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOptionRepository extends JpaRepository<Option, Long> {
}
