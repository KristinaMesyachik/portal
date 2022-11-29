package com.example.demo.controller;

import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Api(description = "Endpoints of WebSocket.",
        tags = {"WebSocket"})
@Controller
public class WebSocketController {

    @ApiOperation(value = "Listening to the socket end point", tags = {"WebSocket"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation")})
    @MessageMapping("/portal")
    public void qwerty(
            @ApiParam("String for action. Cannot null or empty.")
                    String e) {
    }

    @ApiOperation(value = "Send msg to the socket end point", tags = {"WebSocket"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation")})
    @SendTo("/topic/portal")
    public String greeting(
            @ApiParam("String for action. Cannot null or empty.")
                    String e) {
        return e;
    }
}
