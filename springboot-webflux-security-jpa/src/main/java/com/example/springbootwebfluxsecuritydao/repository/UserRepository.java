package com.example.springbootwebfluxsecuritydao.repository;

import com.example.springbootwebfluxsecuritydao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}