package com.example.demormiclienttest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class BeanConfig {
    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://127.0.0.1:1099/DnsService");
        rmiProxyFactory.setServiceInterface(DnsService.class);
        return rmiProxyFactory;
    }
}
