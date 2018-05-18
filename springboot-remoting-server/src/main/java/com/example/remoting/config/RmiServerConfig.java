package com.example.remoting.config;

import com.example.remoting.service.DnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
@Slf4j
public class RmiServerConfig {

    @Autowired
    @Qualifier(value = "DnsServiceRmiImpl")
    DnsService dnsService;

    @Bean
    RmiServiceExporter exporter() {

        RmiServiceExporter exporter = new RmiServiceExporter();
        // 接口
        exporter.setServiceInterface(DnsService.class);
        // 实现
        exporter.setService(dnsService);
        // 服务名称
        exporter.setServiceName(DnsService.class.getSimpleName());
        log.info("Rmi service name: {}", DnsService.class.getSimpleName());
//        exporter.setRegistryHost("127.0.0.1");
        // 端口
        exporter.setRegistryPort(1099);

        return exporter;
    }
}
