//package com.example.remoting.config;
//
//import com.example.remoting.service.DnsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.remoting.jaxws.SimpleHttpServerJaxWsServiceExporter;
//import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;
//
//@Configuration
//public class WebServiceConfig {
//
//  @Autowired
//  @Qualifier(value = "DnsServiceWsImpl")
//  DnsService dnsService;
//
////  @Bean
////  public SimpleJaxWsServiceExporter simpleJaxWsServiceExporter() {
////    SimpleJaxWsServiceExporter exporter = new SimpleJaxWsServiceExporter();
////    exporter.setBaseAddress("http://localhost:9090/services/");
////    return exporter;
////  }
//
//  @Bean
//  public SimpleHttpServerJaxWsServiceExporter simpleHttpServerJaxWsServiceExporter() {
//    SimpleHttpServerJaxWsServiceExporter exporter = new SimpleHttpServerJaxWsServiceExporter();
//    exporter.setPort(22222);
//    exporter.setBasePath("/services/");
//    return exporter;
//  }
//}
