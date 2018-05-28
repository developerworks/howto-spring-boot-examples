package com.example.springbootwebfluxsecuritydao.exception;

import com.example.springbootwebfluxsecuritydao.web.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        Result result = new Result("AuthenticationException", exception.getMessage());
        log.error("登录失败: {}", exception.getMessage());
//        try {
//            String json = objectMapper.writeValueAsString(result);
//            response.writeWith(Mono.just(response.bufferFactory().wrap(json.getBytes())));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return Mono.empty();
    }
}
