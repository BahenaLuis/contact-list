package com.demo.contactlist.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateContactRequest {

    @NotNull
    @NotEmpty
    private String name;

    private Integer age;

    private String nickname;

    @NotNull
    @NotEmpty
    private String phone;
}
