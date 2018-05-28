package com.example.springbootwebfluxsecuritymybatis.config;

import com.example.springbootwebfluxsecuritymybatis.auth.AuthenticationFailureHandler;
import com.example.springbootwebfluxsecuritymybatis.auth.AuthenticationSuccessHandler;
import com.example.springbootwebfluxsecuritymybatis.auth.ReactiveUserDetailsServiceMybatisImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    private AuthenticationFailureHandler failureHandler;
    private AuthenticationSuccessHandler successHandler;
    private ReactiveUserDetailsService reactiveUserDetailsService;

    @Autowired
    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    @Autowired
    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Autowired
    public void setReactiveUserDetailsService(ReactiveUserDetailsServiceMybatisImpl reactiveUserDetailsService) {
        this.reactiveUserDetailsService = reactiveUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("hashed password for 'test' user: {}", encoder.encode("test"));
        return encoder;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity.authorizeExchange()
            .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .anyExchange().authenticated()
            .and()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin()
            .authenticationFailureHandler(failureHandler)
            .authenticationSuccessHandler(successHandler);

        return httpSecurity.build();

    }
}
