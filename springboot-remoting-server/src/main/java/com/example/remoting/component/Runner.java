package com.example.remoting.component;

import com.example.remoting.component.Email;
import com.example.remoting.config.JmsServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
public class Runner implements CommandLineRunner {

  @Autowired
  JmsTemplate jmsTemplate;

  @Override
  public void run(String... args) throws Exception {
    log.info("Sending a email");
    jmsTemplate.convertAndSend(JmsServerConfig.DNS_QUERY_QUEUE, new Email("info@example.com", "Hello"));
  }
}
