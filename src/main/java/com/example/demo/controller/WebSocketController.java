package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/portal")
    public void qwerty(String e) {
    }

    @SendTo("/topic/portal")
    public String greeting(String e) {
        return e;
    }
}
