package com.example.demospringintegration.config;

import com.example.demospringintegration.service.HessianDnsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianServerConfig {

    @Bean(name = "/HessianDnsService")
    HessianServiceExporter hessianDnsService(HessianDnsService hessianDnsService) {

        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setServiceInterface(HessianDnsService.class);
        exporter.setService(hessianDnsService);

        return exporter;
    }
}
