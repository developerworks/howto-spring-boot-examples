package com.example.remoting;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface DnsService {

    @WebMethod
    String getName(String ip);
}
