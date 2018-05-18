package com.example.remoting.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface DnsService {
    String getName(String ip);
}
