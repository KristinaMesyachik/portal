package com.example.demo.service;

import com.example.demo.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IResponseService extends IService<Response, Long>{
    Page<Response> findAll(Pageable pageable);
}
