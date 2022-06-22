package com.example.demo.controller;

import com.example.demo.entity.Response;
import com.example.demo.service.impl.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/responses")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public Page<Response> read(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
            , @RequestParam(value = "size", required = false, defaultValue = "3") Integer size
    ) {
        return responseService.findAll(PageRequest.of(page - 1, size));
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public Response create(@RequestBody Response response) {
        return responseService.save(response);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") Long fieldId) {
        responseService.deleteById(fieldId);
    }
}
