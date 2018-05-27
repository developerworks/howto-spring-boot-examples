package com.example.springbootwebfluxsecuritydao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-webflux-auto-configuration
 * <p>
 * If you want to keep Spring Boot WebFlux features and you want to add additional WebFlux configuration,
 * you can add your own @Configuration class of type WebFluxConfigurer but without @EnableWebFlux.
 * <p>
 * If you want to take complete control of Spring WebFlux, you can add your own @Configuration annotated with @EnableWebFlux.
 * <p>
 * 如果想要保持 Spring Boot WebFlux 的功能,并且同时想添加额外的 WebFlux 配置. 可以创建一个实现 WebFluxConfigurer 接口的配置类, 并且不要使用 @EnableWebFlux
 * 如果想要完全控制 Spring WebFlux 的配置, 可以添加自定义的实现 WebFluxConfigurer 接口的配置类, 并且用 @EnableWebFlux 注解进行标注.
 */
@Configuration
public class WebfluxConfig implements WebFluxConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        CacheControl cc = CacheControl.empty();
//        cc.sMaxAge(3600 * 24, TimeUnit.SECONDS);
//        cc.cachePrivate();
//        cc.mustRevalidate();
//        cc.noTransform();
        registry
            .addResourceHandler("/static/**")
            .addResourceLocations("/static/");
    }
}