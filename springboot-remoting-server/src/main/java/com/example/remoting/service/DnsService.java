package com.example.remoting.service;

import com.example.remoting.Const;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(targetNamespace = Const.NAMESPACE)
@SOAPBinding(style = Style.DOCUMENT)
public interface DnsService {
    @WebMethod
    String getName(String ip);
}
