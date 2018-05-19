package com.example.remoting.config;

import com.example.remoting.service.DnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.SimpleHttpServerJaxWsServiceExporter;

@Configuration
public class WebServiceConfig {

    @Autowired
    @Qualifier(value = "DnsServiceWsImpl")
    DnsService dnsService;

    /**
     * 使用嵌入的 Http Server
     *
     * @return
     */
//  @Bean
//  public SimpleJaxWsServiceExporter simpleJaxWsServiceExporter() {
//    SimpleJaxWsServiceExporter exporter = new SimpleJaxWsServiceExporter();
//    exporter.setBaseAddress(Const.BASE_ADDRESS);
//    return exporter;
//  }

    /**
     * 启动一个独立的Http Server
     */
    @Bean
    public SimpleHttpServerJaxWsServiceExporter simpleHttpServerJaxWsServiceExporter() {
        SimpleHttpServerJaxWsServiceExporter exporter = new SimpleHttpServerJaxWsServiceExporter();
        exporter.setPort(9091);
        exporter.setBasePath("/jaxws/");
        return exporter;
    }
}
