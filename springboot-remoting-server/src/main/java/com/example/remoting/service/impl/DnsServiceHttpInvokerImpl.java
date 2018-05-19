package com.example.remoting.service.impl;

import com.example.remoting.service.DnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "DnsServiceHttpInvokerImpl")
@Slf4j
public class DnsServiceHttpInvokerImpl implements DnsService {
    @Autowired
    BusinessLogic businessLogic;

    @Override
    public String getName(String ip) {
        String name = businessLogic.getName(ip);
        log.info("HttpInvoker remoting method called, return value: {}", name);

        return name;
    }
}
