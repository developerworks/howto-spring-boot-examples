package com.example.demormiclienttest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class BeanConfig {
    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://127.0.0.1:1099/RmiDnsService");
        factoryBean.setServiceInterface(RmiDnsService.class);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean helloClient() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl("http://127.0.0.1:9090/HessianDnsService");
        factoryBean.setServiceInterface(HessianDnsService.class);
        return factoryBean;
    }
}
