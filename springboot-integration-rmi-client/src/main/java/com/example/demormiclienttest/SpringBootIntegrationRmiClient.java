package com.example.demormiclienttest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
@Slf4j
public class SpringBootIntegrationRmiClient implements CommandLineRunner, ApplicationContextAware {

    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootIntegrationRmiClient.class);
    }

    @Override
    public void run(String... args) throws Exception {
        DnsService dnsService = applicationContext.getBean(DnsService.class);
        log.info("DNS解析结果: {}", dnsService.getName(""));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
