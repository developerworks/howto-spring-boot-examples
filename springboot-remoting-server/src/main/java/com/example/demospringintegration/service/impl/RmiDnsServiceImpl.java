package com.example.demospringintegration.service.impl;

import com.example.demospringintegration.service.RmiDnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RmiDnsServiceImpl implements RmiDnsService {
    @Autowired
    BusinessLogic businessLogic;

    @Override
    public String getName(String ip) {

        return businessLogic.getName(ip);
    }
}
