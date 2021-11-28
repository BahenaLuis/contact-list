package com.demo.contactlist.persistence.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    @NotNull
    private String name;

    private Integer age;

    private String nickname;

    @NotNull
    private String phone;

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;
}
