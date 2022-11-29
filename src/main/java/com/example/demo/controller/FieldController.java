package com.example.demo.controller;

import com.example.demo.dto.FieldDTO;
import com.example.demo.dto.interf.Marker;
import com.example.demo.service.impl.FieldService;
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
import java.util.List;

@Api(description = "Endpoints for Creating, Retrieving, Updating and Deleting of Fields.",
        tags = {"field"})
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @ApiOperation(value = "Find fields by number and size page (need authenticated)", tags = {"field"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Page.class)})
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public Page<FieldDTO> read(
            @ApiParam("Page number, default is 1")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
            ,
            @ApiParam("Page size, default is 10")
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return fieldService.findAll(PageRequest.of(page - 1, size));
    }

    @ApiOperation(value = "Find fields", tags = {"field"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = List.class)})
    @RequestMapping(value = {"/all"}, method = RequestMethod.GET)
    public List<FieldDTO> findAll() {
        return fieldService.findAll();
    }

    @ApiOperation(value = "Find fields by active", tags = {"field"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = List.class)})
    @RequestMapping(value = {"/active"}, method = RequestMethod.GET)
    public List<FieldDTO> findByIsActiveTrue() {
        return fieldService.findByIsActiveTrue();
    }

    @ApiOperation(value = "Find field by id (need authenticated)", tags = {"field"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = FieldDTO.class),
            @ApiResponse(code = 404, message = "field not found")})
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public FieldDTO findById(
            @ApiParam("Id of the field to be obtained. Cannot be empty.")
            @PathVariable(name = "id") @Min(0) Long fieldId) {
        return fieldService.findById(fieldId);
    }

    @ApiOperation(value = "Add a new field (need authenticated)", tags = {"field"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Field created", response = FieldDTO.class),
            @ApiResponse(code = 400, message = "Invalid input")})
    @Validated({Marker.OnCreate.class})
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public FieldDTO create(
            @ApiParam("Field to add. Cannot null or empty.")
            @Valid @RequestBody FieldDTO field) {
        FieldDTO save = fieldService.save(field);
        simpMessagingTemplate.convertAndSend("/topic/portal", "CREATED");
        return save;
    }

    @ApiOperation(value = "Update an existing field (need authenticated)", tags = {"field"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = FieldDTO.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Field not found"),
            @ApiResponse(code = 405, message = "Validation exception")})
    @Validated({Marker.OnUpdate.class})
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
    public FieldDTO update(
            @ApiParam("Id of the field to be update. Cannot be empty.")
            @PathVariable(name = "id") @Min(0) Long fieldId,
            @ApiParam("Field to update. Cannot null or empty.")
            @Valid @RequestBody FieldDTO field) {
        FieldDTO update = fieldService.update(fieldId, field);
        simpMessagingTemplate.convertAndSend("/topic/portal", "UPDATE");
        return update;
    }

    @ApiOperation(value = "Deletes a field by id (need authenticated)", tags = { "field" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 404, message = "Field not found") })
    @Validated({Marker.OnDelete.class})
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public void delete(
            @ApiParam("Id of the field to be delete. Cannot be empty.")
            @PathVariable(name = "id") @Min(0) Long fieldId) {
        fieldService.deleteById(fieldId);
        simpMessagingTemplate.convertAndSend("/topic/portal", "DELETE");
    }
}
