package com.example.demo.controller;

import com.example.demo.dto.FieldDTO;
import com.example.demo.dto.interf.Marker;
import com.example.demo.service.impl.FieldService;
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

@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public Page<FieldDTO> read(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
            , @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return fieldService.findAll(PageRequest.of(page - 1, size));
    }

    @RequestMapping(value = {"/all"}, method = RequestMethod.GET)
    public List<FieldDTO> findAll() {
        return fieldService.findAll();
    }

    @RequestMapping(value = {"/active"}, method = RequestMethod.GET)
    public List<FieldDTO> findByIsActiveTrue() {
        return fieldService.findByIsActiveTrue();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public FieldDTO findById(@PathVariable(name = "id") @Min(0) Long fieldId) {
        return fieldService.findById(fieldId);
    }

    @Validated({Marker.OnCreate.class})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public FieldDTO create(@Valid @RequestBody FieldDTO field) {
        FieldDTO save = fieldService.save(field);
        simpMessagingTemplate.convertAndSend("/topic/portal", "CREATED");
        return save;
    }

    @Validated({Marker.OnUpdate.class})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
    public FieldDTO update(@PathVariable(name = "id") @Min(0) Long fieldId, @Valid @RequestBody FieldDTO field) {
        FieldDTO update = fieldService.update(fieldId, field);
        simpMessagingTemplate.convertAndSend("/topic/portal", "UPDATE");
        return update;
    }

    @Validated({Marker.OnDelete.class})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") @Min(0) Long fieldId) {
        fieldService.deleteById(fieldId);
        simpMessagingTemplate.convertAndSend("/topic/portal", "DELETE");
    }
}
