package com.abdrabo.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exc,
                                                                 WebRequest request){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, exc.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exc,
                                                                 WebRequest request){
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exc,
                                                                 WebRequest request){
        // Log the exception
        // exc.printStackTrace();
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
