package com.example.springbootwebfluxsecuritydao.converter;

import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.Function;

public class ServerBodyAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    private final ResolvableType usernamePasswordType = ResolvableType.forClass(UsernamePasswordDto.class);

    private ServerCodecConfigurer serverCodecConfigurer;

    public ServerBodyAuthenticationConverter(ServerCodecConfigurer serverCodecConfigurer) {
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        final ServerHttpRequest request = exchange.getRequest();

        MediaType contentType = request.getHeaders().getContentType();

        if (contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            return serverCodecConfigurer.getReaders().stream()
                .filter(reader -> reader.canRead(this.usernamePasswordType, MediaType.APPLICATION_JSON))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No JSON reader for UsernamePasswordContent"))
                .readMono(this.usernamePasswordType, request, Collections.emptyMap())
                .cast(UsernamePasswordDto.class)
                .map(o -> new UsernamePasswordAuthenticationToken(o.getUsername(), o.getPassword()));
        } else {
            return Mono.empty();
        }
    }
}
