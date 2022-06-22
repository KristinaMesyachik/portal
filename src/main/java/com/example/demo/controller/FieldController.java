package com.example.demo.controller;

import com.example.demo.entity.Field;
import com.example.demo.service.impl.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public Page<Field> read(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
            , @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return fieldService.findAll(PageRequest.of(page - 1, size));
    }

    @RequestMapping(value = {"/active"}, method = RequestMethod.GET)
    public List<Field> findByIsActiveTrue() {
        return fieldService.findByIsActiveTrue();
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public Field findById(@PathVariable(name = "id") Long fieldId) {
        return fieldService.findById(fieldId);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public Field create(@RequestBody Field field) {
        return fieldService.save(field);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
    public Field update(@PathVariable(name = "id") Long fieldId, @RequestBody Field field) {
        return fieldService.update(fieldId, field);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") Long fieldId) {
        fieldService.deleteById(fieldId);
    }
}
