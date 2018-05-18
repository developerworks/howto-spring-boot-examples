//package com.example.remoting.service.impl;
//
//import com.example.remoting.service.DnsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import javax.jws.WebMethod;
//import javax.jws.WebService;
//
//@Service
//@Qualifier(value = "DnsServiceWsImpl")
//@WebService(
//    serviceName = "WsDnsService",
//    targetNamespace = "http://impl.service.remoting.example.com/",
//    endpointInterface = "com.example.remoting.service.DnsService"
//)
//
//public class DnsServiceWsImpl implements DnsService {
//    @Autowired
//    BusinessLogic businessLogic;
//
//    @WebMethod(action = "getName")
//    public String getName(String ip) {
//        return businessLogic.getName(ip);
//    }
//}
