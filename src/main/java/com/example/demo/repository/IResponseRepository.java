package com.example.demo.repository;

import com.example.demo.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IResponseRepository extends JpaRepository<Response, Long> {
}
