package com.prueba.ol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.ol.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
