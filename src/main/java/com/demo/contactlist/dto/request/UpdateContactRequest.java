package com.demo.contactlist.dto.request;

import lombok.Data;

@Data
public class UpdateContactRequest {
    private String name;
    private Integer age;
    private String nickname;
    private String phone;
}
