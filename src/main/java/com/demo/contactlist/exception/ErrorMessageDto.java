package com.demo.contactlist.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessageDto {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
