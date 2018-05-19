package com.example.remoting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication(exclude = {JmsAutoConfiguration.class})
@Slf4j
@EnableScheduling
public class SpringBootRemotingClient implements CommandLineRunner {

    @Autowired
    @Qualifier(value = "Rmi")
    DnsService rmi;

    @Autowired
    @Qualifier(value = "Hessian")
    DnsService hessian;

    @Autowired
    @Qualifier(value = "HttpInvoker")
    DnsService httpInvoker;

    @Autowired
    @Qualifier(value = "JaxWs")
    DnsService jaxWs;

    @Autowired
    @Qualifier(value = "jmsInvoker")
    DnsService jmsInvoker;

    @Autowired
    @Qualifier(value = "Amqp")
    DnsService amqp;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRemotingClient.class);
    }

    @Scheduled(fixedRate = 5000L)
    public void query() {
        log.info("[RMI         ] DNS Result: {}", rmi.getName(""));
        log.info("[Hessian     ] DNS Result: {}", hessian.getName(""));
        log.info("[HTTP Invoker] DNS Result: {}", httpInvoker.getName(""));
        log.info("[JAX-WS      ] DNS Result: {}", jaxWs.getName(""));
        log.info("[JMS         ] DNS Result: {}", jmsInvoker.getName(""));
        log.info("[AMQP        ] DNS Result: {}", amqp.getName(""));
    }

    @Override
    public void run(String... args) throws Exception {
        query();
    }

}
