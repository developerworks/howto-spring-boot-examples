package com.example.springbootwebfluxsecuritydao.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return null;
    }
}
