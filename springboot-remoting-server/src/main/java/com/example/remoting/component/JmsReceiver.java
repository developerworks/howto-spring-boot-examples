//package com.example.remoting.component;
//
//import com.example.remoting.config.JmsServerConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class JmsReceiver {
//
//    @JmsListener(destination = JmsServerConfig.DNS_QUERY_QUEUE, containerFactory = "myFactory")
//    public void receiveMessage(Email email) {
//        log.info("Received <" + email + ">");
//    }
//}