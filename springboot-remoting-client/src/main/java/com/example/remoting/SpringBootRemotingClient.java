package com.example.remoting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

@SpringBootApplication(exclude = {JmsAutoConfiguration.class})
@Slf4j
public class SpringBootRemotingClient implements CommandLineRunner {

    @Autowired
    @Qualifier(value = "RmiDnsService")
    DnsService rmiDnsService;

    @Autowired
    @Qualifier(value = "HessianDnsService")
    DnsService hessianDnsService;

    @Autowired
    @Qualifier(value = "HttpInvokerDnsService")
    DnsService httpInvokerDnsService;

    @Autowired
    @Qualifier(value = "JaxWsDnsService")
    DnsService jaxWsDnsService;

    @Autowired
    @Qualifier(value = "JmsDnsService")
    DnsService jmsDnsService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRemotingClient.class);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("[RMI         ] DNS解析结果: {}", rmiDnsService.getName(""));
        log.info("[Hessian     ] DNS解析结果: {}", hessianDnsService.getName(""));
        log.info("[HTTP Invoker] DNS解析结果: {}", httpInvokerDnsService.getName(""));
        log.info("[JAX-WS      ] DNS解析结果: {}", jaxWsDnsService.getName(""));
        log.info("[JMS         ] DNS解析结果: {}", jmsDnsService.getName(""));
    }

}
