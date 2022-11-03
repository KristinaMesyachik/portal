package com.example.demo.service.interf;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IResponseService extends IService<ResponseDTO, Long, Response> {
    Page<ResponseDTO> findAll(Pageable pageable);
}
