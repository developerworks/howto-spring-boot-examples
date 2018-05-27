package com.example.springbootwebfluxsecuritydao.repository;

import com.example.springbootwebfluxsecuritydao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
