package com.aafaque.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aafaque.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}