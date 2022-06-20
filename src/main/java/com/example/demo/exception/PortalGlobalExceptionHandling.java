package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PortalGlobalExceptionHandling {

    @ExceptionHandler
    public ResponseEntity<PortalIncorrectData> handleException(NoSuchPortalException exception) {
        PortalIncorrectData portalIncorrectData= new PortalIncorrectData();
        portalIncorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(portalIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PortalIncorrectData> handleException(Exception exception) {
        PortalIncorrectData portalIncorrectData = new PortalIncorrectData();
        portalIncorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(portalIncorrectData, HttpStatus.BAD_REQUEST);
    }
}
