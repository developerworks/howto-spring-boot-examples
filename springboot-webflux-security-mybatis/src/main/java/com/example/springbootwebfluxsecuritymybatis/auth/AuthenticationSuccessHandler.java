package com.example.springbootwebfluxsecuritymybatis.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private ObjectMapper objectMapper;
    private URI location = URI.create("/");
    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("code", "200");
            map.put("message", "OK");
            requestCache.getRedirectUri(webFilterExchange.getExchange()).defaultIfEmpty(location).subscribe(s -> map.put("data", s.getPath()));
            byte[] bytes = objectMapper.writeValueAsBytes(map);
            DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(dataBuffer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }
}
