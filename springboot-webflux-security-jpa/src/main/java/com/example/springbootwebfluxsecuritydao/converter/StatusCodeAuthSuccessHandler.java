package com.example.springbootwebfluxsecuritydao.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

@Slf4j
public class StatusCodeAuthSuccessHandler implements ServerAuthenticationSuccessHandler {

    private HttpStatus statusCode = HttpStatus.OK;

    public StatusCodeAuthSuccessHandler statusCode(HttpStatus statusCode) {
        log.info("构造: StatusCodeAuthSuccessHandler");
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Authorities: {}", authentication.getAuthorities());
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Credentials: {}", authentication.getCredentials());
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Details: {}", authentication.getDetails());
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Principal: {}", authentication.getPrincipal());
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Name: {}", authentication.getName());
        log.info("custom AuthenticationSuccessHandler -------------------------> " + authentication.getName());
        webFilterExchange.getExchange().getResponse().setStatusCode(statusCode);
        return Mono.empty();
    }
}
