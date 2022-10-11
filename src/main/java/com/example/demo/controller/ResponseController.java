package com.example.demo.controller;

import com.example.demo.dto.interf.Marker;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.impl.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/responses")
public class ResponseController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ResponseService responseService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public Page<ResponseDTO> read(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
            , @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return responseService.findAll(PageRequest.of(page - 1, size));
    }

    @Validated({Marker.OnCreate.class})
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public ResponseDTO create(@Valid @RequestBody ResponseDTO response) {
        ResponseDTO save = responseService.save(response);
        simpMessagingTemplate.convertAndSend("/topic/portal", "CREATE_RESPONSE");
        return save;
    }

    @Validated({Marker.OnDelete.class})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") @Min(0) Long fieldId) {
        responseService.deleteById(fieldId);
    }
}
