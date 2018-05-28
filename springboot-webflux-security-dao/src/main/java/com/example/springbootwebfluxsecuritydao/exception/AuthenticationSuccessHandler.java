package com.example.springbootwebfluxsecuritydao.exception;

import com.example.springbootwebfluxsecuritydao.web.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * https://github.com/raphaelDL/spring-webflux-security-jwt/blob/master/src/main/java/io/rapha/spring/reactive/security/auth/WebFilterChainServerJWTAuthenticationSuccessHandler.java
 */
@Slf4j
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private Mono<Void> ret;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Authorities: {}", authentication.getAuthorities());
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Credentials: {}", authentication.getCredentials());
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Details: {}", authentication.getDetails());
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Principal: {}", authentication.getPrincipal());
        log.info("custom AuthenticationSuccessHandler -------------------------> Authentication Name: {}", authentication.getName());
        log.info("custom AuthenticationSuccessHandler -------------------------> " + authentication.getName());
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
//        Result result = new Result("0", "OK");
//        try {
//            String json = objectMapper.writeValueAsString(result);
//            response.writeWith(Mono.just(response.bufferFactory().wrap(json.getBytes())));
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return webFilterExchange.getChain().filter(exchange);
    }
}
