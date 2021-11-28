package com.demo.contactlist.dto;

import lombok.Data;

@Data
public class ContactDto {
    private Integer id;
    private String name;
    private Integer age;
    private String nickname;
    private String phone;
    private String userId;
    private Boolean deleted;
}
