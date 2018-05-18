package com.example.remoting.service.impl;

import com.example.remoting.service.DnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "DnsServiceHessainImpl")
public class DnsServiceHessainImpl implements DnsService {
    @Autowired
    BusinessLogic businessLogic;

    @Override
    public String getName(String ip) {
        return businessLogic.getName(ip);
    }
}
