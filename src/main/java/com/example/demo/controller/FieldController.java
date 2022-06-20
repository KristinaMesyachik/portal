package com.example.demo.controller;

import com.example.demo.entity.Field;
import com.example.demo.service.impl.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/api/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
//    public Page<Field> findAll(Model model
    public String findAll(Model model
            , @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
            , @RequestParam(value = "size", required = false, defaultValue = "3") Integer size
    ) {
        Page<Field> fieldsPage = fieldService.findAll(PageRequest.of(page - 1, size));
        model.addAttribute("fieldsPage", fieldsPage);
        int totalPages = fieldsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "field";
//        return fieldsPage;
    }


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/addPriceList"}, method = RequestMethod.GET)
    public String showAddPriceListPage(Model model) {
        Field field = new Field();
        model.addAttribute("field", field);
        return "addField";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "fieldId") Long fieldId, Model model) {
        Field field = fieldService.findById(fieldId);
        model.addAttribute("field", field);
        return "addField";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam(name = "fieldId") Long fieldId) {
        fieldService.delete(fieldId);
        return "redirect:/api/fields/";
    }
}
