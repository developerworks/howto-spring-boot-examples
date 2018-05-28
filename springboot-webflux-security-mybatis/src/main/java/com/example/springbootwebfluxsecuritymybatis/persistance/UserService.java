package com.example.springbootwebfluxsecuritymybatis.persistance;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDetails> findByUsername(String username);
}
