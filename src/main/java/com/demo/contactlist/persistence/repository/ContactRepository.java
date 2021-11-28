package com.demo.contactlist.persistence.repository;

import com.demo.contactlist.persistence.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Page<Contact> findByDeletedFalse(Pageable pageable);
    List<Contact> findByDeletedFalse();
}
