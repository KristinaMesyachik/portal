package com.example.demo.controller;

import com.example.demo.entity.Field;
import com.example.demo.service.impl.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private FieldService fieldService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Field greeting(Field field) throws Exception {
        return fieldService.webSocketGet(field);
    }
}
