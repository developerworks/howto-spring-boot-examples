package com.example.remoting;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(targetNamespace="http://www.example.com/")
@SOAPBinding(style = Style.DOCUMENT)
public interface DnsService {
    @WebMethod
    String getName(String ip);
}
