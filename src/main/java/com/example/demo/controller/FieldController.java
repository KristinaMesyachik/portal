package com.example.demo.controller;

import com.example.demo.entity.Field;
import com.example.demo.service.impl.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public Page<Field> read(
//            Model model
//    public String findAll(Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
            , @RequestParam(value = "size", required = false, defaultValue = "2") Integer size
    ) {
//        Page<Field> fieldsPage = fieldService.findAll(PageRequest.of(page - 1, size));
        return fieldService.findAll(PageRequest.of(page - 1, size));
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public Field update(@PathVariable(name = "id") Long fieldId) {
        return fieldService.findById(fieldId);
    }


    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public Field create(@RequestBody Field field) {
        return fieldService.create(field);
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
