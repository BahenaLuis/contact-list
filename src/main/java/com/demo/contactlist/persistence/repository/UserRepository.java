package com.demo.contactlist.persistence.repository;

import com.demo.contactlist.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
