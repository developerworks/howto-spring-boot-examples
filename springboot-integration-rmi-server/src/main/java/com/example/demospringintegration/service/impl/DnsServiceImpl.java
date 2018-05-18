package com.example.demospringintegration.service.impl;

import com.example.demospringintegration.service.DnsService;
import org.springframework.stereotype.Service;

@Service
public class DnsServiceImpl implements DnsService {
    @Override
    public String getName(String ip) {
        return "www.yumi.app";
    }
}
