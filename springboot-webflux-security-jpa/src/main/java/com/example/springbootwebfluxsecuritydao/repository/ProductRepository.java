package com.example.springbootwebfluxsecuritydao.repository;

import com.example.springbootwebfluxsecuritydao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
