package com.example.remoting.config;

import com.example.remoting.service.DnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@Configuration
public class HttpInvokerConfig {

    @Autowired
    @Qualifier("DnsServiceHttpInvokerImpl")
    DnsService dnsService;

    @Bean(name = "/HttpInvokerDnsService")
    HttpInvokerServiceExporter httpInvokerServiceExporter() {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(dnsService);
        exporter.setServiceInterface(DnsService.class);
        return exporter;
    }

}
