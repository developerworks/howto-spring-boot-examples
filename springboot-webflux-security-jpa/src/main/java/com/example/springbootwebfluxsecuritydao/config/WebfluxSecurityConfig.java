package com.example.springbootwebfluxsecuritydao.config;

import com.example.springbootwebfluxsecuritydao.converter.LoginWebFilter;
import com.example.springbootwebfluxsecuritydao.exception.AuthenticationFailureHandler;
import com.example.springbootwebfluxsecuritydao.exception.AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
public class WebfluxSecurityConfig {

    /**
     * 密码编码器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    ////////////////////////////////////////////////
    // 通过 ReactiveAuthenticationManagerAdapter 去适配Spring MVC方式的 AuthenticationManager
    ////////////////////////////////////////////////

//    /**
//     * 这个DAO认证提供者是用于Sring MVC的, 而不是WebFlux
//     *
//     * @param passwordEncoder
//     * @param userDetailsService
//     * @return
//     */
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return daoAuthenticationProvider;
//    }

    ////////////////////////////////////////////////
    // 直接的 Reactive 配置方式
    ////////////////////////////////////////////////

    /**
     * 定义认证管理器
     * <p>
     * 认证管理器需要密码编码器和用户服务的支持
     *
     * @param passwordEncoder    密码编码器
     * @param userDetailsService 用户明细服务
     * @return ReactiveAuthenticationManager
     */
    @Bean
    public ReactiveAuthenticationManager authenticationManager(
        PasswordEncoder passwordEncoder,
        ReactiveUserDetailsService userDetailsService
    ) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    /**
     * 认证过滤器需要使用认证管理器作为参数实例化
     * <p>
     * - Custom Spring WebFlux AuthenticationWebFilter
     * https://gist.github.com/itzg/dc07a711295b4c5c1d1f93322df9634b
     * <p>
     * - https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/server/authentication/AuthenticationWebFilter.html
     * <p>
     * A WebFilter that performs authentication of a particular request. An outline of the logic:
     * - A request comes in and if it does not match setRequiresAuthenticationMatcher(ServerWebExchangeMatcher),
     * then this filter does nothing and the WebFilterChain is continued. If it does match then...
     * <p>
     * - An attempt to convert the ServerWebExchange into an Authentication is made. If the result is empty,
     * then the filter does nothing more and the WebFilterChain is continued. If it does create an Authentication...
     * <p>
     * - The ReactiveAuthenticationManager specified in AuthenticationWebFilter(ReactiveAuthenticationManager)
     * is used to perform authentication.
     * <p>
     * - If authentication is successful, ServerAuthenticationSuccessHandler is invoked and the authentication is set
     * on ReactiveSecurityContextHolder, else ServerAuthenticationFailureHandler is invoked
     *
     * @param authenticationManager 认证管理器
     * @return AuthenticationWebFilter 认证过滤器
     */
    @Bean
    AuthenticationWebFilter authenticationWebFilter(ReactiveAuthenticationManager authenticationManager, ServerCodecConfigurer serverCodecConfigurer) {

        LoginWebFilter filter = new LoginWebFilter(authenticationManager, serverCodecConfigurer);
//        filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login"));
//        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
//        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler());
        return filter;
    }

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 把认证过滤器添加到过滤器链条中
     *
     * @param http                    服务器HTTP安全类
     * @param authenticationWebFilter 认证过滤器
     * @return SecurityWebFilterChain
     */
    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(
        ServerHttpSecurity http,
        AuthenticationWebFilter authenticationWebFilter
    ) {
        return http
            .csrf().disable()
            .httpBasic().disable()
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .authorizeExchange()
            .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .pathMatchers("/", "/login", "/logout").permitAll()
            .pathMatchers("/static/**", "/resources/**", "/resources/public/**").permitAll()
            .pathMatchers("/admin/**").hasAuthority("ADMIN")
            .pathMatchers("/product/show/*", "/products", "/product/show/*", "/console/*", "/h2-console/**").permitAll()
            .anyExchange()
            .authenticated()
            .and().formLogin()
//            .authenticationSuccessHandler(authenticationSuccessHandler)
            .authenticationFailureHandler(authenticationFailureHandler)
            .and().build();
    }

}

