package com.example.remoting.config;

import com.example.remoting.service.DnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianServerConfig {

    @Autowired
    @Qualifier(value = "DnsServiceHessainImpl")
    DnsService dnsService;

    @Bean(name = "/HessianDnsService")
    HessianServiceExporter hessianDnsService() {

        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setServiceInterface(DnsService.class);
        exporter.setService(dnsService);

        return exporter;
    }
}
