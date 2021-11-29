package com.demo.contactlist.exception;

import lombok.Data;

@Data
public class ValidationErrorDto {
    private String field;
    private String message;
}
