package com.wookie.lukapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ImpossibleEventTimeFramesException.class)
    public ResponseEntity<?> handleImpossibleTimeFramesException
            (ImpossibleEventTimeFramesException exception, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), exception.getFailedEventsNames());
        return new ResponseEntity(errorDetails, HttpStatus.EXPECTATION_FAILED);
    }
}
