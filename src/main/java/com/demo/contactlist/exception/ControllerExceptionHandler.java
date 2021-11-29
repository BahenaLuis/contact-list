package com.demo.contactlist.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    private Environment environment;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto();
        message.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        message.setTimestamp(new Date());
        message.setMessage(ex.getMessage());
        message.setDescription(request.getDescription(false));
        return new ResponseEntity<ErrorMessageDto>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ValidationErrorDto>> validateFields(BindingResult ex) {
        List<ValidationErrorDto> validationErrorList = new ArrayList<>();
        ex.getFieldErrors().forEach(fieldError -> {
            ValidationErrorDto validationErrorDto = new ValidationErrorDto();
            validationErrorDto.setField(fieldError.getField());
            validationErrorDto.setMessage(fieldError.getDefaultMessage());
            validationErrorList.add(validationErrorDto);
        });
        return new ResponseEntity<List<ValidationErrorDto>>(validationErrorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto();
        message.setStatusCode(HttpStatus.NOT_FOUND.value());
        message.setTimestamp(new Date());
        message.setMessage(ex.getMessage());
        message.setDescription(request.getDescription(false));
        return new ResponseEntity<ErrorMessageDto>(message, HttpStatus.NOT_FOUND);
    }
}
