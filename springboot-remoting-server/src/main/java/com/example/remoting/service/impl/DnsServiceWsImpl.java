package com.example.remoting.service.impl;

import com.example.remoting.Const;
import com.example.remoting.service.DnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service
@Qualifier(value = "DnsServiceWsImpl")
@WebService(serviceName = "WsDnsService", targetNamespace = Const.NAMESPACE, endpointInterface = "com.example.remoting.service.DnsService")
@Slf4j
public class DnsServiceWsImpl implements DnsService {
    @Autowired
    BusinessLogic businessLogic;

    @Override
    public String getName(String ip) {
        String name = businessLogic.getName(ip);
        log.info("JAX-WS remoting method called, return value: {}", name);

        return name;
    }
}
