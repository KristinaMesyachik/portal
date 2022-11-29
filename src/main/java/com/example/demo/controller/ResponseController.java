package com.example.demo.controller;

import com.example.demo.dto.interf.Marker;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.impl.ResponseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Api(description = "Endpoints for Creating, Retrieving and Deleting of Responses.",
        tags = {"response"})
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/responses")
public class ResponseController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ResponseService responseService;

    @ApiOperation(value = "Find responses by number and size page (need authenticated)", tags = {"response"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Page.class)
    }
    )
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public Page<ResponseDTO> read(
            @ApiParam("Page number, default is 1")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
            ,
            @ApiParam("Page size, default is 10")
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return responseService.findAll(PageRequest.of(page - 1, size));
    }

    @ApiOperation(value = "Add a new response (need authenticated)", tags = {"response"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Response created", response = ResponseDTO.class),
            @ApiResponse(code = 400, message = "Invalid input")})
    @Validated({Marker.OnCreate.class})
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public ResponseDTO create(
            @ApiParam("Response to add. Cannot null or empty.")
            @Valid @RequestBody ResponseDTO response) {
        ResponseDTO save = responseService.save(response);
        simpMessagingTemplate.convertAndSend("/topic/portal", "CREATE_RESPONSE");
        return save;
    }

    @ApiOperation(value = "Deletes a response by id (need authenticated)", tags = { "response" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 404, message = "Response not found") })
    @Validated({Marker.OnDelete.class})
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public void delete(
            @ApiParam("Id of the response to be delete. Cannot be empty.")
            @PathVariable(name = "id") @Min(0) Long fieldId) {
        responseService.deleteById(fieldId);
    }
}
