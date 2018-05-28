package com.example.springbootwebfluxsecuritydao.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Slf4j
public class LoginWebFilter extends AuthenticationWebFilter {
    private final ServerCodecConfigurer serverCodecConfigurer;

    /**
     * Creates an instance
     *
     * @param authenticationManager the authentication manager to use
     * @param serverCodecConfigurer
     */
    public LoginWebFilter(ReactiveAuthenticationManager authenticationManager, ServerCodecConfigurer serverCodecConfigurer) {

        super(authenticationManager);
        this.serverCodecConfigurer = serverCodecConfigurer;

        setRequiresAuthenticationMatcher(
            ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login")
        );

        setAuthenticationConverter(new ServerBodyAuthenticationConverter(this.serverCodecConfigurer));

        setAuthenticationSuccessHandler(new StatusCodeAuthSuccessHandler());
    }

}
