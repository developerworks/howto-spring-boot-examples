package com.example.remoting.config;

import com.example.remoting.service.DnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RmiServerConfig {

    @Autowired
    @Qualifier(value = "DnsServiceRmiImpl")
    DnsService dnsService;

    @Bean
    MyRmiServiceExporter exporter() {
        MyRmiServiceExporter exporter = new MyRmiServiceExporter();
        exporter.setServiceInterface(DnsService.class);
        exporter.setService(dnsService);
        exporter.setServiceName(DnsService.class.getSimpleName());
        exporter.setRegistryHost("127.0.0.1");
        exporter.setRegistryPort(1099);
        return exporter;
    }
}
