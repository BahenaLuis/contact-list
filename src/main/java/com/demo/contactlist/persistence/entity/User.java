package com.demo.contactlist.persistence.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;
}
