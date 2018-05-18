package com.example.demormiclienttest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

@SpringBootApplication
@Slf4j
public class SpringBootIntegrationRmiClient implements CommandLineRunner {

    @Autowired
    RmiDnsService rmiDnsService;
    @Autowired
    HessianDnsService hessianDnsService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootIntegrationRmiClient.class);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("[RMI    ] DNS解析结果: {}", rmiDnsService.getName(""));
        log.info("[Hessian] DNS解析结果: {}", hessianDnsService.getName(""));
    }

}
