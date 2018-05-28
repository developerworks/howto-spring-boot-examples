package com.example.springbootwebfluxsecuritydao.service.impl;

import com.example.springbootwebfluxsecuritydao.entity.User;
import com.example.springbootwebfluxsecuritydao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "userToUserDetails")
    private Converter<User, UserDetails> userUserDetailsConverter;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        UserDetails userDetails = userUserDetailsConverter.convert(userService.findByUsername(username));
        return Mono.just(userDetails);
    }
}
