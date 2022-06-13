package com.example.demo.controller;

import com.example.demo.entity.Field;
import com.example.demo.entity.Response;
import com.example.demo.service.impl.FieldService;
import com.example.demo.service.impl.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/responses")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public List<Response> read() {
        return responseService.findAll();
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public Response create(@RequestBody Response response) {
        return responseService.create(response);
    }
}
